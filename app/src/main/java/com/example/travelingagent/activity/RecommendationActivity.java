package com.example.travelingagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.travelingagent.R;

public class RecommendationActivity extends AppCompatActivity {
    private Button btn;
    private CheckBox cb;
    private LinearLayout linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);//此activity方法将生成指定布局的视图并将其放在屏幕上

        linear= (LinearLayout) findViewById(R.id.linear);
        btn= (Button) findViewById(R.id.search_button);//引用已经实例化的组件

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer sb = new StringBuffer();
                int cnum =linear.getChildCount();//linear下所包含的复选框个数
                for (int i = 0;i<cnum;++i)
                {CheckBox cb = (CheckBox) linear.getChildAt(i);//通过linear对象查看其所包含的复选框状态
                    if (cb.isChecked()){//判断checkbox是否被选中
                        sb.append(cb.getText().toString());//将复选框中文字加载到StringBuffer中
                        sb.append("和");
                    }}
                sb.append("被选中了！");
                Toast.makeText(RecommendationActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();//显示那些被选中的条件

                Intent intent = new Intent(RecommendationActivity.this, RecommendationDisplayActivity.class);
                startActivity(intent);
            }
        });

    }



}
