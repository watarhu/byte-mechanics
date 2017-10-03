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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author afarre
 */
public interface TypifiableException {
	
	public Throwable getCause();
	public ExceptionType getExceptionType();
	public Optional<Object[]> getArguments();

	public default String getFormatedMessage(){
		return getArguments()
				.map(args -> TypeExHelper.format(getExceptionType().getMessage(),args))
				.orElse(getExceptionType().getMessage());
	}
	
	public default TypifiableException from(final Throwable _cause) {
		return TypeExHelper.instance(_cause,getExceptionType(),getArguments().orElse(null));
	}
	public default TypifiableException with(final Object... _args) {
		return TypeExHelper.instance(getCause(),getExceptionType(),_args);
	}
	
	public void printStackTrace(final PrintWriter _printWriter);
	public default Optional<String> getStringStacktrace(){
		
		Optional<String> reply;
		
		try(StringWriter writer=new StringWriter();
				PrintWriter printWriter=new PrintWriter(writer)){
			printStackTrace(printWriter);
			reply=Optional.of(writer.toString());
		} catch (IOException e) {
			Logger.getLogger(TypifiableException.class.getName()).log(Level.SEVERE, "Unable to convert exception {} to string",new Object[]{getExceptionType(),e});
			reply=Optional.empty();
		}
		
		return reply;
	}
}
