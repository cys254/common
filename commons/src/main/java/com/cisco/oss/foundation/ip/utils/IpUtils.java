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

package com.cisco.oss.foundation.ip.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * utility to get local host once as it is very heavy on performance.
 *
 * @author Yair OGen
 */
public class IpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);

    private static InetAddress localHost = null;
    private static String hostName = null;
    private static String ipAddress = null;

    /**
     * get the local host ip address.
     *
     * @return the local host ip address.
     */
    public static InetAddress getLocalHost() {

        if (localHost == null) {
            try {
                localHost = java.net.InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                LOGGER.error("Cannot get local host address. exception is: " + e);
                localHost = null;
                hostName = "UNKNOWN";
                ipAddress = "UNKNOWN";
            }
        }

        return localHost;

    }

    public static String getHostName() {
        if (hostName == null) {
            hostName = getLocalHost().getHostName();
        }
        return hostName;
    }

    public static String getIpAddress() {
        if (ipAddress == null) {
            ipAddress = new String(getLocalHost().getHostAddress());
        }
        return ipAddress;
    }

}
