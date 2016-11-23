package com.bwie.test.baidudingwei;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.test.baidudingwei.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;
public class SecondActivity extends FragmentActivity {
    private LinearLayout ll;
    private ViewPager vp;
    private List<TextView> list_tv=new ArrayList<TextView>();
    private List<Fragment> lf;
    private String[] title={"http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page=2&pagesize=10&sort=asc&time=1418745237","http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page=3&pagesize=10&sort=asc&time=1418745237","http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page=4&pagesize=10&sort=asc&time=1418745237","http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page=5&pagesize=10&sort=asc&time=1418745237"};
    private TextView tv_ding;
    private String ad;
    private String la;
    private String lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //初始化控件
        tv_ding = (TextView) findViewById(R.id.tv);
        vp = (ViewPager) findViewById(R.id.vp);
        ll = (LinearLayout) findViewById(R.id.ll);
        //点击跳转到主界面并将定位地址传过去
        Intent intent=getIntent();
        ad = intent.getStringExtra("addrstr");
        tv_ding.setText("定位地址："+ad);
        //循环取出LinerLayout里面的TextView
        for (int i=0;i<ll.getChildCount();i++){
            TextView tv= (TextView) ll.getChildAt(i);
            //将取出的值赋值给num
            final int num=i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (TextView tv : list_tv) {
                        tv.setTextColor(Color.BLACK);
                    }
                    //设置TextView跟随ViewPager实现连动效果
                    vp.setCurrentItem(num);
                }
            });
            //将所取出来的TextView添加到集合
            list_tv.add(tv);
        }
        //设置默认选中第一个
        list_tv.get(0).setTextColor(Color.GREEN);
        //设置适配器并传入一个接口的数组
        vp.setAdapter(new MyAdapter(getSupportFragmentManager(),title));
        //viewpager改变时监听
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (TextView tv :list_tv) {
                    tv.setTextColor(Color.BLACK);
                }
                list_tv.get(position).setTextColor(Color.GREEN);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
