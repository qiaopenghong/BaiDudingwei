package com.bwie.test.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwie.test.myapplication.com.bwie.test.xlistview.Data;

import java.util.List;

/**
 * Created by qiao on 2016/11/23.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Data> data;

    public MyAdapter(Context context, List<Data> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= View.inflate(context,R.layout.item,null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(data.get(i).content);
        return view;
    }
}
