package com.dawn.dawning.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

import com.dawn.dawning.R;
import com.dawn.dawning.common.HttpUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


public class moodReport extends AppCompatActivity {
    private BarChart barChart;
    private XAxis xAxis;
    private String date[];
    private int mood[];
    private String avg;
    Handler handler=new Handler();
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_report);

        getMoodData();
    }

    final Runnable UpdateUI=new Runnable() {
        @Override
        public void run() {
            setMyBarChart();
            setAvgCircle();
        }
    };

    public void getMoodData(){


        String address=HttpUtil.host+"moodReport?token=1";
        HttpUtil.sendOkHttpRequest(address,new okhttp3.Callback(){

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TextView tvTest=findViewById(R.id.tvTest);
                String result=response.body().string();
                result.trim();
                String data[]=result.split("[]\\[\\{}:,\"]+");

                date = new String[7];
                mood = new int[7];
                for(int i=0;i<7;i++){
                    date[i]=(4*i+1+1) < data.length ? data[4*i+1+1] : "";
                    mood[i]=(4*i+3+1) < data.length ? Integer.valueOf(data[4*i+3+1]) : 0;
                }
                avg = data[data.length-1].split("\\.")[0];

                handler.post(UpdateUI);
            }

            @Override
            public void onFailure(Call call,IOException e){
                TextView tvTest=findViewById(R.id.tvTest);
                tvTest.setText(e.toString());
            }
        });
    }

    public void setMyBarChart(){
        barChart = (BarChart) findViewById(R.id.barChartOfMood);
        //1、基本设置
        xAxis = barChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.setTouchEnabled(false); // 设置是否可以触摸
        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放
        //2、y轴和比例尺

        barChart.getDescription().setEnabled(false);

        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        Legend legend = barChart.getLegend();//隐藏比例尺
        legend.setEnabled(false);

//        //3、x轴数据,和显示位置
//        ArrayList<String> xValues = new ArrayList<String>();
//        xValues.add("一季度");
//        xValues.add("二季度");
//        xValues.add("三季度");
//        xValues.add("四季度");
//
//        xAxis.setValueFormatter();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//数据位于底部
        xAxis.setGranularity(1f);


        //4、y轴数据
        final ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        //new BarEntry(20, 0)前面代表数据，后面代码柱状图的位置；
        yValues.add(new BarEntry(1, mood[0]));
        yValues.add(new BarEntry(2, mood[1]));
        yValues.add(new BarEntry(3, mood[2]));
        yValues.add(new BarEntry(4, mood[3]));
        yValues.add(new BarEntry(5, mood[4]));
        yValues.add(new BarEntry(6, mood[5]));
        yValues.add(new BarEntry(7, mood[6]));


        //5、设置显示的数字为整形
        BarDataSet barDataSet = new BarDataSet(yValues, "");
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int)value+"";
            }
        });

//        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return yValues.get((int)value).getData()+"";
//            }
//        });

        //6、设置柱状图的颜色
        barDataSet.setColors(Color.rgb(104, 202, 37));

        //7、显示，柱状图的宽度和动画效果
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);//值越大，柱状图就越宽度越小；
        barChart.animateY(1200);
        barChart.setData(barData); //
    }

    public void setAvgCircle(){
        TextView tvAvgCircle=findViewById(R.id.circleMoodAvg);
        tvAvgCircle.setText(avg);
    }
}
