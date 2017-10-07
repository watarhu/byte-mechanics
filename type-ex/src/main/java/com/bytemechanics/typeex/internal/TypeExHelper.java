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
package com.bytemechanics.typeex.internal;

import com.bytemechanics.typeex.ExceptionType;
import com.bytemechanics.typeex.TypifiableException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class for internal use only
 * Please keep in mind that this class can be changed, renamed, deleted or extended without previous advice between fix releases, minor versions or major versions
 * @author Albert Farré Figueras
 * @since 0.0.1
 * @version 1.0.0
 */
public final class TypeExHelper {


	public static final TypifiableException instance(final Throwable _cause,final ExceptionType _exceptionType,final Object... _args) {

		TypifiableException reply;
		
		try {
			final Constructor constructor=_exceptionType.getExceptionClass().getConstructor(Throwable.class,ExceptionType.class,Object[].class);
			reply=(TypifiableException)constructor.newInstance(_cause,_exceptionType,_args);
		} catch (NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
			throw new Error(format("Unable to construct typified exception {} with {} caused: {}",_exceptionType,_exceptionType.getExceptionClass(),e.getMessage()),e);
		}
		
		return reply;	
	}
	
	public static final String format(final String _message, final Object... _args) {
		
		return Optional.ofNullable(_message)
			.filter(message -> _args.length>0)
			.filter(message -> message.contains("{"))
			.map(message -> Stream.of(message.split("\\{\\}"))
			.flatMap(new Function<String, Stream<String>>() {

				private int ic1=0;

				@Override
				public Stream<String> apply(final String _segment) {
					return Stream.of(_segment, Optional.of(ic1++)
						.filter(counter -> counter<_args.length)
						.map(counter -> _args[counter])
						.map(object -> String.valueOf(object))
						.orElse(""));
				}
			})
			.collect(Collectors.joining("")))
			.orElse(_message);
	}	
}
