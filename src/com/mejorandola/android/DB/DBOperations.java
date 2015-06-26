package com.mejorandola.android.DB;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mejorandola.android.models.Tweet;

public class DBOperations {

	private static final String TAG = DBOperations.class.getSimpleName();
	private DBHelper dbHelper;

	public DBOperations(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void insertOrIgnore(ContentValues values) {
		Log.d(TAG, "insertOrIgnore on: " + values);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.insertWithOnConflict(DBHelper.TABLE, null, values,
					SQLiteDatabase.CONFLICT_IGNORE);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, "No insertado " + values);
		} finally {
			db.close();
		}
	}
	
	public void DeleteRows(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.delete(DBHelper.TABLE, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, "No Borrado");
		} finally {
			db.close();
		}
		
	}

	
	//Obtiene los tweets de la base de datos
	public ArrayList<Tweet> getStatusUpdates() {

		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
		Cursor cursor = dataBase.query(DBHelper.TABLE, null, null, null, null, null, null);
		//Si hay datos en el cursor
		if(cursor.moveToFirst()){
			//Mientras no este en la ultima posicion
			while(cursor.isAfterLast()==false){
				Tweet tweet = new Tweet();
				tweet.setId(String.valueOf(cursor.getInt(DBHelper.C_ID_INDEX)));
				tweet.setName(cursor.getString(DBHelper.C_NAME_INDEX));
				tweet.setScreenName(cursor.getString(DBHelper.C_SCREEN_NAME_INDEX));
				tweet.setProfileImageUrl(cursor.getString(DBHelper.C_IMAGE_PROFILE_URL_INDEX));
				tweet.setText(cursor.getString(DBHelper.C_TEXT_INDEX));
				tweet.setCreatedAt(cursor.getString(DBHelper.C_CREATED_AT_INDEX));
				tweets.add(tweet);
				cursor.moveToNext();
			}
		}
			Log.d(TAG,"Numero de Tweets en la DB: "+tweets.size());
		return tweets;
	}
}
