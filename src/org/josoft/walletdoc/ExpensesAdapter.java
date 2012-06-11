package org.josoft.walletdoc;

import java.util.List;
import java.util.Locale;

import org.josoft.walletdoc.R.color;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ExpensesAdapter extends BaseAdapter {

	protected List<Expense> mExpenses;
	protected Activity mActivity;
	
	public ExpensesAdapter(Activity activity, List<Expense> expenses) {
		mExpenses = expenses;
		mActivity = activity;
	}
	
	@Override
	public int getCount() {
		if (mExpenses != null)
			return mExpenses.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mExpenses != null)
			return mExpenses.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (mExpenses != null)
			return mExpenses.get(position).id;
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.expenses_list_item, null);
		}
		
		Expense expense = mExpenses.get(position);
		
		TextView tvAmount = ((TextView)(convertView.findViewById(R.id.textview_amount)));
		TextView tvSubject = ((TextView)(convertView.findViewById(R.id.textview_subject)));
		TextView tvTimestamp = ((TextView)(convertView.findViewById(R.id.textview_timestamp)));
		
		Button btnRemove = ((Button)(convertView.findViewById(R.id.button_remove)));
	    btnRemove.setOnClickListener(new OnRemoveClickedListener(mActivity, expense));
		
		String amount = Integer.toString(expense.amount);
		String beforePoint = amount.substring(0, amount.length()-2);
		String afterPoint = amount.substring(amount.length()-2);
		amount = beforePoint + "," + afterPoint + "€";
		
		if (expense.amount<0)
			tvAmount.setTextColor(mActivity.getResources().getColor(R.color.red));
		else
			tvAmount.setTextColor(mActivity.getResources().getColor(R.color.white));
		
		tvAmount.setText(amount);
		tvSubject.setText(expense.subject);
		tvTimestamp.setText(expense.timestamp);
		
		return convertView;
	}
	
	public class OnRemoveClickedListener implements OnClickListener{
		protected Expense mExpense;
		protected Context mContext;
		
		public OnRemoveClickedListener(Context context, Expense e) {
			this.mExpense = e;
			this.mContext = context;
		}

		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder
				.setMessage(R.string.message_confirm_delete)
				.setTitle(R.string.title_confirm_delete)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ExpensesDatasource source = new ExpensesDatasource(OnRemoveClickedListener.this.mContext);
						source.removeExpense(mExpense);
						ExpensesAdapter.this.notifyDataSetChanged();
						dialog.dismiss();
					}
				})
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

}
