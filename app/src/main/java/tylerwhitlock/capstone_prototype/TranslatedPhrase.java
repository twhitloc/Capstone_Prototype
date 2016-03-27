package tylerwhitlock.capstone_prototype;

/**
 * Created by tylerwhitlock on 3/27/16.
 */
public class TranslatedPhrase {
    //
    public static final String TABLE_NAME = "translatedPhrase_length*";
    public static final String ENGLISH_COLUMN = "englishPhrase";

    public static final String ASL_COLUMN = "aslPhrase";
    //
    public static final String[] TABLE_COLUMNS = { ENGLISH_COLUMN, ASL_COLUMN };
    //
    public static final String CREATE_TRANSLATED_PHRASE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ENGLISH_COLUMN
            + " varchar(255) not NULL, " + ASL_COLUMN + " varchar(255) not NULL , PRIMARY KEY (" + ENGLISH_COLUMN
            + "))";

    //
    private String englishPhrase;

    //
    private String aslPhrase;

    /**
     *
     */
    public TranslatedPhrase() {
        englishPhrase = "";
        aslPhrase = "";
    }

    /**
     *
     * @param english
     * @param asl
     */
    public TranslatedPhrase(String english, String asl) {
        englishPhrase = english;
        aslPhrase = asl;
    }

    /**
     *
     * @return
     */
    public String getASLPhrase() {
        return aslPhrase;
    }

    /**
     *
     * @return
     */
    public String getEnglishPhrase() {
        return englishPhrase;
    }

    /**
     *
     * @param phrase
     */
    public void setASLPhrase(String phrase) {
        aslPhrase = phrase;
    }

    /**
     *
     * @param phrase
     */
    public void setEnglishPhrase(String phrase) {
        englishPhrase = phrase;
    }
}
