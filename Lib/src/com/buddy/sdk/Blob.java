/*
 * Copyright (C) 2012 Buddy Platform, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.buddy.sdk;

import java.io.InputStream;
import java.util.Date;

import com.buddy.sdk.responses.Response;
import com.buddy.sdk.utils.Utils;
import com.buddy.sdk.json.responses.BlobDataResponse.BlobData;
import com.buddy.sdk.Callbacks.OnCallback;

/**
 * @author RyanB
 *
 */
public class Blob {
	protected BlobDataModel blobDataModel = null;
	
	protected long id;
	protected String friendlyName;
	protected String mimeType;
	protected int fileSize;
	protected String appTag;
	protected long owner;
	protected double latitude;
	protected double longitude;
	protected Date uploadDate;
	protected Date lastTouchDate;
	
	Blob(BuddyClient client, AuthenticatedUser user, BlobData data){
		blobDataModel = new BlobDataModel(client, user);
		
		this.id = Long.parseLong(data.blobID);
		this.friendlyName = data.friendlyName;
		this.mimeType = data.mimeType;
		this.fileSize = Integer.parseInt(data.fileSize);
		this.appTag = data.appTag;
		this.owner = Long.parseLong(data.owner);
		this.latitude = Double.parseDouble(data.latitude);
		this.longitude = Double.parseDouble(data.longitude);
		this.uploadDate = Utils.convertDateString(data.uploadDate);
		this.lastTouchDate = Utils.convertDateString(data.lastTouchDate);
	}
	
	public void delete(OnCallback<Response<Boolean>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.delete(this.id, callback);
		}		
	}
	
	public void get(OnCallback<Response<InputStream>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.get(id, callback);
		}
	}
}
