package net.londatiga.android.bluebamboo;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

public class SerialPort {

	private static final String TAG = "SerialPort";

	/*
	 * Do not remove or rename the field mFd: it is used by native method close();
	 */
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;
	private int mBaudRate,mFlags;
	private String mPortPath="";
	private byte[] mRetData=new byte[512];
	
	public SerialPort(String path, int baudrate, int flags){
		mPortPath=path;
		mBaudRate=baudrate;
		mFlags=flags;
	}

	// Getters and setters
	public InputStream getInputStream() {
		return mFileInputStream;
	}

	public OutputStream getOutputStream() {
		return mFileOutputStream;
	}
	public int openPort() {
		mFd = open(mPortPath, mBaudRate, mFlags);
		if (mFd == null) {
			Log.e(TAG, "native open returns null");
			return -1;
		}
		mFileInputStream = new FileInputStream(mFd);
		mFileOutputStream = new FileOutputStream(mFd);
		
		return 0;
	}
	public int writePort(byte[] data,int bytes) throws IOException {		
		if (mFd == null) {
			Log.e(TAG, "port No open");
			return -1;
		}
		mFileOutputStream.write(data, 0, bytes);			
		return 0;
	}
	public int waitRead() throws IOException {
		int size;
		if (mFd == null) {
			Log.e(TAG, "port No open");
			return -1;
		}
		size = mFileInputStream.read(mRetData);		
		return size;
	}


	public int available() throws IOException {
		int size;
		if (mFd == null) {
			Log.e(TAG, "port No open");
			return -1;
		}
		size = mFileInputStream.available();		
		return size;
	}
	
	public byte[] readPort(){	
		return mRetData;
	}
	
	public void closePort() {
		close();
	}
	
	// JNI
	private native static FileDescriptor open(String path, int baudrate, int flags);
	public native void close();
	static {
		System.loadLibrary("serial_port");
	}
}
