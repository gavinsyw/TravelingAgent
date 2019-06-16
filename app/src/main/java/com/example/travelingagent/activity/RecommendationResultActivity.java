package com.example.travelingagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.travelingagent.R;
import com.example.travelingagent.activity.ui.main.SectionsPagerAdapter;
import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.ButtonFlat;

import retrofit2.Retrofit;

public class RecommendationResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_result);

        Intent intent = getIntent();
        final int[] choice_data = intent.getIntArrayExtra("choiceData");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        ButtonFlat confirm_bt = findViewById(R.id.confirm_plan);
        confirm_bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //开始confirm的动作
//                Intent intent = new Intent(RecommendationResultActivity.this,RecommendationDisplayActivity.class);
                Intent intent = new Intent(RecommendationResultActivity.this,RecommendationDisplayActivity.class);
                intent.putExtra("choiceData",choice_data);
                startActivity(intent);
            }
        });
    }
}