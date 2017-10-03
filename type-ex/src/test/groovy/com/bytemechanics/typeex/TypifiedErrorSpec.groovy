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
class TypifiedErrorSpec extends Specification {

	def "Build a typified exception without arguments will create the correct exception type"(){
		setup:
			def TypifiedError result;
			
		when:
			result=MockedTypifiedErrorType.TEST_NO_PARAMS.get();
			
		then:
			result!=null
			!result.getArguments().isPresent()
			result.getCause()==null
			result.getExceptionType().equals(MockedTypifiedErrorType.TEST_NO_PARAMS)
			result.getMessage().equals(MockedTypifiedErrorType.TEST_NO_PARAMS.getMessage())
	}

	def "Build a typified exception with cause will create the correct exception type with its correspondent cause"(){
		setup:
			def Throwable cause=new RuntimeException("My cause");
			def TypifiedError result;
			
		when:
			result=MockedTypifiedErrorType.TEST_NO_PARAMS
											.from(cause);
			
		then:
			result!=null
			!result.getArguments().isPresent()
			result.getCause().equals(cause)
			result.getExceptionType().equals(MockedTypifiedErrorType.TEST_NO_PARAMS)
			result.getMessage().equals(MockedTypifiedErrorType.TEST_NO_PARAMS.getMessage())
	}

	def "Build a typified exception with arguments will create the correct exception type with its correspondent message"(){
		setup:
			def Object[] arguments=["String1",1]
			def TypifiedError result;
			
		when:
			result=MockedTypifiedErrorType.TEST_WITH_PARAMS
											.with("String1",1);
		then:
			result!=null
			result.getArguments().isPresent()
			result.getArguments().get()==["String1",1]
			result.getCause()==null
			result.getExceptionType().equals(MockedTypifiedErrorType.TEST_WITH_PARAMS)
			result.getMessage().equals("Test message with parameter1 String1 and parameter2 1")
	}

	def "Build a typified exception with arguments and case will create the correct exception type with its correspondent message"(){
		setup:
			def Throwable cause=new RuntimeException("My cause");
			def TypifiedError result;
			
		when:
			result=MockedTypifiedErrorType.TEST_WITH_PARAMS
										.from(cause)
										.with("String1",1);
		then:
			result!=null
			result.getArguments().isPresent()
			result.getArguments().get()==["String1",1]
			result.getCause().equals(cause)
			result.getExceptionType().equals(MockedTypifiedErrorType.TEST_WITH_PARAMS)
			result.getMessage().equals("Test message with parameter1 String1 and parameter2 1")
	}
	def "A Typified exception should be able to create stacktrace into an string correctly"(){
		setup:
			def TypifiedError result;
			
		when:
			result=MockedTypifiedErrorType.TEST_WITH_PARAMS
										.with("String1",1);

		then:
			result.getStringStacktrace().isPresent()
			result.getStringStacktrace().get()=="com.bytemechanics.typeex.impl.TypifiedError: Test message with parameter1 String1 and parameter2 1"+
												"\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)"+
												"\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)"+
												"\n\tat sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)"+
												"\n\tat java.lang.reflect.Constructor.newInstance(Constructor.java:423)"+
												"\n\tat com.bytemechanics.typeex.internal.TypeExHelper.instance(TypeExHelper.java:43)"+
												"\n\tat com.bytemechanics.typeex.ExceptionType.with(ExceptionType.java:39)"+
												"\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)"+
												"\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)"+
												"\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)"+
												"\n\tat java.lang.reflect.Method.invoke(Method.java:498)"+
												"\n\tat org.codehaus.groovy.runtime.callsite.PojoMetaMethodSite\$PojoCachedMethodSite.invoke(PojoMetaMethodSite.java:192)"+
												"\n\tat org.codehaus.groovy.runtime.callsite.PojoMetaMethodSite.call(PojoMetaMethodSite.java:56)"+
												"\n\tat org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:48)"+
												"\n\tat org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:113)"+
												"\n\tat org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:133)"+
												"\n\tat com.thegame.commons.exceptions.TypifiedErrorSpec.\$spock_feature_0_4(TypifiedErrorSpec.groovy:99)"+
												"\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)"+
												"\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)"+
												"\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)"+
												"\n\tat java.lang.reflect.Method.invoke(Method.java:498)"+
												"\n\tat org.spockframework.util.ReflectionUtil.invokeMethod(ReflectionUtil.java:188)"+
												"\n\tat org.spockframework.runtime.model.MethodInfo.invoke(MethodInfo.java:84)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.invokeRaw(BaseSpecRunner.java:481)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.invoke(BaseSpecRunner.java:464)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.runFeatureMethod(BaseSpecRunner.java:406)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.doRunIteration(BaseSpecRunner.java:324)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner\$6.invoke(BaseSpecRunner.java:309)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.invokeRaw(BaseSpecRunner.java:481)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.invoke(BaseSpecRunner.java:464)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.runIteration(BaseSpecRunner.java:288)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.initializeAndRunIteration(BaseSpecRunner.java:278)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.runSimpleFeature(BaseSpecRunner.java:269)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.doRunFeature(BaseSpecRunner.java:263)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner\$5.invoke(BaseSpecRunner.java:246)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.invokeRaw(BaseSpecRunner.java:481)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.invoke(BaseSpecRunner.java:464)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.runFeature(BaseSpecRunner.java:238)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.runFeatures(BaseSpecRunner.java:188)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.doRunSpec(BaseSpecRunner.java:98)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner\$1.invoke(BaseSpecRunner.java:84)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.invokeRaw(BaseSpecRunner.java:481)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.invoke(BaseSpecRunner.java:464)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.runSpec(BaseSpecRunner.java:76)"+
												"\n\tat org.spockframework.runtime.BaseSpecRunner.run(BaseSpecRunner.java:67)"+
												"\n\tat org.spockframework.runtime.Sputnik.run(Sputnik.java:63)"+
												"\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.execute(JUnit4Provider.java:283)"+
												"\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.executeWithRerun(JUnit4Provider.java:173)"+
												"\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:153)"+
												"\n\tat org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:128)"+
												"\n\tat org.apache.maven.surefire.booter.ForkedBooter.invokeProviderInSameClassLoader(ForkedBooter.java:203)"+
												"\n\tat org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:155)"+
												"\n\tat org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:103)\n"
	}
}