package com.miao.movieshow.util;

public class UtilData {
	public static int mCurrentFrag = 0;
	public static final int mFragShowing = 1;
	public static final int mFrag = 2;
	public static final int mFragSearch = 3;

	public static final int mGetDataSuccess = 0x111;
	public static final int mGetDataFailed = 0x110;
	public static final int mUpdatePhoto = 0x999;

	public static final String mSearchRresult = "SearchResult";

	// APPKey&Sign
	public static class cDeveloperInfo {
		public static final String mAppKey = "21618566";
		public static final String mSign = "adabca3a5254444cb7e8e9d18758f727";
	}

	// URL
	public static class cURL {
		public static final String mUrlBase = "http://api.dianping.com/v1/";
		// Search Business By Region
		public static final String mUrlSearchByRegion = "business/find_businesses_by_region";
	}

	// Search & Get Parameter
	public static class cSGParameter {
		public static final String mStrJSON = "strJSON";
		// Search
		public static final String mCity = "city";
		public static final String mLatitide = "latitude";
		public static final String mLongitude = "longitude";
		public static final String mCategory = "category";
		public static final String mRegion = "region";
		public static final String mLimit = "limit";
		public static final String mRadius = "radius";
		public static final String mOffset_type = "offset_type";
		public static final String mHas_coupon = "has_coupon";
		public static final String mHas_deal = "has_deal";
		public static final String mKeyword = "keyword";
		public static final String mSort = "sort";
		public static final String mFformat = "format";
		// Get
		public static final String mBusinesses = "businesses";
		public static final String mName = "name";
		public static final String mPhoto_url = "photo_url";
		public static final String mPhoto = "businesses_photo";
	}

	public static class CinemaShow {
	
	}
}
