package com.example.smartcar;

import java.io.*;
import java.net.*;
import android.app.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.RadioGroup.*;

public class MainActivity extends Activity implements OnClickListener, OnCheckedChangeListener{
	
	private BackPressCloseHandler backPressCloseHandler;
	
	private String videoURL;
	
	private Socket socket;
	private OutputStream outs;
	public logger logger;
	private String sndOpkey;
	private Thread serverThread;
	
	private DualJoystickView joystick;
	private TextView textViewStatus;
	private ImageView buttonUp;
	private ImageView buttonDown;
	private ImageView buttonLeft;
	private ImageView buttonRight;

	private RadioGroup radioGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		socket = SingletonSocket.getSocket();
		videoURL = "rtsp://" + socket.getInetAddress().getHostAddress() + ":8554/test";
		try {
			outs = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MyVideoView videoView = (MyVideoView)findViewById(R.id.videoView);
		videoView.setVideoURI(Uri.parse(videoURL));
		videoView.setMediaController(null);					//control bar remove
		videoView.requestFocus();
		videoView.start();
		
		joystick = (DualJoystickView) findViewById(R.id.dualjoystickView);
		joystick.setOnJostickMovedListener(_listenerLeft, _listenerRight);
		
		textViewStatus = (TextView)this.findViewById(R.id.textViewStatus);
		textViewStatus.setText("Connected");
		
		logger = new logger(textViewStatus);
		
		serverThread = new Thread(new ServerThread(logger, socket));
		serverThread.start();
		
		buttonUp = (ImageView)this.findViewById(R.id.buttonUp);
		buttonDown = (ImageView)this.findViewById(R.id.buttonDown);
		buttonLeft = (ImageView)this.findViewById(R.id.buttonLeft);
		buttonRight = (ImageView)this.findViewById(R.id.buttonRight);
		
		buttonUp.setOnClickListener(this);
		buttonDown.setOnClickListener(this);
		buttonLeft.setOnClickListener(this);
		buttonRight.setOnClickListener(this);
		
		radioGroup = (RadioGroup)this.findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(this);
		
		backPressCloseHandler = new BackPressCloseHandler(this);
	}

	@Override
	public void onClick(View v) {
		
		if(v == buttonUp){
			sndOpkey = "camUp";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}
		
		if(v == buttonDown){
			sndOpkey = "camDown";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}
		
		if(v == buttonLeft){
			sndOpkey = "camLeft";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}
		
		if(v == buttonRight){
			sndOpkey = "camRight";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.pwm1Radio:
			sndOpkey = "pwmPower1";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
			break;
		case R.id.pwm2Radio:
			sndOpkey = "pwmPower2";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
			break;
		case R.id.pwm3Radio:
			sndOpkey = "pwmPower3";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
			break;
		case R.id.pwm4Radio:
			sndOpkey = "pwmPower4";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
			break;
		}
	};
	
	private JoystickMovedListener _listenerLeft = new JoystickMovedListener() {

		@Override
		public void OnMoved(int pan, int tilt) {
			if(tilt < 0){
				sndOpkey = "Up";
			}
			
			if(tilt > 0){
				sndOpkey = "Down";
			}
			
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}

		@Override
		public void OnReleased() {
			sndOpkey = "Motor1_Stop";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}

		public void OnReturnedToCenter() {
			sndOpkey = "Motor1_Stop";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}
	};

	private JoystickMovedListener _listenerRight = new JoystickMovedListener() {

		@Override
		public void OnMoved(int pan, int tilt) {
			if(pan < 0){
				sndOpkey = "Left";
			}
			
			if(pan >0){
				sndOpkey = "Right";
			}
			
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}

		@Override
		public void OnReleased() {
			sndOpkey = "Motor2_Stop";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}

		public void OnReturnedToCenter() {
			sndOpkey = "Motor2_Stop";
			try{
				outs.write(sndOpkey.getBytes("UTF-8"));
				outs.flush();
			} catch (IOException e){
				logger.log("Fail to send");
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}

}
