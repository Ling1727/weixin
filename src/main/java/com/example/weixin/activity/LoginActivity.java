package com.example.weixin.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.weixin.R;
import com.example.weixin.tool.TouXiang;
import com.example.weixin.litepal.Member;
import com.example.weixin.litepal.Meself;
import com.example.weixin.litepal.User;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hasee on 2019/3/20.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    private TextView tvRegister,tvFind,tvFreeze;
    private EditText etId,etPassword;
    private Button btLogin;
    private List<Member> list;
    private List<Meself> listme;
    private List<User> listUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        initControl();
    }

    public void initView() {
        tvRegister=findViewById(R.id.tvRegister);
        tvFind=findViewById(R.id.tvFind);
        tvFreeze=findViewById(R.id.tvFreeze);
        etId=findViewById(R.id.etId);
        etPassword=findViewById(R.id.etPassword);
        btLogin=findViewById(R.id.btLogin);
    }

    public void initData() {
        list=LitePal.findAll(Member.class);
        listme=LitePal.findAll(Meself.class);
    }
    public void initData1(String me){
        SharedPreferences sp=getSharedPreferences("set",0);
        Boolean isFirst=sp.getBoolean("isFirst",true);
        if(isFirst){
            listUser=new ArrayList();
            listUser.add(new User("霞",me,"xia"));
            listUser.add(new User("波比",me,"bobi"));
            listUser.add(new User("阿狸",me,"li"));
            listUser.add(new User("小炮",me,"xiaopao"));
            listUser.add(new User("小鱼人",me,"xiaoyuren"));
            listUser.add(new User("Jason",me,"java"));
            listUser.add(new User("Java",me,"java"));
            listUser.add(new User("Python",me,"java"));
            listUser.add(new User("C",me,"java"));
            listUser.add(new User("C#",me,"java"));
            listUser.add(new User("C++",me,"java"));
            listUser.add(new User("卡莎",me,"kasa"));
            listUser.add(new User("寒冰",me,"hanbing"));
            listUser.add(new User("赵信",me,"zhaoxin"));
            listUser.add(new User("女警",me,"nvjing"));
            listUser.add(new User("快乐风男",me,"yasuo"));
            listUser.add(new User("刀妹",me,"daomei"));
            listUser.add(new User("提莫",me,"timo"));
            listUser.add(new User("瑞雯",me,"ruiwen"));
            listUser.add(new User("安妮",me,"anni"));
            listUser.add(new User("女枪",me,"nvqiang"));
            listUser.add(new User("奥巴马",me,"aobama"));
            Collections.sort(listUser);
            for(int i=0;i<listUser.size();i++){
                User user=listUser.get(i);
                user.save();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new TouXiang(getApplicationContext()).write();
                }
            }).start();
            sp.edit().putBoolean("isFirst",false);
        }
    }

    public void initControl() {
        btLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvFind.setOnClickListener(this);
        tvFreeze.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btLogin:
                if(btLogin.getText().toString().equals("登录")){
                    if(etId.getText().toString().equals("")||etPassword.getText().toString().equals("")){
                        Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                    }else{
                        Boolean isOk=false;
                        for(int i=0;i<list.size();i++){
                            if(etId.getText().toString().equals(list.get(i).getName())){
                                if(etPassword.getText().toString().equals(list.get(i).getPassword())){
                                    SharedPreferences sharedPreferences=getSharedPreferences("set",0);
                                    sharedPreferences.edit().putBoolean("isLogin",true).apply();
                                    if(listme.size()==0){
                                        Meself meself=new Meself();
                                        meself.setName(etId.getText().toString());
                                        meself.setPassword(etPassword.getText().toString());
                                        meself.setUsers(listUser);
                                        meself.setTouxiangPath("mao");
                                        meself.save();
                                    }else{
                                        Meself meself=new Meself();
                                        meself.setName(etId.getText().toString());
                                        meself.setPassword(etPassword.getText().toString());
                                        meself.setUsers(listUser);
                                        meself.setTouxiangPath("mao");
                                        meself.update(1);
                                    }
                                    isOk=true;
                                    initData1(etId.getText().toString());
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                break;
                            }
                        }
                        if(!isOk)
                        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(etId.getText().toString().equals("")||etPassword.getText().toString().equals("")){
                        Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                    }else{
                        Boolean isOk=true;
                        for(int i=0;i<list.size();i++){
                            if(etId.getText().toString().equals(list.get(i).getName())){
                                isOk=false;
                                Toast.makeText(this, "该名字已被使用", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        if(isOk){
                            Member member=new Member();
                            member.setName(etId.getText().toString());
                            member.setPassword(etPassword.getText().toString());
                            member.save();
                            list=LitePal.findAll(Member.class);
                            btLogin.setText("登录");
                            tvRegister.setText("新用户注册");
                            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return;
            case R.id.tvRegister:
                if(tvRegister.getText().toString().equals("新用户注册")){
                    etId.setText("");
                    etPassword.setText("");
                    btLogin.setText("注册");
                    tvRegister.setText("返回登录");
                }else{
                    btLogin.setText("登录");
                    tvRegister.setText("新用户注册");
                }
                return;
            case R.id.tvFind:
                return;
            case R.id.tvFreeze:
                return;
        }
    }

}
