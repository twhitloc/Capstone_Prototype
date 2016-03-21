package tylerwhitlock.capstone_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Main Class -
 *          Entry Point for Application
 */
public class MainActivity extends AppCompatActivity {

  //    private MySQLiteHelper mSignLetterDatabase;
  //    private MySQLiteHelper mSignNumberDatabase;
    /**
                @TODO Implement Menu
     */

    /**
     * On Create Method  *Required
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prototype__entrance__point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*/
            mSignLetterDatabase = new MySQLiteHelper(MainActivity.this);
            if(mSignLetterDatabase.empty == true);
            createInitialDatabase();
         /*/
    }

    /**
     * Create Option Menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prototype__initial__screen, menu);
        return true;
    }

    /**
     * On Menu Item Selection
     * @param item
     * @return
     * @TODO Implement Menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *  Start Intent for Translation Activity
     * @param view
     */
    public void goToTranslateActivity(View view){
        Intent intent = new Intent(MainActivity.this, Prototype_Translation_Activity.class);
        startActivity(intent);

    }

    /**
     * Start Intent for View Saved Translation Activity
     * @param view
     */
    public void goToViewSavedTranslationsActivity(View view){
        Intent intent = new Intent(MainActivity.this, Prototype_View_Saved_Translations.class);
        startActivity(intent);
    }

    /**
     * Start Intent for Save Translation Activity
     * @param view
     */
    public void goToSaveTranslationActivity(View view){
        Intent intent = new Intent(MainActivity.this, Prototype_Save_Translation.class);
        startActivity(intent);
    }

}
