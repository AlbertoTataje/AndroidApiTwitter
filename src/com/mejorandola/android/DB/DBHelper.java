package com.mejorandola.android.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = DBHelper.class.getSimpleName();

	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;

	public static final String TABLE = "timeline";
	// identificador unico para una fila
	public static final String C_ID = BaseColumns._ID;
	public static final String C_NAME = "name";
	public static final String C_SCREEN_NAME = "screen_name";
	public static final String C_IMAGE_PROFILE_URL = "image_profile_url";
	public static final String C_TEXT = "text";
	public static final String C_CREATED_AT = "date";

	public static final int C_ID_INDEX = 0;
	public static final int C_NAME_INDEX = 1;
	public static final int C_SCREEN_NAME_INDEX = 2;
	public static final int C_IMAGE_PROFILE_URL_INDEX = 3;
	public static final int C_TEXT_INDEX = 4;
	public static final int C_CREATED_AT_INDEX = 5;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE + " (" + C_ID
				+ " int primary key, " + C_NAME + " text, " + C_SCREEN_NAME
				+ " text, " + C_IMAGE_PROFILE_URL + " text, " + C_TEXT
				+ " text, " + C_CREATED_AT + " text)";
		db.execSQL(sql);
		Log.d(TAG, "onCreated sql: " + sql);
	}

	// LLamado cuando la antigua version es distinta a la nueva version
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		db.execSQL("drop table if exists " + TABLE); // drops the old database
		Log.d(TAG, "onUpdated > borra Tabla");
		onCreate(db);

	}

}
