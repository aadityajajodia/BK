package jajodia.aditya.com.bunkmanager2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by kunalsingh on 12/12/16.
 */

public class ButtonFragment extends Fragment {

    View view;
    public ButtonFragment() {

    }
        String subject;
    public  void setSubject(String subject){
        this.subject = subject;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout rl = new RelativeLayout(getActivity());
        TextView tv = new TextView(getActivity());
        tv.setText(subject);
        rl.addView(tv);
        view = rl;
        return view;
    }
}
