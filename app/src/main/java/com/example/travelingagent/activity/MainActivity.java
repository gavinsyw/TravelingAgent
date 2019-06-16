package com.example.travelingagent.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelingagent.R;
import com.example.travelingagent.util.adapter.ModeAdapter;
import com.example.travelingagent.entity.ItemEntity;
import com.example.travelingagent.entity.ModeEntity;
import com.example.travelingagent.protocol.Weather;
import com.example.travelingagent.protocol.WeatherClientApi;
import com.example.travelingagent.util.pileLayout.util.Utils;
import com.example.travelingagent.util.pileLayout.widget.FadeTransitionImageView;
import com.example.travelingagent.util.pileLayout.widget.HorizontalTransitionLayout;
import com.example.travelingagent.util.pileLayout.widget.VerticalTransitionLayout;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.stone.pile.libs.PileLayout;
import com.webianks.easy_feedback.EasyFeedback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xmuSistone on 2017/5/12.
 */
public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private View positionView;
    private PileLayout pileLayout;
    private List<ItemEntity> dataList;
    private List<ModeEntity> modeList = new ArrayList<ModeEntity>();

    private int lastDisplay = -1;

    private ObjectAnimator transitionAnimator;
    private float transitionValue;
    private HorizontalTransitionLayout countryView, temperatureView;
    private VerticalTransitionLayout addressView, timeView;
    private FadeTransitionImageView bottomView;
    private Animator.AnimatorListener animatorListener;
    private TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // 加载模式选择需要的信息
        initModes();
        initDataList();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String username = intent.getStringExtra("UserName");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Capture the layout's TextView and set the string as its text
        if(navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            TextView un = (TextView) header.findViewById(R.id.textUser);
            un.setText(username);
        }
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {//实现点击操作
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                System.out.println("点击的item id 是："+id);
                Intent mIntent;
                switch (id) {
                    case R.id.nav_login:
                        mIntent = new Intent(MainActivity.this, NewLoginActivity.class);
                        startActivity(mIntent);
                        break;
                    case R.id.nav_poster:
                        mIntent = new Intent(MainActivity.this, HistoryResultActivity.class);
                        startActivity(mIntent);
                        break;
                    case R.id.nav_share:
                        break;
                    case R.id.nav_send:
                        new EasyFeedback.Builder(MainActivity.this)
                                .withEmail("fffffarmer@sjtu.edu.com")
                                .withSystemInfo()
                                .build()
                                .start();
                        break;
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        positionView = findViewById(R.id.positionView);
        countryView = findViewById(R.id.countryView);
        temperatureView = findViewById(R.id.temperatureView);
        pileLayout = findViewById(R.id.pileLayout);
        addressView = findViewById(R.id.addressView);
        descriptionView = findViewById(R.id.descriptionView);
        timeView = findViewById(R.id.timeView);
        bottomView = findViewById(R.id.bottomImageView);

        // 1. 状态栏侵入
        boolean adjustStatusHeight = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            adjustStatusHeight = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

        // 2. 状态栏占位View的高度调整
        String brand = Build.BRAND;
        if (brand.contains("Xiaomi")) {
            Utils.setXiaomiDarkMode(this);
        } else if (brand.contains("Meizu")) {
            Utils.setMeizuDarkMode(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            adjustStatusHeight = false;
        }
        if (adjustStatusHeight) {
            adjustStatusBarHeight(); // 调整状态栏高度
        }

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                countryView.onAnimationEnd();
                temperatureView.onAnimationEnd();
                addressView.onAnimationEnd();
                bottomView.onAnimationEnd();
                timeView.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };


        // 3. PileLayout绑定Adapter
//        initDataList();
        pileLayout.setAdapter(new PileLayout.Adapter() {
            @Override
            public int getLayoutId() {
                return R.layout.item_layout;
            }

            @Override
            public void bindView(View view, int position) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                if (viewHolder == null) {
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
                    view.setTag(viewHolder);
                }

                Glide.with(MainActivity.this).load(dataList.get(position).getCoverImageUrl()).into(viewHolder.imageView);
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }

            @Override
            public void displaying(int position) {
                descriptionView.setText(dataList.get(position).getDescription());
                if (lastDisplay < 0) {
                    initSecene(position);
                    lastDisplay = 0;
                } else if (lastDisplay != position) {
                    transitionSecene(position);
                    lastDisplay = position;
                }
            }

            @Override
            public void onItemClick(View view, int position) {
                super.onItemClick(view, position);
                ModeAdapter adapter = new ModeAdapter(MainActivity.this, R.layout.simple_list_item, modeList);

                DialogPlus dialog = DialogPlus.newDialog(MainActivity.this)
                        .setAdapter(adapter)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                Intent intent = null;
                                switch (position) {
                                    case 0:
                                        intent = new Intent(MainActivity.this, RecommendationActivity.class);
                                        break;
                                    case 1:
                                        intent = new Intent(MainActivity.this, SimulationActivity.class);
                                        break;
                                }
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .setHeader(R.layout.mode_header)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new GridHolder(2))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();

//                Intent intent2 = new Intent(MainActivity.this, Main2Activity.class);
//                String country_name=dataList.get(position).getCountry();
//                //Toast.makeText(MainActivity.this, country_name, Toast.LENGTH_SHORT ).show();
//                intent2.putExtra(EXTRA_MESSAGE, country_name);
//                startActivity(intent2);
            }
        });

    }

    private void initSecene(int position) {
        countryView.firstInit(dataList.get(position).getCountry());
        temperatureView.firstInit(dataList.get(position).getTemperature());
        addressView.firstInit(dataList.get(position).getAddress());
        bottomView.firstInit(dataList.get(position).getMapImageUrl());
        timeView.firstInit(dataList.get(position).getTime());
    }

    private void transitionSecene(int position) {
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }

        countryView.saveNextPosition(position, dataList.get(position).getCountry() + "-" + position);
        temperatureView.saveNextPosition(position, dataList.get(position).getTemperature());
        addressView.saveNextPosition(position, dataList.get(position).getAddress());
        bottomView.saveNextPosition(position, dataList.get(position).getMapImageUrl());
        timeView.saveNextPosition(position, dataList.get(position).getTime());

        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);

    }

    /**
     * 加载模式选择列表
     */
    private void initModes() {
        ModeEntity recommendation = new ModeEntity("路线推荐", R.drawable.recommendation_icon);
        modeList.add(recommendation);
        ModeEntity simulation = new ModeEntity("模拟旅行", R.drawable.map_icon);
        modeList.add(simulation);
    }

    /**
     * 调整沉浸状态栏
     */
    private void adjustStatusBarHeight() {
        int statusBarHeight = Utils.getStatusBarHeight(this);
        ViewGroup.LayoutParams lp = positionView.getLayoutParams();
        lp.height = statusBarHeight;
        positionView.setLayoutParams(lp);
    }


    /**
     * 从asset读取文件json数据
     */
    private void initDataList() {
        dataList = new ArrayList<>();
        try {
            InputStream in = getAssets().open("preset2.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int j = 0; j < 3; j++) {
                    for (int i = 0; i < len; i++) {
                        JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                        final ItemEntity itemEntity = new ItemEntity(itemJsonObject);
                        String cityId = itemEntity.getCityid();

                        // 魅族天气API接入
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://aider.meizu.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        WeatherClientApi WeatherClientApi = retrofit.create(WeatherClientApi.class);
                        Call<Weather> call = WeatherClientApi.weather(cityId);

                        call.enqueue(new Callback<Weather>() {
                            @Override
                            public void onResponse(Call<Weather> call, Response<Weather> response) {
                                Weather weather = response.body();
                                Weather.Value info = weather.value.get(0);
                                List<Weather.Value.WeatherInfo> weather_info = info.weathers;
                                List<Weather.Value.ForecastInfo> forecast_Info_info = info.indexes;
                                itemEntity.setTemperature(weather_info.get(0).temp_night_c + "-" + weather_info.get(0).temp_day_c + "°C");
                                for (Weather.Value.ForecastInfo forecastInfo : forecast_Info_info) {
                                    if (forecastInfo.name.equals("穿衣指数")) {
                                        itemEntity.setTime(forecastInfo.level + "-" + forecastInfo.content);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Weather> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                        dataList.add(itemEntity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 属性动画
     */
    public void setTransitionValue(float transitionValue) {
        this.transitionValue = transitionValue;
        countryView.duringAnimation(transitionValue);
        temperatureView.duringAnimation(transitionValue);
        addressView.duringAnimation(transitionValue);
        bottomView.duringAnimation(transitionValue);
        timeView.duringAnimation(transitionValue);
    }

    public float getTransitionValue() {
        return transitionValue;
    }
    class ViewHolder {
        ImageView imageView;
    }
}