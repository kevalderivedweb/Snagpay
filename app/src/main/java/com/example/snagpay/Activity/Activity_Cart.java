package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snagpay.Adapter.AdapterMyCart;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.Database;
import com.example.snagpay.Utils.UserSession;

import java.util.ArrayList;

public class Activity_Cart extends AppCompatActivity {

    private RecyclerView recMyCart;
    private AdapterMyCart adapterMyCart;
    private UserSession session;
    private ArrayList<CategoryDetailsModel> detailsModelArrayList = new ArrayList<>();
    private Database dbHelper;
    private RelativeLayout cartEmpty;
    private Button proceedCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_Cart.this);

        dbHelper = new Database(Activity_Cart.this);

        detailsModelArrayList = dbHelper.getAllUser();


        for (int h = 0; h < detailsModelArrayList.size(); h++) {
            Log.e("dataArrayCart", detailsModelArrayList.get(h).getShow_deal_option_id() + "--");

        }

        recMyCart = findViewById(R.id.recMyCart);
        cartEmpty = findViewById(R.id.cartEmpty);
        proceedCheck = findViewById(R.id.proceedCheck);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recMyCart.setLayoutManager(new LinearLayoutManager(Activity_Cart.this));
        adapterMyCart = new AdapterMyCart(Activity_Cart.this, detailsModelArrayList, new AdapterMyCart.OnItemClickListener() {
            @Override
            public void onItemClickPlus(int position, String quantity) {
                dbHelper.Update(detailsModelArrayList.get(position).getShow_deal_option_id(), quantity);
            }

            @Override
            public void onItemClickMinus(int position, String quantity) {
                dbHelper.Update(detailsModelArrayList.get(position).getShow_deal_option_id(), quantity);
            }

            @Override
            public void onItemDelete(String s, int pos) {
                dbHelper.removeCart(s);
                detailsModelArrayList.remove(pos);
                adapterMyCart.notifyDataSetChanged();

                if (detailsModelArrayList.isEmpty()){
                    cartEmpty.setVisibility(View.VISIBLE);
                    proceedCheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemClickSaveLater(String dealId) {

                Log.e("dealIdCart", dealId + "--");
            }


        });
        recMyCart.setAdapter(adapterMyCart);


        if (detailsModelArrayList.isEmpty()){
            cartEmpty.setVisibility(View.VISIBLE);
            proceedCheck.setVisibility(View.GONE);
        }

        findViewById(R.id.proceedCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Cart.this, Activity_ReviewOrder.class);
                intent.putExtra("valueForOrder", "fromCart");
                startActivity(intent);

            }
        });

    }



}