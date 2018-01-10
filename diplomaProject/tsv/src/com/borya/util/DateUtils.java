package com.borya.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private DateUtils() {
	}

	public static String getCurTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
				.format(new Date());
	}

	public static String getCurTime(String fromat) {
		return new SimpleDateFormat(fromat).format(new Date());
	}
	
	public static String getFormatTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(date);
	}

	public static String getDetailedTime() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	public static String getDetailTime() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	public static Timestamp getCurTimeStamp() {
		return new Timestamp(new Date().getTime());

	}

	public static String addTime(Date date, int field, int amount,
			String DateFormat) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return new SimpleDateFormat(DateFormat).format(calendar.getTime());

	}

	public static long addTime(long timeStamp, int field, int amount) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeStamp);
		calendar.add(field, amount);
		return calendar.getTimeInMillis();
	}

	public static boolean isBeforeCurDate(String date) {
		Calendar curCal = Calendar.getInstance();
		curCal.setTimeInMillis(new Date().getTime());
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(date.substring(0, 4)),
				Integer.valueOf(date.substring(5, 7)) - 1,
				Integer.valueOf(date.substring(8, date.length())));
		if (cal.compareTo(curCal) < 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String getTime(Long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String lasttime = sdf.format(new Date(time));
		return lasttime;
	}

	public static Long getcurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	public static long getLongTime(String time, String pattern) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}

	public static long getLastTimeOfMonth(String monthDate) {
		int year = Integer.valueOf(monthDate.substring(0, 4));
		int month = Integer.valueOf(monthDate.substring(5));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date date = cal.getTime();
		return getLongTime(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
						+ ":999", "yyyy-MM-dd HH:mm:ss:SSS");
	}

	public static long getFirstTimeOfMonth(String monthDate) {
		int year = Integer.valueOf(monthDate.substring(0, 4));
		int month = Integer.valueOf(monthDate.substring(5));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date date = cal.getTime();
		return date.getTime();
	}

	public static void main(String args[]) {
		/*
		 * System.out.println(getLongTime("2012-08-28 17:30:26:140",
		 * "yyyy-MM-dd HH:mm:ss:SSS")); long date =
		 * getLastTimeOfMonth("2012-09"); long firstdate =
		 * getFirstTimeOfMonth("2012-09"); System.out.println(date);
		 */
		System.out.println(getTime(1358992294641l));
		System.out.println(new Date().getTime());
		System.out.println(getTime(1359041344978l));
		System.out.println(DateUtils.getcurrentTimeMillis());
		System.out.println(getTime(DateUtils.getcurrentTimeMillis()));
		// System.out.println(getTime(firstdate));
		// System.out.println(isBeforeCurDate("2012-02-11"));

		/*
		 * Long time=new Date().getTime(); String stime=DateUtils.getTime(time);
		 * System.out.println(time); System.out.println(stime); Long
		 * stime1=DateUtils.getLongTime(stime, "yyyy-MM-dd HH:mm:ss:SSS");
		 * System.out.println(stime1);
		 * System.out.println(DateUtils.getTime(stime1));
		 */
	}
}
