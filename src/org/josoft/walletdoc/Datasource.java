/**
 * 
 */
package org.josoft.walletdoc;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author johannes
 *
 */
public abstract class Datasource {
	DatabaseHelper helper	= null;
	SQLiteDatabase database		= null;
	Context mContext			= null;
	/*
	 * Konstruktor. Erstellt das MusicFileDBHelper-Objekt mit dem überebenen
	 * Kontext
	 * @param context	Kontext
	 */
	public Datasource(Context context) {
		helper = DatabaseHelper.getHelper(context, false);
		this.mContext = context;
	}
	
	/*
	 * Stellt eine Datenbankverbindung her
	 * @throws SQLException
	 */
	public void open() throws SQLException  {
		database = DatabaseHelper.getDatabase(mContext, false);
	}
	
	/*
	 * Schließt die Datenbankverbindung
	 */
	public void close() {
		helper.close();
	}	
}
