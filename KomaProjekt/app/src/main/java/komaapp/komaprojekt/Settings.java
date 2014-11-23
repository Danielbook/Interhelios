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
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;


public class Settings extends Activity {

    private SeekBar musicSeekBar;
    private SeekBar soundSeekBar = null;

    private TextView musicText = null;
    private TextView soundText = null;

    protected Vector<dbSettings> dbSettings = new Vector<dbSettings>();
    protected Vector<dbUpgrades> dbUpgrades = new Vector<dbUpgrades>();

    final String settingFile = "settings.txt";
    final String upgradesFile = "upgrades.txt";

    //Read setting files and storing the
    //database settings in vectors
    public void readFile() throws IOException
    {
        String line;
        StringTokenizer tokens;

        FileInputStream streamSettings = openFileInput(settingFile);
        BufferedReader infileSettings = new BufferedReader(new InputStreamReader(streamSettings));

        FileInputStream streamUpgrades = openFileInput(upgradesFile);
        BufferedReader infileUpgrades = new BufferedReader(new InputStreamReader(streamUpgrades));

        while( ( line = infileSettings.readLine() ) != null)
        {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();
            int val = Integer.parseInt(tokens.nextToken());

            dbSettings.add(new dbSettings(setting, val));
        }

        infileSettings.close();

        while( ( line = infileUpgrades.readLine() ) != null)
        {
            tokens = new StringTokenizer(line, " ");
            String upgrade = tokens.nextToken();
            int level = Integer.parseInt(tokens.nextToken());
            int price = Integer.parseInt(tokens.nextToken());

            dbUpgrades.add(new dbUpgrades(upgrade, level, price));
        }
        infileUpgrades.close();
    }

    //Write to database files
    public void writeFile() throws IOException
    {
        //Write to settings.txt
        FileOutputStream outFileSettings = openFileOutput(settingFile, MODE_PRIVATE);
        PrintWriter printSettings = new PrintWriter(outFileSettings);

        for (int i = 0; i < dbSettings.size(); i++)
        {
            printSettings.print((dbSettings.elementAt(i).getSetting() + " " + dbSettings.elementAt(i).getVal() + "\n"));
        }
        outFileSettings.flush();
        outFileSettings.close();

        //Write to upgrades.txt
        FileOutputStream outFileUpgrades = openFileOutput(upgradesFile, MODE_PRIVATE);
        PrintWriter printUpgrades = new PrintWriter(outFileUpgrades);

        for (int i = 0; i < dbUpgrades.size(); i++)
        {
            printUpgrades.print( (dbUpgrades.elementAt(i).getUpgrade() + " " + dbUpgrades.elementAt(i).getLevel() + " " + dbUpgrades.elementAt(i).getPrice() + "\n"));
        }
        outFileSettings.flush();
        outFileUpgrades.close();
    }

    public int getVolume(String setting)
    {
        for (int i = 0; i < dbSettings.size(); i++)
        {
            if(dbSettings.elementAt(i).getSetting().equalsIgnoreCase(setting))
            {
                return dbSettings.elementAt(i).getVal();
            }
        }
        return 0;
    }

    //Function to change the volume
    public void setVolume(String setting, int newVolume)
    {
        for (int i = 0; i < dbSettings.size(); i++)
        {
            if(dbSettings.elementAt(i).getSetting().equalsIgnoreCase(setting))
            {
                dbSettings.elementAt(i).setVal(newVolume);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Read settings file
        try { readFile(); Log.d("TextLog", "Databasefile read!"); }
        catch (IOException e) { e.printStackTrace(); }


        //Initializing all variables
        musicText = (TextView)findViewById(R.id.musicText);
        soundText = (TextView)findViewById(R.id.soundText);

        musicSeekBar = (SeekBar)findViewById(R.id.musicBar);
        soundSeekBar = (SeekBar)findViewById(R.id.soundBar);

        musicSeekBar.setProgress(getVolume("music"));
        soundSeekBar.setProgress(getVolume("sound"));

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
                setVolume("music", progress);
            }
            else if(seekBar == soundSeekBar)
            {
                setVolume("sound", progress);
            }

            Log.d("TextLog", "Progress: " + progress );
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            try { writeFile(); } catch (IOException e) { e.printStackTrace(); }

            Log.d("TextLog","Music: " + getVolume("music") + "\nSound: " + getVolume("sound"));
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




