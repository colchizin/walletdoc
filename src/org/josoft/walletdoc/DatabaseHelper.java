package org.josoft.walletdoc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "expenses.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_EXPENSES = "expenses";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_SUBJECT = "subject";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_CREATED = "created";
	
	public static final int COLUMN_ID_IDX = 0;
	public static final int COLUMN_SUBJECT_IDX = 1;
	public static final int COLUMN_AMOUNT_IDX = 2;
	public static final int COLUMN_CREATED_IDX = 3;
	
	public static DatabaseHelper databaseHelper = null;
	public static SQLiteDatabase database = null;
	
	private static final String TABLE_EXPENSES_CREATE =
			"CREATE TABLE " + TABLE_EXPENSES + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_SUBJECT + " TEXT NOT NULL, " +
					COLUMN_AMOUNT + " INTEGER NOT NULL, " +
					COLUMN_CREATED + " TEXT NOT NULL" +
		");";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_EXPENSES_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " +
				oldVersion + " to version " + newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES + ";");
		onCreate(db);
	}
	
	public static DatabaseHelper getHelper(Context context, boolean forceRebuild) {
		if (databaseHelper == null || forceRebuild)
			databaseHelper = new DatabaseHelper(context);
		return databaseHelper;
	}
	
	public static SQLiteDatabase getDatabase(Context context, boolean forceRebuild) {
		if (DatabaseHelper.database == null || forceRebuild)
			return DatabaseHelper.getHelper(context, forceRebuild).getWritableDatabase();
		return null;
	}

}
