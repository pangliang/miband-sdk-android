# miband-sdk-android

[![Join the chat at https://gitter.im/pangliang/miband-sdk-android](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/pangliang/miband-sdk-android?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
小米手环sdk

## TODO
- 获取心跳数据
- 获取及设置睡眠信息
- 按时段获取及设置运动信息
- 收集通知类型

## Release Notes

### 1.0.11201903 - 2015-11-20

ios版本: 1.3.57 
android: 1.7.521
固件版本: 4.16.11.7
心率版本: 1.3.73.16 

- 扫描附近的Le设备, 附近存在多个手环时可以选择连接
- 添加设备断开监听器
- 连接之后无需再setUserInfo也可以使用, 并且setUserInfo 会导致心跳版手环断开连接, 待修复
- 修复震动问题, 现在有三种震动模式:三灯亮震2次, 无灯震2次, 中间灯震10次; 震动时可随时使用stop停止
- 重力感应数据不可用
- 心跳版好像是单色led灯, 无法设置led颜色; 原版可以

### 1.0.07171543 - 2015-08-17

- 获取动力感应器数据

### 1.0.05271733 - 2015-05-27

- 设置用户信息
- 获取实时步数通知
- 震动手环
- 设置led颜色
- 获取电池信息
- 获取信号强度RSSI值信息

## API

```java

// 实例化
MiBand miband = new MiBand(context);

// 扫描附近的设备
final ScanCallback scanCallback = new ScanCallback()
{
	@Override
	public void onScanResult(int callbackType, ScanResult result)
	{
		BluetoothDevice device = result.getDevice();
		Log.d(TAG,
				“找到附近的蓝牙设备: name:” + device.getName() + “,uuid:”
						+ device.getUuids() + “,add:”
						+ device.getAddress() + “,type:”
						+ device.getType() + “,bondState:”
						+ device.getBondState() + “,rssi:” + result.getRssi());
		// 根据情况展示
	}
};
MiBand.startScan(scanCallback);

// 停止扫描
MiBand.stopScan(scanCallback);

// 连接, 指定刚才扫描到的设备中的一个
miband.connect(device, new ActionCallback() {
						
	@Override
	public void onSuccess(Object data)
	{
		Log.d(TAG,"connect success");
	}
	
	@Override
	public void onFail(int errorCode, String msg)
	{
		Log.d(TAG,"connect fail, code:"+errorCode+",mgs:"+msg);
	}
});

// 设置断开监听器, 方便在设备断开的时候进行重连或者别的处理
miband.setDisconnectedListener(new NotifyListener()
{
	@Override
	public void onNotify(byte[] data)
	{
		Log.d(TAG,
				“连接断开!!!”);
	}
});

// 设置UserInfo, 貌似没啥用, 不配对也可以做其他的操作
// 并且在手环闪动的时候, 需要拍一下手环; 就像官方app 配对时一样
UserInfo userInfo = new UserInfo(20111111, 1, 32, 180, 55, "胖梁", 1);
miband.setUserInfo(userInfo);

// 配对, 貌似没啥用, 不配对也可以做其他的操作
miband.pair(new ActionCallback() {
	@Override
	public void onSuccess(Object data)
	{
		changeStatus("pair succ");
	}
	
	@Override
	public void onFail(int errorCode, String msg)
	{
		changeStatus("pair fail");
	}
});

// 读取和连接设备的信号强度Rssi值
miband.readRssi(new ActionCallback() {
		
	@Override
	public void onSuccess(Object data)
	{
		Log.d(TAG, "rssi:"+(int)data);
	}
	
	@Override
	public void onFail(int errorCode, String msg)
	{
		Log.d(TAG, "readRssi fail");
	}
});

// 读取手环电池信息
miband.getBatteryInfo(new ActionCallback() {
		
	@Override
	public void onSuccess(Object data)
	{
		BatteryInfo info = (BatteryInfo)data;
		Log.d(TAG, info.toString());
		//cycles:4,level:44,status:unknow,last:2015-04-15 03:37:55
	}
	
	@Override
	public void onFail(int errorCode, String msg)
	{
		Log.d(TAG, "readRssi fail");
	}
});

//震动2次， 三颗led亮
miband.startVibration(VibrationMode.VIBRATION_WITH_LED);

//震动2次, 没有led亮
miband.startVibration(VibrationMode.VIBRATION_WITHOUT_LED);

//震动10次, 中间led亮蓝色
miband.startVibration(VibrationMode.VIBRATION_10_TIMES_WITH_LED);

//停止震动, 震动时随时调用都可以停止
miband.stopVibration();

//获取普通通知, data一般len=1, 值为通知类型, 类型暂未收集
miband.setNormalNotifyListener(new NotifyListener() {
		
	@Override
	public void onNotify(byte[] data)
	{
		Log.d(TAG, "NormalNotifyListener:" + Arrays.toString(data));
	}
});

// 获取实时步数通知, 设置好后, 摇晃手环(需要持续摇动10-20下才会触发), 会实时收到当天总步数通知
// 使用分两步:
// 1.设置监听器

miband.setRealtimeStepsNotifyListener(new RealtimeStepsNotifyListener() {
		
	@Override
	public void onNotify(int steps)
	{
		Log.d(TAG, "RealtimeStepsNotifyListener:" + steps);
	}
});

// 2.开启通知
miband.enableRealtimeStepsNotify();

//关闭(暂停)实时步数通知, 再次开启只需要再次调用miband.enableRealtimeStepsNotify()即可
miband.disableRealtimeStepsNotify();

//设置led颜色, 橙, 蓝, 红, 绿
miband.setLedColor(LedColor.ORANGE);
miband.setLedColor(LedColor.BLUE);
miband.setLedColor(LedColor.RED);
miband.setLedColor(LedColor.GREEN);

// 获取重力感应器原始数据, 需要两步
// 1. 设置监听器
miband.setSensorDataNotifyListener(new NotifyListener()
{
	@Override
	public void onNotify(byte[] data)
	{
		int i = 0;

		int index = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;  // 序号
		int d1 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;    
		int d2 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;
		int d3 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;

	}
});

// 2. 开启
miband.enableSensorDataNotify();

```


## 参考文献

- [xiaomi-mi-band-ble-protocol-reverse](http://allmydroids.blogspot.de/2014/12/xiaomi-mi-band-ble-protocol-reverse.html)
- [xiaomi-miband-android](https://github.com/UgoRaffaele/xiaomi-miband-android)