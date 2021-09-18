package com.bharat444.sunshineweather.adapter;
import android.view.*;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.bharat444.sunshineweather.R;
import android.view.View.OnClickListener;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>
{
	private String [] mWeatherData;
	
	public interface ForecastAdapterOnClickHandler{
		void setForecastAdapterClickListener(String weatherOfDay);
	}
	
	private final ForecastAdapterOnClickHandler mClickHandler;
	
	public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler){
		mClickHandler = clickHandler;
	}
	
	public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener
	{

		public final TextView mWeatherTextView;
		public ForecastAdapterViewHolder(View view)
		{
			super(view);
			mWeatherTextView = view.findViewById(R.id.tv_weather_data);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v)
		{
			int adapterPosition = getAdapterPosition();
			String weatherOfDay = mWeatherData[adapterPosition];
			mClickHandler.setForecastAdapterClickListener(weatherOfDay);
		}

	}
	
	@Override
	public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
	{
		Context context = viewGroup.getContext();
		int forecastListItemId = R.layout.forecast_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		boolean shouldAttachToParentImmediately = false;
		View view = inflater.inflate(forecastListItemId,viewGroup,shouldAttachToParentImmediately);
		return new ForecastAdapterViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ForecastAdapterViewHolder forecastViewHolder, int position)
	{
		String weatherForThisDay = mWeatherData[position];
		forecastViewHolder.mWeatherTextView.setText(weatherForThisDay);
	}

	@Override
	public int getItemCount()
	{
		if(mWeatherData!=null){
			return mWeatherData.length;
		}
		return 0;
	}

	public void setWeatherData(String[] weatherData){
		mWeatherData = weatherData;
		notifyDataSetChanged();
	}
	
}
