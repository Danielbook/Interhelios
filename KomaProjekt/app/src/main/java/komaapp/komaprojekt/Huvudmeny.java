package komaapp.komaprojekt;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Huvudmeny extends Activity
{
    private Database database = new Database();

    //private Music music;
    private RelativeLayout tutorial;
    private ViewFlipper viewFlipper;
    private Button tutSkip, tutNext, tutPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Context ctx = getBaseContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huvudmeny);

        //// TUTORIAL OVERLAY
        tutorial = (RelativeLayout)findViewById(R.id.tutorial);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        Log.d("TextLog", "App start\n");

        //Tries to read database files
        try {
            database.readFile(ctx);
            Log.d("TextLog", "Databasefile read!");
            tutorial.setVisibility(View.GONE);
        }

        //If the files couldnt be found, a new player is created and the tutorial is shown
        catch (FileNotFoundException e) {
            try {
                database.newPlayer(ctx);
                tutorial.setVisibility(View.VISIBLE);
                //viewFlipper.setVisibility(View.VISIBLE);
                Log.d("TextLog", "New Player");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Hides the ugly status bar
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Initiate the buttons and add listeners to them
        Button startBtn = (Button) findViewById(R.id.startBtn);
        Button upgradeBtn = (Button) findViewById(R.id.upgradeBtn);
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        Button howToBtn = (Button) findViewById(R.id.howToBtn);

        startBtn.setOnClickListener(buttonListener);
        upgradeBtn.setOnClickListener(buttonListener);
        settingsBtn.setOnClickListener(buttonListener);
        howToBtn.setOnClickListener(buttonListener);

        /*try{
            music = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "mfx/Presenterator.ogg");
            music.setLooping(true);
        }
        catch(IOException e){
            e.printStackTrace();
        }*/

        //music.play();

        tutSkip = (Button) findViewById(R.id.tutSkip);
        tutNext = (Button) findViewById(R.id.tutNext);
        tutPrev = (Button) findViewById(R.id.tutPrev);

        tutNext.setOnClickListener(tutorialButtonListener);
        tutPrev.setOnClickListener(tutorialButtonListener);
        tutSkip.setOnClickListener(tutorialButtonListener);

        tutUpdateBtns();
    }

    //Takes care of the button clicks
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.upgradeBtn && tutorial.getVisibility() == View.GONE) {
                startActivity(new Intent(getApplicationContext(), Upgrades.class));
            }

            if (v.getId() == R.id.settingsBtn && tutorial.getVisibility() == View.GONE) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
            }

            if (v.getId() == R.id.startBtn && tutorial.getVisibility() == View.GONE) {
                startActivity(new Intent(getApplicationContext(), Game.class));
            }

            if (v.getId() == R.id.howToBtn) {
                if (tutorial.getVisibility() == View.VISIBLE) {
                    tutorial.setVisibility(View.GONE);
                }
                else {
                    tutorial.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private View.OnClickListener tutorialButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tutSkip) {
                tutorial.setVisibility(View.GONE);
            }

            if (v.getId() == R.id.tutNext)
            {
                    viewFlipper.showNext();
                    tutUpdateBtns();
            }

            if (v.getId() == R.id.tutPrev)
            {
                    viewFlipper.showPrevious();
                    tutUpdateBtns();
            }
        }
    };

    private void tutUpdateBtns() {
        final int first = 0, last = 4;
        if (viewFlipper.getDisplayedChild() == first) {
            tutPrev.setVisibility(View.GONE);
        } else {
            tutPrev.setVisibility(View.VISIBLE);
        }

        if (viewFlipper.getDisplayedChild() == last) {
            tutNext.setVisibility(View.GONE);
        } else {
            tutNext.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.huvudmeny, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
    }
    /*
    //Takes care of the button clicks
    public View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(v.getId() == R.id.upgradeBtn)
            {
                startActivity(new Intent (getApplicationContext(), Upgrades.class));
            }

            else if (v.getId() == R.id.settingsBtn)
            {
                startActivity(new Intent (getApplicationContext(), Settings.class));
            }

            else if (v.getId() == R.id.startBtn)
            {
              //  startActivity( new Intent (getApplicationContext(), Launcher.class));
                startActivity(new Intent (getApplicationContext(), Game.class));
            }

            else if(v.getId() == R.id.howToBtn)
            {
                Log.d("TextLog", "Inte implementerad än");
            }
        }
    };
    */
}
