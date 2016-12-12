package jajodia.aditya.com.bunkmanager2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TotalInfo extends AppCompatActivity {

    private static final String TAG = "TotalInfo";
    RelativeLayout li;
    String subjects[];
    //   MainActivity ma = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_info);

        final int size = MainActivity.getsize();
      //  Log.d(TAG,"size"+size);// no. of subjects
        String subject = MainActivity.getSubjects(); // subjects as a String separated by $
         subjects = new String[size];
        int l = subject.length();
        int j=0;
        int k=0;
       // Log.d(TAG,subject);
        for(int i=0;i<l;i++){
            Log.d(TAG,"SEE"+" "+i);
            if(subject.charAt(i)=='$'){
                subjects[k++] = subject.substring(j,i);
                j=i+1;
                Log.d(TAG,"string came"+" " +subjects[k-1]);

            }
        }
        li = (RelativeLayout)findViewById(R.id.activity_total_info);
        RelativeLayout.LayoutParams params[] = new RelativeLayout.LayoutParams[size];
        final Button btn[] = new Button[size];
        for(int i=0;i<size;i++){
         //   Log.d(TAG,"sss"+" " +i);
            btn[i] = new Button(this);
            btn[i].setId((i+1)*4);
            params[i] = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(i%3==0&&i>0){
           //    Log.d(TAG,"came "+" "+i);
                params[i].addRule(RelativeLayout.BELOW,btn[i-3].getId());

            }
        else if(i>0&&i>3){
                params[i].addRule(RelativeLayout.RIGHT_OF,btn[i-1].getId());
                params[i].addRule(RelativeLayout.BELOW,btn[i-3].getId());
            }
            else if(i>0){
                params[i].addRule(RelativeLayout.RIGHT_OF,btn[i-1].getId());
            }
            else{
                params[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            }
            btn[i].setText(subjects[i]);
            btn[i].setLayoutParams(params[i]);
            li.addView(btn[i],params[i]);
            final int finalI = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fragmentManager = getSupportFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               ButtonFragment fragment = new ButtonFragment();
               fragment.setSubject(subjects[finalI]);
             //   Log.d(TAG,"button called"+" "+finalI);
               fragmentTransaction.replace(R.id.frame_layout,fragment,null);
               fragmentTransaction.commit();
           }
       });
        }





        // adding fragments for each buttons
        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW,btn[size-1].getId());
        frameLayout.setLayoutParams(layoutParams);
        if(frameLayout.getParent()!=null){
            ((ViewGroup)frameLayout.getParent()).removeView(frameLayout);
        }
        li.addView(frameLayout);





    }


}
