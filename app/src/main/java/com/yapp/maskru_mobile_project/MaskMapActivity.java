package com.yapp.maskru_mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.yapp.maskru_mobile_project.java.NMapViewerResourceProvider;

public class MaskMapActivity  extends NMapActivity  {


    //지도 위 오버레이 객체 드로잉에 필요한 리소스 데이터 제공 클래스
    NMapViewerResourceProvider mMapViewerResourceProvider;
    NMapMyLocationOverlay mMyLocationOverlay; //지도 위에 현재 위치를 표시하는 오버레이 클래스
    NMapLocationManager mMapLocationManager; //단말기의 현재 위치 탐색 기능 사용 클래스
    NMapCompassManager mMapCompassManager;
    NMapOverlayManager mOverlayManager;
    NMapController mMapController;

    private NMapView mapView;
    private final String CLIENT_ID = "cMyLnJ1Z1LyXaPq1Rv62";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //맵 뷰 생성.
        mapView = new NMapView(this);
        mapView.setApiKey(CLIENT_ID);

        setContentView(mapView);

        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapView.setFocusable(true);
        mapView.setFocusableInTouchMode(true);
        mapView.requestFocus();

        mMapController = mapView.getMapController(); //지도 컨트롤러(줌 인/아웃 등) 사용

        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mapView, mMapViewerResourceProvider);


        //mapView.setBuiltInZoomControls(true,null);
        //동현
        //위치 관리 메니저 객체 생성
        mMapLocationManager = new NMapLocationManager(this);
        //현재 위치 변경 시 호출되는 콜백 인터페이스를 설정한다.
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        //NMapMyLocationOverlay 객체 생성
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager,
                mMapCompassManager);
        startMyLocation();

    }



    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener =
            new NMapLocationManager.OnLocationChangeListener() { //위치 변경 콜백 인터페이스 정의
                //위치가 변경되면 호출
                @Override
                public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {
                    if (mMapController != null) {
                        mMapController.animateTo(myLocation); //지도 중심을 현재 위치로 이동
                    }
                    return true;
                }
                //정해진 시간 내에 위치 탐색 실패 시 호출
                @Override
                public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
                }
                //현재 위치가 지도 상에 표시할 수 있는 범위를 벗어나는 경우 호출
                @Override
                public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {
                    stopMyLocation(); //내 위치 찾기 중지 함수 호출
                }
            };



    private void startMyLocation() {
        if (mMapLocationManager.isMyLocationEnabled()) { //현재 위치를 탐색 중인지 확인
            if (!mapView.isAutoRotateEnabled()) { //지도 회전기능 활성화 상태 여부 확인
                mMyLocationOverlay.setCompassHeadingVisible(true); //나침반 각도 표시
                mMapCompassManager.enableCompass(); //나침반 모니터링 시작
                mapView.setAutoRotateEnabled(true, false); //지도 회전기능 활성화
            }
            mapView.invalidate();
        } else { //현재 위치를 탐색 중이 아니면
            Boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(false); //현재 위치 탐색 시작
            if (!isMyLocationEnabled) { //위치 탐색이 불가능하면
                Toast.makeText(this, "GPS를 켜주세요!!",
                        Toast.LENGTH_LONG).show();
                Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(goToSettings);
                return;
            }
        }
    }


    private void stopMyLocation() {
        mMapLocationManager.disableMyLocation(); //현재 위치 탐색 종료
        if (mapView.isAutoRotateEnabled()) { //지도 회전기능이 활성화 상태라면
            mMyLocationOverlay.setCompassHeadingVisible(false); //나침반 각도표시 제거
            mMapCompassManager.disableCompass(); //나침반 모니터링 종료
            mapView.setAutoRotateEnabled(false, false); //지도 회전기능 중지
        }
    }

}
