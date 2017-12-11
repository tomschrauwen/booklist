package com.example.tom.mybookslist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConfirmDeleteDialog extends DialogFragment {

   /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */

    public interface ConfirmDeleteDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    ConfirmDeleteDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the ConfirmDeleteDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ConfirmDeleteDialogListener so we can send events to the host
            mListener = (ConfirmDeleteDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ConfirmDeleteDialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_book_deletion_confirmation)
                .setPositiveButton(R.string.delete_book, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        //activate method onDialogPositiveClick inside implementing class
                        mListener.onDialogPositiveClick(ConfirmDeleteDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        //activate method onDialogNegativeClick inside implementing class
                        mListener.onDialogNegativeClick(ConfirmDeleteDialog.this);
                    }
                });
        return builder.create();
    }
}

