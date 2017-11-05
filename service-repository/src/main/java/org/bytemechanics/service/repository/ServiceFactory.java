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

import java.text.MessageFormat;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * @author afarre
 */
public interface ServiceFactory {
	
	public ServiceSupplier getServiceSupplier();
	
	public default String name(){
		return getServiceSupplier().getName();
	}
	public default Class getAdapter(){
		return getServiceSupplier().getAdapter();
	}
	public default boolean isSingleton(){
		return getServiceSupplier().isSingleton();
	}
	public default Object get(){
		return getServiceSupplier().get();
	}
	public default <T> T get(final Class<T> _class){
		return (T)getServiceSupplier().get();
	}
	public default Optional<Object> tryGet(){
		return getServiceSupplier().tryGet();
	}
	public default <T> Optional<T> tryGet(final Class<T> _class){
		return getServiceSupplier().tryGet()
							.map(object -> (T)object);
	}
	public default void init(){

		final Logger logger=Logger.getLogger(ServiceFactory.class.getName());

		try{
			logger.finest(() -> MessageFormat.format("service::factory::init::{0}::begin",name()));
			getServiceSupplier().init();
			logger.finest(() -> MessageFormat.format("service::factory::init::{0}::end",name()));
		}catch(RuntimeException e){
			logger.log(Level.SEVERE,e,() -> MessageFormat.format("service::factory::init::{0}::fail::{1}",name(),e.getMessage()));
		}
	}
	public default void dispose(){
		
		final Logger logger=Logger.getLogger(ServiceFactory.class.getName());

		try{
			logger.finest(() -> MessageFormat.format("service::factory::dispose::{0}::begin",name()));
			getServiceSupplier().close();
			logger.finest(() -> MessageFormat.format("service::factory::dispose::{0}::end",name()));
		}catch(Throwable e){
			logger.log(Level.SEVERE,e,() -> MessageFormat.format("service::factory::dispose::{0}::fail::{1}",name(),e.getMessage()));
		}
	}

	
	public static void startup(final Stream<ServiceFactory> _services){

		final Logger logger=Logger.getLogger(ServiceFactory.class.getName());

		logger.finest("service::factory::startup::begin");
		_services.forEach(service -> service.init());
		logger.finer("service::factory::startup::end");
	}
	public static void close(final Stream<ServiceFactory> _services){

		final Logger logger=Logger.getLogger(ServiceFactory.class.getName());

		logger.finest("service::factory::close::begin");
		_services.forEach(service -> service.dispose());
		logger.finest("service::factory::close::end");
	}
	public static void reset(final Stream<ServiceFactory> _services){

		final Logger logger=Logger.getLogger(ServiceFactory.class.getName());

		logger.finest("service::factory::reset::begin");
		_services.forEach(service -> {
										service.dispose();
										service.init();
									});
		logger.finer("service::factory::reset::end");
	}	
}
