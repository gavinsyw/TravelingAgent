package com.example.travelingagent.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelingagent.R;
import com.example.travelingagent.protocol.ItineraryClientApi;
import com.example.travelingagent.util.adapter.FoldingCellListAdapter;
import com.example.travelingagent.util.adapter.RecyclerAdapter;
import com.example.travelingagent.util.model.DataBean;
import com.gc.materialdesign.views.ButtonFlat;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HistoryResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<DataBean> dataBeanList;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;
    private List<Itinerary> itineraries;
    private String user_id;
    private ListView mListView;
    private String BASE_URL = "http://192.168.43.126:8080/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_result);//获得activity_main布局
        mListView = findViewById(R.id.mainListView);

        final Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ItineraryClientApi itineraryClientApi = retrofit.create(ItineraryClientApi.class);
        Call<List<Itinerary>> call = itineraryClientApi.itinerariesLoad(user_id);

        call.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Call<List<Itinerary>> call, Response<List<Itinerary>> response) {
                itineraries = response.body();

                initData();
//                setData();

//                // add custom btn handler to first list item
//                itineraries.get(0).setRequestBtnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
//                    }
//                });

                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final FoldingCellListAdapter adapter = new FoldingCellListAdapter(HistoryResultActivity.this, itineraries);

//                // add default btn handler for each request btn on each item if custom handler not found
//                adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
//                    }
//                });

                mListView.setAdapter(adapter);

                // set on click event listener to list view
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        Intent intent1 = new Intent(HistoryResultActivity.this, SavedItineraryDisplayActivity.class);
                        intent1.putExtra("user_id", user_id);
                        intent1.putExtra("itinerary_index", String.valueOf(pos));
                        startActivity(intent1);
//                        // toggle clicked cell state
//                        ((FoldingCell) view).toggle(false);
//                        // register in adapter that state for selected cell is toggled
//                        adapter.registerToggle(pos);
                    }
                });

//                mListView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        View view = View.inflate(HistoryResultActivity.this, R.layout.content_recommendation, null);
//                        ButtonFlat buttonFlat = findViewById(R.id.delete_itinerary_button);
//                        buttonFlat.setOnClickListener();
//
//                        DialogPlus dialog = DialogPlus.newDialog(HistoryResultActivity.this)
//                                .setHeader(R.layout.delete_header)
//                                .setGravity(Gravity.CENTER)
//                                .setContentHolder(new ViewHolder(view))
//                                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
//                                .create();
//                        dialog.show();
//
//
//
//                        return true;
//                    }
//                });
            }

            @Override
            public void onFailure(Call<List<Itinerary>> call, Throwable t) {
                Toast.makeText(HistoryResultActivity.this, "failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }



    /**
     * 模拟数据
     */
//    private void setData(){//初始化数据源,   ****需整合后端1****
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//获得布局管理器
//        mAdapter = new RecyclerAdapter(this, dataBeanList);//由databean list 创建Recycler 适配器
//        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {//滚动监听器
//            @Override
//            public void scrollTo(int pos) {
//                mRecyclerView.scrollToPosition(pos);
//            }
//        });
//    }

    private void initData(){
        dataBeanList = new ArrayList<>();
        if (itineraries.size() == 0) {
            Toast.makeText(HistoryResultActivity.this, "没有已创建的行程", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < itineraries.size(); i++) {//在此处放入 1.城市、此城市的第几次规划 2、出发、结束日期 3、始点、终点站 4、所有景点罗列
            dataBean = new DataBean();
            dataBean.setID(i+"");
            dataBean.setType(0);//父类布局
            dataBean.setParentLeftTxt(itineraries.get(i).getCityName() + "市 第" + i + "条路线");
            dataBean.setParentRightTxt("出发->6/17/2019\n 结束->6/22/2019");
//            dataBean.setParentbottomRightTxt("迪士尼乐园--->海洋馆");
            System.out.println("111路线：："+dataBean.getParentbottomRightTxt());

//            dataBean.setChildLeftTxt("迪士尼乐园--->SJTU--->海洋馆-->上海和平饭店\n");
//            dataBean.setChildRightImage(R.drawable.hotel_2);//此处加载图片
            dataBean.setChildBean(dataBean);

            dataBeanList.add(dataBean);
        }
    }

    private void getItineraryList(ItineraryClientApi itineraryClientApi, String user_id) {
        Call<List<Itinerary>> call = itineraryClientApi.itinerariesLoad(user_id);

        call.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Call<List<Itinerary>> call, Response<List<Itinerary>> response) {
                List<Itinerary> itineraries = response.body();
            }

            @Override
            public void onFailure(Call<List<Itinerary>> call, Throwable t) {

            }
        });
    }
}
