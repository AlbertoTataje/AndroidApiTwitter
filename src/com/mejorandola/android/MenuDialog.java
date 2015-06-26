package com.mejorandola.android;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MenuDialog extends DialogFragment implements View.OnClickListener{

	
	private Button buscar;
	private Button cancelar;
	Communicator communicator;
	private EditText editext;
	private String tweetSearch=null;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		communicator = (Communicator) activity;
	}
	 
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View  view = inflater.inflate(R.layout.dialog_tweet,null);
		buscar = (Button) view.findViewById(R.id.button1);
		cancelar = (Button) view.findViewById(R.id.button2);
		editext = (EditText)view.findViewById(R.id.editText1);
		buscar.setOnClickListener(this);
		cancelar.setOnClickListener(this);
		setCancelable(false);
		return view;
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.button1){
			tweetSearch = editext.getText().toString();
			if(tweetSearch!=null){
			communicator.onDialogMessage(tweetSearch);
			}
			dismiss();
		}else if(view.getId() == R.id.button2){
			
			dismiss();
		}
		
	}
	
     interface Communicator
	{
		public void onDialogMessage(String tweetSearch);
	}
	
}
