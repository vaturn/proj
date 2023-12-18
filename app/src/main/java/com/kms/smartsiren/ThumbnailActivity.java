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

    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("SmartSiren");


        mDatabaseRef.child("Unconfirmed").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                UnconfirmedCase caseing = dataSnapshot.getValue(UnconfirmedCase.class);
                if(caseing.getReliability() > RELIABILITY_CRITICAL_VALUE){
                    mDatabaseRef.child("Unconfirmed").child(caseing.getUuid()).removeValue();
                    CaseInfo a = new CaseInfo(caseing.getLatitude(), caseing.getLongitude(), caseing.getCategory(), caseing.getRating(), caseing.getDetail(), caseing.getUuid());
                    mDatabaseRef.child("CaseInfo").child(caseing.getUuid()).setValue(a);
                    for (String user : caseing.getInformants()){
                        mDatabaseRef.child("UserInfo").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                UserAccount userac = dataSnapshot.getValue(UserAccount.class);
                                userac.setReportG(userac.getReportG() + 1);
                                userac.setReliability(getUserReliability(userac.getReportG(), userac.getReportN()));
                                mDatabaseRef.child("UserInfo").child(user).setValue(userac);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                // ...
                            }
                        });
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                UnconfirmedCase caseing = dataSnapshot.getValue(UnconfirmedCase.class);
                if(caseing.getReliability() > RELIABILITY_CRITICAL_VALUE){
                    mDatabaseRef.child("Unconfirmed").child(caseing.getUuid()).removeValue();
                    CaseInfo a = new CaseInfo(caseing.getLatitude(), caseing.getLongitude(), caseing.getCategory(), caseing.getRating(), caseing.getDetail(), caseing.getUuid());
                    mDatabaseRef.child("CaseInfo").child(caseing.getUuid()).setValue(a);

                    mDatabaseRef.child("CaseInfo").child(caseing.getUuid()).setValue(a);
                    for (String user : caseing.getInformants()){
                        mDatabaseRef.child("UserInfo").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                UserAccount userac = dataSnapshot.getValue(UserAccount.class);
                                userac.setReportG(userac.getReportG() + 1);
                                userac.setReliability(getUserReliability(userac.getReportG(), userac.getReportN()));
                                mDatabaseRef.child("UserInfo").child(user).setValue(userac);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                // ...
                            }
                        });
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ThumbnailActivity.this, TestActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1010);
    }
}