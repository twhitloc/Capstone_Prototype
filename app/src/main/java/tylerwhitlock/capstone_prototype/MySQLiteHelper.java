package tylerwhitlock.capstone_prototype;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tylerwhitlock on 2/11/16.
 */


/**
 * Adapter Class for Sign Letters
 * SIGN LETTER CLASS DEPRECATED
 * No Longer in Use
 *
 */
@Deprecated
public class MySQLiteHelper extends SQLiteOpenHelper {


    private static final String TAG = "SQLiteHelper";

    private static boolean mInbuiltRowsAdded = false;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "SignDB";


    /**
     * Constructor
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Constructor
     * @param context
     */
    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * On Create Method
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create preferences and user
        db.execSQL(SignLetter.CREATE_SIGNLETTER_TABLE);
    }

    /**
     * On Database Upgrade
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if exist
        db.execSQL("DROP TABLE IF EXISTS " + SignLetter.TABLE_NAME);

        // Create fresh tables
        this.onCreate(db);
    }

    /**
     * Add Sign Letter To Database
     * @param signLetter
     */
    public void addSignLetter(SignLetter signLetter){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SignLetter.TEXT_VALUE_COLUMN, signLetter.getmTextValue());
        values.put(SignLetter.URI_SOURCE_COLUMN, signLetter.getmSource().toString());
        values.put(SignLetter.RESOURCE_STRING_COLUMN, signLetter.getmResourceString());
        values.put(SignLetter.RESOURCE_ID_COLUMN, signLetter.getmResourceID());

        db.insert(SignLetter.TABLE_NAME, null, values);

        db.close();
    }


    /**
     * Get Sign Letter from Database
     * @param keyTextValue
     * @return Sign Letter
     */
    public SignLetter getSignLetter(String keyTextValue){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(SignLetter.TABLE_NAME,
                        SignLetter.TABLE_COLUMNS,
                        "textValue = ?",
                        new String[]{keyTextValue},
                        null,
                        null,
                        null,
                        null);

        SignLetter signLetter = null;

        if( cursor != null && cursor.moveToFirst()){
            cursor.moveToFirst();
        }
        else
            return null;

        do{
            if(cursor!=null){
                signLetter = new SignLetter();
                signLetter.setmTextValue(cursor.getString(0));
                signLetter.setmSource(cursor.getString(1));
                signLetter.setmResourceString(cursor.getString(2));
                signLetter.setmResourceID(cursor.getInt(3));
            }

        }while(cursor.moveToNext());

        return signLetter;
    }

}
