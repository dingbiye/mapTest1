package com.sincodest.maptest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class DrawerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "data Deleted", Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DrawerActivity.this, "data Restored", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.mipmap.ic_launcher);
        mToolbar.setTitle("主标题");
        mToolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

//        NestedScrollView lv_test = findViewById(R.id.lv_test);
        List<String> list = new ArrayList();
        for(int i = 0; i < 25; i++){
            list.add("hi");
        }
//        lv_test.setAdapter(new MyAdapter(list));


        //        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        };
//        mDrawerToggle.syncState();
//        mDrawerLayout.setDrawerListener(mDrawerToggle);//设置监听器
    }
    class MyAdapter extends BaseAdapter{
        private List<String> list;
        public MyAdapter(List<String> list){
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = View.inflate(getApplicationContext(), R.layout.my_text, null);
            }
            TextView tv_test_appbarlayout = convertView.findViewById(R.id.tv_test_appbarlayout);
            tv_test_appbarlayout.setText("test_appbarlayout" + position);
            return convertView;
        }
    }
    protected boolean toggleDrawerLayout() {
        //如果左边的已打开，则关闭左边的，不进行后续操作
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return true;
        }
        //如果左边的没打开，右边的打开了关闭，关闭了打开
        if (mDrawerLayout.isDrawerOpen(Gravity.END)) {
            mDrawerLayout.closeDrawer(Gravity.END);
        } else {
            mDrawerLayout.openDrawer(Gravity.END);
        }
        return true;
    }
}

