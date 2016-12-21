package jajodia.aditya.com.bunkmanager2;

/**
 * Created by kunalsingh on 21/12/16.
 */

public class WeekPeriodTable extends DbTable{


    public static final String TABLE_NAME= "WeekDay";


    public interface Columns{

        String ID = "_id";
        String DAY = "day";
        String PERIOD_ONE = "period_one";
        String PERIOD_TWO = "period_two";
        String PERIOD_THREE = "period_three";
        String PERIOD_FOUR = "period_four";
        String PERIOD_FIVE = "period_five";
        String PERIOD_SIX = "period_six";
        String PERIOD_SEVEN = "period_seven";
        String PERIOD_EIGHT = "period_eight";

    }

    public static final String CREATE_TABLE_CMD=" CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                                                LBR+
                                                Columns.ID+TYPE_INT_PK_AI+COMMA+
                                                Columns.DAY+TYPE_INT+COMMA+
                                                Columns.PERIOD_ONE+TYPE_INT+COMMA+
                                                Columns.PERIOD_TWO+TYPE_INT+COMMA+
                                                Columns.PERIOD_THREE+TYPE_INT+COMMA+
                                                Columns.PERIOD_FOUR+TYPE_INT+COMMA+
                                                Columns.PERIOD_FIVE+TYPE_INT+COMMA+
                                                Columns.PERIOD_SIX+TYPE_INT+COMMA+
                                                Columns.PERIOD_SEVEN+TYPE_INT+COMMA+
                                                Columns.PERIOD_EIGHT+TYPE_INT+
                                                RBR+" ;";
}
