package com.example.yuantabonus.yuantabonus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Intent intent = new Intent();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shopping_cart:
                    return true;
                case R.id.navigation_accounts:
                    intent.setClass(MainActivity.this, Account.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    intent.setClass(MainActivity.this, Notification.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_webView:
                    intent.setClass(MainActivity.this, Webview.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_settings:
                    intent.setClass(MainActivity.this, Setting.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_shopping_cart);

        ArrayList<String> myDataset = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            myDataset.add(i + "");
        }
        MyAdapter myAdapter = new MyAdapter(myDataset);
        RecyclerView mList = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);

        }

        public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
            private List<String> mData;

            public class ViewHolder extends RecyclerView.ViewHolder {
                public TextView mTextView;

                public ViewHolder(View v) {
                    super(v);
                    mTextView = (TextView) v.findViewById(R.id.info_text);
                }
            }

            public MyAdapter(List<String> data) {
                mData = data;
            }

            @Override
            public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item, parent, false);
                ViewHolder vh = new ViewHolder(v);
                return vh;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                holder.mTextView.setText(mData.get(position));

            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_shopping_cart);
    }
}
