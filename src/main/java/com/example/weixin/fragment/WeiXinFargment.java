package com.example.weixin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.weixin.R;
import com.example.weixin.tool.TouXiang;
import com.example.weixin.litepal.Message;
import com.example.weixin.litepal.User;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/3/9.
 */

public class WeiXinFargment extends Fragment {
    private PullToRefreshListView pullToRefreshListView;
    private ListView lv;
    private List<User> list;
    private Handler handler=new Handler();
    private List<User> listUser;
    private LvAdapter lvAdapter;
    private View view;
    private PopupMenu popupMenu;
    private String name,touxiang;
    private List<Message> messageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_weixin,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    public void initView(){
        pullToRefreshListView=view.findViewById(R.id.pullToRefreshListView);
        lv = pullToRefreshListView.getRefreshableView();
    }

    public void initData(){
        listUser= LitePal.findAll(User.class);
        list=new ArrayList<>();
        for(int i=0;i<listUser.size();i++){
            if(listUser.get(i).getChat()){
                list.add(listUser.get(i));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        listUser= LitePal.findAll(User.class);
        list.clear();
        for(int i=0;i<listUser.size();i++){
            if(listUser.get(i).getChat()){
                list.add(listUser.get(i));
            }
        }
        lvAdapter.notifyDataSetChanged();
    }

    public void initControl(){
        lvAdapter=new LvAdapter(list);
        lv.setAdapter(lvAdapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                popupMenu = new PopupMenu(getActivity(),view);
                popupMenu.setGravity(Gravity.CENTER_HORIZONTAL);
                popupMenu.inflate(R.menu.menu1);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item1:
                                User user=new User();
                                user.setChat(false);
                                user.updateAll("name=?",list.get(position-1).getName());
                                listUser= LitePal.findAll(User.class);
                                list.clear();
                                for(int i=0;i<listUser.size();i++){
                                    if(listUser.get(i).getChat()){
                                        list.add(listUser.get(i));
                                    }
                                }
                                lvAdapter.notifyDataSetChanged();
                                break;
                            case R.id.item2:
                                break;
                            case R.id.item3:
                                break;
                        }
                        return true;
                    }
                });
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent("android.intent.action.CHAT");
                intent.putExtra("name",list.get(position-1).getName());
                startActivity(intent);
            }
        });


        /**
         * 设置刷新的模式：常用的有三种
         * PullToRefreshBase.Mode.BOTH  //上下拉刷新都可以
         * PullToRefreshBase.Mode.PULL_FROM_START  //只允许下拉刷新
         * PullToRefreshBase.Mode.PULL_FROM_END   //只允许上拉刷新
         *
         */
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置是否允许刷新的时候可以滑动
        pullToRefreshListView.setScrollingWhileRefreshingEnabled(true);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //完成下拉刷新操作
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //休眠2s
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                       /* //向集合中添加一个随机数
                        list.add(0,"item-------"+(int)(Math.random()*100+1));*/

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                lvAdapter.notifyDataSetChanged();
                                //控件刷新最新的数据
                                pullToRefreshListView.onRefreshComplete();
                            }
                        });
                    }
                }).start();
            }
            //完成上拉刷新操作
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
    }


    class LvAdapter extends BaseAdapter{
        List list;
        public LvAdapter(List list) {
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
                LayoutInflater inflater=getLayoutInflater().from(getActivity());
                convertView=inflater.inflate(R.layout.fragment_weixin_item,parent,false);
                viewHolder=new ViewHolder();
                viewHolder.ivHint=convertView.findViewById(R.id.ivHint);
                viewHolder.ivTouXiang=convertView.findViewById(R.id.ivTouXiang);
                viewHolder.tvHint=convertView.findViewById(R.id.tvHint);
                viewHolder.tvTime=convertView.findViewById(R.id.tvTime);
                viewHolder.tvName=convertView.findViewById(R.id.tvName);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder) convertView.getTag();
            }
            name=((User)list.get(position)).getName();
            messageList=LitePal.where("name=?",name).find(Message.class);
            viewHolder.tvName.setText(name);
            viewHolder.tvHint.setText(messageList.get(messageList.size()-1).getCon());
            viewHolder.ivTouXiang.setImageBitmap(TouXiang.getBitmap(((User) list.get(position)).getTouxiang()));
            viewHolder.tvTime.setText(messageList.get(messageList.size()-1).getTime());
            return convertView;
        }
        class ViewHolder{
            public TextView tvName,tvHint,tvTime;
            public ImageView ivTouXiang,ivHint;
        }
    }

}
