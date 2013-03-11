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

package com.buddy.sdk;

import com.buddy.sdk.responses.BaseResponse;

public class Callbacks {
    /**
     * Interface to the Web API Callback. The callback returns a
     * BuddyCallbackParams response and an user specified object passed in to
     * the calling method.
     */
    public interface OnResponseCallback {
        void OnResponse(BuddyCallbackParams response, Object state);
    }

    /**
     * Interface for the callback to the client code. The callback returns a
     * response relevant to the API called and an user specified object passed
     * in to the calling method.
     */
    public interface OnCallback<T extends BaseResponse> {
        void OnResponse(T response, Object state);
    }
}
