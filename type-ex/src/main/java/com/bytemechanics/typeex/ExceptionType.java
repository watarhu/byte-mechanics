/*
 * Copyright 2017 afarre.
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
package com.bytemechanics.typeex;

import com.bytemechanics.typeex.internal.TypeExHelper;
import java.util.function.Supplier;

/**
 * @author e103880
 */
public interface ExceptionType extends Supplier<TypifiableException>{
	
	public String name();
	public String getMessage();
	public Class<? extends TypifiableException> getExceptionClass();

	
	@Override
	public default TypifiableException get() {
		return TypeExHelper.instance(null,this);
	}
	public default TypifiableException from(final Throwable _cause) {
		return TypeExHelper.instance(_cause,this);
	}
	public default TypifiableException with(final Object... _args) {
		return TypeExHelper.instance(null,this,_args);
	}
}
