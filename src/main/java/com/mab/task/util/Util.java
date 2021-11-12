package com.mab.task.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class Util {

	public static String generateID() {
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();
		int second = now.getSecond();
		int millis = now.get(ChronoField.MILLI_OF_SECOND);
		
		StringBuilder str = new StringBuilder();
		str.append(year);
		str.append(month);
		str.append(day);
		str.append(hour);
		str.append(minute);
		str.append(second);
		str.append(millis);

		return str.toString();
	}

}
