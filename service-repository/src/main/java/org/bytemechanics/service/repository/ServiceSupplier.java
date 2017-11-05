/*
 * Copyright 2017 Byte Mechanics.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bytemechanics.service.repository;

import java.io.Closeable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytemechanics.service.repository.internal.ObjectFactory;

/**
 * Service supplier interface to store all service metadata information and the current instance in case of singletons
 * @author afarre
 * @since 0.1.0
 */
public interface ServiceSupplier extends Supplier {

	public String getName();
	public Class getAdapter();
	public boolean isSingleton();
	public Supplier getSupplier();

	public Object getInstance();
	public void setInstance(final Object _instance);	
	
	@SuppressWarnings("DoubleCheckedLocking")
	public default Optional<Object> tryGet() {
		
		Optional<Object> reply=Optional.empty();

		try{
			reply=Optional.ofNullable(get());
		}catch(Throwable e){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,e,() -> MessageFormat.format("service::supplier::service::{0}::get::fail::{1}",getName(),e.getMessage()));
		}
				
		return reply;
	}

	@Override
	@SuppressWarnings("DoubleCheckedLocking")
	public default Object get() {
		
		Object reply;

		if(isSingleton()){
			Object current=getInstance();
			if(current==null){
				synchronized(this){
					current=getInstance();
					if(current==null){
						setInstance(getSupplier().get());
					}			
				}
			}
			reply=current;
		}else{
			reply=getSupplier().get();
		}
				
		return reply;
	}
	
	public default void init(){

		if(isSingleton()){
			get();
		}
	}
	@SuppressWarnings("UseSpecificCatch")
	public default void close(){
		
		if(isSingleton()){
			Object current=getInstance();
			if(current!=null){
				synchronized(this){
					current=getInstance();
					if(current!=null){
						if(Closeable.class.isAssignableFrom(getAdapter())){
							try {
								((Closeable)current).close();
							} catch (Throwable e) {
								throw new RuntimeException("Close service {0} failed",e);
							}
						}
						setInstance(null);
					}			
				}
			}
		}
	}
	
	
	public static <T> Supplier<T> generateSupplier(final String _name,final Class<T> _implementation,final Object... _attributes){
		return () -> ObjectFactory.of(_implementation)
							.with(_attributes)
							.supplier()
							.get()
							.orElseThrow(() -> new RuntimeException(MessageFormat
																	.format("Unable to instantiate service {0} with class {1} using constructor({2})", 
																		_name,
																		_implementation,
																		Optional.ofNullable(_attributes)
																				.map(attributesArray -> Arrays.asList(attributesArray))
																				.orElse(Collections.emptyList()))));
	}

}
