package jajodia.aditya.com.tickernotify;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewTimeTable extends FragmentActivity {

    TextView timeTable[][] = new TextView[6][8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_table);

        android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("Time Table");

        assignTable();

        setTextInTable();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home : onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void assignTable(){

        timeTable[0][0] = (TextView) findViewById(R.id.tt_tr_one_et_one);
        timeTable[0][1] = (TextView) findViewById(R.id.tt_tr_one_et_two);
        timeTable[0][2] = (TextView) findViewById(R.id.tt_tr_one_et_three);
        timeTable[0][3] = (TextView) findViewById(R.id.tt_tr_one_et_four);
        timeTable[0][4] = (TextView) findViewById(R.id.tt_tr_one_et_five);
        timeTable[0][5] = (TextView) findViewById(R.id.tt_tr_one_et_six);
        timeTable[0][6] = (TextView) findViewById(R.id.tt_tr_one_et_seven);
        timeTable[0][7] = (TextView) findViewById(R.id.tt_tr_one_et_eight);
        timeTable[1][0] = (TextView) findViewById(R.id.tt_tr_two_et_one);
        timeTable[1][1] = (TextView) findViewById(R.id.tt_tr_two_et_two);
        timeTable[1][2] = (TextView) findViewById(R.id.tt_tr_two_et_three);
        timeTable[1][3] = (TextView) findViewById(R.id.tt_tr_two_et_four);
        timeTable[1][4] = (TextView) findViewById(R.id.tt_tr_two_et_five);
        timeTable[1][5] = (TextView) findViewById(R.id.tt_tr_two_et_six);
        timeTable[1][6] = (TextView) findViewById(R.id.tt_tr_two_et_seven);
        timeTable[1][7] = (TextView) findViewById(R.id.tt_tr_two_et_eight);
        timeTable[2][0] = (TextView) findViewById(R.id.tt_tr_three_et_one);
        timeTable[2][1] = (TextView) findViewById(R.id.tt_tr_three_et_two);
        timeTable[2][2] = (TextView) findViewById(R.id.tt_tr_three_et_three);
        timeTable[2][3] = (TextView) findViewById(R.id.tt_tr_three_et_four);
        timeTable[2][4] = (TextView) findViewById(R.id.tt_tr_three_et_five);
        timeTable[2][5] = (TextView) findViewById(R.id.tt_tr_three_et_six);
        timeTable[2][6] = (TextView) findViewById(R.id.tt_tr_three_et_seven);
        timeTable[2][7] = (TextView) findViewById(R.id.tt_tr_three_et_eight);
        timeTable[3][0] = (TextView) findViewById(R.id.tt_tr_four_et_one);
        timeTable[3][1] = (TextView) findViewById(R.id.tt_tr_four_et_two);
        timeTable[3][2] = (TextView) findViewById(R.id.tt_tr_four_et_three);
        timeTable[3][3] = (TextView) findViewById(R.id.tt_tr_four_et_four);
        timeTable[3][4] = (TextView) findViewById(R.id.tt_tr_four_et_five);
        timeTable[3][5] = (TextView) findViewById(R.id.tt_tr_four_et_six);
        timeTable[3][6] = (TextView) findViewById(R.id.tt_tr_four_et_seven);
        timeTable[3][7] = (TextView) findViewById(R.id.tt_tr_four_et_eight);
        timeTable[4][0] = (TextView) findViewById(R.id.tt_tr_five_et_one);
        timeTable[4][1] = (TextView) findViewById(R.id.tt_tr_five_et_two);
        timeTable[4][2] = (TextView) findViewById(R.id.tt_tr_five_et_three);
        timeTable[4][3] = (TextView) findViewById(R.id.tt_tr_five_et_four);
        timeTable[4][4] = (TextView) findViewById(R.id.tt_tr_five_et_five);
        timeTable[4][5] = (TextView) findViewById(R.id.tt_tr_five_et_six);
        timeTable[4][6] = (TextView) findViewById(R.id.tt_tr_five_et_seven);
        timeTable[4][7] = (TextView) findViewById(R.id.tt_tr_five_et_eight);
        timeTable[5][0] = (TextView) findViewById(R.id.tt_tr_six_et_one);
        timeTable[5][1] = (TextView) findViewById(R.id.tt_tr_six_et_two);
        timeTable[5][2] = (TextView) findViewById(R.id.tt_tr_six_et_three);
        timeTable[5][3] = (TextView) findViewById(R.id.tt_tr_six_et_four);
        timeTable[5][4] = (TextView) findViewById(R.id.tt_tr_six_et_five);
        timeTable[5][5] = (TextView) findViewById(R.id.tt_tr_six_et_six);
        timeTable[5][6] = (TextView) findViewById(R.id.tt_tr_six_et_seven);
        timeTable[5][7] = (TextView) findViewById(R.id.tt_tr_six_et_eight);

    }

    public void setTextInTable(){

        for(int i=0;i<6;i++){

            Cursor cursor = DatabaseOpenHelperTwo.readData(this,i+1);
            cursor.moveToFirst();

            if(cursor.getCount()>0){

                for(int j=0;j<8;j++){
                    timeTable[i][j].setText(cursor.getString(j+1));
                }
            }


        }

    }

}
