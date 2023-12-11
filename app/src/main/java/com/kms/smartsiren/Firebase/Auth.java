package com.kms.smartsiren.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.kms.smartsiren.RegisterActivity;

public class Auth {
    private FirebaseAuth mFirebaseAuth; // DB 인증 관련

    public void createAccount(String email, String pwd){
        mFirebaseAuth = FirebaseAuth.getInstance();

        //mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new)
    }
}
