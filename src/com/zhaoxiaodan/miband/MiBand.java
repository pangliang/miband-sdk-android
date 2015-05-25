package com.zhaoxiaodan.miband;

import java.util.Arrays;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.util.Log;

public class MiBand
{
	
	private static final String	TAG	= "miband-android";
	
	private Context				context;
	private BluetoothIO			io;
	
	public MiBand(Context context)
	{
		this.context = context;
		this.io = new BluetoothIO();
	}
	
	public void connect(final ActionCallback callback)
	{
		this.io.connect(context, callback);
	}
	
	/**
	 * 和手环配对
	 * 
	 * @return data = null
	 */
	public void pair(final ActionCallback callback)
	{
		ActionCallback ioCallback = new ActionCallback() {
			
			@Override
			public void onSuccess(Object data)
			{
				BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) data;
				Log.d(TAG, "pair result " + Arrays.toString(characteristic.getValue()));
				if (characteristic.getValue().length == 1 && characteristic.getValue()[0] == 2)
				{
					callback.onSuccess(null);
				} else
				{
					callback.onFail(-1, "respone values no succ!");
				}
			}
			
			@Override
			public void onFail(int errorCode, String msg)
			{
				callback.onFail(errorCode, msg);
			}
		};
		this.io.writeAndRead(Profile.UUID_CHAR_PAIR, Protocol.PAIR, ioCallback);
	}
	
	/**
	 * 读取手环和当前连接设备间的RSSI值
	 * 
	 * @param callback
	 * @return data : int, rssi值
	 */
	public void readRssi(ActionCallback callback)
	{
		this.io.readRssi(callback);
	}
	
	public void getBatteryInfo(final ActionCallback callback)
	{
		ActionCallback ioCallback = new ActionCallback() {
			
			@Override
			public void onSuccess(Object data)
			{
				BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) data;
				Log.d(TAG, "getBatteryInfo result " + Arrays.toString(characteristic.getValue()));
				if (characteristic.getValue().length == 10)
				{
					BatteryInfo info = BatteryInfo.fromByteData(characteristic.getValue());
					callback.onSuccess(info);
				} else
				{
					callback.onFail(-1, "result format wrong!");
				}
			}
			
			@Override
			public void onFail(int errorCode, String msg)
			{
				callback.onFail(errorCode, msg);
			}
		};
		this.io.readCharacteristic(Profile.UUID_CHAR_BATTERY, ioCallback);
	}
	
}
