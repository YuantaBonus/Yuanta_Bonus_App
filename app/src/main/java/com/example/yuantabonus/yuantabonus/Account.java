package com.example.yuantabonus.yuantabonus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    Intent intent = new Intent();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shopping_cart:
                    intent.setClass(Account.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_accounts:
                    return true;
                case R.id.navigation_notifications:
                    intent.setClass(Account.this, Notification.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_webView:
                    intent.setClass(Account.this, Webview.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_settings:
                    intent.setClass(Account.this, Setting.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_accounts);
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_accounts);
    }
}
