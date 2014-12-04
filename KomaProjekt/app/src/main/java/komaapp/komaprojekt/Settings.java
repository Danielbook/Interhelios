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
        musicSeekBar = (SeekBar)findViewById(R.id.musicBar);
        soundSeekBar = (SeekBar)findViewById(R.id.soundBar);

        Button backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(buttonListener);

        musicSeekBar.setProgress(database.getVolume("music"));
        soundSeekBar.setProgress(database.getVolume("sound"));

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
                startActivity(new Intent (getApplicationContext(), Huvudmeny.class));
            }
        }
    };

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

    public void onBackPressed() {
    }
}




