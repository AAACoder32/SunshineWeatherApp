package com.bharat444.sunshineweather.utilities;
import android.content.Context;
import com.bharat444.sunshineweather.R;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public final class SunshineDateUtils
{
	public static final long SECOND_IN_MILLIS = 1000;
	public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS *60;
	public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
	public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
	
	public static long getDayNumber(long date){
		TimeZone tz = TimeZone.getDefault();
		long gmtOffset = tz.getOffset(date);
		return (date + gmtOffset)/HOUR_IN_MILLIS;
	}
	
	/*
	public static long getLocalDateFromUTC(long utcDate){
		TimeZone tz = TimeZone.getDefault();
		long gmtOffset = tz.getOffset(utcDate);
		return utcDate - gmtOffset;
	}
	
	public static long getUTCDateFromLocal(long localDate){
		TimeZone tz = TimeZone.getDefault();
		long gmtOffset = tz.getOffset(localDate);
		return localDate + gmtOffset;
	}
	
	public static String getFriendlyDateString(Context context,long dateInMillis,boolean showFullDate){
		
		long localDate = getLocalDateFromUTC(dateInMillis);
		long dayNumber = getDayNumber(localDate);
		long currentDayNumber = getDayNumber(System.currentTimeMillis());
		
		if(dayNumber == currentDayNumber || showFullDate){
			
			String dayName = getDayName(context,localDate);
			String readableDate = getReadableDateString(context,localDate);
			
			if(dayNumber - currentDayNumber < 2){
				String localizedDayName = new SimpleDateFormat("EEEE").format(localDate);
				return readableDate.replace(localizedDayName,dayName);
			}
			else{
				return readableDate;
			}
		}
		else if(dayNumber < currentDayNumber + 7){
			return getDayName(context,localDate);
		}
		else{
			int flags = DateUtils.FORMAT_SHOW_DATE
			            | DateUtils.FORMAT_ABBREV_ALL
						| DateUtils.FORMAT_NO_YEAR
						| DateUtils.FORMAT_SHOW_WEEKDAY;
			return DateUtils.formatDateTime(context,localDate,flags);
		}
		
	}
	
	private static String getReadableDateString(Context context,long dateInMillis){
		int flags = DateUtils.FORMAT_SHOW_DATE
		           |DateUtils.FORMAT_NO_YEAR
				   |DateUtils.FORMAT_SHOW_WEEKDAY;
	    return DateUtils.formatDateTime(context,dateInMillis,flags);
	}
    */
	
	public static String getReadableDateString(Context context,long dateInMillis){
		String dayName = getDayName(context,dateInMillis);
		if(dayName.equals("Today") || dayName.equals("Tomorrow")){
			SimpleDateFormat formatedDate = new SimpleDateFormat("EEE MMM dd");
			String newDate = formatedDate.format(dateInMillis);
			return dayName+", "+newDate;
		}
		String pattern = "EEE MMM dd, yyyy";
		String detailDate = new SimpleDateFormat(pattern).format(dateInMillis);
		return detailDate;
	}
	private static String getDayName(Context context,long dateInMillis){
		
		long dayNumber = getDayNumber(dateInMillis);
		long currentDayNumber = getDayNumber(System.currentTimeMillis());
		
		if(dayNumber == currentDayNumber){
			return context.getString(R.string.today);
		}
		else if(dayNumber == currentDayNumber + 1){
			return context.getString(R.string.tomorrow);
		}
		else{
			SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
			return dayFormat.format(dateInMillis);
		}
	}
}
