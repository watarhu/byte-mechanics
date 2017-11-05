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
package org.bytemechanics.service.repository.mocks;

import org.bytemechanics.service.repository.ServiceFactory;
import org.bytemechanics.service.repository.ServiceSupplier;
import org.bytemechanics.service.repository.beans.DefaultServiceSupplier;

/**
 *
 * @author afarre
 */
public enum DummieServiceFactory implements ServiceFactory {

	DUMMIE_SERVICE(DummieService.class,DummieServiceImpl.class),
	SINGLETON_DUMMIE_SERVICE(DummieService.class,DummieServiceImpl.class),
	;

	private final ServiceSupplier serviceSupplier;	
		
	
	<T> DummieServiceFactory(final Class<T> _adapter,final Class<? extends T> _implementation){
		this.serviceSupplier=new DefaultServiceSupplier(name(), _adapter, _implementation);
	}
	<T> DummieServiceFactory(final Class<T> _adapter,final boolean _singleton,final Class<? extends T> _implementation,final Object... _args){
		this.serviceSupplier=new DefaultServiceSupplier(name(),_adapter,_singleton,_implementation,_args);
	}
		
	@Override
	public ServiceSupplier getServiceSupplier() {
		return this.serviceSupplier;
	}
}
