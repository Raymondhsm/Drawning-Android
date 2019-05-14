package com.dawn.dawning.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dawn.dawning.Entity.Program;
import com.dawn.dawning.Entity.Radio;
import com.dawn.dawning.R;
import com.dawn.dawning.activity.RadioDetailActivity;
import com.dawn.dawning.util.HttpUtil;

import java.util.List;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {
    private List<Radio> mRadioList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mRadioImage;
        TextView mRadioName;
        TextView mSubscribeCount;

        public ViewHolder(View view) {
            super(view);
            mRadioImage = (ImageView)view.findViewById(R.id.radio_image);
            mSubscribeCount = (TextView) view.findViewById(R.id.radio_subscribe_count);
            mRadioName = (TextView) view.findViewById(R.id.radio_name);
        }
    }

    public RadioAdapter(Context context,List<Radio> radios){
        mContext = context;
        mRadioList = radios;
    }

    // Create new views (invoked by the layout manager)
    //创建新View，被LayoutManager调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.radio_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,RadioDetailActivity.class);
                intent.putExtra("radio_id",mRadioList.get(Integer.parseInt(v.getTag().toString())).getRadioId());
                Log.i("radio_id_click",v.getTag().toString());
                mContext.startActivity(intent);
            }
        });
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.itemView.setTag(position);
        Radio radio = mRadioList.get(position);
        Glide.with(mContext).load(HttpUtil.resourceAddress+radio.getCoverLink()).into(holder.mRadioImage);
        holder.mRadioName.setText(radio.getRadioName());
        holder.mSubscribeCount.setText(radio.getSubscribeCount()+"");
    }

    @Override
    public int getItemCount() {
        return mRadioList.size();
    }
}
