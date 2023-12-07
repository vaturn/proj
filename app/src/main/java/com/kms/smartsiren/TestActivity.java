package com.kms.smartsiren;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestActivity extends AppCompatActivity {

    TextView tv_lat, tv_long, tv_name;
    Button btn_insert, btn_output;
    private DatabaseReference mDatabaseRef; // 실시간 DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tv_lat = findViewById(R.id.tv_result_lat);
        tv_long = findViewById(R.id.tv_result_long);
        tv_name = findViewById(R.id.tv_result_name);
        btn_insert = findViewById(R.id.btn_insert);
        btn_output = findViewById(R.id.btn_output);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("SmartSiren");

    }
}