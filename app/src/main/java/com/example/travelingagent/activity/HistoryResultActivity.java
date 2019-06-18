package com.example.travelingagent.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelingagent.R;
import com.example.travelingagent.protocol.api.ItineraryClientApi;
import com.example.travelingagent.util.adapter.FoldingCellListAdapter;
import com.example.travelingagent.util.adapter.RecyclerAdapter;
import com.example.travelingagent.util.model.DataBean;

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
        setContentView(R.layout.activity_history_result);   //获得activity_main布局
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

                final FoldingCellListAdapter adapter = new FoldingCellListAdapter(HistoryResultActivity.this, itineraries);

                mListView.setAdapter(adapter);

                // set on click event listener to list view
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        Intent intent1 = new Intent(HistoryResultActivity.this, SavedItineraryDisplayActivity.class);
                        intent1.putExtra("user_id", user_id);
                        intent1.putExtra("itinerary_index", String.valueOf(pos));
                        startActivity(intent1);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Itinerary>> call, Throwable t) {
                Toast.makeText(HistoryResultActivity.this, "failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
