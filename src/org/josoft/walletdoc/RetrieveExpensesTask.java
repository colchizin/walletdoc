package org.josoft.walletdoc;

import java.util.List;

import android.os.AsyncTask;

public class RetrieveExpensesTask extends AsyncTask<Void, Void, Void> {
	protected ExpensesDatasource mDatasource;
	protected ExpensesRetrievedListener mListener;
	protected List<Expense> mExpenses;
	
	public RetrieveExpensesTask (ExpensesDatasource datasource, ExpensesRetrievedListener listener) {
		mDatasource = datasource;
		mListener = listener;
	}
	
	protected Void doInBackground(Void... arg0) {
		mExpenses = mDatasource.findAll();
		return null;
    }
	
	 @Override
    protected void onPostExecute(Void result) {
        mListener.onExpensesRetrieved(mExpenses);
    }

	public interface ExpensesRetrievedListener {
		public void onExpensesRetrieved(List<Expense> expenses);
	}
}
