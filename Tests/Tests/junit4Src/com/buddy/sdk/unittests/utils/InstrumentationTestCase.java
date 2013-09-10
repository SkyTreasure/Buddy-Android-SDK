package com.buddy.sdk.unittests.utils;

import java.io.InputStream;

import android.content.res.AssetManager;

public abstract class InstrumentationTestCase extends android.test.InstrumentationTestCase {

    protected InputStream getStreamFromFile(String fileName){
    	try{AssetManager m = getInstrumentation().getContext().getAssets();
    		return m.open(fileName);
    	}catch(Exception e){
    		// TODO Auto-generated catch block
            e.printStackTrace();
            return null;
    	}
    }
}
