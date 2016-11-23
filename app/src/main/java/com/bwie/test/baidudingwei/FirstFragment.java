package com.bwie.test.baidudingwei;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.baidudingwei.com.bwie.test.bean.Data;
import com.bwie.test.baidudingwei.com.bwie.test.bean.DataBean;
import com.bwie.test.baidudingwei.util.OkHttp;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
/**
 * Created by qiao on 2016/11/22.
 */
public class FirstFragment extends Fragment{
    private HomeAdapter mAdapter;
    private RecyclerView recyview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(),R.layout.item_f1,null);//返回一个填充的布局
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //得到一个bundle对象接收传过来的值
        Bundle bundle = getArguments();
        String path=bundle.getString("title");
        //初始化控件
        recyview = (RecyclerView) getView().findViewById(R.id.rc);
        //设置布局管理器
        recyview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //应用Okhttp实现异步请求数据
        OkHttp.getAsync(path,new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                //解析json字符串
                Gson gson=new Gson();
                DataBean dataBean=gson.fromJson(result,DataBean.class);
                List<Data> data=dataBean.result.data;
                //设置适配器
                recyview.setAdapter(mAdapter = new HomeAdapter(getActivity(),data));
            }
        });
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {
        private Context context;
        private List<Data> data;
        public HomeAdapter(Context context, List<Data> data) {
            this.context = context;
            this.data=data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            //将得到的值赋值给TextView
            holder.tv.setText(data.get(position).content);
            holder.tv_datatime.setText(data.get(position).updatetime);
            //添加监听事件
            recyview.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), new RecyclerViewClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //点击吐司解析得到的内容
                    Toast.makeText(getActivity(),data.get(position).content,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            }));

        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView tv;
            TextView tv_datatime;
            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
                tv_datatime=(TextView) view.findViewById(R.id.id_datatime);
            }
        }
    }
}
