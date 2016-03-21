package tylerwhitlock.capstone_prototype;

import android.media.Image;

/**
 * Created by tylerwhitlock on 2/10/16.
 */

/**
 * Sign Letter Class ------- Deprecated
 * ----------------------------------------------------
 *  mSignImage      =       Image of Sign Letter - Resource in drawables
 *  mTextValue      =       The character / letter represented by SignLetter
 *  mSource         =       Source for Image
 *  mResourceID     =       Assigned ID for resource
 *  mResourceString =       Resource as String
 */
@Deprecated
public class SignLetter {


    private static final String TAG = "SignLetter";

    /**
     * Database Management Variables for use with SQLite operations
     */
    public static final String TABLE_NAME = "signLetter";
    public static final String TEXT_VALUE_COLUMN = "textValue";
    public static final String URI_SOURCE_COLUMN = "uriSource";
    public static final String RESOURCE_STRING_COLUMN = "resourceString";
    public static final String RESOURCE_ID_COLUMN = "resourceID";


    private Image mSignImage;
    private String mTextValue;
    private String mSource;
    private String mResourceString;
    private int mResourceID;


    /**
     *  Column Name array for SQLite Operations
     */
    public static final String[] TABLE_COLUMNS = {
            TEXT_VALUE_COLUMN,
            URI_SOURCE_COLUMN,
            RESOURCE_STRING_COLUMN,
            RESOURCE_ID_COLUMN,
    };

    /**
     *  Create Table String for SQLite Table Creation Command
     */
    public static final String CREATE_SIGNLETTER_TABLE = "CREATE TABLE " +
            SignLetter.TABLE_NAME  + " ( " +
            SignLetter.TEXT_VALUE_COLUMN + " TEXT PRIMARY KEY, " +
            SignLetter.URI_SOURCE_COLUMN + " TEXT, " +
            SignLetter.RESOURCE_STRING_COLUMN + " TEXT, " +
            SignLetter.RESOURCE_ID_COLUMN + " TEXT)";

    /**
     * Empty
     * Constructor
     */
    public SignLetter() {
        this.mSignImage = null;
        this.mTextValue = null;
        this.mSource = null;
        this.mResourceString = null;
    }

    /**
     * Constructor
     * @param mSignImage
     * @param mTextValue
     * @param mSource
     * @param mResourceString
     */
    public SignLetter(Image mSignImage, String mTextValue, String mSource, String mResourceString) {
        this.mSignImage = mSignImage;
        this.mTextValue = mTextValue;
        this.mSource = mSource;
        this.mResourceString = mResourceString;
    }

    /**
     * Constructor
     * @param mTextValue
     * @param mSource
     */
    public SignLetter(String mTextValue, String mSource) {
        this.mTextValue = mTextValue;
        this.mSource = mSource;
    }

    /**
     * Constructor
     * @param mSignImage
     * @param mTextValue
     */
    public SignLetter(Image mSignImage, String mTextValue) {
        this.mSignImage = mSignImage;
        this.mTextValue = mTextValue;
    }

    /**
     * Constructor
     * @param val
     */
    public SignLetter(String val)
    {
        this.mTextValue = val;
    }

    /**
     * Constructor
     * @param mSignImage
     */
    public SignLetter(Image mSignImage) {
        this.mSignImage = mSignImage;
    }

    /**
     * Get Sign Image
     * @return Sign Image
     */
    public Image getmSignImage() {
        return mSignImage;
    }

    /**
     * Get Text Value
     * @return  Text Value
     */
    public String getmTextValue() {
        return mTextValue;
    }


    /**
     * Get Resource String
     * @return  Resource String
     */
    public String getmResourceString() {
        return mResourceString;
    }

    /**
     * Get Resource ID
     * @return Resource ID
     */
    public int getmResourceID() {
        return mResourceID;
    }

    /**
     * Get Source
     * @return Source
     */
    public String getmSource() {
        return mSource;
    }

    /**
     * Set Sign Image
     * @param mSignImage
     */
    public void setmSignImage(Image mSignImage) {
        this.mSignImage = mSignImage;
    }

    /**
     * Set Text Value
     * @param mTextValue
     */
    public void setmTextValue(String mTextValue) {
        this.mTextValue = mTextValue;
    }

    /**
     * Set Resource ID
     * @param mResourceID
     */
    public void setmResourceID(int mResourceID) {
        this.mResourceID = mResourceID;
    }

    /**
     * Set Resource String
     * @param mResourceString
     */
    public void setmResourceString(String mResourceString) {
        this.mResourceString = mResourceString;
    }

    /**
     * Set Source
     * @param mSource
     */
    public void setmSource(String mSource) {
        this.mSource = mSource;
    }
}
