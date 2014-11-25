package komaapp.komaprojekt;

import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.*;

import java.io.IOException;


public class Settings extends Activity {

    private Database database = new Database();

    private SeekBar musicSeekBar;
    private SeekBar soundSeekBar;

    private TextView musicText;
    private TextView soundText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Context ctx = getBaseContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Read settings file
        try { database.readFile(ctx); Log.d("TextLog", "Databasefile read!"); }
        catch (IOException e) { e.printStackTrace(); }


        //Initializing all variables
        musicText = (TextView)findViewById(R.id.musicText);
        soundText = (TextView)findViewById(R.id.soundText);

        musicSeekBar = (SeekBar)findViewById(R.id.musicBar);
        soundSeekBar = (SeekBar)findViewById(R.id.soundBar);

        musicSeekBar.setProgress(database.getVolume("music"));
        soundSeekBar.setProgress(database.getVolume("sound"));

        musicSeekBar.setMax(10);
        soundSeekBar.setMax(10);

        musicSeekBar.setOnSeekBarChangeListener(seekBarListener);
        soundSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    //the seekbar listener - must have all 3 functions that are inside
    public OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            if(seekBar == musicSeekBar)
            {
                database.setVolume("music", progress);
            }
            else if(seekBar == soundSeekBar)
            {
                database.setVolume("sound", progress);
            }

            Log.d("TextLog", "Progress: " + progress );
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar){}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            Context ctx = getApplicationContext();
            try { database.writeFile(ctx); } catch (IOException e) { e.printStackTrace(); }

            Log.d("TextLog","Music: " + database.getVolume("music") + "\nSound: " + database.getVolume("sound"));
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
}




