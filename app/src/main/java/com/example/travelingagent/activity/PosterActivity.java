package com.example.travelingagent.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.travelingagent.R;

import java.util.List;

public class PosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        Intent intent = getIntent();
        String post_list = intent.getStringExtra("attractions");

//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Capture the layout's TextView and set the string as its text
//        if(navigationView.getHeaderCount() > 0) {
//            View header = navigationView.getHeaderView(0);
//            TextView un = (TextView) header.findViewById(R.id.textUser);
//            un.setText(post_list);
//        }

    }
}
//
//
//public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {
//    public HomeAdapter(int layoutResId, List data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, HomeItem item) {
//        helper.setText(R.id.text, item.getTitle());
//        helper.setImageResource(R.id.icon, item.getImageResource());
//        // 加载网络图片
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));
//    }
//
//
//
//adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
//
//    {
//        @Override
//        public void onItemClick (BaseQuickAdapter adapter, View view,int position){
//        Log.d(TAG, "onItemClick: ");
//        Toast.makeText(ItemClickActivity.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
//    }
//    });
//
//}
