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
import java.util.Date;

import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.json.responses.VideoDataResponse.VideoData;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.utils.Utils;

/**
 * @author RyanB
 *
 */
public class Video {
	private VideoDataModel videoDataModel = null;
	
	private long id;
	private String friendlyName;
	private String mimeType;
	private int fileSize;
	private String appTag;
	private long owner;
	private double latitude;
	private double longitude;
	private Date uploadDate;
	private Date lastTouchDate;
	
	Video(BuddyClient client, AuthenticatedUser user, VideoData data){
		videoDataModel = new VideoDataModel(client, user);
		
		this.id = Long.parseLong(data.videoID);
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
		if(this.videoDataModel != null){
			this.videoDataModel.delete(this.id, callback);
		}		
	}
	
	public void edit(String friendlyName, String appTag, OnCallback<Response<Boolean>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.editInfo(this.id, friendlyName, appTag, callback);
		}
	}
	
	public void get(OnCallback<Response<InputStream>> callback){
		if(this.videoDataModel != null){
			this.videoDataModel.get(id, callback);
		}
	}
	
	public long getId(){
		return this.id;
	}
	
	public String getFriendlyName(){
		return this.friendlyName;
	}
	
	public String getMimeType(){
		return this.mimeType;
	}
	
	public int getFileSize(){
		return this.fileSize;
	}
	
	public String getAppTag(){
		return this.appTag;
	}
	
	public long getOwner(){
		return this.owner;
	}
	
	public double getLatitude(){
		return this.latitude;
	} 
	
	public double getLongtidue(){
		return this.longitude;
	}
	
	public Date getUploadDate(){
		return this.uploadDate;
	}
	
	public Date getLastTouchDate(){
		return this.lastTouchDate;
	}
}
