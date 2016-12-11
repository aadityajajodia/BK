package jajodia.aditya.com.bunkmanager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

EditText timeTable[][] = new EditText[6][8];
    HashMap<String,Integer> map = new HashMap<>(); // no. of periods of the given subject
    String periods [] [] = new String[6][8]; // actual period
    Button btn;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn_done);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignTable();
                readTable();
                size = map.size();
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("You hav a total of"+" "+size+" "+"subjects");
              //  builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            builder.create();
                builder.show();
            }
        });

    }

    public void assignTable(){

        timeTable[0][0] = (EditText)findViewById(R.id.tr_one_et_one);
        timeTable[0][1] = (EditText)findViewById(R.id.tr_one_et_two);
        timeTable[0][2] = (EditText)findViewById(R.id.tr_one_et_three);
        timeTable[0][3] = (EditText)findViewById(R.id.tr_one_et_four);
        timeTable[0][4] = (EditText)findViewById(R.id.tr_one_et_five);
        timeTable[0][5] = (EditText)findViewById(R.id.tr_one_et_six);
        timeTable[0][6] = (EditText)findViewById(R.id.tr_one_et_seven);
        timeTable[0][7] = (EditText)findViewById(R.id.tr_one_et_eight);
        timeTable[1][0] = (EditText)findViewById(R.id.tr_two_et_one);
        timeTable[1][1] = (EditText)findViewById(R.id.tr_two_et_two);
        timeTable[1][2] = (EditText)findViewById(R.id.tr_two_et_three);
        timeTable[1][3] = (EditText)findViewById(R.id.tr_two_et_four);
        timeTable[1][4] = (EditText)findViewById(R.id.tr_two_et_five);
        timeTable[1][5] = (EditText)findViewById(R.id.tr_two_et_six);
        timeTable[1][6] = (EditText)findViewById(R.id.tr_two_et_seven);
        timeTable[1][7] = (EditText)findViewById(R.id.tr_two_et_eight);
        timeTable[2][0] = (EditText)findViewById(R.id.tr_three_et_one);
        timeTable[2][1] = (EditText)findViewById(R.id.tr_three_et_two);
        timeTable[2][2] = (EditText)findViewById(R.id.tr_three_et_three);
        timeTable[2][3] = (EditText)findViewById(R.id.tr_three_et_four);
        timeTable[2][4] = (EditText)findViewById(R.id.tr_three_et_five);
        timeTable[2][5] = (EditText)findViewById(R.id.tr_three_et_six);
        timeTable[2][6] = (EditText)findViewById(R.id.tr_three_et_seven);
        timeTable[2][7] = (EditText)findViewById(R.id.tr_three_et_eight);
        timeTable[3][0] = (EditText)findViewById(R.id.tr_four_et_one);
        timeTable[3][1] = (EditText)findViewById(R.id.tr_four_et_two);
        timeTable[3][2] = (EditText)findViewById(R.id.tr_four_et_three);
        timeTable[3][3] = (EditText)findViewById(R.id.tr_four_et_four);
        timeTable[3][4] = (EditText)findViewById(R.id.tr_four_et_five);
        timeTable[3][5] = (EditText)findViewById(R.id.tr_four_et_six);
        timeTable[3][6] = (EditText)findViewById(R.id.tr_four_et_seven);
        timeTable[3][7] = (EditText)findViewById(R.id.tr_four_et_eight);
        timeTable[4][0] = (EditText)findViewById(R.id.tr_five_et_one);
        timeTable[4][1] = (EditText)findViewById(R.id.tr_five_et_two);
        timeTable[4][2] = (EditText)findViewById(R.id.tr_five_et_three);
        timeTable[4][3] = (EditText)findViewById(R.id.tr_five_et_four);
        timeTable[4][4] = (EditText)findViewById(R.id.tr_five_et_five);
        timeTable[4][5] = (EditText)findViewById(R.id.tr_five_et_six);
        timeTable[4][6] = (EditText)findViewById(R.id.tr_five_et_seven);
        timeTable[4][7] = (EditText)findViewById(R.id.tr_five_et_eight);
        timeTable[5][0] = (EditText)findViewById(R.id.tr_six_et_one);
        timeTable[5][1] = (EditText)findViewById(R.id.tr_six_et_two);
        timeTable[5][2] = (EditText)findViewById(R.id.tr_six_et_three);
        timeTable[5][3] = (EditText)findViewById(R.id.tr_six_et_four);
        timeTable[5][4] = (EditText)findViewById(R.id.tr_six_et_five);
        timeTable[5][5] = (EditText)findViewById(R.id.tr_six_et_six);
        timeTable[5][6] = (EditText)findViewById(R.id.tr_six_et_seven);
        timeTable[5][7] = (EditText)findViewById(R.id.tr_six_et_eight);

    }

    public void readTable() {

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                String s = String.valueOf(timeTable[i][j].getText());
                if (map.containsKey(s)) {

                    int d = map.get(s);
                    map.remove(s);
                    map.put(s, ++d);
                } else if (!s.equals("")) {
                    map.put(s, 1);

                }
                periods[i][j] = s;
            }
        }
    }

  public static int returnsize(){
        return 0;
    }

    }


