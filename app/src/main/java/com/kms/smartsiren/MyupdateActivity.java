package com.kms.smartsiren;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        tv_name.setText(user.getDisplayName());

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
