package com.example.smartcar;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class MyVideoView extends VideoView{

	public MyVideoView(Context context) {
		super(context);
	}

	public MyVideoView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public MyVideoView(Context context, AttributeSet attrs, int defstyle){
		super(context, attrs, defstyle);
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
		int deviceWidth = displayMetrics.widthPixels;
		int deviceHeight = displayMetrics.heightPixels;
		setMeasuredDimension(deviceWidth, deviceHeight);
	}
}
