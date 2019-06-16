package com.example.travelingagent.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.travelingagent.R;
import com.example.travelingagent.util.adapter.RecyclerAdapter;
import com.example.travelingagent.util.model.DataBean;

import java.util.ArrayList;
import java.util.List;


public class HistoryResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<DataBean> dataBeanList;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_result);//获得activity_main布局
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);//得到recycler View
        initData();
    }

    /**
     * 模拟数据
     */
    private void initData(){//初始化数据源,   ****需整合后端1****
        dataBeanList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {//在此处放入 1.城市、此城市的第几次规划 2、出发、结束日期 3、始点、终点站 4、所有景点罗列
            dataBean = new DataBean();
            dataBean.setID(i+"");
            dataBean.setType(0);//父类布局
            dataBean.setParentLeftTxt("上海市 第"+i+"条路线");
            dataBean.setParentRightTxt("出发->6/17/2019\n 结束->6/22/2019");
            dataBean.setParentbottomRightTxt("迪士尼乐园--->海洋馆");
            System.out.println("111路线：："+dataBean.getParentbottomRightTxt());

//            dataBean.setChildLeftTxt("第一站：迪士尼乐园\n" +
//                    "第二站：SJTU\n"+"第三站：海洋馆\n"+"酒店：上海和平饭店\n");
            dataBean.setChildLeftTxt("迪士尼乐园--->SJTU--->海洋馆-->上海和平饭店\n");
            dataBean.setChildRightImage(R.drawable.hotel_2);//此处加载图片
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();
    }

    private void setData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//获得布局管理器
        mAdapter = new RecyclerAdapter(this,dataBeanList);//由databean list 创建Recycler 适配器
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {//滚动监听器
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }

}
