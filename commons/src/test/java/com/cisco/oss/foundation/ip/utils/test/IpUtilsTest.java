/*
 * Copyright 2015 Cisco Systems, Inc.
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

package com.cisco.oss.foundation.ip.utils.test;

import com.cisco.oss.foundation.ip.utils.IpUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Yair Ogen on 3/17/14.
 */
public class IpUtilsTest {

    Logger logger = LoggerFactory.getLogger(IpUtilsTest.class);

    @Test
    public void testIpUtils(){
        logger.info("inet address: {}; host name: {}, ip: {}", IpUtils.getLocalHost(), IpUtils.getHostName(), IpUtils.getIpAddress());
    }
}
