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
        setContentView(R.layout.activity_total_info);

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
        li = (RelativeLayout)findViewById(R.id.activity_total_info);
        RelativeLayout.LayoutParams params[] = new RelativeLayout.LayoutParams[size];
        Button btn[] = new Button[size];
        for(int i=0;i<size;i++){
            btn[i] = new Button(this);
            btn[i].setId((i+1)*4);
            params[i] = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(i%3==0&&i>0){
               Log.d(TAG,"came "+" "+i);
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
        }
    }
}
