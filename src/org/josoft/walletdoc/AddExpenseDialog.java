package org.josoft.walletdoc;
import org.josoft.walletdoc.AddExpenseTask.ExpenseAddedListener;
import org.josoft.walletdoc.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class AddExpenseDialog extends Dialog {
	protected EditText mEditSubject;
	protected EditText mEditAmount;
	protected ExpenseAddedListener mListener;
	protected Spinner mSpinnerType;
	
	public AddExpenseDialog(Context context, ExpenseAddedListener listener) {
		super(context);
		this.mListener = listener;
		this.setContentView(R.layout.add_expense_dialog);
		this.setTitle(R.string.add_expense);
		
		mEditSubject = ((EditText)(this.findViewById(R.id.etSubject)));
		mEditAmount = ((EditText)(this.findViewById(R.id.etAmount)));
		mSpinnerType = ((Spinner)(this.findViewById(R.id.spType)));
		
		Button b = ((Button)(this.findViewById(R.id.button_add)));
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Expense e = new Expense();
				String strAmount = AddExpenseDialog.this.mEditAmount.getText().toString();
				String strSubject = AddExpenseDialog.this.mEditSubject.getText().toString();
				
				e.amount = Integer.parseInt(strAmount);
				if (AddExpenseDialog.this.mSpinnerType.getSelectedItemPosition() == 0)
					e.amount *= -1;
				
				e.subject = strSubject;	
				e = (new ExpensesDatasource(AddExpenseDialog.this.getContext())).createExpense(e);
				AddExpenseDialog.this.mListener.onExpenseAdded(e);
				
				/*(new AddExpenseTask(
						new ExpensesDatasource(
								AddExpenseDialog.this.getContext()
						),
						e,
						AddExpenseDialog.this.mListener)
				).execute();*/
				AddExpenseDialog.this.dismiss();
			}
		});
	}

}
