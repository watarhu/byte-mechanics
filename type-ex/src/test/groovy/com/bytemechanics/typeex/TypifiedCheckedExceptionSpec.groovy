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
package com.thegame.commons.exceptions;

import spock.lang.*
import spock.lang.Specification
import com.bytemechanics.typeex.*
import com.bytemechanics.typeex.impl.*

/**
 * @author afarre
 */
class TypifiedCheckedExceptionSpec extends Specification {

	def "Build a typified exception without arguments will create the correct exception type"(){
		setup:
			def TypifiedCheckedException result;
			
		when:
			result=MockedTypifiedCheckedExceptionType.TEST_NO_PARAMS.get();
			
		then:
			result!=null
			!result.getArguments().isPresent()
			result.getCause()==null
			result.getExceptionType().equals(MockedTypifiedCheckedExceptionType.TEST_NO_PARAMS)
			result.getMessage().equals(MockedTypifiedCheckedExceptionType.TEST_NO_PARAMS.getMessage())
	}
	def "Build a typified exception with cause will create the correct exception type with its correspondent cause"(){
		setup:
			def Throwable cause=new RuntimeException("My cause");
			def TypifiedCheckedException result;
			
		when:
			result=MockedTypifiedCheckedExceptionType.TEST_NO_PARAMS
											.from(cause);
			
		then:
			result!=null
			!result.getArguments().isPresent()
			result.getCause().equals(cause)
			result.getExceptionType().equals(MockedTypifiedCheckedExceptionType.TEST_NO_PARAMS)
			result.getMessage().equals(MockedTypifiedCheckedExceptionType.TEST_NO_PARAMS.getMessage())
	}
	
	def "Build a typified exception with 1 argument will create the correct exception type with its correspondent message"(){
		setup:
			def TypifiedCheckedException result;
			
		when:
			result=MockedTypifiedCheckedExceptionType.TEST_WITH_1_PARAM
											.with("String1");
		then:
			result!=null
			result.getArguments().isPresent()
			result.getArguments().get()==["String1"]
			result.getCause()==null
			result.getExceptionType().equals(MockedTypifiedCheckedExceptionType.TEST_WITH_1_PARAM)
			result.getMessage().equals("Test message with parameter1 String1 other")
	}
	def "Build a typified exception with 1 argument and case will create the correct exception type with its correspondent message"(){
		setup:
			def Throwable cause=new RuntimeException("My cause");
			def TypifiedCheckedException result;
			
		when:
			result=MockedTypifiedCheckedExceptionType.TEST_WITH_1_PARAM
										.from(cause)
										.with(null);
		then:
			result!=null
			!result.getArguments().isPresent()
			result.getCause().equals(cause)
			result.getExceptionType().equals(MockedTypifiedCheckedExceptionType.TEST_WITH_1_PARAM)
			result.getMessage().equals(MockedTypifiedCheckedExceptionType.TEST_WITH_1_PARAM.getMessage())
	}
	def "A Typified exception with 1 argument should be able to create stacktrace into an string correctly"(){
		setup:
			def TypifiedCheckedException result;
			
		when:
			result=MockedTypifiedCheckedExceptionType.TEST_WITH_1_PARAM
										.with("String1");

		then:
			result.getStringStacktrace().isPresent()
			result.getStringStacktrace().get()!=null
	}
	
	def "Build a typified exception with arguments will create the correct exception type with its correspondent message"(){
		setup:
			def TypifiedCheckedException result;
			
		when:
			result=MockedTypifiedCheckedExceptionType.TEST_WITH_PARAMS
											.with("String1",1);
		then:
			result!=null
			result.getArguments().isPresent()
			result.getArguments().get()==["String1",1]
			result.getCause()==null
			result.getExceptionType().equals(MockedTypifiedCheckedExceptionType.TEST_WITH_PARAMS)
			result.getMessage().equals("Test message with parameter1 String1 and parameter2 1")
	}
	def "Build a typified exception with arguments and case will create the correct exception type with its correspondent message"(){
		setup:
			def Throwable cause=new RuntimeException("My cause");
			def TypifiedCheckedException result;
			
		when:
			result=MockedTypifiedCheckedExceptionType.TEST_WITH_PARAMS
										.from(cause)
										.with("String1",1);
		then:
			result!=null
			result.getArguments().isPresent()
			result.getArguments().get()==["String1",1]
			result.getCause().equals(cause)
			result.getExceptionType().equals(MockedTypifiedCheckedExceptionType.TEST_WITH_PARAMS)
			result.getMessage().equals("Test message with parameter1 String1 and parameter2 1")
	}
	def "A Typified exception should be able to create stacktrace into an string correctly"(){
		setup:
			def TypifiedCheckedException result;
			
		when:
			result=MockedTypifiedCheckedExceptionType.TEST_WITH_PARAMS
										.with("String1",1);

		then:
			result.getStringStacktrace().isPresent()
			result.getStringStacktrace().get()!=null
	}
}