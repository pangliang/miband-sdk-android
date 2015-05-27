package com.zhaoxiaodan.miband;

import java.util.Arrays;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.util.Log;

import com.zhaoxiaodan.miband.model.BatteryInfo;
import com.zhaoxiaodan.miband.model.LedColor;
import com.zhaoxiaodan.miband.model.Profile;
import com.zhaoxiaodan.miband.model.Protocol;
import com.zhaoxiaodan.miband.model.UserInfo;
import com.zhaoxiaodan.miband.model.VibrationMode;

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
	
	/**
	 * 会自动搜索android设备附近的手环, 自动连接, 因为手上只有一个手环, 当前只支持搜索到一个手环的情况;
	 * 
	 * @param callback
	 */
	public void connect(final ActionCallback callback)
	{
		this.io.connect(context, callback);
	}
	
	/**
	 * 和手环配对, 实际用途未知, 不配对也可以做其他的操作
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
	
	public BluetoothDevice getDevice()
	{
		return this.io.getDevice();
	}
	
	/**
	 * 读取和连接设备的信号强度RSSI值
	 * 
	 * @param callback
	 * @return data : int, rssi值
	 */
	public void readRssi(ActionCallback callback)
	{
		this.io.readRssi(callback);
	}
	
	/**
	 * 读取手环电池信息
	 * 
	 * @return {@link BatteryInfo}
	 */
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
	
	/**
	 * 让手环震动
	 */
	public void startVibration(VibrationMode mode)
	{
		byte[] protocal;
		switch (mode)
		{
		case VIBRATION_WITH_LED:
			protocal = Protocol.VIBRATION_WITH_LED;
			break;
		case VIBRATION_UNTIL_CALL_STOP:
			protocal = Protocol.VIBRATION_UNTIL_CALL_STOP;
			break;
		case VIBRATION_WITHOUT_LED:
			protocal = Protocol.VIBRATION_WITHOUT_LED;
			break;
		default:
			return;
		}
		this.io.writeCharacteristic(Profile.UUID_CHAR_CONTROL_POINT, protocal, null);
	}
	
	/**
	 * 停止以模式Protocol.VIBRATION_UNTIL_CALL_STOP 开始的震动
	 */
	public void stopVibration()
	{
		this.io.writeCharacteristic(Profile.UUID_CHAR_CONTROL_POINT, Protocol.STOP_VIBRATION, null);
	}
	
	public void setNormalNotifyListener(NotifyListener listener)
	{
		this.io.setNotifyListener(Profile.UUID_CHAR_NOTIFICATION, listener);
	}
	
	/**
	 * 实时步数通知监听器, 设置完之后需要另外使用 {@link MiBand#enableRealtimeStepsNotify} 开启 和
	 * {@link MiBand##disableRealtimeStepsNotify} 关闭通知
	 * 
	 * @param listener
	 */
	public void setRealtimeStepsNotifyListener(final RealtimeStepsNotifyListener listener)
	{
		this.io.setNotifyListener(Profile.UUID_CHAR_REALTIME_STEPS, new NotifyListener() {
			
			@Override
			public void onNotify(byte[] data)
			{
				Log.d(TAG, Arrays.toString(data));
				if (data.length == 4)
				{
					int steps = data[3] << 24 | (data[2] & 0xFF) << 16 | (data[1] & 0xFF) << 8 | (data[0] & 0xFF);
					listener.onNotify(steps);
				}
			}
		});
	}
	
	/**
	 * 开启实时步数通知
	 */
	public void enableRealtimeStepsNotify()
	{
		this.io.writeCharacteristic(Profile.UUID_CHAR_CONTROL_POINT, Protocol.ENABLE_REALTIME_STEPS_NOTIFY, null);
	}
	
	/**
	 * 关闭实时步数通知
	 */
	public void disableRealtimeStepsNotify()
	{
		this.io.writeCharacteristic(Profile.UUID_CHAR_CONTROL_POINT, Protocol.DISABLE_REALTIME_STEPS_NOTIFY, null);
	}
	
	/**
	 * 设置led灯颜色
	 */
	public void setLedColor(LedColor color)
	{
		byte[] protocal;
		switch (color)
		{
		case RED:
			protocal = Protocol.SET_COLOR_RED;
			break;
		case BLUE:
			protocal = Protocol.SET_COLOR_BLUE;
			break;
		case GREEN:
			protocal = Protocol.SET_COLOR_GREEN;
			break;
		case ORANGE:
			protocal = Protocol.SET_COLOR_ORANGE;
			break;
		default:
			return;
		}
		this.io.writeCharacteristic(Profile.UUID_CHAR_CONTROL_POINT, protocal, null);
	}
	
	/**
	 * 设置用户信息
	 * 
	 * @param userInfo
	 */
	public void setUserInfo(UserInfo userInfo)
	{
		BluetoothDevice device = this.io.getDevice();
		this.io.writeCharacteristic(Profile.UUID_CHAR_USER_INFO, userInfo.getBytes(device.getAddress()), null);
	}
	
	/**
	 * 自检 -- 作用未知
	 */
	public void selfTest()
	{
		this.io.writeCharacteristic(Profile.UUID_CHAR_TEST, Protocol.SELF_TEST, null);
	}
	
}
