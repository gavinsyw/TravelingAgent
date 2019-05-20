package com.example.travelingagent.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.travelingagent.myclass.Hotel;
import com.example.travelingagent.myclass.Spot;
import com.example.travelingagent.overlayutil.DrivingRouteOverlay;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecommendationDisplayActivity extends AppCompatActivity implements OnGetRoutePlanResultListener {
    public LocationClient mLocationClient;
//    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;
//    private boolean isFirstLocate = true;
    RoutePlanSearch mSearch = null;
    LatLng currentLocation = null;
    List<Spot> itinerary = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_simulation);
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        currentLocation = new LatLng(31.23, 121.47 );   // 上海的中心经纬

        final BitmapDescriptor defaultBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        final BitmapDescriptor selectedBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_selected);

//        mLocationClient = new LocationClient(getApplicationContext());
//        mLocationClient.registerLocationListener(new BDLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation location) {
//                if (location.getLocType() == BDLocation.TypeGpsLocation
//                        || location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                    navigateTo(location.getLatitude(), location.getLongitude(), 18, location.getAddrStr());
//                }
//            }
//        });

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(RecommendationDisplayActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(RecommendationDisplayActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(RecommendationDisplayActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(RecommendationDisplayActivity.this, permissions, 1);
        } else {
//            requestLocation();
            navigateTo(currentLocation, 8,"城市名");
        }

        BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                final DialogPlus dialog = DialogPlus.newDialog(RecommendationDisplayActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.content_simulation))
//                        .setAdapter(adapter)
                        .setCancelable(true)
                        .setHeader(R.layout.header_simulation)
                        .setExpanded(true, 2000)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();

                Button button_yes = (Button) findViewById(R.id.like_it_button);
                Button button_no = (Button) findViewById(R.id.maybe_not_button);

                button_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RecommendationDisplayActivity.this, "The spot is saved.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        mSearch = RoutePlanSearch.newInstance();
                        mSearch.setOnGetRoutePlanResultListener(RecommendationDisplayActivity.this);

                        PlanNode stNode = PlanNode.withLocation(currentLocation);
                        PlanNode enNode = PlanNode.withLocation(marker.getPosition());

                        mSearch.drivingSearch((new DrivingRoutePlanOption())
                                .from(stNode)
                                .to(enNode));

                        currentLocation = marker.getPosition();
                        navigateTo(currentLocation, 8, null);

                        marker.remove();
                        addMarker(currentLocation, selectedBitmap);
                    }
                });

                button_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RecommendationDisplayActivity.this, "The spot is deleted.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        marker.remove();
                    }
                });
//                Intent intent = new Intent(RecommendationDisplayActivity.this, PoiSearchActivity.class);
//                startActivity(intent);
                return true;
            }
        };

        baiduMap.setOnMarkerClickListener(markerClickListener);

//        baiduMap


//        PlanNode stNode = PlanNode.withLocation(new LatLng(37.963175, 116.400244));
//        PlanNode enNode = PlanNode.withLocation(new LatLng(38.963175, 116.400244));

//        mSearch.drivingSearch((new DrivingRoutePlanOption())
//                .from(stNode).to(enNode).policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST)
//                .trafficPolicy(DrivingRoutePlanOption.DrivingTrafficPolicy.ROUTE_PATH_AND_TRAFFIC));

//        addMarker(37.963175, 116.400244);
        addMarker(new LatLng(39.963175, 116.400244), defaultBitmap);
        addMarker(new LatLng(39.963175, 118.400244), defaultBitmap);

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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mLocationClient.stop();
//        mapView.onDestroy();
//        baiduMap.setMyLocationEnabled(false);
//    }

    private void navigateTo(LatLng ll, float zoom, String description) {
//        if (isFirstLocate) {
//            Toast.makeText(this, "nav to " + description, Toast.LENGTH_SHORT).show();
//            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
//            baiduMap.animateMapStatus(update);
//            update = MapStatusUpdateFactory.zoomTo(zoom);
//            baiduMap.animateMapStatus(update);
//            isFirstLocate = false;
//        }
//        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
//        locationBuilder.latitude(lat);
//        locationBuilder.longitude(lng);
//        MyLocationData locationData = locationBuilder.build();
//        baiduMap.setMyLocationData(locationData);

        if (description != null) {
            Toast.makeText(this, "nav to " + description, Toast.LENGTH_SHORT).show();
        }
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(update);
        update = MapStatusUpdateFactory.zoomTo(zoom);
        baiduMap.animateMapStatus(update);
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
//                Toast.makeText(RecommendationDisplayActivity.this, "blabla", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        };
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

//    public class MyLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (location.getLocType() == BDLocation.TypeGpsLocation
//                    || location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                navigateTo(location);
//            }
//
//        }
//    }

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
                Toast.makeText(this, "有结果", Toast.LENGTH_SHORT).show();
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
