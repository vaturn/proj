package com.kms.smartsiren;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private KakaoMap kakaoMap;
    MapView mapView;
    private FusedLocationProviderClient fusedLocationClient; //위치 서비스 클라이언트 객체
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Double selectedLatitude = null;
    private Double selectedLongitude = null;
    private RecyclerView recyclerview;
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> data = new ArrayList<>();  // 데이터를 담을 List

        // 각 헤더 아이템과 그에 따른 자식 아이템 생성 및 초기 상태 설정
        ExpandableListAdapter.Item header1 = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER1, "");
        header1.invisibleChildren = new ArrayList<>();  // 초기에는 닫혀 있는 상태
        header1.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD1, ""));
        data.add(header1);

        ExpandableListAdapter.Item header2 = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER2, "");
        header2.invisibleChildren = new ArrayList<>();  // 초기에는 닫혀 있는 상태
        header2.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD2, ""));
        data.add(header2);

        ExpandableListAdapter.Item header3 = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER3, "");
        header3.invisibleChildren = new ArrayList<>();  // 초기에는 닫혀 있는 상태
        header3.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD3, ""));
        data.add(header3);

        //recyclerview.addItemDecoration(new RecyclerViewDecoration(30));
        //B
        ExpandableListAdapter adapter = new ExpandableListAdapter(data, new ExpandableListAdapter.OnChildItemClickListener() {
            @Override
            public void onChildItemClick() {
                // CHILD1 아이템 클릭 시 수행할 작업
                Intent intent = new Intent(ReportActivity.this, ReportMapActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });
        recyclerview.setAdapter(adapter);


        // Handle button4 click event
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show '신고 접수가 완료되었습니다.' popup
                showReportConfirmationDialog();
            }
        });
    }

    //B
    private void showReportConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("신고 접수가 완료되었습니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Switch to 'map' activity
                        // 위도와 경도가 선택되었는지 확인
                        if(selectedLatitude != null && selectedLongitude != null) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("latitude", selectedLatitude);
                            returnIntent.putExtra("longitude", selectedLongitude);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish(); // 액티비티 종료
                        } else {
                            Toast.makeText(ReportActivity.this, "위치가 설정되지 않았습니다. 지도에서 위치를 선택해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // 뒤로가기 버튼을 눌렀을 때의 동작 설정
            Intent intent = new Intent(this, MapActivity.class); // 'go'에서 'map'으로 이동
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //B
    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    selectedLatitude = data.getDoubleExtra("latitude", 0.0);
                    selectedLongitude = data.getDoubleExtra("longitude", 0.0);

                }
            }
    );
}
