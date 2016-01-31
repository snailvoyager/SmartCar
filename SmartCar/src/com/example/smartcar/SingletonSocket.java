package com.example.smartcar;

import java.io.*;
import java.net.*;

public class SingletonSocket {
	private static SingletonSocket mInstance = new SingletonSocket();
	private static Socket mSocket;
	
	private SingletonSocket(){
		
	}
	
	public static Socket getSocket(String ip, int port) throws IOException{
		mSocket = new Socket(ip, port);
		return mSocket;
	}
	
	public static Socket getSocket(){
		return mSocket;
	}
	
	public static void closeSocket() throws IOException{
		mSocket.close();
	}
}
