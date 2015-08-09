package com.miao.movieshow;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.miao.movieshow.movie.FragEmpty;
import com.miao.movieshow.movie.FragSearch;
import com.miao.movieshow.movie.FragShowing;
import com.miao.movieshow.util.UtilData;

public class MainActivity extends Activity {
	private Button mBtnShowing;
	private Button mBtnSearch;
	private Button mBtnEmpty;
	private Fragment mContent = new FragShowing();
	private FragShowing mFragShowing = new FragShowing();
	private FragSearch mFragSearch = new FragSearch();
	private FragEmpty mFragEmpty = new FragEmpty();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		setContentView(R.layout.activity_main);
		initView();

		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder()
		// .permitAll().build();
		// StrictMode.setThreadPolicy(policy);

		mBtnShowing.callOnClick();
	}

	private void initView() {
		mBtnShowing = (Button) findViewById(R.id.btnShowing);
		mBtnSearch = (Button) findViewById(R.id.btnSearch);
		mBtnEmpty = (Button) findViewById(R.id.btnEmpty);
		mBtnShowing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changBtnColor(UtilData.mFragShowing);
				switchContent(mContent, mFragShowing);
				// FragShowing fragment = new FragShowing();
				// getFragmentManager().beginTransaction()
				// .replace(R.id.funcImplement, fragment).commit();
			}
		});
		mBtnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changBtnColor(UtilData.mFragSearch);
				// FragSearch fragment = new FragSearch();
				// getFragmentManager().beginTransaction()
				// .replace(R.id.funcImplement, fragment).commit();
				switchContent(mContent, mFragSearch);
			}
		});
		mBtnEmpty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changBtnColor(UtilData.mFrag);
				switchContent(mContent, mFragEmpty);
			}
		});
	}

	private void changBtnColor(int id) {
		switch (UtilData.mCurrentFrag) {
		case UtilData.mFragSearch:
			mBtnSearch.setBackgroundColor(getResources().getColor(
					R.color.orange));
			break;
		case UtilData.mFrag:
			mBtnEmpty.setBackgroundColor(getResources()
					.getColor(R.color.orange));
			break;
		case UtilData.mFragShowing:
			mBtnShowing.setBackgroundColor(getResources().getColor(
					R.color.orange));
			break;
		}
		switch (id) {
		case UtilData.mFragSearch:
			mBtnSearch.setBackgroundColor(getResources().getColor(R.color.red));
			break;
		case UtilData.mFrag:
			mBtnEmpty.setBackgroundColor(getResources().getColor(R.color.red));
			break;
		case UtilData.mFragShowing:
			mBtnShowing
					.setBackgroundColor(getResources().getColor(R.color.red));
			break;
		}
		UtilData.mCurrentFrag = id;
	}

	public void switchContent(Fragment from, Fragment to) {
		if (mContent != to) {
			mContent = to;
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(from).add(R.id.funcImplement, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
