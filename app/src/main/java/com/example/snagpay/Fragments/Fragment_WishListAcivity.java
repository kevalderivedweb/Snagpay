package com.example.snagpay.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snagpay.Adapter.AdapterFragWishlist;
import com.example.snagpay.Adapter.AdapterFragWishlistRecentViewed;
import com.example.snagpay.R;

public class Fragment_WishListAcivity extends Fragment {

    private RecyclerView resFragWishList,resFragRecentlyViewed;
    private AdapterFragWishlist adapterFragWishlist;
    private AdapterFragWishlistRecentViewed adapterFragWishlistRecentViewed;

    private RelativeLayout rltvWishListTopBar, rltvWishListTopBarDeleteCancel;
    private TextView txtEditWishlistTopBar;
    private ImageView imgCancelWishlishTopBar;

    public Fragment_WishListAcivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_wish_list_acivity, container, false);

        imgCancelWishlishTopBar = view.findViewById(R.id.imgCancelWishlishTopBar);
        rltvWishListTopBar = view.findViewById(R.id.rltvWishListTopBar);
        rltvWishListTopBarDeleteCancel = view.findViewById(R.id.rltvWishListTopBarDeleteCancel);
        txtEditWishlistTopBar = view.findViewById(R.id.txtEditWishlistTopBar);

        resFragWishList = view.findViewById(R.id.resFragWishList);
        resFragRecentlyViewed = view.findViewById(R.id.resFragRecentlyViewed);

        resFragWishList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterFragWishlist = new AdapterFragWishlist(getActivity());
        resFragWishList.setAdapter(adapterFragWishlist);

        resFragRecentlyViewed.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterFragWishlistRecentViewed = new AdapterFragWishlistRecentViewed(getActivity());
        resFragRecentlyViewed.setAdapter(adapterFragWishlistRecentViewed);

        // for top bar hidden
        txtEditWishlistTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rltvWishListTopBar.setVisibility(View.GONE);
                rltvWishListTopBarDeleteCancel.setVisibility(View.VISIBLE);
            }
        });

        imgCancelWishlishTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rltvWishListTopBar.setVisibility(View.VISIBLE);
                rltvWishListTopBarDeleteCancel.setVisibility(View.GONE);
            }
        });

        return view;
    }
}