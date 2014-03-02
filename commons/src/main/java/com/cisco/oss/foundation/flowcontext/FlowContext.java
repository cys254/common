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

/**
 * 
 */
package com.cisco.oss.foundation.flowcontext;

import java.io.Serializable;

/**
 * This interface defines data that is to be passed between components while invoked from one to another.
 * The interface should be the first argument in each public method in every class exposed to another component.
 * Examples of use for such a functionality can be considered in a case like Logging when you want to share data cross all log entries.
 * @author Yair Ogen, Yehudit Glass
 *
 */
public interface FlowContext extends Serializable{

	/**
	 * 
	 */
	static final long serialVersionUID = -3934451414870443512L;

	/**
	 * 
	 * @return Unique id to identify the FlowContext
	 */
	String getUniqueId();
}
