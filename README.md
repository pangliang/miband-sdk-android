# miband-sdk-android
小米手环sdk

## 已实现

- 设置用户信息
- 获取实时步数通知
- 震动手环
- 设置led颜色
- 获取电池信息
- 获取信号强度RSSI值信息

## TODO, 实现 官方APP 小米运动 中的大部分功能, 如:

- 获取设备名称
- 获取及设置睡眠信息
- 按时段获取及设置运动信息


## 文档暂不完整，使用参考Demo : 
[MiBandTest](https://github.com/pangliang/MiBandTest)

## API

### 实例化
```java
miband = new MiBand(context);
```

### 连接
会自动搜索android设备附近的手环, 自动连接, 因为手上只有一个手环, 当前只支持搜索到一个手环的情况;

```java
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
### 设置UserInfo

重要, 连接完之后一定要设置UserInfo, 不然只能使用读取设备信息(RSSI, 电池)



### 配对, 貌似没啥用, 不配对也可以做其他的操作
```java
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


