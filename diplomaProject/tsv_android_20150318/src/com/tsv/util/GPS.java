package com.tsv.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class GPS {

	private Context context;
	public GPS(Context context){
		this.context = context;
	}
	
	public String getGPS(){
		try {
			String contextService = Context.LOCATION_SERVICE;
			//通过系统服务，取得LocationManager对象
			LocationManager loctionManager = (LocationManager) this.context
					.getSystemService(contextService);
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
			criteria.setAltitudeRequired(false);//不要求海拔
			criteria.setBearingRequired(false);//不要求方位
			criteria.setCostAllowed(true);//允许有花费
			criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗
			//从可用的位置提供器中，匹配以上标准的最佳提供器
			String provider = loctionManager.getBestProvider(criteria, true);
			//获得最后一次变化的位置
			Location location = loctionManager.getLastKnownLocation(provider);
			return location.getLongitude() + "," + location.getLatitude();
		} catch (Exception e) {
			System.out.println("Unknow Exception."+e.getMessage());
			return "0,0";
		}
	}
}
