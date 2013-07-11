package com.buddy.sdk.unittests.utils;

import android.content.Context;
import android.content.ContextWrapper;

public class BuddyMockContext extends ContextWrapper {
	
	public BuddyMockContext(Context base) {
		super(null);
	}

	@Override
	public String getPackageName()
	{
		return "";
	}
}
