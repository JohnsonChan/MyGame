/**
 * Copyright (c) 2014, 华南师范大学
 * All rights reserved
 * 
 * 文件名称：LogUtil.java
	* 文件标识：见配置管理计划书
 * 摘       要：
	* 
 * 当前版本：
 * 作       者：chen_zhuosheng
 * 完成日期：2014-3-2
	* 
 * 以前版本：
 * 作       者：
 * 完成日期：2014-3-2
 */
package com.czs.mygame.util;
import android.util.Log;

public class LogUtil
{
	public final static boolean DEBUG = true;
	private final static String TAG = "mygame ";

	private static void log(int type, String message)
	{
		StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];
		String className = stackTrace.getClassName();
		String tag = TAG + className.substring(className.lastIndexOf('.') + 1)
				+ "." + stackTrace.getMethodName() + "#"
				+ stackTrace.getLineNumber();

		switch (type)
		{
			case Log.DEBUG:
				Log.d(tag, message);
			break;

			case Log.INFO:
				Log.i(tag, message);
			break;

			case Log.WARN:
				Log.w(tag, message);
			break;

			case Log.ERROR:
				Log.e(tag, message);
			break;

			case Log.VERBOSE:
				Log.v(tag, message);
			break;
		}
	}

	public static void d(String message)
	{
		if (LogUtil.DEBUG)
			log(Log.DEBUG, message);
	}
	
	public static void d(int message)
	{
		if (LogUtil.DEBUG)
			log(Log.DEBUG, message+"");
	}
	
	public static void d(float message)
	{
		if (LogUtil.DEBUG)
			log(Log.DEBUG, message+"");
	}
	
	public static void d(double message)
	{
		if (LogUtil.DEBUG)
			log(Log.DEBUG, message+"");
	}


	public static void i(String message)
	{
		if (LogUtil.DEBUG)
			log(Log.INFO, message);
	}
	
	public static void i(int message)
	{
		if (LogUtil.DEBUG)
			log(Log.INFO, message+"");
	}

	public static void w(String message)
	{
		if (LogUtil.DEBUG)
			log(Log.WARN, message);
	}

	public static void e(String message)
	{
		log(Log.ERROR, message);
	}

	public static void v(String message)
	{
		if (LogUtil.DEBUG)
			log(Log.VERBOSE, message);
	}
}