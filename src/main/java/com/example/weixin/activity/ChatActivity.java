package com.example.weixin.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.weixin.litepal.Message;
import com.example.weixin.R;
import com.example.weixin.adapter.RecyclerViewAdapter;
import com.example.weixin.litepal.User;
import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2019/3/16.
 */

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Message> list;
    private Button btLeft,btRight;
    private EditText editText;
    RecyclerViewAdapter recyclerViewAdapter;
    private Toolbar toolbarChat;
    private String name;
    private Boolean timeIsShow=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_view);
        setState();
        initView();
        initDate();
        initControl();
    }

    //取消actionbar并状态栏设为透明留出空位
    public void setState(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE; //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(this.getResources().getColor(R.color.color2));
        }
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/
    }
    public void initDate(){
        name=getIntent().getStringExtra("name");
        list=LitePal.where("name=?",name).find(Message.class);
    }

    public void initView(){
        btLeft=findViewById(R.id.btLeft);
        btRight=findViewById(R.id.btRight);
        editText=findViewById(R.id.edieText);
        toolbarChat=findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbarChat);
        toolbarChat.setNavigationIcon(R.drawable.back1);
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ChatActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void initControl() {
        getSupportActionBar().setTitle(name);
        recyclerView.setItemAnimator(null);
        recyclerViewAdapter=new RecyclerViewAdapter(ChatActivity.this,list,name);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.scrollToPosition(list.size()-1);
        btLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessage(0);
            }
        });
        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessage(1);
            }
        });
        toolbarChat.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarChat.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.chat:
                        Toast.makeText(ChatActivity.this,"点击了菜单",Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat,menu);
        return true;
    }

    public void setMessage(int type){
        if(!editText.getText().toString().equals("")){
            Date date=new Date();
            //SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm:ss");//设置日期格式
            Message message0=LitePal.where("name=?",name).findLast(Message.class);
            if(message0==null){
                timeIsShow=true;
            }else{
                if(getDatePoor(date,message0.getDate())>15){
                    long l=getDatePoor(date,message0.getDate());
                    timeIsShow=true;
                }
            }
            Message message=new Message(name,editText.getText().toString(),type,dfTime.format(date),timeIsShow,date);
            message.save();
            list.add(message);
            recyclerViewAdapter.notifyItemChanged(list.size()-1);
            recyclerView.scrollToPosition(list.size()-1);
            editText.setText("");
            User user=new User();
            user.setChat(true);
            user.updateAll("name=?",name);
            timeIsShow=false;
        }else {
            Toast.makeText(this, "不能发送空消息！", Toast.LENGTH_SHORT).show();
        }
    }

    public static long getDatePoor(Date nowDate, Date beforeDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = nowDate.getTime() - beforeDate.getTime();
        // 计算差多少天
        long day = diff/nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return sec;
    }
}
