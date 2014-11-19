package komaapp.komaprojekt;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;

public class Huvudmeny extends Activity {

    protected Vector<dbSettings> dbSettings = new Vector<dbSettings>();
    protected Vector<dbUpgrades> dbUpgrades = new Vector<dbUpgrades>();

    protected final String settingFile = "settings.txt";
    protected final String upgradesFile = "upgrades.txt";

    //Called when the textfiles are no where to be found on that fucking
    //piece of shit android device...
    //Creates a "new" player from the shitty files in the assets folder
    public void newPlayer() throws IOException
    {
        String line;
        StringTokenizer tokens;

        //Läs in ifrån assetsmappen
        InputStream settingStream = getResources().getAssets().open(settingFile);
        BufferedReader settingsReader = new BufferedReader(new InputStreamReader(settingStream));

        InputStream upgradeStream = getResources().getAssets().open(upgradesFile);
        BufferedReader upgradesReader = new BufferedReader(new InputStreamReader(upgradeStream));

        //Spara i databaserna dbSettings och dbUpgrades
        while( ( line = settingsReader.readLine() ) != null)
        {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();
            int val = Integer.parseInt(tokens.nextToken());

            dbSettings.add(new dbSettings(setting, val));
        }

        settingStream.close();

        while( ( line = upgradesReader.readLine() ) != null)
        {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();
            int val = Integer.parseInt(tokens.nextToken());
            int price = Integer.parseInt(tokens.nextToken());

            dbUpgrades.add(new dbUpgrades(setting, val, price));
        }
        
        upgradeStream.close();

        //Spara i databastextfilen
        writeFile();
    }

    //Read setting file and storing the
    //database settings in a vector
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
            outFile.write((dbSettings.elementAt(i).getSetting() + " " + dbSettings.elementAt(i).getVal() + "\n").getBytes());
        }

        //Write to upgrades.txt
        outFile = openFileOutput(upgradesFile, MODE_PRIVATE);

        for (int i = 0; i < dbUpgrades.size(); i++) {
            outFile.write((dbUpgrades.elementAt(i).getUpgrade() + " " + dbUpgrades.elementAt(i).getLevel() + " " + dbUpgrades.elementAt(i).getPrice() + "\n").getBytes());
        }
        outFile.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huvudmeny);

        Log.d("TextLog", "App start\n");

        try{ readFile(); Log.d("TextLog", "Databasefile read!"); }
        catch (FileNotFoundException e){
            try {
                newPlayer();
                Log.d("TextLog", "New Player");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) { e.printStackTrace(); }

        //Hides the ugly statusbar
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Takes care of the button clicks
        View.OnClickListener buttonListener = new View.OnClickListener()
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
                    startActivity(new Intent (getApplicationContext(), Game.class));
                }

                else if(v.getId() == R.id.howToBtn)
                {
                    Log.d("TextLog", "Inte implementerad än");
                }
            }
        };

        Button startBtn = (Button)findViewById(R.id.startBtn);
        Button upgradeBtn = (Button)findViewById(R.id.upgradeBtn);
        Button settingsBtn = (Button)findViewById(R.id.settingsBtn);
        Button howToBtn = (Button)findViewById(R.id.howToBtn);

        startBtn.setOnClickListener(buttonListener);
        upgradeBtn.setOnClickListener(buttonListener);
        settingsBtn.setOnClickListener(buttonListener);
        howToBtn.setOnClickListener(buttonListener);
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
}
