package jajodia.aditya.com.bunkmanager2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by kunalsingh on 13/12/16.
 */

public class ButtonFragmentTwo extends Fragment {

    private static final String TAG = "ButtonFragmentTwo";

    public ButtonFragmentTwo() {
    }
    String subject;

    public void setSubject(String subject){
        this.subject = subject;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.button_fragment_two,container,false);

        TextView sub = (TextView)view.findViewById(R.id.tv_subject_two);
        TextView pre = (TextView)view.findViewById(R.id.tv_present_two);
        TextView tot = (TextView)view.findViewById(R.id.tv_total_two);

        sub.setText(subject);

        Cursor c = DatabaseOpenHelper.readData(getActivity(),subject);
        c.moveToFirst();
        Log.d(TAG,"s"+"  " +c.getColumnName(0)+" "+c.getColumnName(1)+" "+c.getColumnName(2)+" "+c.getColumnName(3));
        tot.setText(String.valueOf(c.getInt(2)));
        pre.setText(String.valueOf(c.getInt(3)));
        c.close();
        return view;
    }
}

