package com.wxhl.core.utils;

import android.content.Context;
import android.content.res.Resources;

import com.wxhl.core.R;
import com.wxhl.core.activity.CoreApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	// yyyy-MM-dd hh:mm:ss 12小时制
	// yyyy-MM-dd HH:mm:ss 24小时制

	public static final SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat df3 = new SimpleDateFormat("yyyy/MM/dd");
	public static final SimpleDateFormat df4 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	public static final SimpleDateFormat df5 = new SimpleDateFormat("Gyyyy年MM月dd日");
	public static final SimpleDateFormat df6 = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat df7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat SDF = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat df8 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	public static final DateFormat DATE_FULL_SDF = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");

	//2016-01-07T00:07:18+08:00
	public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


	public static final DateFormat DF = new SimpleDateFormat("MM月dd日");

	public static final String TYPE_01 = "yyyy-MM-dd HH:mm:ss";

	public static final String TYPE_02 = "yyyy-MM-dd";

	public static final String TYPE_03 = "HH:mm:ss";

	public static final String TYPE_04 = "yyyy年MM月dd日";

	public static final String TYPE_05 = "yyyy年MM月";

	private static final String[] WEEK = { "天", "一", "二", "三", "四", "五", "六" };
	public static final String XING_QI = "星期";
	public static final String ZHOU = "周";



	public static String dateToString(DateFormat df, Date date) {
		return df.format(date);
	}


	public static String formatDate(long time, String format) {
		Calendar cal = Calendar.getInstance();
		if(time>0)
			cal.setTimeInMillis(time);
		return new SimpleDateFormat(format).format(cal.getTime());
	}



	public static String formatDate(String longStr, String format) {
		try {
			return formatDate(Long.parseLong(longStr), format);
		} catch (Exception e) {
		}
		return "";
	}



	public static long formatStr(String timeStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(timeStr).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getWeek(int num, String format) {
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int weekNum = c.get(Calendar.DAY_OF_WEEK) + num;
		if (weekNum > 7)
			weekNum = weekNum - 7;
		return format + WEEK[weekNum - 1];
	}

	public static String getZhouWeek() {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd");
		return format.format(new Date(System.currentTimeMillis())) + " "
				+ getWeek(0, ZHOU);
	}
	
	public static long getLongTime(String time,String format) {
		try {
			time = time.substring(0, time.indexOf('.'));
			Date date = new SimpleDateFormat(format).parse(time);
			return date.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	private static String getTime(long time) {
		return new SimpleDateFormat("HH:mm").format(new Date(time));
	}

	/**
	 * 转换日期
	 * @param timesamp
	 * @return
	 */
	public static String getDay(long timesamp) {
		if(timesamp == 0L){
			return "未";
		}
		String result = "未";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
		case 0:
			result = "今天" + getTime(timesamp);
			break;
		case 1:
			result = "昨天"+ getTime(timesamp);
			break;
		case 2:
			result = "前天"+ getTime(timesamp);
			break;

		default:
			result = temp + "天前"+ getTime(timesamp);
			break;
		}
		return result;
	}

	/**
	 * 转换日期
	 * @param time
	 * @return
	 */
	public static String convDate(String time) {
		Context context = CoreApplication.getContext();
		Resources res = context.getResources();

		StringBuffer buffer = new StringBuffer();

		Calendar createCal = Calendar.getInstance();
		createCal.setTimeInMillis(Date.parse(time));
		Calendar currentcal = Calendar.getInstance();
		currentcal.setTimeInMillis(System.currentTimeMillis());

		long diffTime = (currentcal.getTimeInMillis() - createCal.getTimeInMillis()) / 1000;

		// 同一月
		if (currentcal.get(Calendar.MONTH) == createCal.get(Calendar.MONTH)) {
			// 同一天
			if (currentcal.get(Calendar.DAY_OF_MONTH) == createCal.get(Calendar.DAY_OF_MONTH)) {
				if (diffTime < 3600 && diffTime >= 60) {
					buffer.append((diffTime / 60) + res.getString(R.string.msg_few_minutes_ago));
				} else if (diffTime < 60) {
					buffer.append(res.getString(R.string.msg_now));
				} else {
					buffer.append(res.getString(R.string.msg_today)).append(" ").append(DateUtils.formatDate(createCal.getTimeInMillis(), "HH:mm"));
				}
			}
			// 前一天
			else if (currentcal.get(Calendar.DAY_OF_MONTH) - createCal.get(Calendar.DAY_OF_MONTH) == 1) {
				buffer.append(res.getString(R.string.msg_yesterday)).append(" ").append(DateUtils.formatDate(createCal.getTimeInMillis(), "HH:mm"));
			}
		}

		if (buffer.length() == 0) {
			buffer.append(DateUtils.formatDate(createCal.getTimeInMillis(), "MM-dd HH:mm"));
		}

		String timeStr = buffer.toString();
		if (currentcal.get(Calendar.YEAR) != createCal.get(Calendar.YEAR)) {
			timeStr = createCal.get(Calendar.YEAR) + " " + timeStr;
		}
		return timeStr;
	}

}
