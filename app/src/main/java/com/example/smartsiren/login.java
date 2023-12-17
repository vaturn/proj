package com.example.smartsiren;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    Button btn_login;
    Button btn_signup;
    EditText editTextId; // 아이디 입력란
    EditText editTextPw; // 비밀번호 입력란

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_login = findViewById(R.id.login_btn);
        btn_signup = findViewById(R.id.signup_btn);
        editTextId = findViewById(R.id.edit_id);
        editTextPw = findViewById(R.id.edit_pwd);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredId = editTextId.getText().toString().trim();
                String enteredPw = editTextPw.getText().toString().trim();

                Intent intent = new Intent(getApplicationContext(), map.class);
                startActivity(intent);
                finish();
//                if (TextUtils.isEmpty(enteredId) && TextUtils.isEmpty(enteredPw)) {
//                    Toast.makeText(login.this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(enteredId)) {
//                    Toast.makeText(login.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                } else if (!isValidEmail(enteredId)) {
//                    Toast.makeText(login.this, "올바른 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(enteredPw)) {
//                    Toast.makeText(login.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), map.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signup.class);
                startActivity(intent);
            }
        });
    }
    private boolean isValidEmail(String email) {
        // 정규식을 사용하여 이메일 형식을 검증
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
