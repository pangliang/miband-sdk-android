# miband-sdk-android
小米手环sdk

## TODO, 实现 官方APP 小米运动 中的大部分功能, 如:
- 震动
- 设置led颜色
- 获取设备名称


## 使用异步回调方式

## 使用参考Demo : 
[MiBandTest](https://github.com/pangliang/MiBandTest)

## 如何使用, 及已实现功能
### 连接
会自动搜索android设备附近的手环, 自动连接, 因为手上只有一个手环, 当前只支持搜索到一个手环的情况;

```java
miband = new MiBand(context);
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
```

### 配对, 貌似没啥用, 不配对也可以做其他的操作
```java
miband = new MiBand(context);
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
```
### 读取和连接设备的信号强度Rssi值
```java
miband = new MiBand(context);
miband.readRssi(new ActionCallback() {
		
	@Override
	public void onSuccess(Object data)
	{
		changeStatus("rssi:"+(int)data);
	}
	
	@Override
	public void onFail(int errorCode, String msg)
	{
		changeStatus("readRssi fail");
	}
});
```
### 读取手环电池信息
```java
miband = new MiBand(context);
miband.getBatteryInfo(new ActionCallback() {
		
	@Override
	public void onSuccess(Object data)
	{
		BatteryInfo info = (BatteryInfo)data;
		changeStatus(info.toString());
		//cycles:4,level:44,status:unknow,last:2015-04-15 03:37:55
	}
	
	@Override
	public void onFail(int errorCode, String msg)
	{
		changeStatus("readRssi fail");
	}
});
```


