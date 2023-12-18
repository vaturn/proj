package com.example.smartsiren;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Useredit2Activity extends AppCompatActivity {

    EditText et_nowPW, et_newPW, et_newPW_chk;
    Button btn_changePW, btn_user_withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useredit2);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 뒤로가기 버튼 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        et_nowPW = findViewById(R.id.et_nowPW);
        et_newPW = findViewById(R.id.et_newPW);
        et_newPW_chk = findViewById(R.id.et_newPW_chk);

        btn_changePW = findViewById(R.id.btn_changePW);
        btn_user_withdraw = findViewById(R.id.btn_user_withdraw);

        btn_changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowPW = et_nowPW.getText().toString().trim();
                String newPW = et_newPW.getText().toString().trim();
                String newPWchk = et_newPW_chk.getText().toString().trim();


                // DB에서 가져온 현재 비밀번호를 확인하는 기능 추가

                if (TextUtils.isEmpty(nowPW) || TextUtils.isEmpty(newPW) || TextUtils.isEmpty(newPWchk)) {
                    Toast.makeText(Useredit2Activity.this, "빈칸을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (newPW.length() < 8) {
                    Toast.makeText(Useredit2Activity.this, "비밀번호를 8자 이상 설정해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!newPW.equals(newPWchk)) {
                    Toast.makeText(Useredit2Activity.this, "비밀번호가 일치하지 않습니다. 동일한 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Useredit2Activity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Useredit2Activity.this, MapActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_user_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Useredit2Activity.this, UserwithdrawActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // 뒤로가기 버튼을 눌렀을 때의 동작
            Intent intent = new Intent(this, UsereditActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
