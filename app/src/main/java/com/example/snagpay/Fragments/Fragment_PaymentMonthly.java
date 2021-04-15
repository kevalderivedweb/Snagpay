package com.example.snagpay.Fragments;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Adapter.AdapterMonthlyViewPayment;
import com.example.snagpay.Model.MonthlyView;
import com.example.snagpay.Model.OrderModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.EndlessRecyclerViewScrollListener;
import com.example.snagpay.Utils.FileDownloader;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_PaymentMonthly extends Fragment {

    private ArrayList<String> yearList = new ArrayList<>();
    private String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String[] monthDigit = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private Spinner spinnerYearHistory, spinnerMonthHistory;
    private UserSession session;
    private RadioGroup radioGroup;

    private AdapterMonthlyViewPayment adapterMonthlyViewPayment;

    private String selectMonth;
    private String selectYear;
    private int option = 1;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;

    private RecyclerView recMonthlyView;

    private String urlPdf;


    private int last_size;
    private String Mpage = "1";
    private LinearLayoutManager linearlayout;

    private ArrayList<MonthlyView> monthlyViewArrayList = new ArrayList<>();

    private TextView noDataPayment;

    private NestedScrollView nestedScroll;


    public Fragment_PaymentMonthly() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_fragment_payment_monthly, container, false);

        session = new UserSession(getContext());

        spinnerYearHistory = view.findViewById(R.id.spinnerYearHistory);
        spinnerMonthHistory = view.findViewById(R.id.spinnerMonthHistory);
        radioGroup = view.findViewById(R.id.radioGroup1);
        recMonthlyView = view.findViewById(R.id.recMonthlyView);
        noDataPayment = view.findViewById(R.id.noDataPayment);
        nestedScroll = view.findViewById(R.id.nestedScroll);

        recMonthlyView.setNestedScrollingEnabled(false);

        nestedScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged()
            {
                View view = (View)nestedScroll.getChildAt(nestedScroll.getChildCount() - 1);

                int diff = (view.getBottom() - (nestedScroll.getHeight() + nestedScroll
                        .getScrollY()));

                if (diff == 0) {
                    // your pagination code
                    if (diff!=last_size){
                        Mpage = String.valueOf(diff+1);

                        getMonthlyView(Mpage, selectMonth, selectYear);

                    }
                }
            }
        });



        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearList.add(String.valueOf(currentYear));
        yearList.add(String.valueOf(currentYear - 1));
        yearList.add(String.valueOf(currentYear - 2));
        yearList.add(String.valueOf(currentYear - 3));

        ArrayAdapter<String> spinnerArrayAdapterYear = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, yearList);
        spinnerYearHistory.setAdapter(spinnerArrayAdapterYear);

        spinnerYearHistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectYear = yearList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerArrayAdapterMonth = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, monthList);
        spinnerMonthHistory.setAdapter(spinnerArrayAdapterMonth);

        spinnerMonthHistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectMonth = monthDigit[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.btnSubmitMonthYear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monthlyViewArrayList.clear();
                adapterMonthlyViewPayment.notifyDataSetChanged();

                if (option == 1){

                    getMonthlyView("1", selectMonth, selectYear);

                } else if (option == 2){
                    getPdf(selectMonth, selectYear);
                }



            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioView){
                    option = 1;

                } else if (checkedId == R.id.radioDownLoad){
                    option = 2;
                    getPdf(selectMonth, selectYear);
                }


            }
        });

        linearlayout = new LinearLayoutManager(getContext());
        recMonthlyView.setLayoutManager(linearlayout);
        adapterMonthlyViewPayment = new AdapterMonthlyViewPayment(getContext(), monthlyViewArrayList);
        recMonthlyView.setAdapter(adapterMonthlyViewPayment);

/*
        recMonthlyView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearlayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.e("PageStatus",page + "  " + last_size);

            }
        });*/


      //  getPdf(selectMonth, selectYear);

        checkAndroidVersion();


        return view;
    }

    public void getMonthlyView(String Mpage, String selectMonth, String selectYear){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "monthly-payment-history?month=" + selectMonth +
                "&year=" + selectYear + "&page=" + Mpage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();
                recMonthlyView.setVisibility(View.VISIBLE);

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());

                    if (jsonObject.getString("ResponseCode").equals("200")){

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        last_size = jsonObject1.getInt("last_page");


                        JSONArray jsonArray = jsonObject1.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);

                            MonthlyView monthlyView = new MonthlyView();
                            monthlyView.setE_wallet_id(jsonObject11.getString("e_wallet_id"));
                            monthlyView.setE_wallet_tran_code(jsonObject11.getString("e_wallet_tran_code"));
                            monthlyView.setWallet_credit(jsonObject11.getString("wallet_credit"));
                            monthlyView.setBalance(jsonObject11.getString("balance"));
                            monthlyView.setTransaction_title(jsonObject11.getString("transaction_title"));
                            monthlyView.setDatetime(jsonObject11.getString("datetime"));
                            monthlyView.setTransaction_type(jsonObject11.getString("transaction_type"));

                            monthlyViewArrayList.add(monthlyView);
                        }


                        if (monthlyViewArrayList.isEmpty()){
                            recMonthlyView.setVisibility(View.GONE);
                            noDataPayment.setVisibility(View.VISIBLE);
                        } else {
                            noDataPayment.setVisibility(View.GONE);
                        }

                        adapterMonthlyViewPayment.notifyDataSetChanged();

                        Toast.makeText(getContext(),jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(getContext(), Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(Activity_ReviewOrder.this, "No Data", Toast.LENGTH_SHORT).show();

                            /*session.logout();
                            Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();*/

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Log.e("dssdsd", error.getMessage() + "--");

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + session.getAPITOKEN());
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);

    }


    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        //    File folder = new File(extStorageDirectory, "Snagpay");

            File folder = new File(getActivity().getExternalFilesDir(null) + "/" + "Snagpay");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }


    public void getPdf(String selectMonth, String selectYear){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //    .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET,
                session.BASEURL + "monthly-payment-download?year=" + selectYear + "&month=" + selectMonth, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                //  progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                        urlPdf = jsonObject1.getString("pdf_url");


                        if (option == 2){
                            new DownloadFile().execute(urlPdf, "snagpay transaction.pdf");
                            Toast.makeText(getContext(), "Check Android > data > com.example.snagpay", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(getContext(),jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(getContext(), Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(Activity_ReviewOrder.this, "No Data", Toast.LENGTH_SHORT).show();

                            /*session.logout();
                            Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();*/

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Log.e("dssdsd", error.getMessage() + "--");

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
              //  params.put("amount", amount);
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + session.getAPITOKEN());
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }



    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();

        } else {
            // code for lollipop and pre-lollipop devices
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(getActivity(), "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
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