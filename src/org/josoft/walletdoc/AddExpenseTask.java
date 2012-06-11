package org.josoft.walletdoc;

import android.os.AsyncTask;

public class AddExpenseTask extends AsyncTask<Void, Void, Void> {
	protected ExpensesDatasource mDatasource;
	protected ExpenseAddedListener mListener;
	protected Expense mExpense;
	
	public AddExpenseTask (ExpensesDatasource datasource, Expense expense, ExpenseAddedListener listener) {
		mDatasource = datasource;
		mListener = listener;
		mExpense = expense;
	}
	
	protected Void doInBackground(Void... arg0) {
		mExpense = mDatasource.createExpense(mExpense);
		return null;
    }
	
	 @Override
    protected void onPostExecute(Void result) {
        //mListener.onExpenseAdded(mExpense);
    }

	public interface ExpenseAddedListener {
		public void onExpenseAdded(Expense e);
	}
}
