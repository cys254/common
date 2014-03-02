/*
 * Copyright 2014 Cisco Systems, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.cisco.oss.foundation.application.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a base exception to be used by all the CAB components that need to
 * create custom exceptions. This exception wraps an <link {@code ErrorCode}>
 * Object and knows to display it when the getMessage function is used and in
 * exception stack traces. Note: If you override the getMessage method in your
 * code, you must call the super so the error code can be updated accordingly.
 * 
 * @author Yair Ogen
 * @author Joel Gurfinkel
 */
public class RuntimeApplicationException extends RuntimeException implements ApplicationExceptionInterface {

	private static final long serialVersionUID = 6167023793706785078L;

	private final List<ErrorCodeInterface> errorCodes = new ArrayList<ErrorCodeInterface>(5);

	public RuntimeApplicationException(final String message, final Throwable cause) {
		super(message, cause);
		ErrorCodeUtil.INSTANCE.updateErrorDetails(cause, errorCodes);

	}

	public RuntimeApplicationException(final String message, final ErrorCodeInterface errorCode) {
		super(message);
		errorCodes.add(errorCode);
	}

	public RuntimeApplicationException(final String message, final Throwable cause, final ErrorCodeInterface errorCode) {
		super(message, cause);
		ErrorCodeUtil.INSTANCE.updateErrorDetails(cause, errorCodes);
		errorCodes.add(errorCode);
	}

	@Override
	public final ErrorCodeInterface getErrorCode() {
		return (errorCodes == null || errorCodes.isEmpty()) ? null : errorCodes.get(0);
	}

	@Override
	public final List<ErrorCodeInterface> getAllErrorCodes() {
		return errorCodes;
	}

}
