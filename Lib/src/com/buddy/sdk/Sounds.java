/*
 * Copyright (C) 2012 Buddy Platform, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.buddy.com/terms-of-service/ 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.buddy.sdk;

import java.io.InputStream;

import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.responses.Response;

/**
 * @author RyanB
 *
 */
public class Sounds {
	private SoundDataModel soundDataModel = null;
	protected BuddyClient client;
	
	public enum SoundQuality{
		Medium, Low, High
	}
	
	Sounds(BuddyClient client){
		if(client == null)
		{ throw new IllegalArgumentException("client can't be null or empty."); }
		
		this.client = client;
		
		this.soundDataModel = new SoundDataModel(client);
	}
	
	public void get(String soundName, SoundQuality quality, final OnCallback<Response<InputStream>> callback){
		if(this.soundDataModel != null){
			this.soundDataModel.get(soundName, quality, callback);
		}
	}
}
