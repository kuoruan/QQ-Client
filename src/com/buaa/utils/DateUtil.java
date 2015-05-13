package com.buaa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * long转时间字符串
	 * 
	 * @param t
	 *            long值
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static String getTime(long t) {
		Date d = new Date(t);
		return getTime(d);
	}

	/**
	 * Date转时间字符串
	 * 
	 * @param date
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static String getTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 自定义格式转换
	 * 
	 * @param format   自定义的格式
	 * 
	 * 如："yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param t  long值
	 * @return
	 */
	public static String getTime(String format, long t) {
		Date d = new Date(t);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}

}
