package jajodia.aditya.com.bunkmanager2;

import android.animation.Animator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by kunalsingh on 12/12/16.
 */

public class ButtonFragment extends Fragment {

    private static final String TAG ="ButtonFragment" ;
    View view1;
    public ButtonFragment() {

    }
        String subject;
    public  void setSubject(String subject){
        this.subject = subject;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int cx = view1.getWidth()/2;
        int cy = view1.getHeight()/2;

        float finalRadius = (float) Math.hypot(cx,cy);

        final Animator animator = ViewAnimationUtils.createCircularReveal(view1,cx,cy,0,finalRadius);
        animator.start();
        }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view1 = inflater.inflate(R.layout.button_fragment,container,false);
        TextView tv = (TextView)view1.findViewById(R.id.tv_subject);
        tv.setText(subject);
        Button btn = (Button)view1.findViewById(R.id.btn_sub_done);
        final EditText etTotal = (EditText) view1.findViewById(R.id.et_total);
        final EditText etPresent = (EditText)view1.findViewById(R.id.et_present);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(String.valueOf(etTotal.getText()).isEmpty()||String.valueOf(etPresent.getText()).isEmpty()){
                    Toast.makeText(getActivity(), "Please enter total and present", Toast.LENGTH_SHORT).show();
                }else {
                    int total = Integer.parseInt(String.valueOf(etTotal.getText()));
                    int present = Integer.parseInt(String.valueOf(etPresent.getText()));

                    long t = DatabaseOpenHelper.insertData(getActivity(), subject, total, present);
                    Log.d(TAG, "value : " + t);



                    FragmentManager manager = getFragmentManager();
                    ButtonFragmentTwo bt = new ButtonFragmentTwo();
                    bt.setSubject(subject);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frame_layout, bt, null);
                    transaction.commit();

                }
                }
        });
        return view1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}
