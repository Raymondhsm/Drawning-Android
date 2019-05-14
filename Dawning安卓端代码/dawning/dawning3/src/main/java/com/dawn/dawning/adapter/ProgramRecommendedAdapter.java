package com.dawn.dawning.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dawn.dawning.Entity.Program;
import com.dawn.dawning.R;
import com.dawn.dawning.util.HttpUtil;

import java.util.List;

public class ProgramRecommendedAdapter extends RecyclerView.Adapter<ProgramRecommendedAdapter.ViewHolder> {
    //此处存储mContext有风险，可能会内存泄漏
    private Context mContext;
    private List<Program> mProgramList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mProgramImage;
        TextView mProgramName;
        TextView mRadioName;

        public ViewHolder(View view) {
            super(view);
            mProgramImage = (ImageView)view.findViewById(R.id.image_program);
            mProgramName = (TextView) view.findViewById(R.id.text_program_name);
            mRadioName = (TextView) view.findViewById(R.id.text_radio_name);
        }
    }

    public ProgramRecommendedAdapter(Context context,List<Program> programs){
        mContext =context;
        mProgramList = programs;
    }

    // Create new views (invoked by the layout manager)
    //创建新View，被LayoutManager调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Program program = mProgramList.get(position);
        Glide.with(mContext).load(HttpUtil.resourceAddress+program.getCoverLink()).into(holder.mProgramImage);
        holder.mProgramName.setText(program.getProgramName());
        holder.mRadioName.setText(program.getRadioName());
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }
}
