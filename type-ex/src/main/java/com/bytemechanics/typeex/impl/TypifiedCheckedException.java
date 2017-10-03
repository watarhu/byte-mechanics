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
package com.bytemechanics.typeex.impl;

import com.bytemechanics.typeex.ExceptionType;
import com.bytemechanics.typeex.TypifiableException;
import java.util.Arrays;
import java.util.Optional;


/**
 *
 * @author afarre
 */
public abstract class TypifiedCheckedException extends Exception implements TypifiableException{

	private final ExceptionType exceptionType;
	private final Optional<Object[]> arguments;
	
	
	public TypifiedCheckedException(final ExceptionType _exceptionType,final Object... _arguments){
		this(null,_exceptionType,_arguments);
	}
	public TypifiedCheckedException(final Throwable _cause,final ExceptionType _exceptionType,final Object... _arguments){
		super(_exceptionType.getMessage(),_cause);
		this.exceptionType=_exceptionType;
		this.arguments=Optional.ofNullable(_arguments)
							.filter(args -> args.length>0)
							.map(args -> Arrays.copyOf(args, args.length));
	}
	
	@Override
	public String getMessage() {
		return getFormatedMessage();
	}
	@Override
	public final ExceptionType getExceptionType(){
		return this.exceptionType;
	}
	@Override
	public final Optional<Object[]> getArguments() {
		return	this.arguments
					.map(args -> Arrays.copyOf(args, args.length));
	}
} 
