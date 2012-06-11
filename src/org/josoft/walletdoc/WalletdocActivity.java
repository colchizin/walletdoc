package org.josoft.walletdoc;

import java.util.List;

import org.josoft.walletdoc.AddExpenseTask.ExpenseAddedListener;
import org.josoft.walletdoc.RetrieveExpensesTask.ExpensesRetrievedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WalletdocActivity extends Activity implements ExpensesRetrievedListener, ExpenseAddedListener {
    /** Called when the activity is first created. */
	
	public static final int DIALOG_ADD_EXPENSE = 1;
	
	public static final String TAG = "WalletdocActivity";
	
	protected ListView mListview;
	protected Button mButtonAdd;
	protected Button mButtonRemove;
	
	protected List<Expense> mExpenses;
	protected ExpensesDatasource mDatasource;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatasource = new ExpensesDatasource(this);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        mListview = ((ListView)(this.findViewById(R.id.listview_expenses)));
        mButtonAdd = ((Button)(this.findViewById(R.id.button_add)));
        mButtonAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddExpenseDialog dialog = new AddExpenseDialog(WalletdocActivity.this, WalletdocActivity.this);
				dialog.show();
			}
        });
        
        // DatabaseHelper helper = DatabaseHelper.getHelper(this, false);
        // helper.onUpgrade(DatabaseHelper.getDatabase(this, false),1,1);
        
        (new RetrieveExpensesTask(mDatasource, this)).execute();
    }
    
    protected void getTotal() {
    	TextView tvTotal = ((TextView)(this.findViewById(R.id.tvTotal)));
    	int total = mDatasource.findTotal();
    	
    	
    	if (total < 0)
    		tvTotal.setTextColor(this.getResources().getColor(R.color.red));
    	else
    		tvTotal.setTextColor(this.getResources().getColor(android.R.color.primary_text_dark));
    	
    	tvTotal.setText(ExpensesDatasource.getAmountString(total) + "€");
    }

	@Override
	public void onExpensesRetrieved(List<Expense> expenses) {
		mExpenses = expenses;
		mListview.setAdapter(new ExpensesAdapter(this, mExpenses));
		getTotal();
	}
	
	public void onExpenseAdded(Expense e) {
		mListview.setAdapter(new ExpensesAdapter(this, mExpenses));
		mListview.invalidate();
		getTotal();
	}
}