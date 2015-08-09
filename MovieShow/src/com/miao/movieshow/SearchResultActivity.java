package com.miao.movieshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.miao.movieshow.util.UtilData;

public class SearchResultActivity extends Activity {
	private String mStrJSON = "";
	private TextView tvTest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		tvTest = (TextView) findViewById(R.id.tvTest);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
