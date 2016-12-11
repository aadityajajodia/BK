package jajodia.aditya.com.bunkmanager2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TotalInfo extends AppCompatActivity {

    private static final String TAG = "TotalInfo";
    RelativeLayout li;
    //   MainActivity ma = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(li);

        int size = MainActivity.getsize();
        Log.d(TAG,"size"+size);// no. of subjects
        String subject = MainActivity.getSubjects(); // subjects as a String separated by $
        String subjects[] = new String[size];
        int l = subject.length();
        int j=0;
        int k=0;
        for(int i=0;i<l;i++){
            if(subject.charAt(i)=='$'){
                subjects[k++] = subject.substring(j,i);
                j=i+1;
            }
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btn[] = new Button[size];
        for(int i=0;i<size;i++){
            btn[i] = new Button(this);
            //Log.d(TAG,"size :"+size);
            btn[i].setText(subjects[i]);
            btn[i].setId(i);
           /* if(i%3==0&&i>0){
                Log.d(TAG,"came in loop 1");
                params.addRule(RelativeLayout.BELOW,btn[i-3].getId());
            }
             else if(i>0){
                Log.d(TAG,"came in loop 2");
                 params.addRule(RelativeLayout.RIGHT_OF,btn[i-1].getId());

             }*/
            if(i>0){
                params.addRule(RelativeLayout.BELOW,btn[i-1].getId());
            }
            btn[i].setLayoutParams(params);
            li.addView(btn[i],params);
           setContentView(li);

            //btn[i].setLayoutParams(params);
        }
    }
}
