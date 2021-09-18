package com.bharat444.sunshineweather;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends Activity
{
	TextView mShowWeatherOfDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		mShowWeatherOfDay = findViewById(R.id.tv_display_weather);
		Intent weatherIntent = getIntent();
		if(weatherIntent.hasExtra(Intent.EXTRA_TEXT)){
			String weatherOfDay = weatherIntent.getStringExtra(Intent.EXTRA_TEXT).toString();
			mShowWeatherOfDay.setText(weatherOfDay);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.share,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int selectedOptionId = item.getItemId();
		if(selectedOptionId==R.id.share_weather_data){
			Intent sendInntent = new Intent();
			sendInntent.setAction(Intent.ACTION_SEND);
			sendInntent.putExtra(Intent.EXTRA_TEXT,mShowWeatherOfDay.getText().toString());
			sendInntent.setType("text/plain");
		    
			Intent shareIntent = Intent.createChooser(sendInntent,null);
			startActivity(shareIntent);
			return true;
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
