package com.dawn.dawning.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawn.dawning.Entity.Program;
import com.dawn.dawning.Entity.ProgramDetail;
import com.dawn.dawning.R;
import com.dawn.dawning.widget.ProgramDetailDialog;

import java.util.List;

public class ProgramDetailAdapter extends RecyclerView.Adapter<ProgramDetailAdapter.ViewHolder> {
    private List<ProgramDetail> mProgramList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mProgramName;
        TextView mUploadDate;
        TextView mPlayCount;
        TextView mDuration;
        ImageButton mImageButton;

        public ViewHolder(View view) {
            super(view);
            mProgramName = (TextView) view.findViewById(R.id.program_name);
            mUploadDate = (TextView) view.findViewById(R.id.upload_date);
            mPlayCount = (TextView) view.findViewById(R.id.play_count);
            mDuration = (TextView) view.findViewById(R.id.duration);
            mImageButton = (ImageButton) view.findViewById(R.id.image_more);
        }
    }

    public ProgramDetailAdapter(Context context,List<ProgramDetail> programs){
        mProgramList = programs;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    //创建新View，被LayoutManager调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_detail_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        vh.mImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("clickImage","clickMore") ;
                ProgramDetailDialog dialog = new ProgramDetailDialog(mContext,R.style.Theme_Light_NoTitle_Dialog);
                dialog.show();
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ProgramDetail program = mProgramList.get(position);
        holder.mProgramName.setText(program.getProgramName());
        holder.mUploadDate.setText(program.getUploadTime().toString());
        holder.mPlayCount.setText(program.getPlayCount()+"");
        holder.mDuration.setText(program.getDuration().toString());
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }
}
