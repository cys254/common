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

package com.cisco.oss.foundation.flowcontext;

import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum FlowContextGenerator {

	INSTANCE;
	private static final Logger LOGGER = LoggerFactory.getLogger(FlowContextGenerator.class);
	private static final int _genmachine;
	static {

		try {
			// build a 2-byte machine piece based on NICs info
			int machinePiece;
			{
				try {
					StringBuilder sb = new StringBuilder();
					Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
					while (e.hasMoreElements()) {
						NetworkInterface ni = e.nextElement();
						sb.append(ni.toString());
					}
					machinePiece = sb.toString().hashCode() << 16;
				} catch (Throwable e) {
					// exception sometimes happens with IBM JVM, use random
					LOGGER.warn(e.getMessage(), e);
					machinePiece = (new Random().nextInt()) << 16;
				}
				LOGGER.trace("machine piece post: " + Integer.toHexString(machinePiece));
			}

			// add a 2 byte process piece. It must represent not only the JVM
			// but the class loader.
			// Since static var belong to class loader there could be collisions
			// otherwise
			final int processPiece;
			{
				int processId = new Random().nextInt();
				try {
					processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
				} catch (Throwable t) {
				}

				ClassLoader loader = FlowContextGenerator.class.getClassLoader();
				int loaderId = loader != null ? System.identityHashCode(loader) : 0;

				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toHexString(processId));
				sb.append(Integer.toHexString(loaderId));
				processPiece = sb.toString().hashCode() & 0xFFFF;
				LOGGER.trace("process piece: " + Integer.toHexString(processPiece));
			}

			_genmachine = machinePiece | processPiece;
			LOGGER.trace("machine : " + Integer.toHexString(_genmachine));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	private static AtomicInteger nextInc = new AtomicInteger((new Random()).nextInt());
	private FlowContextGenerator() {
	}


    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

	public String getNextFlowContext() {
		int time = (int) (System.currentTimeMillis() / 1000);
		int machine = _genmachine;

		nextInc.compareAndSet(Integer.MAX_VALUE, 0);

		int inc = nextInc.getAndIncrement();

		byte bytes[] = new byte[12];
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		// by default BB is big endian like we need
		byteBuffer.putInt(time);
		byteBuffer.putInt(machine);
		byteBuffer.putInt(inc);

		return bytesToHex(bytes);
	}

}
