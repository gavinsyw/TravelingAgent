package com.example.travelingagent.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelingagent.R;
import com.example.travelingagent.activity.ui.main.SectionsPagerAdapter;
import com.gc.materialdesign.views.ButtonFlat;
import com.google.android.material.tabs.TabLayout;

public class RecommendationResultActivity extends AppCompatActivity {
    private String BASE_URL = "http://192.168.43.126:8080/";


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