package tylerwhitlock.capstone_prototype;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Activity for Translation Input -
 *
 * Will accept either spoken input or typed input.
 */
public class Prototype_Translation_Activity extends AppCompatActivity {


    protected static final int RESULT_SPEECH = 1;

    /**
     *  Important Page Elements
     */
    private ImageButton mSpeechButton;
    private TextView mTextView;
    private EditText mEditTextView;


    /**
     * On Create Method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Create instance
        super.onCreate(savedInstanceState);

        //Set View
        setContentView(R.layout.activity_prototype_translation);

        //Find Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Set Action Bar
        setSupportActionBar(toolbar);

        //Find and Initialize Important Views on Current Page
        mSpeechButton = (ImageButton) findViewById(R.id.speak_Button);
        mTextView = (TextView) findViewById(R.id.txtText);
        mEditTextView = (EditText) findViewById(R.id.entered_text);

        //Set On Click Event Listener for Speech To Text Activity Button
        mSpeechButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Define On Click Event for Speech Button
             * @param v
             */
            @Override
            public void onClick(View v) {

                //Create Speech To Text Recognition Intent
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                //Set the Language Desired for Speech Recognition
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");


                //@IMPORTANT: This feature does not work for old android devices without internet connections
                //            Will require installation of a package to allow offline translation
                try {
                    //Try to start activity
                    startActivityForResult(intent, RESULT_SPEECH);
                    //Clear Text for current view
                    mTextView.setText("");
                } catch (ActivityNotFoundException a) {

                    /**
                     * If this occurs Check Google Speech Packages on Phone
                     * Download Offline Packages if old device
                     *
                     */
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Oops! Your device doesn't support Speech to Text, try enabling WiFi",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

    }

    /**
     * Create Menu Method
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prototype__initial__screen, menu);
        return true;
    }

    /**
     * On Completion of Speech To Text Activity
     *
     * Load information into TextViews for correction / verification
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            /**
             * Set View text to string from Speech to Text Activity if not null
             */
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //Set both Text-based Views to the same text
                    mTextView.setText(text.get(0));
                    mEditTextView.setText(text.get(0));
                }
                break;
            }
        }
    }

    /**
     * Get Translation from Server
     *
     * Performs a post request to the hardcoded URL
     * Receives Sign / translation information from the server
     * @TODO : url field will need to be pointed to your server
     * @param v
     */
    public void getTranslation(View v) {

        /**
         * Create Background thread for Asynchronous Requests
         */
        new Thread(new Runnable() {
            public void run() {

                try {
                    /**
                     * @TODO Change url field to point to your local server as shown below
                     * ----------------------------------------------------------------------------
                     * URL url = new URL("http://10.0.1.2:8080/English-ASL_prototype/English_ASL");
                     * ----------------------------------------------------------------------------
                     */

                    //Local Connection Point
                    URL url = new URL("http://10.0.1.6:8080/English-ASL_prototype/English_ASL");

                    //Create Connection and Open
                    URLConnection connection = url.openConnection();

                    //Get Input String from Edit Text
                    String inputString = mEditTextView.getText().toString();

                    //Log the Input String
                    Log.d("inputString", inputString);

                    //Set up Output
                    connection.setDoOutput(true);

                    //Create Output Stream with Connection
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

                    //Write String to Output
                    out.write(inputString);

                    //Close Stream
                    out.close();

                    //Create Buffer Reader for Response from Server
                    final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    //Initialize return String to prevent null ptr exceptions if connection fails.
                    String returnString = "";

                    //Get Return String from Server Response
                    if ((returnString = in.readLine()) != null){

                        //Put Response into return String
                        while(in.readLine() !=null){
                            returnString+=in.readLine();
                        }

                        //Get final Return String and Parse
                        final String finalReturnString = returnString;

                        //Create thread to Parse the Response String
                        runOnUiThread(new Runnable() {
                            public void run() {

                                //Create Post Translation Intent
                                Intent intent = new Intent(Prototype_Translation_Activity.this, Prototype_Post_Translation_Response.class);

                                //Get List of Signs Returned by Server  ** Important ** Escape Reserved RegExp Symbol '|'
                                String[] responseSigns = finalReturnString.split("\\|");

                                //Create List of URLs and Lemmas
                                ArrayList<String> urls = new ArrayList<>();
                                ArrayList<String> lemmas = new ArrayList<String>();

                                //parse the url and the lemma
                                for(String sign : responseSigns){
                                    //Lemmas and URLS are separated by " "
                                    //Split on this feature and add Lemma and URL to Lists
                                    lemmas.add(sign.substring(0, sign.indexOf(" ")));
                                    urls.add(sign.substring(sign.indexOf(" "), sign.length()));

                                }

                                //Put Lemmas, URLS and Initial String into the Intent's bundle
                                intent.putExtra("returnString", finalReturnString); // @TODO Re-evaluate need for this
                                //Original Sentence
                                intent.putExtra("initialString", mEditTextView.getText().toString().trim());
                                //List of URLs
                                intent.putExtra("urls",urls);
                                //List of Lemmas
                                intent.putExtra("lemmas", lemmas);

                                //Start New Post Translation Activity
                                startActivity(intent);
                            }
                        });
                    }
                    in.close();
                    //Catch and log Exceptions
                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
            }
        }).start();
    }

}
