package com.bharat444.sunshineweather;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.bharat444.sunshineweather.utilities.*;

import android.app.Activity;
import com.bharat444.sunshineweather.adapter.ForecastAdapter;
import java.io.IOException;
import java.net.URL;
import org.json.JSONException;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

public class MainActivity extends Activity implements ForecastAdapter.ForecastAdapterOnClickHandler
{
	EditText mSearchBoxEditText;
	TextView mURLDisplayTextView,mSearchResultsTextView;
	TextView mShowErrorMsgTextView;
	ProgressBar mLoadingIndicator;
	
	protected RecyclerView mRecyclerView;
	protected ForecastAdapter mForecastAdapter;
	
	//private final String owmUrl = "https://api.openweathermap.org/data/2.5/onecall?lat=27.14&lon=83.56&exclude=hourly,daily&appid=8131f81af27dfbcfde8ea1103fb6def6";
	//private final String owmUrl = "https://api.openweathermap.org/data/2.5/onecall?lat=27.14&lon=83.56&exclude=daily&appid=8131f81af27dfbcfde8ea1103fb6def6";
	//private final String udacityWeather = "https://andfun-weather.udacity.com/staticweather?q=US&mode=json&units=metric&cnt=14";
	String location = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		mSearchBoxEditText = findViewById(R.id.et_search_box);
		mURLDisplayTextView = findViewById(R.id.tv_url_display);
		mShowErrorMsgTextView = findViewById(R.id.tv_show_error_message);
		mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
		
		mRecyclerView = findViewById(R.id.recyclerview_forecast);
		location = mSearchBoxEditText.getText().toString();
		new FetchWeatherTask().execute("US");
		
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int selectedItemId = item.getItemId();
		if(selectedItemId==R.id.action_search){
			Toast.makeText(this,"Search clicked",Toast.LENGTH_SHORT).show();
			location = mSearchBoxEditText.getText().toString();
			new FetchWeatherTask().execute(location);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class FetchWeatherTask extends AsyncTask<String,Void,String[]>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			mLoadingIndicator.setVisibility(View.VISIBLE);
		}

		@Override
		protected String[] doInBackground(String... params)
		{
			String searchLocation = params[0];
			try
			{
				URL url  = NetworkUtils.buildUrl(searchLocation);
				String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
				String[] data = OpenWeatherJsonUtils.getWeatherStringFromJson(MainActivity.this,jsonResponse);
				return data;
			}
			catch(IOException e){
				e.printStackTrace();
				return null;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
	

		@Override
		protected void onPostExecute(String[] result)
		{
			mLoadingIndicator.setVisibility(View.INVISIBLE);
			if(result!=null){
				
				Toast.makeText(MainActivity.this,result[0],Toast.LENGTH_LONG).show();
				
				LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
				mRecyclerView.setLayoutManager(layoutManager);
				mRecyclerView.setHasFixedSize(true);

				mForecastAdapter = new ForecastAdapter(MainActivity.this);
				mForecastAdapter.setWeatherData(result);
				mRecyclerView.setAdapter(mForecastAdapter);
			}
			else{
				Toast.makeText(MainActivity.this,"No result found",Toast.LENGTH_LONG).show();
			}
		}
		
		
	}

	@Override
	public void setForecastAdapterClickListener(String weatherOfDay)
	{
		Context context = MainActivity.this;
		Intent startDetailActivity = new Intent(context,DetailActivity.class);
		startDetailActivity.putExtra(Intent.EXTRA_TEXT,weatherOfDay);
		startActivity(startDetailActivity);
	}
}
