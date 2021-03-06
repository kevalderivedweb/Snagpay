package com.example.snagpay.Fragments;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.snagpay.Activity.Activity_BarcodeScanner;
import com.example.snagpay.Utils.UserSession;
import com.example.snagpay.Activity.Activity_Cart;
import com.example.snagpay.Activity.Activity_ChangePassword;
import com.example.snagpay.Activity.Activity_Company;
import com.example.snagpay.Activity.Activity_IncredibleDeals;
import com.example.snagpay.Activity.Activity_ManageMyWishList;
import com.example.snagpay.Activity.Activity_More;
import com.example.snagpay.Activity.Activity_MyPurchases;
import com.example.snagpay.Activity.Activity_NotificationSettings;
import com.example.snagpay.Activity.Activity_PaymentMethods;
import com.example.snagpay.R;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Activity.Activity_ShippingAddress;
import com.example.snagpay.Activity.Activity_SnagpayDeals;
import com.example.snagpay.Activity.Activity_SnagpayGuide;
import com.example.snagpay.Activity.Activity_SnagpayWallet;
import com.example.snagpay.Activity.Activity_WorkwithSnagpay;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

public class Fragment_MyStuffActivity extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private UserSession session;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private ImageView scane;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;
    
    public Fragment_MyStuffActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//By clicking below, I agree to the Terms of Use and have read the Privacy Statement.

        View view = inflater.inflate(R.layout.activity_fragment_my_stuff, container, false);

        session = new UserSession(getContext());

        scane = view.findViewById(R.id.scane);

        scane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED){
                    checkAndroidVersion();
                } else {
                    startActivity(new Intent(getContext(), Activity_BarcodeScanner.class));
                }
            }
        });

        try {

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity(), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        } catch (Exception e){

        }

        view.findViewById(R.id.lyotDeals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_SnagpayDeals.class));
            }
        });

        view.findViewById(R.id.lyotMyPurchases).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_MyPurchases.class));
            }
        });

        view.findViewById(R.id.lyotManageMyWishlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_ManageMyWishList.class));
            }
        });


        view.findViewById(R.id.lyotPaymentMethods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_PaymentMethods.class));
            }
        });

        view.findViewById(R.id.lyotShippingAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_ShippingAddress.class);
                intent.putExtra("value", 1);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.lyotNotificationSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_NotificationSettings.class));
            }
        });

        view.findViewById(R.id.lyotSnagpayWallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_SnagpayWallet.class));
            }
        });

        view.findViewById(R.id.btnCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_Cart.class));
            }
        });

        view.findViewById(R.id.lyotChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_ChangePassword.class));
            }
        });

        try {
            view.findViewById(R.id.btnSignOut).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    session.logout();
                    try {

                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {
                                        if (status.isSuccess()) {
                                            Intent intent = new Intent(getContext(), Activity_SelectCity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            getActivity().finish();
                                        } else {
                                            Toast.makeText(getContext(), "Session not close", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }catch (Exception e){

                    }

                    Intent intent = new Intent(getContext(), Activity_SelectCity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        } catch (Exception e){

        }

        view.findViewById(R.id.lyotCompany).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_Company.class));
            }
        });

        view.findViewById(R.id.lyotSNAGpayGuide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_SnagpayGuide.class));
            }
        });

        view.findViewById(R.id.lyotIncredibles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_IncredibleDeals.class));
            }
        });

        view.findViewById(R.id.lyotWorkWith).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_WorkwithSnagpay.class));
            }
        });

        view.findViewById(R.id.lyotMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_More.class));
            }
        });

        return view;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Sign out Falied", Toast.LENGTH_SHORT).show();
    }


    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();

        } else {
            // code for lollipop and pre-lollipop devices
        }

    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


}