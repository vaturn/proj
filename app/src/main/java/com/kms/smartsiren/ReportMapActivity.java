//B
package com.kms.smartsiren;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.camera.CameraUpdate;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;
import com.kakao.vectormap.label.LabelTransition;
import com.kakao.vectormap.label.Transition;

import java.text.MessageFormat;

public class ReportMapActivity extends AppCompatActivity {
    private KakaoMap kakaoMap;
    MapView mapView;
    private FusedLocationProviderClient fusedLocationClient; //위치 서비스 클라이언트 객체
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    LatLng reportLocation;
    Button btn_report_location_R;
    Label centerLabel;
    LabelLayer labelLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportmap);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapView = findViewById(R.id.map_view_R);

        btn_report_location_R = findViewById(R.id.btn_report_location_R);

        mapView.start(new MapLifeCycleCallback() {
            @Override
            public void onMapResumed() {
                super.onMapResumed();
            }

            @Override
            public void onMapPaused() {
                super.onMapPaused();
            }
            @Override
            public void onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
            }

            @Override
            public void onMapError(Exception error) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
            }
        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(KakaoMap Map) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
                kakaoMap = Map;
                labelLayer = kakaoMap.getLabelManager().getLayer();
                if (ContextCompat.checkSelfPermission(ReportMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReportMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                } else {
                    getLastLocation();
                    ReportLocation();
                }
            }
        });

        btn_report_location_R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportLocation!=null) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("latitude", reportLocation.latitude);
                    returnIntent.putExtra("longitude", reportLocation.longitude);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish(); // 액티비티 종료
                }
                else {
                    // 위치가 설정되지 않았을 경우
                    Toast.makeText(ReportMapActivity.this, "위치가 설정되지 않았습니다. 지도에서 위치를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    reportLocation = LatLng.from(location.getLatitude(),location.getLongitude());
                    // 현재 위치의 위도와 경도를 기반으로 카메라 업데이트 생성
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.getLatitude(), location.getLongitude()));
                    // 지도의 카메라 위치를 업데이트
                    kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true));

//                    LabelStyles styles = kakaoMap.getLabelManager()
//                            .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.pink_marker)
//                                    .setIconTransition(LabelTransition.from(Transition.None, Transition.None))));
                    centerLabel = labelLayer.addLabel(LabelOptions.from("centerLabel", LatLng.from(location.getLatitude(), location.getLongitude()))
                            .setStyles(LabelStyle.from(R.drawable.pink_marker).setAnchorPoint(0.5f, 0.5f))
                            .setRank(1));
                }
            }
        });
    }

    private void ReportLocation() {
        kakaoMap.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
            @Override
            public void onCameraMoveEnd(@NonNull KakaoMap kakaoMap, @NonNull CameraPosition position, @NonNull GestureType gestureType) {
                // CameraPosition 파라미터 값
                // 카메라 움직임이 끝난 후 위치 업데이

                reportLocation = position.getPosition();
                centerLabel.moveTo(LatLng.from(reportLocation.latitude, reportLocation.longitude));

                Toast.makeText(
                        ReportMapActivity.this,
                        MessageFormat.format("Lat: {0} Long: {1}", reportLocation.latitude, reportLocation.longitude),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}