package com.zhaoxiaodan.miband;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BatteryInfo
{
	
	enum Status
	{
		/**
		 * 未知状态
		 */
		unknow,
		
		/**
		 * 低电量
		 */
		low,
		
		/**
		 * 充电中
		 */
		charging,
		
		/**
		 * 满电, 且充电中
		 */
		fullAndCharging,
		
		/**
		 * 未在充电
		 */
		notChargin,
	}
	
	/**
	 * 电池电量百分比, level=40 表示有40%的电量
	 */
	private int		level;
	
	/**
	 * 充电循环次数
	 */
	private int		cycles;
	
	/**
	 * 当前状态
	 */
	private Status	status;
	
	/**
	 * 最后充电时间
	 */
	private Date	lastChargedDate;
	
	private BatteryInfo()
	{
		
	}
	
	public static BatteryInfo fromByteData(byte[] data)
	{
		if (data.length < 10)
		{
			return null;
		}
		BatteryInfo info = new BatteryInfo();
		
		info.level = data[0];
		info.status = data[9] <= 4 ? Status.values()[data[9]] : Status.unknow;
		info.cycles = 0xffff & (0xff & data[7] | (0xff & data[8]) << 8);
		try
		{
			info.lastChargedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.CHINA).parse(String.format(
					"%4d-%2d-%2d %2d:%2d:%2d", data[1] + 2000, data[2], data[3], data[4], data[5], data[6]));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return info;
	}
	
	public String toString()
	{
		return "cycles:" + this.getCycles()
				+ ",level:" + this.getLevel()
				+ ",status:" + this.getStatus()
				+ ",last:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.CHINA).format(this.getLastChargedDate());
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getCycles()
	{
		return cycles;
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public Date getLastChargedDate()
	{
		return lastChargedDate;
	}
}
