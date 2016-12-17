package jajodia.aditya.com.bunkmanager2;

/**
 * Created by kunalsingh on 13/12/16.
 */

public class SubjectTable extends DbTable {

    public static final String TABLE_NAME = "SubjectsData";

    public  interface Colums{

        String ID = " _id";
        String SUBJECT = "SubjectName";
        String STATUS = " Status";
        String TOTAL = " Total";
        String PRESENT = " Present";


    }

    public static final String CREATE_TABLE_CMD =
            "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                    LBR+
                    Colums.ID+TYPE_INT_PK_AI+COMMA+
                    Colums.SUBJECT+TYPE_TEXT+COMMA+
                    Colums.STATUS+TYPE_INT+COMMA+
                    Colums.TOTAL+TYPE_INT+COMMA+
                    Colums.PRESENT+TYPE_INT+
                    RBR+" ;";


}
