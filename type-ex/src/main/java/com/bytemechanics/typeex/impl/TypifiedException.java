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
public class TypifiedException extends RuntimeException implements TypifiableException<TypifiedException>{

	private final ExceptionType exceptionType;
	private final Optional<Object[]> arguments;
	
	
	/**
	 * Constructor of this TypifiedException
	 *
	 * @param _exceptionType ExceptionType represented by this exception
	 * @param _arguments arguments to replace in the ExceptionType message
	 * @see ExceptionType
	 * @since 0.1.0
	 */
	public TypifiedException(final ExceptionType _exceptionType,final Object... _arguments){
		this(null,_exceptionType,_arguments);
	}
	/**
	 * Constructor of this TypifiedException
	 *
	 * @param _cause original cause of this exception
	 * @param _exceptionType ExceptionType represented by this exception
	 * @param _arguments arguments to replace in the ExceptionType message
	 * @see ExceptionType
	 * @since 0.1.0
	 */
	public TypifiedException(final Throwable _cause,final ExceptionType _exceptionType,final Object... _arguments){
		super(_exceptionType.getMessage(),_cause);
		this.exceptionType=_exceptionType;
		this.arguments=Optional.ofNullable(_arguments)
							.filter(args -> args.length>0)
							.map(args -> Arrays.copyOf(args, args.length));
	}
	
	
	/**
	 * Returns the formatted message of this TypifiableException
	 *
	 * @return The formatted message of this TypifiableException
	 * @see Throwable#getMessage()
	 * @see TypifiableException
	 * @since 0.1.0
	 */
	@Override
	public String getMessage() {
		return getFormattedMessage();
	}
	/**
	 * Returns the provided ExceptionType
	 *
	 * @return The provided ExceptionType
	 * @see TypifiableException#getExceptionType()
	 * @see ExceptionType
	 * @since 0.1.0
	 */
	@Override
	public final ExceptionType getExceptionType(){
		return this.exceptionType;
	}
	/**
	 * Returns the provided message arguments
	 *
	 * @return The provided message arguments
	 * @see TypifiableException#getArguments()
	 * @since 0.1.0
	 */
	@Override
	public final Optional<Object[]> getArguments() {
		return	this.arguments
					.map(args -> Arrays.copyOf(args, args.length));
	}
} 
