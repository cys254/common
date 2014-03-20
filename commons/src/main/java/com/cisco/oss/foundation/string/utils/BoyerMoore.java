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

package com.cisco.oss.foundation.string.utils;

/**
 * This class implements BoyerMoore algorithm for searching a pattern in a string
 * @author ykasten
 *
 */
public class BoyerMoore {


	/**
	 * An array that contains last location in the pattern for every character in the pattern.
	 */
    private int[] lastLocation;
    
    /**
     * The pattern to search - should be given in the contructor.
     */
    private String pattern;
    
 

	@Override
    public boolean equals(Object other){
    	return pattern.equals(((BoyerMoore)other).pattern);
    }
    
    @Override
    public int hashCode(){
    	return pattern.hashCode();
    }
    
    /**
     * Build BoyerMoore object with a giving pattern, making lastLoction array from the pattern
     * @param pattern
     */
    public BoyerMoore(String pattern) {
        this.pattern = pattern;
        lastLocation = new int[256];
        for (int i = 0; i< lastLocation.length; i++){
        	lastLocation[i] = -1;
        }
        for (int i=0;i<pattern.length(); i++){
        	lastLocation[pattern.charAt(i)]=i;
        }
	
    }


    /**
     * Searching the pattern in the text
     * @param txt the text to search the pattern
     * @return -1 if the pattern was not found, location of the pattern if it was found.
     */
    public int search(String txt) {
    	int p = pattern.length();
        int t = txt.length();
        int skip;
        for (int i=0; i <= t-p; i+=skip) { 
            skip = 0;
            for (int j =p-1;j>= 0;j--) { //index of the pattern to search
                if (pattern.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1,j- lastLocation[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) { //which means all the charcters fit
            	return i;   
            }
        }
        return -1; 
    } 
    
    public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


}

	
	