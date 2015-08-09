package com.miao.movieshow.movie;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.miao.movieshow.R;
import com.miao.movieshow.util.UtilAPI;
import com.miao.movieshow.util.UtilData;

public class FragSearch extends Fragment {
	private EditText mEtSearch;
	private Button mBtnSearch;
	private Activity mActivity;
	private String mStrMovieName;

	LocationClient mLocationClient;
	MyLocationListener mMyLocationListener;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == UtilData.mGetDataSuccess) {
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		initView();
	}

	private void initView() {
		mActivity = getActivity();
		mEtSearch = (EditText) mActivity.findViewById(R.id.etMovieSearch);
		mBtnSearch = (Button) mActivity.findViewById(R.id.btnMovieSearch);
		mBtnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mStrMovieName = mEtSearch.getText().toString();
				if (mStrMovieName == null || mStrMovieName.equals("")) {
					Toast.makeText(mActivity, "please input name of movie",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(mActivity, "定位中，请稍后", Toast.LENGTH_SHORT).show();
				new Thread() {
					@Override
					public void run() {

					}
				}.start();
				LocationClientOption option = new LocationClientOption();
				option.setLocationMode(LocationMode.Hight_Accuracy);
				option.setCoorType("bd09ll");
				option.setIsNeedAddress(true);

				mLocationClient = new LocationClient(mActivity);
				mMyLocationListener = new MyLocationListener();
				mLocationClient.registerLocationListener(mMyLocationListener);
				mLocationClient.setLocOption(option);
				mLocationClient.start();
			}
		});
	}

	private void getData() {
		// String apiUrl = UtilData.cURL.mUrlBase
		// + UtilData.cURL.mUrlSearchByRegion;
		//
		// Map<String, String> paramMap = new HashMap<String, String>();
		// paramMap.put(UtilData.cSGParameter.mCity, "上海");
		// paramMap.put(UtilData.cSGParameter.mCategory, "电影院");

		// String requestResult = UtilAPI.requestApi(apiUrl,
		// UtilData.cDeveloperInfo.mAppKey, UtilData.cDeveloperInfo.mSign,
		// paramMap);
		//
		// Bundle bundleData = new Bundle();
		// bundleData.putString(UtilData.mSearchRresult, requestResult);
		//
		// Message msg = Message.obtain();
		// msg.what = UtilData.mGetDataSuccess;
		// msg.setData(bundleData);
		// mHandler.sendMessage(msg);

		String requestResult = "{\"city\":\"JACK\","
				+ "\"categoty\":[\"Ningbo\",\"Shanghai\"],"
				+ "\"Tel\":{\"companyTel\":\"654321\",\"homeTel\":\"123456\"}}";
		Bundle bundleData = new Bundle();
		bundleData.putString(UtilData.mSearchRresult, requestResult);

		Message msg = Message.obtain();
		msg.what = UtilData.mGetDataSuccess;
		msg.setData(bundleData);
		mHandler.sendMessage(msg);
		Log.e("TEST", "GETDATA funciotn");
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			String str = "pos:" + location.getAddrStr() + ","
					+ location.getRadius() + "," + location.getLatitude() + ","
					+ location.getLongitude();
			Log.e("SDSFD", str);
			Toast.makeText(mActivity.getApplicationContext(), str,
					Toast.LENGTH_SHORT).show();
			new Thread() {
				@Override
				public void run() {
					getData();
				}
			}.start();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_movie_search, container,
				false);
		return rootView;
	}
}
