package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.PressAdapter;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_Press extends AppCompatActivity {

    private RecyclerView resPress;
    private PressAdapter pressAdapter;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_Press.this);

        resPress = findViewById(R.id.resPress);

        resPress.setLayoutManager(new GridLayoutManager(Activity_Press.this, 2));
        pressAdapter = new PressAdapter(Activity_Press.this, new PressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                startActivity(new Intent(Activity_Press.this, Activity_PressInner.class));
            }
        });
        resPress.setAdapter(pressAdapter);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}