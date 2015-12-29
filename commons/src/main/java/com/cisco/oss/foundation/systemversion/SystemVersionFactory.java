package com.cisco.oss.foundation.systemversion;


import org.slf4j.MDC;

/**
 * Created by Yair Ogen (yaogen) on 29/12/2015.
 */
public class SystemVersionFactory {
    public static final String SYSTEM_VERSION = "SYSTEM_VERSION";
    private static final ThreadLocal<String> SYSTEM_VERSION_THREAD_LOCAL = new ThreadLocal<String>();

    private SystemVersionFactory() {
    }

    public static void setSystemVersion(String systemVersion){
        SYSTEM_VERSION_THREAD_LOCAL.set(systemVersion);
        MDC.put("systemVersion",systemVersion);
    }

    public static String getSystemVersion(){
        return SYSTEM_VERSION_THREAD_LOCAL.get();
    }
}
