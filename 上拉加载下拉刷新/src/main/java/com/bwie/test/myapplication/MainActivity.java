package com.bwie.test.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.bwie.test.myapplication.com.bwie.test.xlistview.Data;
import com.bwie.test.myapplication.com.bwie.test.xlistview.DataBean;
import com.bwie.test.myapplication.com.bwie.test.xlistview.OkHttp;
import com.bwie.test.myapplication.com.bwie.test.xlistview.XListView;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private XListView lv;
    private int page=2;
    private String path="http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page="+page+"&pagesize=10&sort=asc&time=1418745237";
    private List<Data> data;
    private MyAdapter adapter;
    private List<Data> dlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        lv = (XListView) findViewById(R.id.lv);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
        lv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page++;
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        lv.setRefreshTime(dateFormat());
                        lv.stopLoadMore();
                        lv.stopRefresh();
                    }
                }, 500);
            }
            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv.stopRefresh();
                        lv.stopLoadMore();
                        lv.setRefreshTime("刚刚");
                    }
                }, 500);

            }
        });

    }

    private void initData() {
        OkHttp.getAsync(path, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson=new Gson();
                DataBean dataBean = gson.fromJson(result, DataBean.class);
                data=dataBean.result.data;
                Message msg=new Message();
                msg.obj=data;
                msg.what=1;
                mHandler.sendMessage(msg);


            }
        });
    }
    // 格式化时间
    private String dateFormat() {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd  hh:mm:ss");
        String format = dateFormat.format(currentTimeMillis);
        return format;
    }
    private Handler mHandler=new Handler(){



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    dlist = (List<Data>) msg.obj;
                    adapter = new MyAdapter(MainActivity.this, dlist);
                    lv.setAdapter(adapter);
                    break;
            }
        }
    };
}
