package com.example.smartcar;

import java.io.*;
import java.net.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class LoginActivity extends Activity implements OnClickListener{

	private EditText editTextIP;
	private EditText editTextPort;
	private Button buttonConnect;
	
	private String serverIP = "192.168.219.105";
	private int serverPort = 8888;
	
	private Socket socket;
	private Toast toast;
	final Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		editTextIP = (EditText)findViewById(R.id.editTextIP);
		editTextIP.setText(serverIP);
		
		editTextPort = (EditText)findViewById(R.id.editTextPort);
		editTextPort.setText(String.valueOf(serverPort));
		
		buttonConnect = (Button)findViewById(R.id.buttonConnect);
		buttonConnect.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v == buttonConnect){
			serverIP = editTextIP.getText().toString();
			serverPort = Integer.parseInt(editTextPort.getText().toString());
			
			new Thread(new Runnable(){
				public void run(){
					try {
						socket = SingletonSocket.getSocket(serverIP, serverPort);
						
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					} catch (Exception e) {
						handler.post(new Runnable(){
							public void run(){
								showError();
							}
						});
						e.printStackTrace();
					}
				}
				
			}).start();
		}
	}
	
	public void showError(){
		toast = Toast.makeText(LoginActivity.this, "서버 연결을 실패했습니다.", Toast.LENGTH_SHORT);
		toast.show();
	}
	
}
