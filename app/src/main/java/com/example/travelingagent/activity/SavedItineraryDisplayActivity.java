package com.example.travelingagent.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.example.travelingagent.entity.Spot;
import com.example.travelingagent.protocol.api.ItineraryClientApi;
import com.example.travelingagent.util.adapter.SpotAdapter;
import com.example.travelingagent.util.baiduMap.DrivingRouteOverlay;
import com.telenav.expandablepager.ExpandablePager;
import com.telenav.expandablepager.listener.OnSliderStateChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SavedItineraryDisplayActivity extends AppCompatActivity implements OnGetRoutePlanResultListener {
    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private String user_id;
    private String itinerary_index;
    private Itinerary itinerary;
    private Retrofit retrofit;
    private List<Spot> spotList;
    RoutePlanSearch mSearch = null;
    LatLng currentLocation = null;
    private String BASE_URL = "http://192.168.43.126:8080/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_save_itinerary_display);
        mapView = findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        currentLocation = new LatLng(31.23, 121.47 );   // 上海的中心经纬
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        itinerary_index = intent.getStringExtra("itinerary_index");

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(SavedItineraryDisplayActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(SavedItineraryDisplayActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(SavedItineraryDisplayActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(SavedItineraryDisplayActivity.this, permissions, 1);
        }

        getItinerary();

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

    private void navigateTo(LatLng ll, float zoom, String description) {
        if (description != null) {
            Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
        }
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(update);
        update = MapStatusUpdateFactory.zoomTo(zoom);
        baiduMap.animateMapStatus(update);
    }

    private void drawItinerary(List<Spot> spotList) {
        Spot currentSpot = spotList.get(0);
        currentLocation = currentSpot.getLatLng();

        for( int i = 0 ; i < spotList.size() ; i++) {
            Spot spot = spotList.get(i);
            mSearch = RoutePlanSearch.newInstance();
            mSearch.setOnGetRoutePlanResultListener(SavedItineraryDisplayActivity.this);

            if (spot.getType() == 0) {
                addMarker(spot, i);
            }
            else {
                if (spot.getType() == 1) {
                    addMarker(spot, i);
                }
            }

            PlanNode stNode = PlanNode.withLocation(currentLocation);
            PlanNode enNode = PlanNode.withLocation(spot.getLatLng());

            mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));

            currentLocation = spot.getLatLng();

        }
    }

    private void addMarker(Spot spot, int id) {
        BitmapDescriptor hotelBitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker_pink);
        BitmapDescriptor sightBitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker_green);

        //构建Marker图标
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = null;
        if (spot.getType() == 0) {
            option = new MarkerOptions()
                    .position(spot.getLatLng())
                    .icon(sightBitmap)
                    .title(spot.getName() + '_' + String.valueOf(spot.getID()) + "_0_" + String.valueOf(id));
        }
        else {
            option = new MarkerOptions()
                    .position(spot.getLatLng())
                    .icon(hotelBitmap)
                    .title(spot.getName() + '_' + String.valueOf(spot.getID()) + "_1_" + String.valueOf(id));
        }


        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
    }

    private int getDrawResourceID(String resourceName) {
        Resources res = getResources();
        int picid = res.getIdentifier(resourceName,"drawable",getPackageName());
        return picid;
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

    private void getItinerary() {
        ItineraryClientApi itineraryClientApi = retrofit.create(ItineraryClientApi.class);

        Map<String, String> options = new HashMap<String, String>();
        options.put("user_id", user_id);
        options.put("index", itinerary_index);

        Call<Itinerary> call = itineraryClientApi.itineraryGet(options);

        call.enqueue(new Callback<Itinerary>() {
            @Override
            public void onResponse(Call<Itinerary> call, Response<Itinerary> response) {
                itinerary = response.body();
                spotList = itinerary.getSpotList();
                String city_id = String.valueOf(itinerary.getCity_id());

                for (Spot spot : spotList) {
                    if (spot.getType() == 0) {
                        spot.setIconResourceID(getDrawResourceID("sight"));
                        spot.setWordCloudResourceID(getDrawResourceID("sight_" + String.valueOf(city_id) + "_" + String.valueOf(spot.getID())));
                    }

                    else {
                        spot.setIconResourceID(getDrawResourceID("hotel"));
                        spot.setWordCloudResourceID(getDrawResourceID("hotel_" + String.valueOf(city_id) + "_" + String.valueOf(spot.getID())));
                    }
                }

                itinerary = new Itinerary(0, spotList, Integer.parseInt(city_id), Integer.parseInt(user_id));

                // TODO: 底部切换条

                SpotAdapter adapter = new SpotAdapter(spotList);
                final ExpandablePager pager = findViewById(R.id.container);
                pager.setAdapter(adapter);
                pager.setOnSliderStateChangeListener(new OnSliderStateChangeListener() {
                    @Override
                    public void onStateChanged(View page, int index, int state) {

                    }

                    @Override
                    public void onPageChanged(View page, int index, int state) {
                        currentLocation = spotList.get(pager.getCurrentItem()).getLatLng();
                        navigateTo(currentLocation, 14, null);
                    }
                });
                drawItinerary(spotList);

                BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        String [] contents = marker.getTitle().split("_"); // 假装利用title携带信息

                        String spotName = contents[0];
                        String spotID = contents[1];
                        String spotType = contents[2];
                        String index = contents[3];

                        try {
                            pager.setCurrentItem(Integer.parseInt(index), true);
                            currentLocation = spotList.get(Integer.parseInt(index)).getLatLng();
                            navigateTo(currentLocation, 14, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        View view = View.inflate(SavedItineraryDisplayActivity.this, R.layout.content_recommendation, null);
                        TextView textView = view.findViewById(R.id.place_name);
                        textView.setText(spotName);

                        ImageView imageView = view.findViewById(R.id.word_cloud_image);
                        if (spotType.equals("0")) {
                            imageView.setImageResource(getDrawResourceID("sight_" + spotID));
                        }
                        else {
                            if (spotType.equals("1")) {
                                imageView.setImageResource(getDrawResourceID("hotel_" + spotID));
                            }
                        }
                        return true;
                    }
                };

                baiduMap.setOnMarkerClickListener(markerClickListener);
            }

            @Override
            public void onFailure(Call<Itinerary> call, Throwable t) {

            }
        });

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
                //获取路径规划数据,(以返回的第一条路线为例）
                //为DrivingRouteOverlay实例设置数据
                overlay.setData(drivingRouteResult.getRouteLines().get(0));

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
