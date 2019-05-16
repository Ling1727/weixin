package com.example.weixin.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weixin.R;
import com.example.weixin.tool.TouXiang;
import com.example.weixin.litepal.User;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2019/3/9.
 */

public class MailListFragment extends Fragment {
    private ListView lvMail;
    private List<User> list;
    private List<String> list1;
    private Map<String,Object> imageMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mail_list,container,false);
        lvMail=view.findViewById(R.id.lvMail);
        initData();
        lvMail.setAdapter(new LvAdapter(list,list1));
        lvMail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0||position==1||position==2||position==3){
                }else{
                    Intent intent=new Intent("android.intent.action.CHAT");
                    intent.putExtra("name",list.get(position-4).getName());
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public void initData(){
        list=LitePal.findAll(User.class);
        list1=new ArrayList<>();
        list1.add("新的朋友");
        list1.add("群聊");
        list1.add("标签");
        list1.add("公众号");
        imageMap=new HashMap<>();
        imageMap.put(list1.get(0),R.drawable.new_friend);
        imageMap.put(list1.get(1),R.drawable.group_chat);
        imageMap.put(list1.get(2),R.drawable.label);
        imageMap.put(list1.get(3),R.drawable.official_accounts);
    }

    class LvAdapter extends BaseAdapter{
        List<User> list;
        List<String> list1;
        public LvAdapter(List<User> list,List<String> list1) {
          this.list=list;
          this.list1=list1;
        }

        @Override
        public int getCount() {
            return list.size()+list1.size();
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
                LayoutInflater inflater=getLayoutInflater().from(getActivity());
                viewHolder=new ViewHolder();
                convertView=inflater.inflate(R.layout.fragment_mail_list_item,null);
                viewHolder.ivTouXiang=convertView.findViewById(R.id.ivTouXiang);
                viewHolder.tvHeader=convertView.findViewById(R.id.tvHeader);
                viewHolder.tvName=convertView.findViewById(R.id.tvName);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder) convertView.getTag();
            }
            if(position<4){
                viewHolder.tvHeader.setVisibility(View.GONE);
                viewHolder.tvName.setText(list1.get(position));
                viewHolder.ivTouXiang.setImageResource((int)imageMap.get(list1.get(position)));
            }else {
                if (position == 4){ //第1个需要显示首字母
                    viewHolder.tvHeader.setVisibility(View.VISIBLE);
                }else if (!list.get(position-4).getFirstAlphabet().equals(list.get(position-5).getFirstAlphabet())){
                    //前后2个首字母不相同,需要显示首字母
                    viewHolder.tvHeader.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.tvHeader.setVisibility(View.GONE);
                }
                viewHolder.tvHeader.setText(list.get(position-4).getFirstAlphabet());
                viewHolder.tvName.setText(list.get(position-4).getName());
                viewHolder.ivTouXiang.setImageBitmap(TouXiang.getBitmap(list.get(position-4).getTouxiang()));
            }
            return convertView;
        }
        class ViewHolder{
            public TextView tvName;
            public TextView tvHeader;
            public ImageView ivTouXiang;
        }
    }
}
