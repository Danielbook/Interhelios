package komaapp.komaprojekt;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;


public class Settings extends Activity {


    //Declaring variables TextViews, SeekBars and AudioManager
    private AudioManager audioManager = null;

    private SeekBar musicSeekBar = null;
    private SeekBar soundSeekBar = null;

    private TextView musicText = null;
    private TextView soundText = null;


    protected Vector<dbSettings> dbSettings = new Vector<dbSettings>();
    protected Vector<dbUpgrades> dbUpgrades = new Vector<dbUpgrades>();

    String settingFile = "settings.txt";
    String upgradesFile = "upgrades.txt";

    // Read setting file and storing the
    // database settings in a vector
    public void readFile() throws IOException
    {
        String line;
        StringTokenizer tokens;

        FileInputStream rs = openFileInput(settingFile);
        BufferedReader infile = new BufferedReader(new InputStreamReader(rs));

        while( ( line = infile.readLine() ) != null)
        {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();

            int val = Integer.parseInt(tokens.nextToken());

            dbSettings.add(new dbSettings(setting, val));
        }

        infile.close();

        rs = openFileInput(upgradesFile);
        infile = new BufferedReader(new InputStreamReader(rs));

        while( ( line = infile.readLine() ) != null)
        {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();
            int val = Integer.parseInt(tokens.nextToken());
            int price = Integer.parseInt(tokens.nextToken());

            dbUpgrades.add(new dbUpgrades(setting, val, price));
        }
        infile.close();
    }

    public void writeFile() throws IOException
    {
        //Write to settings.txt
        FileOutputStream outFile = openFileOutput(settingFile, MODE_PRIVATE);


        for (int i = 0; i < dbSettings.size(); i++) {
            outFile.write( (dbSettings.elementAt(i).getSetting() + " " + dbSettings.elementAt(i).getVal() + "\n").getBytes() );
        }

        //Write to upgrades.txt
        outFile = openFileOutput(upgradesFile, MODE_PRIVATE);

        for (int i = 0; i < dbUpgrades.size(); i++) {
            outFile.write( (dbUpgrades.elementAt(i).getUpgrade() + " " + dbUpgrades.elementAt(i).getLevel() + " " + dbUpgrades.elementAt(i).getPrice() + "\n").getBytes() );
        }
        outFile.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Read settings file
        try { readFile(); } catch (IOException e) { e.printStackTrace(); }

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
                    System.out.println("Progress: " + progress + "FromUser : " + fromUser);
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




