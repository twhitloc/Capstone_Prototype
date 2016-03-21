package tylerwhitlock.capstone_prototype;

import java.util.ArrayList;

/**
 * Created by tylerwhitlock on 3/12/16.
 */

/**
 *      ------------------------------------------------------------------
 *      Important Note: This class differs from the Server-Side Sign Class
 *      ------------------------------------------------------------------
 */

/**
 *  Sign Class
 *  ------------------------------------------------------------------------------------------------
 *  mLemmaValue          = word root
 *  mVideoUrl            = mp4 location
 *  mPageUrl             = more information about sign
 *  mPartOfSpeech        = noun, verb, etc
 *  mConnotationValue    = contextual meaning is implied with this sign
 *  mDependencies        = words that have a dependent relationship with this word, forming a clause
 */
public class Sign {
    String mLemmaValue;
    String mVideoUrl;
    String mPageUrl;
    String mConnotationValue;
    String mPartOfSpeech;
    ArrayList<String> dependencies;


    /**
     * Empty
     * Constructor
     */
    public Sign(){
        this.mLemmaValue = "";
        this.mVideoUrl = "";
        this.mPageUrl ="";
        this.mConnotationValue ="";
        this.mPartOfSpeech ="";
        this.dependencies = new ArrayList<>();
    }

    /**
     * Construct with 2 String parameters
     * @param mLemmaValue
     * @param mVideoUrl
     */
    public Sign(String mLemmaValue, String mVideoUrl){
        this.mLemmaValue = mLemmaValue;
        this.mVideoUrl = mVideoUrl;
        this.mPageUrl ="";
        this.mConnotationValue ="";
        this.mPartOfSpeech ="";
        this.dependencies = new ArrayList<>();
    }

    /**
     * Construct with 3 string parameters
     * @param mLemmaValue
     * @param mVideoUrl
     * @param mPageUrl
     */
    public Sign(String mLemmaValue, String mVideoUrl, String mPageUrl) {
        this.mLemmaValue = mLemmaValue;
        this.mVideoUrl = mVideoUrl;
        this.mPageUrl = mPageUrl;
        this.mConnotationValue ="";
        this.mPartOfSpeech ="";
        this.dependencies = new ArrayList<>();
    }

    /**
     * Construct with just the lemma value
     * @param mLemmaValue
     */
    public Sign(String mLemmaValue) {
        this.mLemmaValue = mLemmaValue;
    }


    /**
     * Get Lemma
     * @return Lemma
     */
    public String getmLemmaValue() {
        return mLemmaValue;
    }

    /**
     * Set Lemma
     * @param mLemmaValue
     */
    public void setmLemmaValue(String mLemmaValue) {
        this.mLemmaValue = mLemmaValue;
    }

    /**
     * Get Video Url
     * @return Video Url
     */
    public String getmVideoUrl() {
        return mVideoUrl;
    }

    /**
     * Set Video Url
     * @param mVideoUrl
     */
    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    /**
     * Get Page Url
     * @return Page Url
     */
    public String getmPageUrl() {
        return mPageUrl;
    }

    /**
     * set Page Url
     * @param mPageUrl
     */
    public void setmPageUrl(String mPageUrl) {
        this.mPageUrl = mPageUrl;
    }

    /**
     *
     * @return
     */
    public String getmConnotationValue() {
        return mConnotationValue;
    }

    /**
     * Set Connotation Value
     * @param mConnotationValue
     */
    public void setmConnotationValue(String mConnotationValue) {
        this.mConnotationValue = mConnotationValue;
    }

    /**
     * Get Part of Speech
     * @return Part of Speech
     */
    public String getmPartOfSpeech() {
        return mPartOfSpeech;
    }

    /**
     * Set Part of Speech
     * @param mPartOfSpeech
     */
    public void setmPartOfSpeech(String mPartOfSpeech) {
        this.mPartOfSpeech = mPartOfSpeech;
    }

    /**
     * Get Dependencies
     * @return Dependencies
     */
    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    /**
     * Set Dependencies
     * @param dependencies
     */
    public void setDependencies(ArrayList<String> dependencies) {
        this.dependencies = dependencies;
    }
}
