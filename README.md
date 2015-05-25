# miband-sdk-android
小米手环sdk

# TODO, 实现 官方APP 小米运动 中的大部分功能, 如:
- 震动
- 设置led颜色
- 获取设备名称

# 如何使用, 及已实现功能
## 使用异步回调方式
## 文档
## 参考Demo : [MiBandTest](https://github.com/pangliang/MiBandTest)

## 连接
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

## 配对
```java
miband = new MiBand(context);

```
## 读取和连接设备的信号强度Rssi值
## 读取手环电池信息
## 
