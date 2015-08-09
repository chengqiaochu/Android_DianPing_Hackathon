package com.miao.movieshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.miao.movieshow.util.UtilData;

public class SearchResultActivity extends Activity {
	private String mStrJSON = "";
	private TextView tvTest;
	private ListView mLvCinema;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		setContentView(R.layout.activity_search_result);

		tvTest = (TextView) findViewById(R.id.tvTest);
		mLvCinema = (ListView) findViewById(R.id.lvCinema);
		Intent mIntent = getIntent();
		mStrJSON = mIntent.getStringExtra(UtilData.cSGParameter.mStrJSON);
		if (mStrJSON == null || mStrJSON.equals("")) {
			Toast.makeText(getApplication(), "Get Data Failed!!!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(getApplication(), "Msg:" + mStrJSON, Toast.LENGTH_SHORT)
				.show();
		tvTest.setText(mStrJSON);

		Map<String, Object> listItem = new HashMap<String, Object>();

		listItem.put(UtilData.cSGParameter.mPhoto, R.drawable.ic_launcher);
		listItem.put(UtilData.cSGParameter.mName, "XX电影院");
		listItem.put(UtilData.cSGParameter.mPhoto_url, "距离：XXX");

		listItems.add(listItem);

		listItem.put(UtilData.cSGParameter.mPhoto, R.drawable.ic_launcher);
		listItem.put(UtilData.cSGParameter.mName, "XX电影院");
		listItem.put(UtilData.cSGParameter.mPhoto_url, "距离：XXX");

		listItems.add(listItem);
		// adapter = new SimpleAdapter(this, listItems, R.layout.list_cinema,
		// new String[] { UtilData.cSGParameter.mPhoto,
		// UtilData.cSGParameter.mName,
		// UtilData.cSGParameter.mPhoto_url }, new int[] {
		// R.id.ivlistPhoto, R.id.tvlistName, R.id.tvlistURL });
		adapter = new SimpleAdapter(this, listItems, R.layout.list_cinema,
				new String[] { UtilData.cSGParameter.mName },
				new int[] { R.id.tvlistName });
		mLvCinema.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
