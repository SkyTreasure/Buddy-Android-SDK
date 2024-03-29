/*
 * Copyright (C) 2012 Buddy Platform, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.buddy.sdk.exceptions;

/**
 * Exception raised for parameter / request parsing / handling errors
 */
public class BuddyServiceException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4393381071791106862L;

    private String error;

    public BuddyServiceException(String error) {
        super(error);
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
