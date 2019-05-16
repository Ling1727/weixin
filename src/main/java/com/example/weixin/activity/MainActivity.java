package com.example.weixin.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.weixin.tool.BottomNavigationViewHelper;
import com.example.weixin.fragment.Fragment1;
import com.example.weixin.adapter.FragmentAdapter;
import com.example.weixin.fragment.FragmentInterface;
import com.example.weixin.fragment.MeFragment;
import com.example.weixin.R;
import com.example.weixin.tool.StatusStyle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity {
    private ViewPager vp1;
    private List<Fragment> fragments=new ArrayList<>();
    public  BottomNavigationView navigation;
    private FragmentInterface fragment1;
    private FragmentManager fragmentManager;
    private Boolean isLogin;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vp1.setCurrentItem(0);
                    fragment1.choose(0);
                    return true;
                case R.id.navigation_dashboard:
                    vp1.setCurrentItem(0);
                    fragment1.choose(1);
                    return true;
                case R.id.navigation_notifications:
                    vp1.setCurrentItem(0);
                    fragment1.choose(2);
                    return true;
                case R.id.navigation_me:
                    vp1.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLogin();
    }

    //判断是否登录
    public void isLogin(){
        SharedPreferences sharedPreferences=getSharedPreferences("set",0);
        isLogin=sharedPreferences.getBoolean("isLogin",false);
        if(isLogin){
            initView();
            initData();
            initControl();
        }else{
            Intent intent=new Intent("android.intent.action.LOGIN");
            startActivity(intent);
            finish();
        }
    }
    //填入页面数据
    public void initView(){
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        vp1=findViewById(R.id.vp1);
    }

    public void initData() {
        //LayoutInflater inflater=getLayoutInflater().from(MainActivity.this);
        /*MeFragment meFragment=new MeFragment();
        Bundle date = new Bundle();
        date.putInt("width", width);
        meFragment.setArguments(date);        //传送数据*//*
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(views1.get(1).findViewById(R.id.llContain).getId(),meFragment);        //使用videoFragment替换llVideo容器的内容（可能为空也可能是别的碎片）
        fragmentTransaction.commit();*/
        fragment1=new Fragment1();
        fragments.add((Fragment1) fragment1);
        fragments.add(new MeFragment());
        fragmentManager =getSupportFragmentManager();
    }

    public void initControl(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        vp1.setAdapter(new FragmentAdapter(fragmentManager,fragments));
        vp1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    navigation.getMenu().getItem(3).setChecked(true);
                    fragment1.choose(2);
                }else{
                    navigation.getMenu().getItem(2).setChecked(true);
                }
                Log.d("test","啦啦啦啦"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为toolbar创建Menu
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

}
