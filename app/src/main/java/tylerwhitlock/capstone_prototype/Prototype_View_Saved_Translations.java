package tylerwhitlock.capstone_prototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * @ TODO IMPLEMENT
 */
public class Prototype_View_Saved_Translations extends AppCompatActivity {

    private ArrayList<TranslatedPhrase> list = new ArrayList<>();


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__saved__translations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                    String inputString = "0400";

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



                                //Get List of Signs Returned by Server  ** Important ** Escape Reserved RegExp Symbol '|'
                                String[] responsePhrases = finalReturnString.split("\\]\\[");

                                //Create List of URLs and Lemmas
                                ArrayList<String> urls = new ArrayList<>();
                                ArrayList<String> lemmas = new ArrayList<String>();

                                //parse the url and the lemma
                                for(String tp : responsePhrases){

                                    tp =  tp.replace("[", "");
                                  tp = tp.replace("]","");
                                    //Lemmas and URLS are separated by " "
                                    String english = tp.substring(0, tp.indexOf(" | "));
                                    String asl = tp.substring(tp.indexOf(" | ") + 3, tp.length());
                                    //Split on this feature and add Lemma and URL to Lists
                                    list.add(new TranslatedPhrase(english, asl));

                                }

                                ListAdapter listAdapter = new ListAdapter(getApplication(), list);

                                ListView listView = (ListView) findViewById(R.id.list_view);
                                listView.setAdapter(listAdapter);

                                setListViewAdapter(listView, listAdapter);

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

    public void setListViewAdapter(ListView listView, final ListAdapter adapter){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Context context = getApplicationContext();
            final TranslatedPhrase tp = adapter.getItem(position);

            /**
             * Create Background thread for Asynchronous Requests
             */
            new Thread(new Runnable() {
                public void run() {
                    final ArrayList<String> urls = new ArrayList<>();
                     final ArrayList<String> lemmas = new ArrayList<>();
                    String returnString = "";
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
                        String input= "[" + tp.getEnglishPhrase() + " & " + tp.getEnglishPhrase() + "]";

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
                                        Toast.makeText(Prototype_View_Saved_Translations.this, "Error Loading Signs from Server", Toast.LENGTH_LONG).show();
                                        Log.d("Exception", e.toString());
                                    }
                                    finally {

                                        if(urls.size() != 0 && lemmas.size() !=0) {
                                            //Create Post Translation Intent
                                            Intent intent = new Intent(Prototype_View_Saved_Translations.this, Prototype_Post_Translation_Response.class);

                                            //Put Lemmas, URLS and Initial String into the Intent's bundle
                                            intent.putExtra("returnString", finalReturnString); // @TODO Re-evaluate need for this
                                            //Original Sentence
                                            intent.putExtra("initialString", tp.getEnglishPhrase());
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
        );
    }
}
