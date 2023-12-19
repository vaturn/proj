package com.kms.smartsiren;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyupdateActivity extends AppCompatActivity {
    TextView tv_name, tv_db1, tv_db2, tv_db3, tv_db4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myupdate);

        tv_name = findViewById(R.id.text_DBname);
        tv_db1 = findViewById(R.id.tv_db1);
        tv_db2 = findViewById(R.id.tv_db2);
        tv_db3 = findViewById(R.id.tv_db3);
        tv_db4 = findViewById(R.id.tv_db4);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("SmartSiren");
        mDatabaseRef.child("UserInfo").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                tv_name.setText(userAccount.getName());
                String n = Integer.toString(userAccount.getReportN());
                String g = Integer.toString(userAccount.getReportG());
                tv_db1.setText(n);
                tv_db2.setText(n);
                tv_db3.setText(g);
                tv_db4.setText("0");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 뒤로가기 버튼 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // 뒤로가기 버튼을 눌렀을 때의 동작
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
