package com.example.weixin.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weixin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by hasee on 2019/3/9.
 */

public class FindFragment extends Fragment {
    private ListView lvFind;
    private List<String> list;
    private Map<String,Integer> imageMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_find,container,false);
        initdata();
        lvFind=view.findViewById(R.id.lvFind);
        lvFind.setAdapter(new LvAdapter(list));
        return view;
    }

    public void initdata(){
        list=new ArrayList<>();
        list.add("朋友圈");
        list.add("扫一扫");
        list.add("摇一摇");
        list.add("看一看");
        list.add("搜一搜");
        list.add("附近的人");
        list.add("购物");
        list.add("游戏");
        list.add("小程序");
        imageMap=new HashMap<>();
        imageMap.put(list.get(0),R.drawable.circle_of_friends);
        imageMap.put(list.get(1),R.drawable.scan);
        imageMap.put(list.get(2),R.drawable.shark_it_off);
        imageMap.put(list.get(3),R.drawable.look);
        imageMap.put(list.get(4),R.drawable.try_to_search);
        imageMap.put(list.get(5),R.drawable.people_nearby);
        imageMap.put(list.get(6),R.drawable.shop);
        imageMap.put(list.get(7),R.drawable.game);
        imageMap.put(list.get(8),R.drawable.mini_program);
    }
    class LvAdapter extends BaseAdapter{
        List<String> list;
        public LvAdapter(List<String> list) {
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                viewHolder=new ViewHolder();
                LayoutInflater inflater=getLayoutInflater().from(getActivity());
                convertView=inflater.inflate(R.layout.fragment_find_item,null);
                viewHolder.tvName=convertView.findViewById(R.id.tvName);
                viewHolder.tvHeader=convertView.findViewById(R.id.tvHeader);
                viewHolder.iv0=convertView.findViewById(R.id.iv0);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder) convertView.getTag();
            }
            if(position==1||position==3||position==5||position==6||position==8){
                viewHolder.tvHeader.setVisibility(View.VISIBLE);
            }
            viewHolder.tvName.setText(list.get(position));
            viewHolder.iv0.setImageResource(imageMap.get(list.get(position)));
            return convertView;
        }
        class ViewHolder{
            public TextView tvName;
            public TextView tvHeader;
            public ImageView iv0;
        }
    }
}
