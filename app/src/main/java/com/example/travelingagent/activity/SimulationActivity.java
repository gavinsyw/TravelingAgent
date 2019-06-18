package com.example.travelingagent.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.travelingagent.R;
import com.example.travelingagent.myentity.Hotel;
import com.example.travelingagent.myentity.Sight;
import com.example.travelingagent.myentity.Spot;
import com.example.travelingagent.protocol.ItineraryClientApi;
import com.example.travelingagent.protocol.CustomizationClientApi;
import com.example.travelingagent.util.baiduMap.DrivingRouteOverlay;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SimulationActivity extends AppCompatActivity implements OnGetRoutePlanResultListener {
    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private String user_id;
    private String city_id;
    private List<Spot> spotList = new ArrayList<>();
    private Retrofit retrofit;

    private String BASE_URL = "http://192.168.43.126:8080/";

    RoutePlanSearch mSearch = null;
    LatLng currentLocation = null;
    List<Hotel> hotelVec = new Vector<>();
    List<Sight> sightVec = new Vector<>();
    Itinerary itinerary; // TODO: 添加id接口
    List<Marker> markers = new ArrayList<>(30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_simulation);
        mapView = findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        currentLocation = new LatLng(31.209519, 121.457545);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final BitmapDescriptor selectedBitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker_yellow);
        final BitmapDescriptor startBitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker_blue);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itinerary.getSpotNum() != 0) {
                    ItineraryClientApi itineraryClientApi = retrofit.create(ItineraryClientApi.class);
                    saveItinerary(itineraryClientApi, itinerary);
                }

                else {
                    Toast.makeText(SimulationActivity.this, "选择下一站以完善行程", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        city_id = intent.getStringExtra("city_id");
        itinerary = new Itinerary(0, Integer.parseInt(city_id), Integer.parseInt(user_id));

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(SimulationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(SimulationActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(SimulationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(SimulationActivity.this, permissions, 1);
        }

        Toast.makeText(SimulationActivity.this, "选择您将要入住的酒店", Toast.LENGTH_SHORT).show();

        final BaiduMap.OnMarkerClickListener markerClickListenerChosen = new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                String content = marker.getTitle(); // 假装利用title携带信息
                if (content == null) return false;
                String spotName = content.split("_")[0];
                final String spotID = content.split("_")[1];
                final String spotType = content.split("_")[2];
                Spot currentSpot = spotList.get(Integer.parseInt(spotID));

                View view = View.inflate(SimulationActivity.this, R.layout.content_simulation, null);
                TextView textView = view.findViewById(R.id.title);
                textView.setText("下一站，" + spotName + '?');

                ImageView imageView = view.findViewById(R.id.word_cloud_image);
                if (spotType.equals("0")) {
                    imageView.setImageResource(getDrawResourceID("sight_" + spotID));
                }
                else {
                    if (spotType.equals("1")) {
                        imageView.setImageResource(getDrawResourceID("hotel_" + spotID));
                    }
                }

                TextView rating = view.findViewById(R.id.page_rating);
                if (currentSpot.getType() == 0) {
                    String rate = String.valueOf(currentSpot.getTotal() / 20.0);
                    rate = rate.substring(0, rate.indexOf(".") + 2);
                    rating.setText(rate + " / 5.0\n 评分");
                }
                else {
                    String rate = String.valueOf(currentSpot.getTotal() / 10.0);
                    rate = rate.substring(0, rate.indexOf(".") + 2);
                    rating.setText(rate + " / 5.0\n 评分");
                }
                setSpan(rating, "\\d\\.\\d / \\d\\.\\d");

                TextView popularity = view.findViewById(R.id.page_popularity);
                popularity.setText(String.valueOf((int) currentSpot.getPopularity()) + "\n人气指数");
                setSpan(popularity, "\\d+");

                TextView money = view.findViewById(R.id.page_money);

                if (currentSpot.getType() == 0) {
                    money.setText(String.valueOf((int) currentSpot.getMoney())+ "\n门票");
                }
                else {
                    money.setText(String.valueOf((int) currentSpot.getMoney())+ "\n均价");
                }
                setSpan(money, "\\d+");

                final DialogPlus dialog = DialogPlus.newDialog(SimulationActivity.this)
                        .setContentHolder(new ViewHolder(view))
//                        .setAdapter(adapter)
                        .setCancelable(true)
                        .setHeader(R.layout.header_simulation)
                        .setExpanded(true, 2000)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();

                Button button_yes = findViewById(R.id.like_it_button);
                Button button_no = findViewById(R.id.maybe_not_button);

                button_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mSearch = RoutePlanSearch.newInstance();
                        mSearch.setOnGetRoutePlanResultListener(SimulationActivity.this);

                        PlanNode stNode = PlanNode.withLocation(currentLocation);
                        PlanNode enNode = PlanNode.withLocation(marker.getPosition());

                        mSearch.drivingSearch((new DrivingRoutePlanOption())
                                .from(stNode)
                                .to(enNode));

                        currentLocation = marker.getPosition();
                        navigateTo(currentLocation, 16, null);

                        marker.remove();
                        addMarker(currentLocation, selectedBitmap);

                        for (Spot spot : spotList) {
                            if (spot.getType() == Integer.parseInt(spotType) && spot.getID() == Integer.parseInt(spotID)) {
                                itinerary.add(spot);
                                break;
                            }
                        }

//                        if (spotType.equals("0")) {
//                            for (Spot spot : spotList) {
//                                if
//                            }
//                            itinerary.add(sightVec.get(Integer.parseInt(spotID) - 1));
//                        }
//                        else {
//                            if (spotType.equals("1")) {
//                                itinerary.add(hotelVec.get(Integer.parseInt(spotID) - 1));
//                            }
//                        }

                    }
                });

                button_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                return true;
            }
        };


        BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                String content = marker.getTitle(); // 假装利用title携带信息
                if (content == null) return false;
                String spotName = content.split("_")[0];
                final String spotID = content.split("_")[1];
                final String spotType = content.split("_")[2];
                Spot currentSpot = spotList.get(Integer.parseInt(spotID));

                if (spotType.equals("1")) {

                    View view = View.inflate(SimulationActivity.this, R.layout.content_simulation, null);
                    TextView textView = view.findViewById(R.id.title);
                    textView.setText("将" + spotName + "定为您旅行所居住的酒店吗?");

                    ImageView imageView = view.findViewById(R.id.word_cloud_image);
                    imageView.setImageResource(getDrawResourceID("hotel_" + spotID));

                    TextView rating = view.findViewById(R.id.page_rating);
                    if (spotType.equals("0")) {
                        String rate = String.valueOf(currentSpot.getTotal() / 20.0);
                        rate = rate.substring(0, rate.indexOf(".") + 2);
                        rating.setText(rate + " / 5.0\n 评分");
                    }
                    else {
                        String rate = String.valueOf(currentSpot.getTotal() / 10.0);
                        rate = rate.substring(0, rate.indexOf(".") + 2);
                        rating.setText(rate + " / 5.0\n 评分");
                    }
                    setSpan(rating, "\\d\\.\\d / \\d\\.\\d");

                    TextView popularity = view.findViewById(R.id.page_popularity);
                    popularity.setText(String.valueOf((int) currentSpot.getPopularity()) + "\n人气指数");
                    setSpan(popularity, "\\d+");

                    TextView money = view.findViewById(R.id.page_money);

                    if (currentSpot.getType() == 0) {
                        money.setText(String.valueOf((int) currentSpot.getMoney())+ "\n门票");
                    }
                    else {
                        money.setText(String.valueOf((int) currentSpot.getMoney())+ "\n均价");
                    }
                    setSpan(money, "\\d+");

                    final DialogPlus dialog = DialogPlus.newDialog(SimulationActivity.this)
                            .setContentHolder(new ViewHolder(view))
                            .setCancelable(true)
                            .setHeader(R.layout.header_simulation)
                            .setExpanded(true, 2000)  // This will enable the expand feature, (similar to android L share dialog)
                            .create();
                    dialog.show();

                    Button button_yes = findViewById(R.id.like_it_button);
                    Button button_no = findViewById(R.id.maybe_not_button);

                    button_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            baiduMap.setOnMarkerClickListener(markerClickListenerChosen);

                            currentLocation = marker.getPosition();
                            navigateTo(currentLocation, 18, null);

                            List<Marker> markers = getMarkersOnMap();
                            for (Marker markerItem : markers) {
                                markerItem.remove();
                            }
                            addMarker(currentLocation, startBitmap);

                            for (Spot spot :spotList) {
                                if (spot.getType() == 0 && spot.getID() != Integer.parseInt(spotID)) {
                                    addMarker(spot);
                                }

                                if (spot.getID() == Integer.parseInt(spotID) && spot.getType() == 1) {
                                    itinerary.add(spot);
                                }
                            }

//                            for (Marker markerItem : markers) {
//                                String id = markerItem.getTitle().split("_")[1];
//                                String type = markerItem.getTitle().split("_")[2];
//                                if (type.equals("0") && markerItem != marker) {
//                                    addMarker(markerItem.getPosition(), Integer.parseInt(type), Integer.parseInt(id));
//                                }
//                            }

//                            for (Spot spot : spotList) {
//                                if (spot.getType() == 1 && Integer.parseInt(spotID) == spot.getID()) {
//                                    itinerary.add(spot);
//                                }
//                            }

//                            itinerary.add(hotelVec.get(Integer.parseInt(spotID) - 1));

                        }
                    });

                    button_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
                return true;
            }
        };

        baiduMap.setOnMarkerClickListener(markerClickListener);

        getSpotData();

//        for (int i = 0; i < sightVec.size(); i++) {
//            addMarker(sightVec.get(i).getLatLng(), 0, sightVec.get(i).getID());
//        }
//
//        for (int j = 0; j < hotelVec.size(); j++) {
//            addMarker(hotelVec.get(j).getLatLng(), 1, hotelVec.get(j).getID());
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private int getDrawResourceID(String resourceName) {
        Resources res = getResources();
        int picid = res.getIdentifier(resourceName,"drawable",getPackageName());
        return picid;
    }

    private void navigateTo(LatLng ll, float zoom, String description) {
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(update);
        update = MapStatusUpdateFactory.zoomTo(zoom);
        baiduMap.animateMapStatus(update);
        isFirstLocate = false;
    }

    private List<Marker> getMarkersOnMap() {
        LatLng northeast = new LatLng(180, 180);
        LatLng southwest = new LatLng(0, 0);
        LatLngBounds.Builder builder  = new LatLngBounds.Builder();
        builder.include(northeast);
        builder.include(southwest);
        LatLngBounds latLngBounds = builder.build();

        return baiduMap.getMarkersInBounds(latLngBounds);
    }

    private void addMarker(LatLng ll, BitmapDescriptor bitmap) {
//        //定义Maker坐标点
//        LatLng point = new LatLng(lat, lng);
        //构建Marker图标
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(ll)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);

//        BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Toast.makeText(SimulationActivity.this, "blabla", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        };
    }

    private void addMarker(Spot spot) {
        BitmapDescriptor hotelBitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker_pink);
        BitmapDescriptor sightBitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker_green);
//        //定义Maker坐标点
//        LatLng point = new LatLng(lat, lng);
        //构建Marker图标
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = null;
        if (spot.getType() == 0) {
            option = new MarkerOptions()
                    .position(spot.getLatLng())
                    .icon(sightBitmap)
                    .title(spot.getName() + '_' + String.valueOf(spot.getID()) + "_0");
        }
        else {
            option = new MarkerOptions()
                    .position(spot.getLatLng())
                    .icon(hotelBitmap)
                    .title(spot.getName() + '_' + String.valueOf(spot.getID()) + "_1");
        }


        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
    }

    private void addMarker(LatLng ll, int type, int id) {
        BitmapDescriptor hotelBitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker_pink);
        BitmapDescriptor sightBitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker_green);
//        //定义Maker坐标点
//        LatLng point = new LatLng(lat, lng);
        //构建Marker图标
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = null;
        if (type == 0) {
            option = new MarkerOptions()
                    .position(ll)
                    .icon(sightBitmap)
                    .title(sightVec.get(id - 1).getName() + '_' + String.valueOf(id) + "_0");
        }
        else {
            option = new MarkerOptions()
                    .position(ll)
                    .icon(hotelBitmap)
                    .title(hotelVec.get(id - 1).getName() + '_' + String.valueOf(id) + "_1");
        }


        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
    }

    private List<Hotel> getHotelFromJSONString(String jsonString) {
        Gson gson = new Gson();
        List<Hotel> hotels = gson.fromJson(jsonString, new TypeToken<List<Hotel>>() {}.getType());

        for (Hotel hotel : hotels) {
            Log.d("hotel", hotel.getName());
        }
        return hotels;
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    private void saveItinerary(ItineraryClientApi itineraryClientApi, Itinerary itinerary) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.108:8080/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        Call<Itinerary> call = itineraryClientApi.itineraryAdd(itinerary);

        call.enqueue(new Callback<Itinerary>() {
            @Override
            public void onResponse(Call<Itinerary> call, Response<Itinerary> response) {

                Toast.makeText(SimulationActivity.this, "行程已添加", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Itinerary> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void getSpotData() {
        CustomizationClientApi customizationClientApi = retrofit.create(CustomizationClientApi.class);

        Call<List<Spot>> call = customizationClientApi.spotLoad(city_id);

        call.enqueue(new Callback<List<Spot>>() {
            @Override
            public void onResponse(Call<List<Spot>> call, Response<List<Spot>> response) {
                spotList = response.body();

                navigateTo(spotList.get(0).getLatLng(), 16, "");

                for (Spot spot : spotList) {
                    addMarker(spot);
                }
            }

            @Override
            public void onFailure(Call<List<Spot>> call, Throwable t) {
                Toast.makeText(SimulationActivity.this, "failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void setSpan(TextView textView, String pattern) {
        float relativeSize = 1.5f;
        Pattern pat = Pattern.compile(pattern);
        Matcher m = pat.matcher(textView.getText());
        if (m.find()) {
            SpannableString span = new SpannableString(textView.getText());
            span.setSpan(new RelativeSizeSpan(relativeSize), 0, m.group(0).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(span);
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        //创建DrivingRouteOverlay实例
        DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
        if (drivingRouteResult == null) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }

        if (drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, drivingRouteResult.error.toString(), Toast.LENGTH_SHORT).show();
        }

        if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            if (drivingRouteResult.getRouteLines().size() > 0) {
//                Toast.makeText(this, "有结果", Toast.LENGTH_SHORT).show();
                //获取路径规划数据,(以返回的第一条路线为例）
                //为DrivingRouteOverlay实例设置数据
                overlay.setData(drivingRouteResult.getRouteLines().get(0));


//                List<OverlayOptions> allOverlay = overlay.getOverlayOptions();
//                for (OverlayOptions option : allOverlay) {
////                    baiduMap.addOverlay(option);
//
//                }


//                在地图上绘制DrivingRouteOverlay
                overlay.addToMap();
            }
            else {
                Toast.makeText(this, "Shit", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

}
