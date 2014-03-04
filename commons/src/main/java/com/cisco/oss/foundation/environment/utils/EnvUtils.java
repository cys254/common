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

package com.cisco.oss.foundation.environment.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * utility yp update environemnt variables in the system via code.
 * @author Yair Ogen
 *
 */
public final class EnvUtils {
	
	public static void updateEnv(String key, String value)
	{
		try
		{
			String os = System.getProperty("os.name");
			Class<?> procEnvClass = EnvUtils.class.forName("java.lang.ProcessEnvironment");

			if (os.toLowerCase().contains("win"))
			{
				Field field = procEnvClass.getDeclaredField("theCaseInsensitiveEnvironment");
				field.setAccessible(true);
				Map map = (Map) field.get(procEnvClass);
				map.put(key, value);
			} else
			{
//				System.out.println("---- set ENV parameters ----");
				Field mapField = procEnvClass.getDeclaredField("theUnmodifiableEnvironment");
				mapField.setAccessible(true);
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(mapField, mapField.getModifiers() & ~Modifier.FINAL);

				Map<String, String> env = (Map<String, String>) mapField.get(procEnvClass);
				Map<String, String> copyEnv = new HashMap<String, String>();
				for (String k : env.keySet())
				{
					String v = env.get(k);
					copyEnv.put(k, v);
				}
				copyEnv.put(key, value);
				mapField.set(null, (Map<String, String>) copyEnv);
			}
		} catch (Exception e)
		{
			e.printStackTrace();

		}
	}

}
