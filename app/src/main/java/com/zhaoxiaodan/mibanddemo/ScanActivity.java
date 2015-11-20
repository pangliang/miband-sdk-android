package com.zhaoxiaodan.mibanddemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zhaoxiaodan.miband.ActionCallback;
import com.zhaoxiaodan.miband.MiBand;
import com.zhaoxiaodan.miband.listeners.NotifyListener;
import com.zhaoxiaodan.miband.listeners.RealtimeStepsNotifyListener;
import com.zhaoxiaodan.miband.model.BatteryInfo;
import com.zhaoxiaodan.miband.model.LedColor;
import com.zhaoxiaodan.miband.model.UserInfo;
import com.zhaoxiaodan.miband.model.VibrationMode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ScanActivity extends Activity
{
	private static final String	TAG		= "==[mibandtest]==";
	private MiBand				miband;

	
	HashMap<String,BluetoothDevice> devices	= new HashMap<String,BluetoothDevice>();


	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan);

		miband = new MiBand(this);

		final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.item, new ArrayList<String>());

		final ScanCallback scanCallback = new ScanCallback()
		{
			@Override
			public void onScanResult(int callbackType, ScanResult result)
			{
				BluetoothDevice device = result.getDevice();
				Log.d(TAG,
						"找到附近的蓝牙设备: name:" + device.getName() + ",uuid:"
								+ device.getUuids() + ",add:"
								+ device.getAddress() + ",type:"
								+ device.getType() + ",bondState:"
								+ device.getBondState() + ",rssi:" + result.getRssi());

				String item = device.getName() + "|" + device.getAddress();
				if (!devices.containsKey(item))
				{
					devices.put(item, device);
					adapter.add(item);
				}

			}
		};


		((Button) findViewById(R.id.starScanButton)).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "开始扫描附近的Le蓝牙设备...");
				MiBand.startScan(scanCallback);
			}
		});

		((Button)findViewById(R.id.stopScanButton)).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "停止扫描...");
				MiBand.stopScan(scanCallback);
			}
		});


		ListView lv = (ListView)findViewById(R.id.listView);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				String item = ((TextView)view).getText().toString();
				if (devices.containsKey(item))
				{

					Log.d(TAG, "停止扫描...");
					MiBand.stopScan(scanCallback);

					BluetoothDevice device = devices.get(item);
					Intent intent = new Intent();
					intent.putExtra("device",device);
					intent.setClass(ScanActivity.this, MainActivity.class);
					ScanActivity.this.startActivity(intent);
					ScanActivity.this.finish();
				}
			}
		});
		
	}
}
