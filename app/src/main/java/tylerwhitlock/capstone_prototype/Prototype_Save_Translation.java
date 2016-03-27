package tylerwhitlock.capstone_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * @TODO IMPLEMENT
 */
public class Prototype_Save_Translation extends AppCompatActivity {
    private EditText mASLEditTextView;
    private EditText mENGEditTextView;
    private boolean isDirty = true;
    //Create List of URLs and Lemmas
    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> lemmas = new ArrayList<>();
    private String returnString = "";
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prototype__save__translation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mASLEditTextView = (EditText) findViewById(R.id.asl_syntax);
        mENGEditTextView = (EditText) findViewById(R.id.std_english_syntax);

    }

    /**
     *
     */
    public void viewTranslationSigned(View v){
        if(isDirty != true){
            if(urls.size() != 0 && lemmas.size() !=0) {
                //Create Post Translation Intent
                Intent intent = new Intent(Prototype_Save_Translation.this, Prototype_Post_Translation_Response.class);

                //Put Lemmas, URLS and Initial String into the Intent's bundle
                intent.putExtra("returnString", returnString); // @TODO Re-evaluate need for this
                //Original Sentence
                intent.putExtra("initialString", mENGEditTextView.getText().toString().trim());
                //List of URLs
                intent.putExtra("urls",urls);
                //List of Lemmas
                intent.putExtra("lemmas", lemmas);

                startActivity(intent);
            }
        }
        else{
            Toast.makeText(Prototype_Save_Translation.this, "This Translation Is Not Being Saved!", Toast.LENGTH_LONG).show();
            //
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
                        10.0.1.6:8080
                         */

                        //Local Connection Point
                        URL url = new URL("http://10.0.1.6:8080/English-ASL_prototype/English_ASL");

                        //Create Connection and Open
                        URLConnection connection = url.openConnection();
                        //Get Input String from Edit Text
                        String input= "[" + mENGEditTextView.getText().toString() + " & " + mASLEditTextView.getText().toString() + "]";

                        //Set the server response type 01 represents savePhrase
                        final String requestString = "0201" + input;

                        //Log the Input String
                        Log.d("inputString", requestString);

                        //Set up Output
                        connection.setDoOutput(true);

                        //Create Output Stream with Connection
                        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

                        //Write String to Output
                        out.write(requestString);

                        //Close Stream
                        out.close();

                        //Create Buffer Reader for Response from Server
                        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        //Initialize return String to prevent null ptr exceptions if connection fails.


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
                                    try {
                                        //Get List of Signs Returned by Server  ** Important ** Escape Reserved RegExp Symbol '|'
                                        String[] responseSigns = finalReturnString.split("\\|");

                                        //parse the url and the lemma
                                        for (String sign : responseSigns) {
                                            //Lemmas and URLS are separated by " "
                                            //Split on this feature and add Lemma and URL to Lists
                                            lemmas.add(sign.substring(0, sign.indexOf(" ")));
                                            urls.add(sign.substring(sign.indexOf(" "), sign.length()));

                                        }
                                    }
                                    catch(Exception e){
                                        Toast.makeText(Prototype_Save_Translation.this, "Error Loading Signs from Server", Toast.LENGTH_LONG).show();
                                        Log.d("Exception", e.toString());
                                    }
                                    finally {

                                        if(urls.size() != 0 && lemmas.size() !=0) {
                                            //Create Post Translation Intent
                                            Intent intent = new Intent(Prototype_Save_Translation.this, Prototype_Post_Translation_Response.class);

                                            //Put Lemmas, URLS and Initial String into the Intent's bundle
                                            intent.putExtra("returnString", returnString); // @TODO Re-evaluate need for this
                                            //Original Sentence
                                            intent.putExtra("initialString", mENGEditTextView.getText().toString().trim());
                                            //List of URLs
                                            intent.putExtra("urls",urls);
                                            //List of Lemmas
                                            intent.putExtra("lemmas", lemmas);

                                            startActivity(intent);
                                        }
                                    }
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

    /**
     *
     * @param v
     */
    public void saveTranslation(View v){

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
                    10.0.1.6:8080
                     */

                    //Local Connection Point
                    URL url = new URL("http://10.0.1.6:8080/English-ASL_prototype/English_ASL");

                    //Create Connection and Open
                    URLConnection connection = url.openConnection();
                    //Get Input String from Edit Text
                    String input= "[" + mENGEditTextView.getText().toString() + " & " + mASLEditTextView.getText().toString() + "]";

                    //Set the server response type 01 represents savePhrase
                    String requestString = "01" + input;

                    //Log the Input String
                    Log.d("inputString", requestString);

                    //Set up Output
                    connection.setDoOutput(true);

                    //Create Output Stream with Connection
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

                    //Write String to Output
                    out.write(requestString);

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
                                try {
                                    //Get List of Signs Returned by Server  ** Important ** Escape Reserved RegExp Symbol '|'
                                    String[] responseSigns = finalReturnString.split("\\|");

                                    //parse the url and the lemma
                                    for (String sign : responseSigns) {
                                        //Lemmas and URLS are separated by " "
                                        //Split on this feature and add Lemma and URL to Lists
                                        lemmas.add(sign.substring(0, sign.indexOf(" ")));
                                        urls.add(sign.substring(sign.indexOf(" "), sign.length()));

                                    }
                                }
                                catch(Exception e){
                                    Toast.makeText(Prototype_Save_Translation.this, "Error Saving Phrase", Toast.LENGTH_LONG).show();
                                    Log.d("Exception", e.toString());
                                }
                                finally {
                                    isDirty = false;
                                    Toast.makeText(Prototype_Save_Translation.this, "Successfully Saved Phrase", Toast.LENGTH_LONG ).show();
                                }
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

