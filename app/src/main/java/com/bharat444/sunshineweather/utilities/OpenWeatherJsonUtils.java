package com.bharat444.sunshineweather.utilities;


import org.json.*;

import android.content.Context;
import java.net.HttpURLConnection;

public final class OpenWeatherJsonUtils
{

	public static String[] getWeatherStringFromJson(Context context,String forecastJson) throws JSONException{

		String[] weatherData = null;
		
		final String OWM_LIST = "list";
		final String OWM_TEMP = "temp";
		final String OWM_TEMP_MIN = "min";
		final String OWM_TEMP_MAX = "max";
		final String OWM_DATE = "dt";
		final String OWM_WEATHER = "weather";
		final String OWM_DESC = "description";
		final String OWM_CODE = "cod";

		JSONObject weatherJson = new JSONObject(forecastJson);
		JSONArray weatherList = weatherJson.getJSONArray(OWM_LIST);
		
		if(weatherJson.has(OWM_CODE)){
			int errorCode = weatherJson.getInt(OWM_CODE);
			switch(errorCode){
				case HttpURLConnection.HTTP_OK:
					break;
				case HttpURLConnection.HTTP_NOT_FOUND:
				    weatherData[0] = "Something went wrong";
					return weatherData;
				default:
				    return null;
			}
		}
		
		weatherData = new String[weatherList.length()];
		//long currentDate = System.currentTimeMillis();
		for(int i=0; i<weatherList.length(); i++)
		{
			JSONObject weatherArray = weatherList.getJSONObject(i);

			String date = weatherArray.getString(OWM_DATE);
			double dd = Double.parseDouble(date);
			long dt = (long)dd;
			
			String readableDate = SunshineDateUtils.getReadableDateString(context,dt);

			JSONObject temperature = weatherArray.getJSONObject(OWM_TEMP);
			String minTemp = temperature.getString(OWM_TEMP_MIN);
			String maxTemp = temperature.getString(OWM_TEMP_MAX);
			String min_max = "Temperature min:" + minTemp + ", max:" + maxTemp;

			JSONArray weather = weatherArray.getJSONArray(OWM_WEATHER);
			String desc = weather.getString(0);
			JSONObject dobj = new JSONObject(desc);
			String description = dobj.getString(OWM_DESC);

			weatherData[i] = readableDate + " - " + description + " - " + min_max;
		}

		return weatherData;
	}
}

