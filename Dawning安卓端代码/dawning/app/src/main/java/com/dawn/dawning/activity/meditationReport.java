package com.dawn.dawning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

import com.dawn.dawning.R;
import com.dawn.dawning.common.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class meditationReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_report);

        setData();

    }
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public void setData(){
        String address=HttpUtil.host+"meditationReport?token=1";
        HttpUtil.sendOkHttpRequest(address,new okhttp3.Callback(){

            @Override
            public void onResponse(Call call,Response response) throws IOException{
                String responseData=response.body().string();
                String data[]=responseData.split("[,:}]");

                TextView tvAvg=(TextView)findViewById(R.id.meditationAvgTimeMiddle);
                TextView tvTimes=(TextView)findViewById(R.id.meditationTimesMiddle);
                TextView tvTotal=(TextView)findViewById(R.id.meditationTotalTimeMiddle);
                TextView tvFrequency=(TextView)findViewById(R.id.meditationFrequencyMiddle);

                tvAvg.setText(data[1]);
                tvTimes.setText(data[3]);
                tvTotal.setText(data[5]);
                tvFrequency.setText(data[7]);
            }

            @Override
            public void onFailure(Call call,IOException e){
                TextView textView=(TextView)findViewById(R.id.test);
                textView.setText(e.toString());
            }
        });
    }
}
