package com.zhaoxiaodan.miband;

public class Protocol
{
	public static final byte[]	PAIR							= { 2 };
	public static final byte[]	TEST							= { 2 };
	public static final byte[]	VIBRATION						= { 8, 2 };
	public static final byte[]	STOP_VIBRATION					= { 19 };
	public static final byte[]	ENABLE_REALTIME_STEPS_NOTIFY	= { 3, 1 };
	public static final byte[]	DISABLE_REALTIME_STEPS_NOTIFY	= { 3, 1 };
	public static final byte[]	SET_COLOR_RED					= { 14, 6, 1, 2, 1 };
	public static final byte[]	SET_COLOR_BLUE					= { 14, 0, 6, 6, 1 };
	public static final byte[]	SET_COLOR_ORANGE				= { 14, 6, 2, 0, 1 };
	public static final byte[]	SET_COLOR_GREEN					= { 14, 4, 5, 0, 1 };
}
