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
package org.bytemechanics.service.repository.beans;

import java.text.MessageFormat;
import java.util.function.Supplier;
import org.bytemechanics.service.repository.ServiceSupplier;

/**
 * Default Service supplier implementation
 * @author afarre
 * @since 0.1.0
 */
public class DefaultServiceSupplier implements ServiceSupplier{

	private final String name;
	private final Supplier supplier;
	private final Class adapter;
	private final boolean singleton;
	private volatile Object instance;
	
	
	public <T> DefaultServiceSupplier(final String _name,final Class<T> _adapter,final Class<? extends T> _implementation,final Object... _args){
		this(_name,_adapter,false,_implementation);
	}
	public <T> DefaultServiceSupplier(final String _name,final Class<T> _adapter,final boolean _isSingleton,final Class<? extends T> _implementation,final Object... _args){
		this(_name,_adapter,false,ServiceSupplier.generateSupplier(_name,_implementation,_args));
	}
	public <T> DefaultServiceSupplier(final String _name,final Class<T> _adapter,final boolean _isSingleton,final Supplier<? extends T> _supplier){
		this.name=_name;
		this.adapter=_adapter;
		this.singleton=_isSingleton;
		this.supplier=_supplier;
		this.instance=null;
	}

	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public Class getAdapter() {
		return adapter;
	}
	@Override
	public boolean isSingleton() {
		return singleton;
	}
	@Override
	public Supplier getSupplier() {
		return supplier;
	}

	@Override
	public Object getInstance() {
		return instance;
	}
	@Override
	public void setInstance(final Object _instance) {
		
		if((_instance!=null)&&(!getAdapter().isAssignableFrom(_instance.getClass()))){
			throw new RuntimeException(MessageFormat.format("Unable to set instance {0} that doesn't implement the required adapter {1}",_instance,getAdapter()));
		}
		this.instance = _instance;
	}
}
