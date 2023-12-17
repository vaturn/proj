package com.example.smartsiren;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class report extends AppCompatActivity {

    private RecyclerView recyclerview;
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_main);

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

//        recyclerview.addItemDecoration(new RecyclerViewDecoration(30));
        recyclerview.setAdapter(new ExpandableListAdapter(data));

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

    private void showReportConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("신고 접수가 완료되었습니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Switch to 'map' activity
                        Intent intent = new Intent(report.this, map.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // 뒤로가기 버튼을 눌렀을 때의 동작 설정
            Intent intent = new Intent(this, map.class); // 'go'에서 'map'으로 이동
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
