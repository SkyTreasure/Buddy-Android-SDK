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

import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.responses.ListResponse;
/**
 * @author RyanB
 *
 */
public class Blobs {
	private BlobDataModel blobDataModel = null;
	protected BuddyClient client;
	protected AuthenticatedUser user;
	
	Blobs(BuddyClient client, AuthenticatedUser user)
	{
		if(client == null)
		{ throw new IllegalArgumentException("client can't be null or empty."); }
		if(user == null)
		{ throw new IllegalArgumentException("user can't be null or empty."); }
		
		this.client = client;
		this.user = user;
		
		this.blobDataModel = new BlobDataModel(client, user);
	}
	
	public void add(String friendlyName, String appTag, double latitude, double longitude, String contentType,
			InputStream blobData, OnCallback<Response<String>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.add(friendlyName, appTag, latitude, longitude, contentType, blobData, callback);
		}
	}
	
	public void edit(long blobID, String friendlyName, String appTag, OnCallback<Response<Boolean>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.editInfo(blobID, friendlyName, appTag, callback);
		}
	}
	
	public void getInfo(long blobID, OnCallback<Response<Blob>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.getInfo(blobID, callback);			
		}		
	}
	
	public void get(long blobID, OnCallback<Response<InputStream>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.get(blobID, callback);			
		}		
	}
		
	public void searchBlobs(String friendlyName, String mimeType, String appTag, int searchDistance, double searchLatitude, double searchLongitude,
			int timeFilter, int recordLimit, OnCallback<ListResponse<Blob>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.searchBlobs(friendlyName, mimeType, appTag, searchDistance, 
					searchLatitude, searchLongitude, timeFilter, recordLimit, callback);
		}		
	}
	
	public void searchMyBlobs(String friendlyName, String mimeType, String appTag, int searchDistance, double searchLatitude, double searchLongitude,
			int timeFilter, int recordLimit, OnCallback<ListResponse<Blob>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.searchMyBlobs(friendlyName, mimeType, appTag, searchDistance, 
					searchLatitude, searchLongitude, timeFilter, recordLimit, callback);
		}
	}
	
	public void getList(long userId, int recordLimit, OnCallback<ListResponse<Blob>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.getList(userId, recordLimit, callback);			
		}
	}
	
	public void getMyList(int recordLimit, OnCallback<ListResponse<Blob>> callback){
		if(this.blobDataModel != null){
			this.blobDataModel.getMyList(recordLimit, callback);
		}
	}
}
