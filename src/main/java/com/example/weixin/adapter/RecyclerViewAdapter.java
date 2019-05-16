package com.example.weixin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weixin.litepal.Message;
import com.example.weixin.R;
import com.example.weixin.litepal.User;
import com.example.weixin.tool.TouXiang;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by hasee on 2019/3/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Message> list;
    private Context context;
    private String name;
    private String touxiang;

    public RecyclerViewAdapter(Context context, List<Message> list,String name){
        this.context=context;
        this.list=list;
        this.name=name;
        touxiang=(LitePal.where("name=?",name).find(User.class).get(0)).getTouxiang();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLeft,linearRight,llTime;
        private ImageView ivLeft,ivRight;
        private TextView tvLeft,tvRight,tvTime;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLeft=itemView.findViewById(R.id.linearLeft);
            linearRight=itemView.findViewById(R.id.linearRight);
            ivLeft=itemView.findViewById(R.id.ivLeft);
            ivRight=itemView.findViewById(R.id.ivRight);
            tvLeft=itemView.findViewById(R.id.tvLeft);
            tvRight=itemView.findViewById(R.id.tvRight);
            llTime=itemView.findViewById(R.id.llTime);
            tvTime=itemView.findViewById(R.id.tvTime);
        }
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_view_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        if(list.get(position).getTimeIsShow()){
            holder.llTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(list.get(position).getTime());
        }else{
            holder.llTime.setVisibility(View.GONE);
        }
        if(list.get(position).getType()==1){
            holder.linearLeft.setVisibility(View.GONE);
            holder.linearRight.setVisibility(View.VISIBLE);
            holder.tvRight.setText(list.get(position).getCon());
        }else{
            holder.linearRight.setVisibility(View.GONE);
            holder.linearLeft.setVisibility(View.VISIBLE);
            holder.tvLeft.setText(list.get(position).getCon());
            holder.ivLeft.setImageBitmap(TouXiang.getBitmap(touxiang));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
