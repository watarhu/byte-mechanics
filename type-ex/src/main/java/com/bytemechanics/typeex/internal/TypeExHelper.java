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
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class for <strong>internal use only</strong>
 * Please keep in mind that this <strong>iclass can be changed, renamed, deleted or extended without previous advice between fix releases, minor versions or major versions</strong>
 * @author Albert Farré Figueras
 * @since 0.0.1
 * @version 1.0.0
 */
public final class TypeExHelper {

	public static final Optional<Constructor> findSuitableConstructor(final ExceptionType _exceptionType) {

		Optional<Constructor> reply;
		
		reply=Stream.of(_exceptionType.getExceptionClass().getConstructors())
				.filter(constructor -> constructor.getParameterCount()==3)
				.filter(constructor -> constructor.getParameterTypes()[0].isAssignableFrom(Throwable.class))
				.filter(constructor -> constructor.getParameterTypes()[1].isAssignableFrom(_exceptionType.getClass()))
				.filter(constructor -> constructor.getParameterTypes()[2].isAssignableFrom(Object[].class))
				.findAny();
		
		return reply;	
	}

	public static final Optional<TypifiableException> instance(final Constructor _constructor,final Throwable _cause,final ExceptionType _exceptionType,final Object... _args) {

		Optional<TypifiableException> reply=Optional.empty();
		
		try {
//System.out.println("ARGS:" +Arrays.asList(_args));
			reply=Optional.ofNullable(_constructor.newInstance(_cause,_exceptionType,_args))
							.map(instance -> (TypifiableException)instance);
//System.out.println("reply:" +reply);
			
		} catch (SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
			throw new Error(format("Unable to construct typified exception {} with class {} with arguments {} caused: {}",_exceptionType,_exceptionType.getExceptionClass(),Arrays.asList(new Object[]{Throwable.class,_exceptionType.getClass(),Object[].class}), e.getMessage()),e);
		}
		
		return reply;	
	}
	
	public static final String format(final String _message, final Object... _args) {
		
		final StringBuilder builder=new StringBuilder();
		
		int lastBreak=0;
		int numArg=0;
		for(int ic1=0;ic1<_message.length();ic1++){
			final char current=_message.charAt(ic1);
			final char next=(ic1<_message.length()-1)? _message.charAt(ic1+1) : 'A';
			if((current=='{')&&(next=='}')){
				builder.append(_message.substring(lastBreak,ic1));
				builder.append(Optional.of(numArg++)
										.filter(counter -> counter<_args.length)
										.map(counter -> _args[counter])
										.map(object -> String.valueOf(object))
										.orElse("null"));
				ic1=lastBreak=ic1+2;
			}
		}
		if(lastBreak<_message.length()){
			builder.append(_message.substring(lastBreak,_message.length()));
		}
		
		return builder.toString();
/*		return Optional.ofNullable(_message)
			.map(message -> Stream.of(message)
									.flatMap(new Function<String, Stream<String>>() {

										private int ic1=0;

										@Override
										public Stream<String> apply(final String _segment) {
											int index=_segment.indexOf("{}");
											return (index>-1) ? 
														Stream.of(_segment.substring(0,index), 
																	String.valueOf(_args[ic1++]),
																	_segment.substring(index)) : 
														Stream.of(_segment);
										}
									})
									.collect(Collectors.joining(""))
							)
			.map(message -> Pattern.compile("\\{\\}")
								.splitAsStream(message)
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
*/
	}	
}
