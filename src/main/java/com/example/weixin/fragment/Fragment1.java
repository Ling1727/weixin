package com.example.weixin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.weixin.R;
import com.example.weixin.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/3/9.
 */

public class Fragment1 extends Fragment implements FragmentInterface{
    private Toolbar toolbar;
    private ViewPager vp2;
    private List<Fragment> fragments=new ArrayList<>();
    private View view;
    public BottomNavigationView navigation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.page1,null);
        fragments.add(new WeiXinFargment());
        fragments.add(new MailListFragment());
        fragments.add(new FindFragment());
        vp2=view.findViewById(R.id.vp2);
        FragmentManager childFragmentManager =getChildFragmentManager();
        vp2.setAdapter(new FragmentAdapter(childFragmentManager,fragments));
        vp2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(navigation==null){
                    navigation=getActivity().findViewById(R.id.navigation);
                }
                navigation.getMenu().getItem(position).setChecked(true);
                switch (position) {
                    case 0:
                        toolbar.setTitle("微信");
                        break;
                    case 1:
                        toolbar.setTitle("通讯录");
                        break;
                    case 2:
                        toolbar.setTitle("发现");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setToolbar();
        return view;
    }

    //设置工具栏
    public void setToolbar(){
        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setTitle("微信");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        Intent intent=new Intent("android.intent.action.SEARCHVIEW");
                        startActivity(intent);
                        break;
                    case R.id.dd:
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public void choose(int position) {
        Log.d("test",position+"");
        vp2.setCurrentItem(position);
        switch (position){
            case 0:
                toolbar.setTitle("微信");
                break;
            case 1:
                toolbar.setTitle("通讯录");
                break;
            case 2:
                toolbar.setTitle("发现");
                break;
            default:
                break;

        }

    }
}
