package komaapp.komaprojekt;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;



public class Settings extends Activity {


         //Declaring variables TextViews, SeekBars and AudioManager
    private AudioManager audioManager = null;

    private SeekBar musicSeekBar = null;
    private SeekBar soundSeekBar = null;

    private TextView musicText = null;
    private TextView soundText = null;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        ActionBar actionBar = getActionBar();
        actionBar.hide();

            //changes activity and checks for previous instance state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

            //Initializing all variables
        musicText = (TextView)findViewById(R.id.textView);
        soundText = (TextView)findViewById(R.id.textView2);

        musicSeekBar = (SeekBar)findViewById(R.id.seekBar2);
        soundSeekBar = (SeekBar)findViewById(R.id.seekBar3);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

            //call function
        initControls();
    }

    private void initControls()
    {       //needs a try/catch
        try
        {
            musicSeekBar = (SeekBar)findViewById(R.id.seekBar2);
            audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

            musicSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

            musicSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

                //the seekbar listener - must have all 3 functions that are inside
            musicSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                }

                @Override //progress is the current value where the seekbar is
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                        //progress value is given to the AudioManager
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress, 0);

                        //console test
                    //System.out.println("Progress: " + progress + "FromUser : " + fromUser);
                }
            });




        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


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




