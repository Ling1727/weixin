package com.example.weixin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.weixin.R;
import com.example.weixin.litepal.Meself;
import com.example.weixin.tool.TouXiang;

import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2019/3/9.
 */

public class MeFragment extends Fragment {
    private ListView lvMe;
    private List<String> list=new ArrayList<>();
    private Map<String,Integer> imageMap;
    private View view;
    private ImageView ivTouXiang,ivCamera;
    private TextView tvName,tvID;
    private Meself meself;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_me,container,false);
        initView();
        initdata();
        initControl();
        return view;
    }

    private void initView() {
        lvMe=view.findViewById(R.id.lvMe);
        ivTouXiang=view.findViewById(R.id.ivTouXiang);
        ivCamera=view.findViewById(R.id.ivCamera);
        tvName=view.findViewById(R.id.tvName);
        tvID=view.findViewById(R.id.tvID);
    }
    public void initControl(){
        lvMe.setAdapter(new LvAdapter(list));
        meself=LitePal.find(Meself.class,1);
        tvName.setText(meself.getName());
        tvID.setText(meself.getName());
        ivTouXiang.setImageBitmap(TouXiang.getBitmap(meself.getTouxiangPath()));
    }

    public void initdata(){
        list.add("支付");
        list.add("收藏");
        list.add("相册");
        list.add("卡包");
        list.add("表情");
        list.add("设置");
        imageMap=new HashMap<>();
        imageMap.put(list.get(0),R.drawable.payment);
        imageMap.put(list.get(1),R.drawable.collect);
        imageMap.put(list.get(2),R.drawable.photo_album);
        imageMap.put(list.get(3),R.drawable.card_bag);
        imageMap.put(list.get(4),R.drawable.expression);
        imageMap.put(list.get(5),R.drawable.set);
    }

    class LvAdapter extends BaseAdapter {
        List<String> list;
        LayoutInflater inflater=getLayoutInflater().from(getActivity());
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
                    convertView=inflater.inflate(R.layout.fragment_find_item,null);
                    viewHolder.tvName=convertView.findViewById(R.id.tvName);
                    viewHolder.tvHeader=convertView.findViewById(R.id.tvHeader);
                    viewHolder.iv0=convertView.findViewById(R.id.iv0);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder=(ViewHolder) convertView.getTag();
                }
                if(position==0||position==1||position==5){
                    viewHolder.tvHeader.setVisibility(View.VISIBLE);
                }
                viewHolder.tvName.setText(list.get(position));
                viewHolder.iv0.setImageResource(imageMap.get(list.get(position)));
            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        class ViewHolder{
            public TextView tvName;
            public TextView tvHeader;
            public  ImageView iv0;
        }
    }
}
