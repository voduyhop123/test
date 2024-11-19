package com.example.duan1_personal_budgeting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "QuanLyChiTieu", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbNguoiDung = "CREATE TABLE NguoiDung(nguoiDungID INTEGER PRIMARY KEY AUTOINCREMENT, tenNguoiDung TEXT NOT NULL UNIQUE, email TEXT NOT NULL, matKhau TEXT NOT NULL)";
        db.execSQL(dbNguoiDung);

        // Data mẫu
//        db.execSQL("INSERT INTO NguoiDung VALUES(null, 'Admin', 'Nguyen Van A', 'nguyenvana@gmail.com', '123456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS NguoiDung");
            onCreate(db);
        }
    }

    public boolean addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenNguoiDung", username);
        contentValues.put("email", email);
        contentValues.put("matKhau", password);
        long result = db.insert("NguoiDung", null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"nguoiDungID"};
        String selection = "tenNguoiDung = ? AND matKhau = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("NguoiDung", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matKhau", newPassword);
        String selection = "tenNguoiDung = ?";
        String[] selectionArgs = {username};
        int result = db.update("NguoiDung", contentValues, selection, selectionArgs);
        return result > 0;
    }

    public boolean checkAccount(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"nguoiDungID"};
        String selection = "tenNguoiDung = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("NguoiDung", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
