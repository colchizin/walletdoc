package org.josoft.walletdoc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExpensesDatasource extends Datasource {
	protected SQLiteDatabase database = null;
	protected String[] ALL_COLUMNS = {
			DatabaseHelper.COLUMN_ID,
			DatabaseHelper.COLUMN_SUBJECT,
			DatabaseHelper.COLUMN_AMOUNT,
			DatabaseHelper.COLUMN_CREATED,
	};
	
	public ExpensesDatasource(Context context) {
		super(context);
		database = DatabaseHelper.getDatabase(context,false);
	}
	
	public void removeExpense(Expense e) {
		database.delete(DatabaseHelper.TABLE_EXPENSES, DatabaseHelper.COLUMN_ID+"="+e.id, null);
	}
	
	public Expense createExpense(Expense e) {
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = new Date();
		
		values.put(DatabaseHelper.COLUMN_AMOUNT, e.amount);
		values.put(DatabaseHelper.COLUMN_SUBJECT, e.subject);
		values.put(DatabaseHelper.COLUMN_CREATED, dateFormat.format(date));
		
		long insertId = database.insert(DatabaseHelper.TABLE_EXPENSES, null, values);
		e.id = insertId;
		return e;
	}
	
	public int findTotal() {
		Cursor c = database.rawQuery("SELECT sum(" + DatabaseHelper.COLUMN_AMOUNT + ") FROM " + DatabaseHelper.TABLE_EXPENSES, null);
		if (c.moveToFirst()) {
			return c.getInt(0);
		}
		return 0;
	}
	
	public List<Expense> findAll() {
		List<Expense> expenses = new ArrayList<Expense>();
		
		Cursor cursor = database.query(
				DatabaseHelper.TABLE_EXPENSES,
				ALL_COLUMNS,
				null, null, null, null,
				DatabaseHelper.COLUMN_CREATED + " DESC");
		
		if (cursor == null)
			return null;
		if (cursor.getCount() == 0)
			return null;
		
		cursor.moveToFirst();
		
		do {
			Expense e = new Expense();
			e.amount = cursor.getInt(DatabaseHelper.COLUMN_AMOUNT_IDX);
			e.subject = cursor.getString(DatabaseHelper.COLUMN_SUBJECT_IDX);
			e.timestamp = cursor.getString(DatabaseHelper.COLUMN_CREATED_IDX);
			e.id = cursor.getLong(DatabaseHelper.COLUMN_ID_IDX);
			expenses.add(e);
		} while (cursor.moveToNext());
		
		cursor.close();
		return expenses;
	}
	
	public static String getAmountString(int amount) {
		boolean negative = false;
		String result = "";
		
		if (amount < 0)
			negative = true;
		amount = Math.abs(amount);
		
		if (amount < 10) {
			result = "0,0" + amount;
		} else if (amount < 100) {
			result = "0," + amount;
		} else {
			result = Integer.toString(amount);
	    	String beforePoint = result.substring(0, result.length()-2);
	    	String afterPoint = result.substring(result.length()-2);
	    	result = beforePoint + "," + afterPoint;
		}
		
		if (negative)
			result = "-" + result;
		
		return result;
	}
}
