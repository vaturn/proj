package com.example.smartsiren;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class signup extends AppCompatActivity {

    EditText etName, etId, etPw, etPw2;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        etName = findViewById(R.id.signupname);
        etId = findViewById(R.id.signupemail);
        etPw = findViewById(R.id.signuppw);
        etPw2 = findViewById(R.id.signuppw2);
        btnSignup = findViewById(R.id.signupbutton);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String id = etId.getText().toString().trim();
                String pw = etPw.getText().toString().trim();
                String pw2 = etPw2.getText().toString().trim(); // Added second password field

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(id) || TextUtils.isEmpty(pw) || TextUtils.isEmpty(pw2)) {
                    // Check if any field is empty
                    Toast.makeText(signup.this, "이름, 이메일, 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (pw.length() < 8) {
                    // Check if password length is less than 8 characters
                    Toast.makeText(signup.this, "비밀번호를 8자 이상 설정해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(id)) {
                    // Check if email format is valid
                    Toast.makeText(signup.this, "올바른 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!pw.equals(pw2)) {
                    // Check if passwords match
                    Toast.makeText(signup.this, "비밀번호가 일치하지 않습니다. 동일한 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etPw.setText(""); // Clear the password fields
                    etPw2.setText("");
                } else {
                    // Successful registration
                    Toast.makeText(signup.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // 뒤로가기 버튼을 눌렀을 때의 동작
            Intent intent = new Intent(signup.this, login.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidEmail(String email) {
        // 정규식을 사용하여 이메일 형식을 검증
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
