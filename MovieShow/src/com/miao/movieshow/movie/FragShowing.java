package com.miao.movieshow.movie;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.miao.movieshow.R;
import com.miao.movieshow.util.UtilAPI;
import com.miao.movieshow.util.UtilData;

public class FragShowing extends Fragment {
	private ListView mListShowing;
	private Activity mActivity;
	private String[] mArrPhotoURL;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

	private Bitmap bitmap;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == UtilData.mGetDataSuccess) {

				Bundle bundle = msg.getData();
				listItems.clear();
				try {
					JSONObject jsonObject = new JSONObject(
							bundle.getString(UtilData.mSearchRresult));
					JSONArray jsonArray = jsonObject
							.getJSONArray(UtilData.cSGParameter.mBusinesses);

					mArrPhotoURL = new String[jsonArray.length()];
					for (int i = 0; i < jsonArray.length(); ++i) {
						JSONObject jsonTemp = jsonArray.getJSONObject(i);

						String mStrName = jsonTemp
								.getString(UtilData.cSGParameter.mName);
						String mStrPhotoUrl = jsonTemp
								.getString(UtilData.cSGParameter.mPhoto_url);
						mArrPhotoURL[i] = mStrPhotoUrl;
						Map<String, Object> listItem = new HashMap<String, Object>();

						listItem.put(UtilData.cSGParameter.mPhoto,
								R.drawable.ic_launcher);
						listItem.put(UtilData.cSGParameter.mName, mStrName);
						listItem.put(UtilData.cSGParameter.mPhoto_url,
								mStrPhotoUrl);

						listItems.add(listItem);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				adapter = new SimpleAdapter(mActivity, listItems,
						R.layout.list_showing, new String[] {
								UtilData.cSGParameter.mPhoto,
								UtilData.cSGParameter.mName,
								UtilData.cSGParameter.mPhoto_url }, new int[] {
								R.id.ivlistPhoto, R.id.tvlistName,
								R.id.tvlistURL });
				adapter.setViewBinder(new ViewBinder() {

					public boolean setViewValue(View view, Object data,
							String textRepresentation) {
						if (view instanceof ImageView && data instanceof Bitmap) {
							ImageView iv = (ImageView) view;

							iv.setImageBitmap((Bitmap) data);
							return true;
						} else
							return false;
					}
				});

				mListShowing.setAdapter(adapter);
				new Thread() {
					@Override
					public void run() {
						UpdatePhoto();
					}
				}.start();
			} else if (msg.what == UtilData.mUpdatePhoto) {
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(mActivity, "FAILED", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private void UpdatePhoto() {
		for (int i = 0; i < mArrPhotoURL.length; ++i) {
			URL url;
			try {
				url = new URL(mArrPhotoURL[i]);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				InputStream is = conn.getInputStream();

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.inSampleSize = 10;
				bitmap = BitmapFactory.decodeStream(is);

				listItems.get(i).put(UtilData.cSGParameter.mPhoto, bitmap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mHandler.sendEmptyMessage(UtilData.mUpdatePhoto);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		initView();
	}

	private void getData() {
		String apiUrl = UtilData.cURL.mUrlBase
				+ UtilData.cURL.mUrlSearchByRegion;

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(UtilData.cSGParameter.mCity, "上海");
		paramMap.put(UtilData.cSGParameter.mCategory, "电影院");

		String requestResult = UtilAPI.requestApi(apiUrl,
				UtilData.cDeveloperInfo.mAppKey, UtilData.cDeveloperInfo.mSign,
				paramMap);

		Bundle bundleData = new Bundle();
		bundleData.putString(UtilData.mSearchRresult, requestResult);

		Message msg = Message.obtain();
		msg.what = UtilData.mGetDataSuccess;
		msg.setData(bundleData);
		mHandler.sendMessage(msg);
	}

	private void initView() {
		mActivity = getActivity();
		mListShowing = (ListView) mActivity.findViewById(R.id.lvShowing);
		new Thread() {
			@Override
			public void run() {
				getData();
			}
		}.start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_movie_showing,
				container, false);
		return rootView;
	}
}
