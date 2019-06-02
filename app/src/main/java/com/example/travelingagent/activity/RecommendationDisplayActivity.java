package com.example.travelingagent.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.travelingagent.myclass.Recommend;
import com.example.travelingagent.myclass.Sight;
import com.example.travelingagent.myclass.Spot;
import com.example.travelingagent.overlayutil.DrivingRouteOverlay;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RecommendationDisplayActivity extends AppCompatActivity implements OnGetRoutePlanResultListener {
    public LocationClient mLocationClient;
//    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;
//    private boolean isFirstLocate = true;
    RoutePlanSearch mSearch = null;
    LatLng currentLocation = null;
    List<Spot> itinerary = null;
    List<Hotel> hotelVec = new Vector<>();
    Vector<Sight> sightVec = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_simulation);
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        currentLocation = new LatLng(31.23, 121.47 );   // 上海的中心经纬

        Intent intent = getIntent();
        int[] choice_data = intent.getIntArrayExtra("choiceData");

        Toast.makeText(RecommendationDisplayActivity.this, String.valueOf(choice_data[1]), Toast.LENGTH_SHORT).show();

        int choice1 = choice_data[0];
        int choice2 = choice_data[1];
        int choice3 = choice_data[2];
        int choice4 = choice_data[3];
        int choice5 = choice_data[4];
        int choice6 = choice_data[5];
//        Hotel hotel = new Hotel("FakeHotel", 1, 0, "Fake", 121.72, 31.55);

        List<Spot> recommend = null;

        try {
            InputStream in = getAssets().open("hotel_information.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "GBK");
            JSONArray jsonArray = new JSONArray(jsonStr);
            // {"id": "1", "name": "上海也山花园酒店(崇明森林公园店)", "popularity": 190.0, "money": 559.0, "total": 49.0, "latitude": 31.666015, "longitude": 121.471442},
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                double popularity = jsonObject.getDouble("popularity");
                double money = jsonObject.getDouble("money");
                double total = jsonObject.getDouble("total");
                double latitude = jsonObject.getDouble("latitude");
                double longitude = jsonObject.getDouble("longitude");
                hotelVec.add(new Hotel(name, id, 1, popularity, money, total, longitude, latitude));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            InputStream in = getAssets().open("sight_information.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "GBK");
            JSONArray jsonArray = new JSONArray(jsonStr);
            // {"id": "1", "name": "上海迪士尼度假区", "popularity": 32903.0, "money": 587.0, "total": 92.0, "environment": 84.0, "service": 85.0, "latitude": 31.141201, "longitude": 121.666345}
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                double popularity = jsonObject.getDouble("popularity");
                double money = jsonObject.getDouble("money");
                double total = jsonObject.getDouble("money");
                double environment = jsonObject.getDouble("environment");
                double service = jsonObject.getDouble("service");
                double latitude = jsonObject.getDouble("latitude");
                double longitude = jsonObject.getDouble("longitude");
                sightVec.add(new Sight(name, id, 0, "blabla", longitude, latitude, popularity, total, environment, service, money));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            navigateTo(currentLocation, 10,"上海");
        }

        BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                String content = marker.getTitle(); // 假装利用title携带信息
                String spotName = content.split("_")[0];
                String spotID = content.split("_")[1];
                String spotType = content.split("_")[2];

//                setContentView(R.layout.content_recommendation_1);
                Toast.makeText(RecommendationDisplayActivity.this, spotName + spotID + "欢迎您~", Toast.LENGTH_SHORT).show();

                View view = View.inflate(RecommendationDisplayActivity.this, R.layout.content_recommendation, null);
                TextView textView = (TextView) view.findViewById(R.id.place_name);
                textView.setText(spotName);


                ImageView imageView = (ImageView) view.findViewById(R.id.word_cloud_image);
                if (spotType.equals("0")) {
                    imageView.setImageResource(getDrawResourceID("sight_" + spotID));
                }
                else {
                    if (spotType.equals("1")) {
                        imageView.setImageResource(getDrawResourceID("hotel_" + spotID));
                    }
                }
                DialogPlus dialog = DialogPlus.newDialog(RecommendationDisplayActivity.this)
                        .setContentHolder(new ViewHolder(view))
                        .setCancelable(true)
                        .setHeader(R.layout.header_recommendation)
                        .setExpanded(true, 1500)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();
                return true;
            }
        };

        baiduMap.setOnMarkerClickListener(markerClickListener);

        Recommend r = new Recommend(3, choice1, choice2, choice3, choice4, choice5, choice6);
        try {
            recommend = r.recommend(sightVec, hotelVec.get(1));
        } catch (FileNotFoundException e) {

        }

        drawItinerary(recommend);

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

        for( int i = 1 ; i < spotList.size() ; i++) {
            Spot spot = spotList.get(i);
            mSearch = RoutePlanSearch.newInstance();
            mSearch.setOnGetRoutePlanResultListener(RecommendationDisplayActivity.this);

            if (spot.getType() == 0) {
                addMarker(spot.getLatLng(), 0, spot.getID());
            }
            else {
                if (spot.getType() == 1) {
                    addMarker(spot.getLatLng(), 1, spot.getID());
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

    public int getDrawResourceID(String resourceName) {
        Resources res=getResources();
        int picid = res.getIdentifier(resourceName,"drawable",getPackageName());
        return picid;
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
