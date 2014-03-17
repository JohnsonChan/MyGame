/**
 * Copyright (c) 2014, 华南师范大学
 * All rights reserved
 * 
 * 文件名称：ExitUtil.java
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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ExitUtil
{
	private static List<Activity> mList = new ArrayList<Activity>();

	private static ExitUtil instance;

	private ExitUtil()
	{

	}

	public static ExitUtil getInstance()
	{
		if (null == instance)
		{
			instance = new ExitUtil();
		}
		return instance;
	}

	public void remove(Activity activity)
	{

		mList.remove(activity);

	}

	public void addActivity(Activity activity)
	{
		mList.add(activity);
	}

	public static void exit()
	{
		try
		{
			for (Activity activity : mList)
			{
				if (activity != null)
					activity.finish();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

}