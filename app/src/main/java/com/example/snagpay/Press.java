package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.PressAdapter;

public class Press extends AppCompatActivity {

    private RecyclerView resPress;
    private PressAdapter pressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        resPress = findViewById(R.id.resPress);

        resPress.setLayoutManager(new GridLayoutManager(Press.this, 2));
        pressAdapter = new PressAdapter(Press.this, new PressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                startActivity(new Intent(Press.this, PressInner.class));
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
}