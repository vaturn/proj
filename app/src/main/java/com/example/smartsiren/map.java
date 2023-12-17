package com.example.smartsiren;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class map extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView navLogout = findViewById(R.id.nav_logout);
        navLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show logout confirmation dialog
                showLogoutConfirmationDialog();
            }
        });

        TextView navLogout3 = findViewById(R.id.nav_logout3);
        navLogout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map.this, appgudie.class);
                startActivity(intent);
            }
        });

        TextView navLogout2 = findViewById(R.id.nav_logout2);
        navLogout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map.this, useredit.class);
                startActivity(intent);
            }
        });

        TextView navLogout1 = findViewById(R.id.nav_logout1);
        navLogout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map.this, myupdate.class);
                startActivity(intent);
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button goButton = findViewById(R.id.go_btn); // Replace 'go_btn' with your actual button ID
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start 'report' activity when the button is clicked
                Intent intent = new Intent(map.this, report.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("안내")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(map.this, login.class);
                        startActivity(intent);
                        // 로그아웃 후 현재 액티비티를 종료하려면 아래 코드를 추가합니다.
                        finish();
                    }
                })
                .show();
    }


}
