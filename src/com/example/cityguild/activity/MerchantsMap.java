package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;

import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import com.examlpe.ices.util.StringUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.R;
import com.example.cityguild.adapter.RouteAdapter;
import com.example.cityguild.entity.RouteEntity;

public class MerchantsMap extends BaseActivity implements
		OnGetRoutePlanResultListener,OnGetGeoCoderResultListener{
	private String city;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	boolean isFirstLoc = true;// 是否首次定位
	RoutePlanSearch mSearch = null;
	RouteLine route = null;
	OverlayManager routeOverlay = null;
	private LatLng ll;
	private LinearLayout chakan;
	private LinearLayout routeplan;
	private LinearLayout ll_bus;
	private LinearLayout ll_tax;
	private LinearLayout ll_buxing;
	private String flag = "1";
	private ImageView im_bus;
	private ImageView im_tax;
	private ImageView im_buxing;
	private ListView list;
	private int Routeid = 0;
	List<RouteEntity> myList = new ArrayList<RouteEntity>();
	private RouteLine mRoute;
	TransitRouteResult mresult1;
	DrivingRouteResult mresult2;
	WalkingRouteResult mresult3;
	private RouteAdapter ra1;
	private RouteAdapter ra2;
	private RouteAdapter ra3;
	private Boolean isFrist = true;
	private ImageView im_jiaohuan;
	private Boolean isRight = true;
	private TextView myplace;
	private TextView mubiao;
	private String adress="";
	GeoCoder geoSearch = null;
	private String name="";
	private LatLng namell;
	private TextView tv_name;
	private TextView tv_adress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SDKInitializer.initialize(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapdetail);
		if(getIntent().getStringExtra("adress")!=null)
		adress = getIntent().getStringExtra("adress");
		if(getIntent().getStringExtra("name")!=null)
		name = getIntent().getStringExtra("name");
		if(adress!=null){
			Log.i("adress",adress);
		}
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setAddrType("all"); 
		mLocClient.setLocOption(option);
		
		mLocClient.start();

		// 初始化搜索模块，注册事件监听
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this); 
 
		geoSearch = GeoCoder.newInstance();
		geoSearch.setOnGetGeoCodeResultListener(this);
		initView();

	}

	private void initView() {
		tv_name = (TextView)findViewById(R.id.tv_name);
		tv_adress = (TextView)findViewById(R.id.tv_adress);
		tv_name.setText(name);
		tv_adress.setText(adress);
		myplace = (TextView) findViewById(R.id.myplace);
		mubiao = (TextView) findViewById(R.id.mubiao);
		chakan = (LinearLayout) findViewById(R.id.chakan);
		chakan.setOnClickListener(this);

		routeplan = (LinearLayout) findViewById(R.id.routeplan);
		ll_bus = (LinearLayout) findViewById(R.id.ll_bus);
		ll_tax = (LinearLayout) findViewById(R.id.ll_tax);
		ll_buxing = (LinearLayout) findViewById(R.id.ll_buxing);
		ll_bus.setOnClickListener(this);
		ll_tax.setOnClickListener(this);
		ll_buxing.setOnClickListener(this);

		im_bus = (ImageView) findViewById(R.id.im_bus);
		im_tax = (ImageView) findViewById(R.id.im_tax);
		im_buxing = (ImageView) findViewById(R.id.im_buxing);
		im_jiaohuan = (ImageView) findViewById(R.id.jiaohuan);
		im_jiaohuan.setOnClickListener(this);
		im_bus.setImageResource(R.drawable.bus1);
		list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (flag.equals("1")) {
					mRoute = mresult1.getRouteLines().get(position);
					TransitRouteOverlay overlay = new MyTransitRouteOverlay(
							mBaiduMap);
					mBaiduMap.clear();
					routeOverlay = overlay;
					overlay.setData((TransitRouteLine) mRoute);
					overlay.addToMap();
					overlay.zoomToSpan();
				}

				if (flag.equals("2")) {
					mRoute = mresult2.getRouteLines().get(position);
					DrivingRouteOverlay overlay = new DrivingRouteOverlay(
							mBaiduMap);
					mBaiduMap.clear();
					routeOverlay = overlay;

					overlay.setData((DrivingRouteLine) mRoute);
					overlay.addToMap();
					overlay.zoomToSpan();
				}
				if (flag.equals("3")) {
					mRoute = mresult3.getRouteLines().get(position);
					WalkingRouteOverlay overlay = new WalkingRouteOverlay(
							mBaiduMap);
					mBaiduMap.clear();
					routeOverlay = overlay;

					overlay.setData((WalkingRouteLine) mRoute);
					overlay.addToMap();
					overlay.zoomToSpan();
				}

				routeplan.setVisibility(View.GONE);

			}
		});

	}

	public class MyLocationListenner implements BDLocationListener {

	

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			
			if (isFirstLoc) {
				isFirstLoc = false;
				ll = new LatLng(location.getLatitude(), location.getLongitude());
				Log.i("ll", ll.toString());
				city = location.getCity();
				Log.i("CITY",city);
				if(!adress.equals("")&&!city.equals(""))
				geoSearch.geocode(new GeoCodeOption().city(city).address(
						adress));
			//	MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				//mBaiduMap.animateMapStatus(u);

			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		myList.clear();
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Log.i("ERROR", "ERROR");
			Toast.makeText(this, "暂无驾车路线!", 1000).show();
			ra2 = new RouteAdapter(this, myList);
			list.setAdapter(ra2);
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			ra2 = new RouteAdapter(this, myList);
			list.setAdapter(ra2);
			Log.i("ERROR", "ERROR");
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mresult2 = result;
			route = result.getRouteLines().get(0);
			DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			int size = result.getRouteLines().size();
			mRoute = result.getRouteLines().get(Routeid);
			route = result.getRouteLines().get(0);
			RouteLine route1 = result.getRouteLines().get(size - 1);
		
			Log.i("dsize", size + "");
			mBaiduMap.setOnMarkerClickListener(overlay);
			
			for (int i = 0; i < result.getRouteLines().size(); i++) {
				RouteLine r = result.getRouteLines().get(i);
				RouteEntity re = new RouteEntity();
				String start=result.getRouteLines().get(i).getAllStep().get(0).getInstructions().toString();
				
				String end = result
						.getRouteLines()
						.get(i)
						.getAllStep()
						.get(result.getRouteLines().get(i).getAllStep()
								.size() - 1).getInstructions().toString();
				re.setId(String.valueOf(i + 1));
				re.setTitle(start + "---" + end);
				re.setTime(StringUtil.change(r.getDuration()) + " | "
						+ r.getDistance() / 1000 + "公里");
				myList.add(re);

			}
			ra2 = new RouteAdapter(this, myList);
			list.setAdapter(ra2);

		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		myList.clear();
		
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Log.i("ERROR", "ERROR");
			Toast.makeText(this, "暂无公交路线!", 1000).show();
			ra1 = new RouteAdapter(this, myList);
			list.setAdapter(ra1);
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			Log.i("ERROR", "ERROR");
			// return;
			ra1 = new RouteAdapter(this, myList);
			list.setAdapter(ra1);
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mresult1 = result;
			int size = result.getRouteLines().size();
			Log.i("tsize", size + "");
			 //Toast.makeText(MerchantsMap.this,result.getRouteLines().get(0).getAllStep().get(0).getVehicleInfo().getTitle().toString(), 1000).show();
			route = result.getRouteLines().get(0);
			RouteLine route1 = result.getRouteLines().get(size - 1);	
			for (int i = 0; i < result.getRouteLines().size(); i++) {
				
				TransitRouteLine r = result.getRouteLines().get(i);
				String start=result.getRouteLines().get(i).getAllStep().get(0).getInstructions().toString();
				String str="";
				for(int j=0;j<r.getAllStep().size();j++){
					if(r.getAllStep().get(j).getVehicleInfo()!=null){
						if(str.equals("")){
							str=r.getAllStep().get(j).getVehicleInfo().getTitle();
						}else{
							str=str+"-->"+r.getAllStep().get(j).getVehicleInfo().getTitle();	
						}
						
					}
					
				}
				//Log.i("tttttttt",result.getRouteLines().get(0).getAllStep().get(0).getVehicleInfo().getTitle().toString());
				String end = result
						.getRouteLines()
						.get(i)
						.getAllStep()
						.get(result.getRouteLines().get(i).getAllStep()
								.size() - 1).getInstructions().toString();
				RouteEntity re = new RouteEntity();
				re.setId(String.valueOf(i + 1));
				//re.setTitle(start + "---" + end);
				re.setTitle(str);
				re.setTime(StringUtil.change(r.getDuration()) + " | "
						+ r.getDistance() / 1000 + "公里");
				myList.add(re);
			}
			ra1 = new RouteAdapter(this, myList);
			list.setAdapter(ra1);
			mRoute = result.getRouteLines().get(Routeid);

		}

	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		myList.clear();
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Log.i("ERROR", "ERROR");
			Toast.makeText(this, "暂无步行路线!", 1000).show();
			ra3 = new RouteAdapter(this, myList);
			list.setAdapter(ra3);
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			ra3 = new RouteAdapter(this, myList);
			list.setAdapter(ra3);
			Log.i("ERROR", "ERROR");
			// return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mresult3 = result;
			route = result.getRouteLines().get(0);
			WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			int size = result.getRouteLines().size();
			mRoute = result.getRouteLines().get(Routeid);
			route = result.getRouteLines().get(0);
			RouteLine route1 = result.getRouteLines().get(size - 1);
			mBaiduMap.setOnMarkerClickListener(overlay);
		
			for (int i = 0; i < result.getRouteLines().size(); i++) {
				RouteLine r = result.getRouteLines().get(i);
				RouteEntity re = new RouteEntity();
				String start=result.getRouteLines().get(i).getAllStep().get(0).getInstructions().toString();
				String end = result
						.getRouteLines()
						.get(i)
						.getAllStep()
						.get(result.getRouteLines().get(i).getAllStep()
								.size() - 1).getInstructions().toString();
				re.setId(String.valueOf(i + 1));
				re.setTitle(start + "---" + end);
				re.setTime(StringUtil.change(r.getDuration()) + " | "
						+ r.getDistance() / 1000 + "公里");
				myList.add(re);

			}
			ra3 = new RouteAdapter(this, myList);
			list.setAdapter(ra3);

		}

	}

	// 定制RouteOverly
	private class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {

			return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);

		}

		@Override
		public BitmapDescriptor getTerminalMarker() {

			return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v.getId() == R.id.chakan) {
			routeplan.setVisibility(View.VISIBLE);
			if(name!=null)
			mubiao.setText(name);
			// startActivity(new Intent(MerchantsMap.this,RouteActivity.class));
			if(city==null){
				return;
			}
			if (isFrist) {

				PlanNode stNode = PlanNode.withLocation(ll);
				PlanNode enNode = PlanNode.withCityNameAndPlaceName(city,
						name);
				if (isRight) {
					mSearch.transitSearch((new TransitRoutePlanOption())
							.from(stNode).city(city).to(enNode));
				} else {
					mSearch.transitSearch((new TransitRoutePlanOption())
							.from(enNode).city("city").to(stNode));
				 
				}
				isFrist = false;
			}
			// mMapView.invalidate();

		}

		if (v.getId() == R.id.ll_bus) {
			flag = "1";
			im_bus.setImageResource(R.drawable.bus1);
			im_tax.setImageResource(R.drawable.tax);
			im_buxing.setImageResource(R.drawable.bx);
			PlanNode stNode = PlanNode.withLocation(ll);
			PlanNode enNode = PlanNode.withCityNameAndPlaceName(city, name);
			if (isRight) {
				mSearch.transitSearch((new TransitRoutePlanOption())
						.from(stNode).city(city).to(enNode));
			} else {
				mSearch.transitSearch((new TransitRoutePlanOption())
						.from(enNode).city(city).to(stNode));
			}
		}
		if (v.getId() == R.id.ll_tax) {

			flag = "2";
			im_tax.setImageResource(R.drawable.tax1);
			im_bus.setImageResource(R.drawable.bus);
			im_buxing.setImageResource(R.drawable.bx);

			PlanNode stNode = PlanNode.withLocation(ll);
			PlanNode enNode = PlanNode.withCityNameAndPlaceName(city, name);
			if (isRight) {
				mSearch.drivingSearch((new DrivingRoutePlanOption()).from(
						stNode).to(enNode));
			} else {
				mSearch.drivingSearch((new DrivingRoutePlanOption()).from(
						enNode).to(stNode));
			}

		}
		if (v.getId() == R.id.ll_buxing) {
			PlanNode stNode = PlanNode.withLocation(ll);
			PlanNode enNode = PlanNode.withCityNameAndPlaceName(city, name);
			if (isRight) {

				mSearch.walkingSearch((new WalkingRoutePlanOption()).from(
						stNode).to(enNode));
			} else {
				mSearch.walkingSearch((new WalkingRoutePlanOption()).from(
						enNode).to(stNode));
			}
			flag = "3";
			im_buxing.setImageResource(R.drawable.bx1);
			im_tax.setImageResource(R.drawable.tax);
			im_bus.setImageResource(R.drawable.bus);
		}
		if (v.getId() == R.id.jiaohuan) {
			String mu = mubiao.getText().toString();
			String my = myplace.getText().toString();
			myplace.setText(mu);
			mubiao.setText(my);
			PlanNode stNode = PlanNode.withLocation(ll);
			PlanNode enNode = PlanNode.withCityNameAndPlaceName("city", name);
			isRight = !isRight;
			if (flag.equals("1")) {

				if (isRight) {
					mSearch.transitSearch((new TransitRoutePlanOption())
							.from(stNode).city("city").to(enNode));
					Log.i("zz", "zz");
				} else {
					mSearch.transitSearch((new TransitRoutePlanOption())
							.from(enNode).city("city").to(stNode));
					Log.i("ff", "ff");
				}
			} else if (flag.equals("2")) {

				if (isRight) {
					mSearch.drivingSearch((new DrivingRoutePlanOption()).from(
							stNode).to(enNode));
				} else {
					mSearch.drivingSearch((new DrivingRoutePlanOption()).from(
							enNode).to(stNode));
				}
			} else if (flag.equals("3")) {

				if (isRight) {

					mSearch.walkingSearch((new WalkingRoutePlanOption()).from(
							stNode).to(enNode));
				} else {
					mSearch.walkingSearch((new WalkingRoutePlanOption()).from(
							enNode).to(stNode));
				}
				flag = "3";
				im_buxing.setImageResource(R.drawable.bx1);
				im_tax.setImageResource(R.drawable.tax);
				im_bus.setImageResource(R.drawable.bus);
			}
		}
		if(v.getId()==R.id.ll_left){
			routeplan.setVisibility(View.GONE);
		}
	}
	 @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == event.KEYCODE_BACK) {  
	        	if(routeplan.getVisibility()==View.VISIBLE){
	        	routeplan.setVisibility(View.GONE);
	        	}else{
	        		this.finish();
	        	}
	        	
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	     
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MerchantsMap.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		namell = new LatLng(result.getLocation().latitude, result.getLocation().longitude);
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		//Toast.makeText(MerchantsMap.this, strInfo, Toast.LENGTH_LONG).show();

		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}  
}
