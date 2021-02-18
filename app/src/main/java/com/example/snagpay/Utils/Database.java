package com.example.snagpay.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.snagpay.Model.CategoryDetailsModel;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Snag_pay_1";

    private static final String TABLE_CART = "wishlist";
    private static final String KEY_DEAL_IMAGE = "deal_image";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DEAL_ID = "deal_id";
    private static final String KEY_SELL_PRICE = "sell_price";
    private static final String KEY_BOUGHT = "bought";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_DEAL_OPTION_ID = "optionid";
    private final Context mContext;

    public Database(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + KEY_DEAL_IMAGE + " TEXT ,"
                + KEY_TITLE + " TEXT ,"
                + KEY_DEAL_ID + " TEXT ,"
                + KEY_DEAL_OPTION_ID + " TEXT ,"
                + KEY_SELL_PRICE + " TEXT ,"
                + KEY_BOUGHT + " TEXT ,"
                + KEY_QUANTITY + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public boolean InsertDetails(String deal_image, String title, String deal_id, String dealOptionId,
                                 String sell_price, String bought, String quantity) {

        SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_DEAL_IMAGE, deal_image);
            values.put(KEY_TITLE, title);
            values.put(KEY_DEAL_ID, deal_id);
            values.put(KEY_DEAL_OPTION_ID, dealOptionId);
            values.put(KEY_SELL_PRICE, sell_price);
            values.put(KEY_BOUGHT, bought);
            values.put(KEY_QUANTITY, quantity);
            db.insert(TABLE_CART, null, values);
            db.close();


        return true;
    }

    public ArrayList<CategoryDetailsModel> getAllUser() {
        ArrayList<CategoryDetailsModel> detailsModelArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CategoryDetailsModel model =new CategoryDetailsModel();
                model.setDeal_image(cursor.getString(0));
                model.setTitle(cursor.getString(1));
                model.setDeal_id(cursor.getString(2));
                model.setShow_deal_option_id(cursor.getString(3));
                model.setSell_price(cursor.getString(4));
                model.setBought(cursor.getString(5));
                model.setQuantity(cursor.getString(6));
                detailsModelArrayList.add(model);

            }while (cursor.moveToNext());
        }
        return detailsModelArrayList;
    }

    public void removeCart(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, KEY_DEAL_ID + " = ?",
                new String[] { String.valueOf(contact) });
        db.close();
    }

    /*public Cursor findTask(String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WISHLIST + " WHERE " +
                KEY_TITLE + " = '" + taskName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }*/
}