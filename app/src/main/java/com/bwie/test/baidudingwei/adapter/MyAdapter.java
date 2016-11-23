package com.bwie.test.baidudingwei.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bwie.test.baidudingwei.FirstFragment;

/**
 * Created by qiao on 2016/11/22.public MyAdapter(FragmentManager , String[] ) {
        super();
    }
 */
public class MyAdapter extends FragmentPagerAdapter {


    private String[] title;

    public MyAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        FirstFragment fm=new FirstFragment();
        Bundle b=new Bundle();
        b.putString("title",title[position]);
        fm.setArguments(b);
        return fm;
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
