package tylerwhitlock.capstone_prototype;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Post Translation Response Activity
 */
public class Prototype_Post_Translation_Response extends AppCompatActivity {

    //This CoverFlow
    private FeatureCoverFlow coverFlow;

    //Data Adapter
    private CoverFlowAdapter adapter;

    //Current Sign Data List
    private ArrayList<Sign> signList = new ArrayList<Sign>();

    /**
     * On Activity Creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        //Set Content View to XML Layout
        setContentView(R.layout.activity_prototype__post__translation__response);

        //Get Toolbar instance
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Set Action Bar to Toolbar instance
        setSupportActionBar(toolbar);

        //Get the Response from the Translation Activity
        Bundle bundle = getIntent().getExtras();

        //Get the string of the original phrase from the Bundle
        String strVal = bundle.get("initialString").toString();

        //Get the Video Urls of the Signs from the Bundle
        ArrayList<String> urls = bundle.getStringArrayList("urls");

        //Get the Lemma values of the Signs from the Bundle
        ArrayList<String> lemmas = bundle.getStringArrayList("lemmas");

        //For each value in the List
        for(int i = 0; i < urls.size(); i++){

            //Create a sign and add to data set
            Sign sign = new Sign(lemmas.get(i), urls.get(i));
            signList.add(sign);
        }

        //Set the value of the original text TextView
        ((TextView) findViewById(R.id.initial_string_view)).setText(strVal);

        //Get current instance of the FeatureCoverFlow
        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);

        //Get an adapter for the current data
        adapter = new CoverFlowAdapter(this, signList);

        //Set the FeatureCoverFlow's Adapter to current data adapter
        coverFlow.setAdapter(adapter);

        //Give the FeatureCoverFlow focus
        coverFlow.bringToFront();

        //Set the OnScrollListener
        coverFlow.setOnScrollPositionListener(onScrollListener());

        //Give the adapter the CoverFlow to manage position information
        adapter.setCoverFlow(coverFlow);

        //Scroll to the initial position
        coverFlow.scrollToPosition(0);
    }

    /**
     * Get Current CoverFlow
     * @return
     */
    public FeatureCoverFlow getCoverFlow(){return coverFlow;}

    /**
     * On Scroll Listener
     * @return OnScrollListener
     */
    private FeatureCoverFlow.OnScrollPositionListener onScrollListener() {
        return new FeatureCoverFlow.OnScrollPositionListener() {

            /**
             * Log change of position
             * @param position
             */
            @Override
            public void onScrolledToPosition(int position) {
                Log.v("Post_Translation", "position: " + position);
                coverFlow.bringToFront();

            }

            /**
             * Log current scrolling
             */
            @Override
            public void onScrolling() {
                Log.i("Post_Translation", "scrolling");
            }
        };
    }


}
