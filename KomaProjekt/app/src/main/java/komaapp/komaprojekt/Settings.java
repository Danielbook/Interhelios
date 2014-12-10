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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.io.IOException;


public class Settings extends Activity {

    private Database database = new Database();

    private SeekBar musicSeekBar;
    private SeekBar soundSeekBar;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ctx = getBaseContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Read settings file
        try { database.readFile(ctx); Log.d("TextLog", "Databasefile read!"); }
        catch (IOException e) { e.printStackTrace(); }

        //Initializing all variables
        musicSeekBar = (SeekBar)findViewById(R.id.musicBar);
        soundSeekBar = (SeekBar)findViewById(R.id.soundBar);

        Button backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(buttonListener);

        musicSeekBar.setProgress(database.getMusicVolume());
        soundSeekBar.setProgress(database.getSoundVolume());

        musicSeekBar.setMax(10);
        soundSeekBar.setMax(10);

        musicSeekBar.setOnSeekBarChangeListener(seekBarListener);
        soundSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(v.getId() == R.id.backBtn){
                MainMenu.soundManager.buttonSound();
                startActivity(new Intent (getApplicationContext(), MainMenu.class));
            }
        }
    };

    //the seekbar listener - must have all 3 functions that are inside
    public OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            if(seekBar == musicSeekBar)
            {
                database.setMusicVolume(progress);
                try
                {
                    database.writeFile(ctx);

                } catch (IOException e) { e.printStackTrace(); }

                MainMenu.soundManager.changeBackgroundVolume();
            }

            else if(seekBar == soundSeekBar)
            {
                database.setSoundVolume(progress);
                try
                {
                    database.writeFile(ctx);

                } catch (IOException e) { e.printStackTrace(); }


            }

            Log.d("TextLog", "Progress: " + progress );
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar){}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {


            Log.d("TextLog","Music: " + database.getMusicVolume() + "\nSound: " + database.getSoundVolume());
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.huvudmeny, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume()
    {
        super.onResume();

    }

    public void onBackPressed() {
    }
}




