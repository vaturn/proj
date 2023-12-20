package com.kms.smartsiren;

import static com.kms.smartsiren.ReliabilityModel.RELIABILITY_CRITICAL_VALUE;
import static com.kms.smartsiren.ReliabilityModel.getUserReliability;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThumbnailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ThumbnailActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1010);
    }
}