package jajodia.aditya.com.tickernotify;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by kunalsingh on 20/12/16.
 */

public class DialogFragmentOne extends DialogFragment {

    public DialogFragmentOne() {
    }

     public static DialogFragmentOne newInstance() {

        Bundle args = new Bundle();

        DialogFragmentOne fragment = new DialogFragmentOne();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select");

        builder.setItems(TotalInfo.getSubjects(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

       // builder.setPositiveButton("YZES",null);

        return builder.create();
    }
}
