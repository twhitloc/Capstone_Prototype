package tylerwhitlock.capstone_prototype;

/**
 * Created by tylerwhitlock on 2/9/16.
 */

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * @Purpose:    Adapter Class for the Feature Cover Flow - Features Sign
 *
 */
public class CoverFlowAdapter extends BaseAdapter {



    //Activity
    private AppCompatActivity activity;
    //List of Signs
    private ArrayList<Sign> data = new ArrayList<Sign>();
    //Cover Flow Object
    private FeatureCoverFlow coverFlow;
    //Tracks Completion of first play through
    private Boolean completedInitialLoop = false;
    //Tracks whether video has finished or not
    public ArrayList<Boolean> isFinished = new ArrayList<>();
    //Internally track position
    private int mTracker =0;

    /**
     * Constructor
     * @param context
     * @param objects
     */
    public CoverFlowAdapter(AppCompatActivity context, ArrayList<Sign> objects) {
        this.activity = context;
        this.data = objects;
    }

    /**
     * Get Size of Data List
     * @return Size
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * Get Item at Position
     * @param position
     * @return Item
     */
    @Override
    public Sign getItem(int position) {
        return data.get(position);
    }

    /**
     * Get Item ID at Position
     * @param position
     * @return Item ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get View for current object
     * @param position
     * @param convertView
     * @param parent
     * @return Object View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get Frame for View
        ViewHolder viewHolder;

        //Video is not finished so set isFinished is false for current position(implied)
        isFinished.add(false);

        //if there is no view loaded currently
        if (convertView == null) {

            //Inflate the layout for current Context
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Set the convertView to the inflated View Layout
            convertView = inflater.inflate(R.layout.item_flow_view, null, false);

            //Create new ViewHolder with the current view to frame Layout
            viewHolder = new ViewHolder(convertView);

            //Set the ConvertView tag to the ViewHolder
            convertView.setTag(viewHolder);
        }
        //If the convertView is not null
        else
        {
            //Get the current ViewHolder Frame
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // @TODO: Will need to change this later for dependencies

        //Set ViewHolder Lemma Field to current object's Lemma Value
        viewHolder.lemma.setText(data.get(position).getmLemmaValue());

        //Set the ViewHolder PartofSpeech Field to current object's Part of Speech
        viewHolder.partOfSpeech.setText(data.get(position).getmPartOfSpeech());

        //Set the ViewHolder's OnclickListener to the custom onClickListener for current Item
        convertView.setOnClickListener(onClickListener(position));

        //Create Handler to introduce pause before creating dialog
        Handler handler = new Handler();

        //finalize View for Handler
        final View finalConvertView = convertView;

        //If the initial Loop has not been completed Create AutoPlay and AutoScroll
        if(!completedInitialLoop) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    finalConvertView.callOnClick();
                }
            }, 2000);
        }
        //Invisible Else - Do not automatically continue playback (infinite loop)

        //Return the converted View
        return convertView;
    }

    /**
     *  @TODO Remove?
     *  Call the onClickListener
     * @param position
     */
    @Deprecated
    public void callSignOnClickListener(final int position){
        this.onClickListener(position);
    }


    /**
     * Create New OnClickListener Function for View defined by Item_Flow_View
     *
     * @param position
     * @return OnClickListener Function
     */
    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {

            /**
             * Defines the OnClick event for The Item_Flow_View
             * @param v
             */
            @Override
            public void onClick(View v) {

                // Create Dialog Box
                final Dialog dialog = new Dialog(activity);

                // Set Content View so fields can be accessed without null ptrs
                dialog.setContentView(R.layout.sign_information);

                //Allow window dismissal by clicking out of window
                dialog.setCancelable(true);

                //Set the Title View of the Dialog
                dialog.setTitle("SignLetter Details");


                /*
                    Path will to Video look like
                    String path = "http://www.signingsavvy.com/signs/mp4/4/4154.mp4";
                 */

                // Prevent Null Ptr by not exceeding List size
                if (position != data.size()) {

                    //Get Path for object will look like path defined above
                    String path = data.get(position).getmVideoUrl().replace("https", "http").toString().trim();

                    //Get Video View of defined Layout for  Sign_Information.xml  and set to final
                    final VideoView video = (VideoView) dialog.findViewById(R.id.video);

                    //Get Text View
                    TextView text = (TextView) dialog.findViewById(R.id.name);

                    //Set Text to current Lemma value
                    text.setText(getItem(position).getmLemmaValue());

                    //Surround asynchronous requests with try / catch
                    try {
                        // Start the MediaController with the current context
                        android.widget.MediaController mediacontroller = new MediaController(v.getContext());

                        //Set the Anchor for the MediaController - attach to layout
                        mediacontroller.setAnchorView(video);

                        //Parse a Uri from the path that defines the Video Url
                        Uri videoUri = Uri.parse(path);

                        //Set the Layout View's media controller to current media controller
                        video.setMediaController(mediacontroller);

                        //Set the View's video Uri with the Sign's video Url as Uri
                        video.setVideoURI(videoUri);

                        //Set Video View's OnClickListener to replay
                        video.setOnClickListener(new VideoView.OnClickListener() {
                            /**
                             * Replay the video in the frame when onClick is fired
                             * @param v
                             */
                            @Override
                            public void onClick(View v) {
                                video.requestFocus();
                                video.start();
                            }
                        });

                        //Set Video View / Media Controller's onCompletionListener
                        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                            /**
                             * On Video Completion Move to Next Item in CoverFlow
                             * @param mp
                             */
                            @Override
                            public void onCompletion(MediaPlayer mp) {

                                //Create new Handler to introduce pauses in AutoScrolling
                                Handler handler = new Handler();

                                //get final Video View
                                final View v = video;

                                //Introduce brief Pause and Move Forward 1 Position via Handler
                                handler.postDelayed(new Runnable() {
                                    /**
                                     * Define function for handler to call after pause
                                     */
                                    public void run() {
                                        //Dismiss the Dialog after video completion
                                        dialog.dismiss();

                                        //Scroll to the next object in list
                                        coverFlow.scrollToPosition(position + 1);

                                        //If the mTracker is not at the end of the list
                                        if (mTracker < data.size()) {

                                            //Increment tracker
                                            mTracker++;

                                            //Set Boolean that this video has finished to true
                                            isFinished.set(position, true);

                                            //Perform onClickListener to continue AutoScrolling with Video Play
                                            onClickListener(mTracker).onClick(v);

                                            //If the Carousel has gone through all items
                                            if(mTracker == data.size()){

                                                //Initial Loop must be finished
                                                completedInitialLoop = true;

                                                //Set Tracker back to 0
                                                mTracker =0;

                                                //Go to Back Initial Position
                                                coverFlow.scrollToPosition(mTracker);
                                            }
                                        }
                                    }   // end of run()

                                    //Wait for 1 second
                                }, 1000);
                            }
                        }); //end of onCompletionListener

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    // if Tracker and position indicate the same position then ->
                    //      object has been scrolled to, not loaded
                    if (mTracker == position ) {
                        //Show Dialog
                        dialog.show();

                        //Give Video View Focus
                        video.requestFocus();

                        //Start Video Playback
                        video.start();
                    }

                    //If position indicates 0 and completedLoop is true, do not auto play with onClick
                    if(position ==0 && completedInitialLoop == true)
                    {
                        //Show dialog
                        dialog.show();

                        //Give Video View Focus
                        video.requestFocus();

                        //Start Video Playback
                        video.start();
                    }

                    //image.setImageResource(getItem(position).getmResourceID());

                }//end if
            }

        };

    }

    /**
     * Get the current Cover Flow
     * @return
     */
    public FeatureCoverFlow getCoverFlow() {
        return coverFlow;
    }

    /**
     * Set the current Cover Flow
     * @param coverFlow
     */
    public void setCoverFlow(FeatureCoverFlow coverFlow) {
        this.coverFlow = coverFlow;
    }


    /**
     * Internal Class to Frame the Views for the Data
     */
    private static class ViewHolder {

        //Part of Speech TextView
        private TextView partOfSpeech;

        //Lemma TextView
        private TextView lemma;

        //Dependencies Textview
        private TextView dependencies;


        /**
         * Constructor
         * @param v
         */
        public ViewHolder(View v) {

            //Match Variables with  XML defined Views
            lemma = (TextView) v.findViewById(R.id.lemma);
            partOfSpeech = (TextView) v.findViewById(R.id.part_of_speech);
            dependencies = (TextView) v.findViewById(R.id.dependencies);
        }
    }
}
