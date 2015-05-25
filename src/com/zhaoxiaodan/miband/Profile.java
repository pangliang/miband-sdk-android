package com.zhaoxiaodan.miband;

import java.util.UUID;

public class Profile
{
	/**
	 * 主要的service
	 */
	public static final UUID	UUID_SERVICE_MILI			= UUID.fromString("0000fee0-0000-1000-8000-00805f9b34fb");
	
	/**
	 * 未知作用
	 */
	public static final UUID	UUID_SERVICE_UNKNOWN1		= UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_SERVICE_UNKNOWN2		= UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_SERVICE_UNKNOWN3		= UUID.fromString("00001802-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_SERVICE_UNKNOWN4		= UUID.fromString("0000fee1-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_SERVICE_UNKNOWN5		= UUID.fromString("0000fee7-0000-1000-8000-00805f9b34fb");

	
	public static final UUID	UUID_CHAR_DEVICE_INFO		= UUID.fromString("0000ff01-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_DEVICE_NAME		= UUID.fromString("0000ff02-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_NOTIFICATION		= UUID.fromString("0000ff03-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_USER_INFO			= UUID.fromString("0000ff04-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_CONTROL_POINT		= UUID.fromString("0000ff05-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_REALTIME_STEPS	= UUID.fromString("0000ff06-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_ACTIVITY			= UUID.fromString("0000ff07-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_FIRMWARE_DATA		= UUID.fromString("0000ff08-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_LE_PARAMS			= UUID.fromString("0000ff09-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_DATA_TIME			= UUID.fromString("0000ff0a-0000-1000-8000-00805f9b34fb");
	public static final UUID	UUID_CHAR_STATISTICS		= UUID.fromString("0000ff0b-0000-1000-8000-00805f9b34fb");
	
	/**
	 * 特性:电池
	 * 权限:只读,通知
	 */
	public static final UUID	UUID_CHAR_BATTERY			= UUID.fromString("0000ff0c-0000-1000-8000-00805f9b34fb");
	
	/**
	 * 特性:测试
	 * 权限:读写
	 */
	public static final UUID	UUID_CHAR_TEST				= UUID.fromString("0000ff0d-0000-1000-8000-00805f9b34fb");
	
	/**
	 * 特性:配对
	 * 权限:读写
	 */
	public static final UUID	UUID_CHAR_PAIR				= UUID.fromString("0000ff0f-0000-1000-8000-00805f9b34fb");
}
