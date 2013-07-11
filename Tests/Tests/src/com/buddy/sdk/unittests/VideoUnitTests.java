package com.buddy.sdk.unittests;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.buddy.sdk.Video;
import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class VideoUnitTests extends BaseUnitTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		createAuthenticatedUser();
	}
	
	@Test public void testGetInfo(){
		String jsonVideo = readDataFromFileAsString("DataResponses/VideoUnitTest-GetVideo.json");
		
		BuddyHttpClientFactory.addDummyResponse(jsonVideo);
		testAuthUser.getVideos().getInfo((long)14, new OnCallback<Response<Video>>(){
			public void OnResponse(Response<Video> response, Object state){
				assertNotNull(response);
				
				Video video = response.getResult();
				
				assertNotNull(video);
			}
		});
	}
	
	@Test public void testGet(){
		//TODO
		InputStream videoStream = getStreamFromFile("test.mp4");
		
		BuddyHttpClientFactory.setDummyReponse(videoStream);
		
		testAuthUser.getVideos().get((long)14, new OnCallback<Response<InputStream>>(){
			public void OnResponse(Response<InputStream> response, Object state) {
				try{
					assertNotNull(response);
					InputStream stream = response.getResult();
					assertNotNull(stream);
					assertTrue(stream.available() > 0);
				}catch(Exception ex){
					assertTrue(false);
				}
			}
		});	
	}
	
	@Test public void testSearchVideos(){
		String jsonVideoList = readDataFromFileAsString("DataResponses/VideoUnitTest-GetVideo.json");
		
		BuddyHttpClientFactory.addDummyResponse(jsonVideoList);
		
		testAuthUser.getVideos().searchVideos("friendlyName.mp4", "video/", "AppTag", 100, 0.0, 0.0, 10, 20, new OnCallback<ListResponse<Video>>(){
			public void OnResponse(ListResponse<Video> response, Object state){
				assertNotNull(response);
				
				List<Video> list = response.getList();
				assertNotNull(list);
				
				Iterator<Video> iterator = list.iterator();
				while (iterator.hasNext()){
					Video video = iterator.next();
					assertNotNull(video);
					//TODO More
				}
			}
		});
	}
	
	@Test public void testSearchMyVideos(){
		String jsonVideoList = readDataFromFileAsString("DataResponses/VideoUnitTest-GetVideo.json");
		
		BuddyHttpClientFactory.addDummyResponse(jsonVideoList);
		
		testAuthUser.getVideos().searchMyVideos("friendlyName.mp4", "video/", "AppTag", 100, 0.0, 0.0, 10, 20, new OnCallback<ListResponse<Video>>(){
			public void OnResponse(ListResponse<Video> response, Object state){
				assertNotNull(response);
				
				List<Video> list = response.getList();
				assertNotNull(list);
				
				Iterator<Video> iterator = list.iterator();
				while (iterator.hasNext()){
					Video video = iterator.next();
					assertNotNull(video);
					//TODO More
				}
			}
		});
	}
	
	@Test public void testGetList(){
		String jsonVideoList = readDataFromFileAsString("DataResponses/VideoUnitTest-GetVideo.json");
		
		BuddyHttpClientFactory.addDummyResponse(jsonVideoList);
		
		testAuthUser.getVideos().getList(-1, 10, new OnCallback<ListResponse<Video>>(){
			public void OnResponse(ListResponse<Video> response, Object state){
				assertNotNull(response);
				
				List<Video> list = response.getList();
				assertNotNull(list);
				
				Iterator<Video> iterator = list.iterator();
				while (iterator.hasNext()){
					Video video = iterator.next();
					assertNotNull(video);
					//TODO More
				}
			}
		});
	}
	
	@Test public void testGetMyList(){
		String jsonVideoList = readDataFromFileAsString("DataResponses/VideoUnitTest-GetVideo.json");
		
		BuddyHttpClientFactory.addDummyResponse(jsonVideoList);
		
		testAuthUser.getVideos().getMyList(10, new OnCallback<ListResponse<Video>>(){
			public void OnResponse(ListResponse<Video> response, Object state){
				assertNotNull(response);
				
				List<Video> list = response.getList();
				assertNotNull(list);
				
				Iterator<Video> iterator = list.iterator();
				while (iterator.hasNext()){
					Video video = iterator.next();
					assertNotNull(video);
					//TODO More
				}
			}
		});
	}
}
