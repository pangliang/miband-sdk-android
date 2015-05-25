package com.zhaoxiaodan.miband;

import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

class BluetoothIO extends BluetoothGattCallback
{
	private static final String	TAG	= "BluetoothIO";
	BluetoothGatt				gatt;
	ActionCallback				currentCallback;
	
	public void connect(final Context context, final ActionCallback callback)
	{
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		adapter.startLeScan(new LeScanCallback() {
			@Override
			public void onLeScan(final BluetoothDevice device, final int rssi,
					final byte[] scanRecord)
			{
				Log.d(TAG,
						"onLeScan: name:" + device.getName() + ",uuid:"
								+ device.getUuids() + ",add:"
								+ device.getAddress() + ",type:"
								+ device.getType() + ",bondState:"
								+ device.getBondState() + ",rssi:" + rssi);
				
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				adapter.stopLeScan(this);
				BluetoothIO.this.currentCallback = callback;
				device.connectGatt(context, false, BluetoothIO.this);
			}
		});
	}
	
	public void writeAndRead(final UUID uuid, byte[] valueToWrite, final ActionCallback callback)
	{
		ActionCallback readCallback = new ActionCallback() {
			
			@Override
			public void onSuccess(Object characteristic)
			{
				BluetoothIO.this.readCharacteristic(uuid, callback);
			}
			
			@Override
			public void onFail(int errorCode, String msg)
			{
				callback.onFail(errorCode, msg);
			}
		};
		this.writeCharacteristic(uuid, valueToWrite, readCallback);
	}
	
	public void writeCharacteristic(UUID uuid, byte[] value, ActionCallback callback)
	{
		try
		{
			BluetoothGattCharacteristic chara = gatt.getService(Profile.UUID_SERVICE_MILI).getCharacteristic(uuid);
			if (null == chara)
			{
				callback.onFail(-1, "BluetoothGattCharacteristic " + uuid + " is not exsit");
				return;
			}
			this.currentCallback = callback;
			chara.setValue(value);
			this.gatt.writeCharacteristic(chara);
		} catch (Throwable tr)
		{
			Log.e(TAG, "writeCharacteristic", tr);
			callback.onFail(-1, tr.getMessage());
		}
	}
	
	public void readCharacteristic(UUID uuid, ActionCallback callback)
	{
		try
		{
			BluetoothGattCharacteristic chara = gatt.getService(Profile.UUID_SERVICE_MILI).getCharacteristic(uuid);
			if (null == chara)
			{
				callback.onFail(-1, "BluetoothGattCharacteristic " + uuid + " is not exsit");
				return;
			}
			this.currentCallback = callback;
			this.gatt.readCharacteristic(chara);
		} catch (Throwable tr)
		{
			Log.e(TAG, "readCharacteristic", tr);
			callback.onFail(-1, tr.getMessage());
		}
	}
	
	public void readRssi(ActionCallback callback)
	{
		try
		{
			this.currentCallback = callback;
			this.gatt.readRemoteRssi();
		} catch (Throwable tr)
		{
			Log.e(TAG, "readRssi", tr);
			callback.onFail(-1, tr.getMessage());
		}
		
	}
	
	@Override
	public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
	{
		super.onConnectionStateChange(gatt, status, newState);
		
		if (newState == BluetoothProfile.STATE_CONNECTED)
		{
			gatt.discoverServices();
		}
	}
	
	@Override
	public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
	{
		super.onCharacteristicRead(gatt, characteristic, status);
		if (BluetoothGatt.GATT_SUCCESS == status)
		{
			this.currentCallback.onSuccess(characteristic);
		} else
		{
			this.currentCallback.onFail(status, "onCharacteristicRead fail");
		}
	}
	
	@Override
	public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
	{
		super.onCharacteristicWrite(gatt, characteristic, status);
		if (BluetoothGatt.GATT_SUCCESS == status)
		{
			this.currentCallback.onSuccess(characteristic);
		} else
		{
			this.currentCallback.onFail(status, "onCharacteristicWrite fail");
		}
	}
	
	@Override
	public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status)
	{
		super.onReadRemoteRssi(gatt, rssi, status);
		if (BluetoothGatt.GATT_SUCCESS == status)
		{
			this.currentCallback.onSuccess(rssi);
		} else
		{
			this.currentCallback.onFail(status, "onCharacteristicRead fail");
		}
	}
	
	@Override
	public void onServicesDiscovered(BluetoothGatt gatt, int status)
	{
		super.onServicesDiscovered(gatt, status);
		if (status == BluetoothGatt.GATT_SUCCESS)
		{
			this.gatt = gatt;
			this.currentCallback.onSuccess(null);
		} else
		{
			this.currentCallback.onFail(status, "onServicesDiscovered fail");
		}
		this.currentCallback = null;
	}
}
