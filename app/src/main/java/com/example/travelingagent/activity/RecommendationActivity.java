package com.example.travelingagent.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.travelingagent.R;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.Slider;
import com.github.channguyen.rsv.RangeSliderView;

import java.util.Vector;

public class RecommendationActivity extends AppCompatActivity {
    private Button btn;
    private CheckBox cb;
    private LinearLayout linear;
    int choice1, choice2, choice3, choice4, choice5, choice6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_preference);//此activity方法将生成指定布局的视图并将其放在屏幕上

        RangeSliderView slider1 = (RangeSliderView) findViewById(
                R.id.rsv_smal1);
        RangeSliderView slider2 = (RangeSliderView) findViewById(
                R.id.rsv_smal2);
        RangeSliderView slider3 = (RangeSliderView) findViewById(
                R.id.rsv_smal3);
        RangeSliderView slider4 = (RangeSliderView) findViewById(
                R.id.rsv_smal4);
        RangeSliderView slider5 = (RangeSliderView) findViewById(
                R.id.rsv_smal5);
        RangeSliderView slider6 = (RangeSliderView) findViewById(
                R.id.rsv_smal6);

        final RangeSliderView.OnSlideListener listener1 = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                choice1 = (index + 1) * 20;
            }
        };

        final RangeSliderView.OnSlideListener listener2 = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                choice2 = (index + 1) * 20;
            }
        };

        final RangeSliderView.OnSlideListener listener3 = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                choice3 = (index + 1) * 20;
            }
        };

        final RangeSliderView.OnSlideListener listener4 = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                choice4 = (index + 1) * 20;
            }
        };

        final RangeSliderView.OnSlideListener listener5 = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                choice5 = (index + 1) * 20;
            }
        };

        final RangeSliderView.OnSlideListener listener6 = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                choice6 = (index + 1) * 20;
            }
        };

        slider1.setOnSlideListener(listener1);
        slider2.setOnSlideListener(listener2);
        slider3.setOnSlideListener(listener3);
        slider4.setOnSlideListener(listener4);
        slider5.setOnSlideListener(listener5);
        slider6.setOnSlideListener(listener6);

        final int[] choice_data = new int[6];
        choice_data[0] = choice1;
        choice_data[1] = choice2;
        choice_data[2] = choice3;
        choice_data[3] = choice4;
        choice_data[4] = choice5;
        choice_data[5] = choice6;

        ButtonFlat confirm_bt = (ButtonFlat) findViewById(R.id.buttonflat);
        confirm_bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //开始confirm的动作
                Intent intent = new Intent(RecommendationActivity.this,RecommendationDisplayActivity.class);
                intent.putExtra("choiceData", choice_data);
                startActivity(intent);
            }
        });


    }



}
