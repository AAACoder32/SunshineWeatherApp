package com.bharat444.sunshineweather.utilities;
import android.net.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class NetworkUtils
{
	final static String DYNAMIC_WEATHER_URL = "https://andfun-weather.udacity.com/weather";
	final static String STATIC_WEATHER_URL = "https://andfun-weather.udacity.com/staticweather";
	final static String FORCAST_BASE_URL = STATIC_WEATHER_URL;
	
	private static final String format = "json";
	private static final String units = "metric";
	private static final int numDays = 14;
	

	final static String PARAM_QUERY = "q";
	final static String LAT_PARAM = "lat";
	final static String LON_PARAM = "lon";
	final static String FORMAT_PARAM = "mode";
	final static String UNIT_PARAM = "uints";
	final static String DAY_PARAM = "cnt";
	

	public static URL buildUrl(String locationQuery)
	{
		Uri builtUri = Uri.parse(FORCAST_BASE_URL).buildUpon()
			           .appendQueryParameter(PARAM_QUERY,locationQuery)
					   .appendQueryParameter(FORMAT_PARAM,format)
					   .appendQueryParameter(UNIT_PARAM,units)
					   .appendQueryParameter(DAY_PARAM,Integer.toString(numDays))
					   .build();
						  
		URL url = null;
		try{
			url = new URL(builtUri.toString());
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		return url;
	}
	
	public static URL buildUrl(double lat,double lon){
		return null;
	}

	public static String getResponseFromHttpUrl(URL url) throws IOException
	{
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		try
		{
			InputStream in = httpConnection.getInputStream();
			Scanner sc = new Scanner(in);
			sc.useDelimiter("\\A");
			
			boolean hasNext = sc.hasNext();
			if(hasNext){
				return sc.next();
			}
			else{
				return null;
			}

		}
		finally
		{
			httpConnection.disconnect();
		}
	} 
}
