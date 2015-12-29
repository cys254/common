package com.cisco.oss.foundation.serverversion;


import org.slf4j.MDC;

/**
 * Created by Yair Ogen (yaogen) on 29/12/2015.
 */
public class ServerVersionFactory {
    public static final String SERVER_VERSION = "SERVER_VERSION";
    private static final ThreadLocal<String> SERVER_VERSION_THREAD_LOCAL = new ThreadLocal<String>();

    private ServerVersionFactory() {
    }

    public static void setServerVersion(String serverVersion){
        SERVER_VERSION_THREAD_LOCAL.set(serverVersion);
        MDC.put("serverVersion",serverVersion);
    }

    public static String getServerVersion(){
        return SERVER_VERSION_THREAD_LOCAL.get();
    }
}
