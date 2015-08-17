# miband-sdk-android
小米手环sdk

## Release Notes

### 1.0.07171543 - 2015-08-17

- 获取动力感应器数据

### 1.0.05271733 - 2015-05-27
[Download](https://github.com/pangliang/miband-sdk-android/releases/tag/1.0.05271733)

- 设置用户信息
- 获取实时步数通知
- 震动手环
- 设置led颜色
- 获取电池信息
- 获取信号强度RSSI值信息

## TODO

- 获取设备名称
- 获取及设置睡眠信息
- 按时段获取及设置运动信息
- 收集通知类型


## 使用方法, 一下两种都可以
- 将miband-sdk-android clone到本地之后import 进eclipse, 在自己的项目中引用
- 直接导入jar包

## !!!!!!!注意!!!!!!!!
连接完之后一定要设置UserInfo, 不然只能使用读取设备信息(RSSI, 电池)

## API

```java

// 实例化
MiBand miband = new MiBand(context);

// 连接, 会自动搜索android设备附近的手环, 自动连接
// 因为手上只有一个手环, 当前只支持搜索到一个手环的情况;
miband.connect(new ActionCallback() {
						
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

// 设置UserInfo
// 重要, 连接完之后一定要设置UserInfo, 不然只能使用读取设备信息(RSSI, 电池)
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

//震动， 中间一颗led闪
miband.startVibration(VibrationMode.VIBRATION_WITH_LED);

//震动, 没有led亮
miband.startVibration(VibrationMode.VIBRATION_WITHOUT_LED);

//会一直震动, 直到调用stop
miband.startVibration(VibrationMode.VIBRATION_UNTIL_CALL_STOP);

//停止震动
miband.stopVibration();

//获取普通通知, data一般len=1, 值为通知类型, 类型暂未收集
miband.setNormalNotifyListener(new NotifyListener() {
		
	@Override
	public void onNotify(byte[] data)
	{
		Log.d(TAG, "NormalNotifyListener:" + Arrays.toString(data));
	}
});

// 获取实时步数通知, 设置好后, 摇晃手环(走路), 会实时收到当天总步数通知
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


```


## 参考文献

- [xiaomi-mi-band-ble-protocol-reverse](http://allmydroids.blogspot.de/2014/12/xiaomi-mi-band-ble-protocol-reverse.html)
- [xiaomi-miband-android](https://github.com/UgoRaffaele/xiaomi-miband-android)