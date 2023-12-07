package com.kms.smartsiren;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraUpdate;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;
import com.kakao.vectormap.label.LabelTransition;
import com.kakao.vectormap.label.Transition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import android.Manifest;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/*
FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in

        } else {
            // No user is signed in

        }
        로그인 했는지 안했는지 검사
 */
public class MapActivity extends AppCompatActivity {

    private KakaoMap kakaoMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
    //Rest API 키, 테스트할려면 바꿔야 함.
    private static final String GEOCODE_USER_INFO = "KakaoAK 1dd2a83a90a775ff04216aa9b7a36ee8";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    LocationRequest locationRequest;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    LatLng labelLocation;
    LabelLayer labelLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Button btn_address_search = findViewById(R.id.btn_address_search);
        final EditText et_address_search = findViewById(R.id.et_address_search);
//        Button btn_report = findViewById(R.id.btn_report);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        MapView mapView = findViewById(R.id.map_view);
        createLocationRequest();

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView navLogout = findViewById(R.id.nav_logout);
        navLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MapActivity.this)
                        .setTitle("안내")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MapActivity.this, LoginActivity.class); // Replace LoginActivity with your actual login activity class
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button goButton = findViewById(R.id.go_btn);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ReportActivity.class); // Replace ReportActivity with your actual report activity class
                getReportLocation.launch(intent);
            }
        });

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
                // Map API is destroyed
            }
            @Override
            public void onMapError(Exception error) {
                // Error during map usage
            }
        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(KakaoMap Map) {
                kakaoMap = Map;
                labelLayer = kakaoMap.getLabelManager().getLayer();
                if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                } else {
                    getLastLocation();
                    startLocationUpdates();
                }
            }
        });

        btn_address_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_address_search.getText().toString();
                new Thread(() -> {
                    LocationSearch(text); // network 동작, 인터넷에서 xml을 받아오는 코드
                }).start();
            }
        });
    }

    // Rest of your methods (createLocationRequest, getLastLocation, startLocationUpdates, onOptionsItemSelected, etc.)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //위치 요청 설정
    protected void createLocationRequest() {
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
                .setIntervalMillis(1000)             // Sets the interval for location updates
                .setMinUpdateIntervalMillis(500)  // Sets the fastest allowed interval of location updates.
                .setWaitForAccurateLocation(true)              // Want Accurate location updates make it true or you get approximate updates
                .setMaxUpdateDelayMillis(100)                   // Sets the longest a location update may be delayed.
                .build();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    //마지막 위치
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // 현재 위치의 위도와 경도를 기반으로 카메라 업데이트 생성
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.getLatitude(), location.getLongitude()));
                    // 지도의 카메라 위치를 업데이트
                    kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true));
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        //버튼 및 텍스트
        Button btn_current_location = findViewById(R.id.btn_current_location);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    if (locationResult == null) {
                        return;
                    }
                    //Showing the latitude, longitude and accuracy on the home screen.
                    for (Location location : locationResult.getLocations()) {

                        //현재위치 버튼 눌렀을 때 작동하는 부분
                        btn_current_location.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                                } else {
                                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.getLatitude(), location.getLongitude()));
                                    // 지도의 카메라 위치를 업데이트
                                    kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true));
                                }
                            }
                        });

                    }
                }
            }
        };
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    //주소 받아오고 해당 주소 위도,경도 받고 카메라 조정
    public void LocationSearch(String address) {

        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            URL obj = new URL(GEOCODE_URL + encodedAddress);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", GEOCODE_USER_INFO);
            con.setRequestProperty("content-type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            String jsonResponse = response.toString();

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray documentsArray = jsonObject.getJSONArray("documents");

            if (documentsArray.length() > 0) {
                JSONObject firstDocument = documentsArray.getJSONObject(0);
                double latitude = Double.parseDouble(firstDocument.getString("y"));
                double longitude = Double.parseDouble(firstDocument.getString("x"));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(latitude, longitude));
                // 지도의 카메라 위치를 업데이트
                kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DangerLabel(String labelId, LatLng labelPosition) {
        if (labelPosition != null) {
            // 라벨 스타일 생성
            LabelStyles styles = kakaoMap.getLabelManager()
                    .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.pink_marker)
                            .setIconTransition(LabelTransition.from(Transition.None, Transition.None))));
            if (labelLayer != null) {
                // 라벨 생성
                labelLayer.addLabel(LabelOptions.from(labelId, labelPosition).setStyles(styles));
            }
            else{
                Toast.makeText(MapActivity.this, "labelLayer error.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(MapActivity.this, "위치를 찾지 못 했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private final ActivityResultLauncher<Intent> getReportLocation = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    // todo - ...
                    Intent data = result.getData();
                    if(data!=null) {
                        double latitude = data.getDoubleExtra("latitude", 0.0);
                        double longitude = data.getDoubleExtra("longitude", 0.0);
                        labelLocation = LatLng.from(latitude, longitude);
                        String uniqueLabelId = generateUniqueLabelId(); // 고유한 라벨 ID "label_현재 시간"으로 생성
                        DangerLabel(uniqueLabelId, labelLocation);
                    }
                }
            }
    );

    private String generateUniqueLabelId() {
        return "label_" + System.currentTimeMillis();
    }


}


