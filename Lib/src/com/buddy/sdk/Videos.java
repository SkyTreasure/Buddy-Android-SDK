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
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;

/**
 * @author RyanB
 *
 */
public class Videos {
	private VideoDataModel videoDataModel = null;
	protected BuddyClient client;
	protected AuthenticatedUser user;
	
	Videos(BuddyClient client, AuthenticatedUser user)
	{
		if(client == null)
		{ throw new IllegalArgumentException("client can't be null or empty."); }
		if(user == null)
		{ throw new IllegalArgumentException("user can't be null or empty."); }
		
		this.client = client;
		this.user = user;
		
		this.videoDataModel = new VideoDataModel(client, user);
	}
	
	public void add(String friendlyName, String appTag, double latitude, double longitude, String contentType, 
			byte[] videoData, OnCallback<Response<String>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.add(friendlyName, appTag, latitude, longitude, contentType, videoData, callback);		
		}				
	}
	
	public void add(String friendlyName, String appTag, double latitude, double longitude, String contentType,
			InputStream videoData, OnCallback<Response<String>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.add(friendlyName, appTag, latitude, longitude, contentType, videoData, callback);
		}
	}
	
	public void edit(long videoID, String friendlyName, String appTag, OnCallback<Response<Boolean>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.editInfo(videoID, friendlyName, appTag, callback);
		}
	}
	
	public void getInfo(long videoID, OnCallback<Response<Video>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.getInfo(videoID, callback);			
		}		
	}
	
	public void get(long videoID, OnCallback<Response<InputStream>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.get(videoID, callback);			
		}		
	}
		
	public void searchVideos(String friendlyName, String mimeType, String appTag, int searchDistance, double searchLatitude, double searchLongitude,
			int timeFilter, int recordLimit, OnCallback<ListResponse<Video>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.searchVideos(friendlyName, mimeType, appTag, searchDistance, 
					searchLatitude, searchLongitude, timeFilter, recordLimit, callback);
		}		
	}
	
	public void searchMyVideos(String friendlyName, String mimeType, String appTag, int searchDistance, double searchLatitude, double searchLongitude,
			int timeFilter, int recordLimit, OnCallback<ListResponse<Video>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.searchMyVideos(friendlyName, mimeType, appTag, searchDistance, 
					searchLatitude, searchLongitude, timeFilter, recordLimit, callback);
		}
	}
	
	public void getList(long userId, int recordLimit, OnCallback<ListResponse<Video>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.getList(userId, recordLimit, callback);			
		}
	}
	
	public void getMyList(int recordLimit, OnCallback<ListResponse<Video>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.getMyList(recordLimit, callback);
		}
	}
}
