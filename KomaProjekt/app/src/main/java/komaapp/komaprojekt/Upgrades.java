package komaapp.komaprojekt;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;

public class Upgrades extends Activity
{
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

        for (int i = 0; i < dbSettings.size(); i++)
        {
            outFileSettings.write( (dbSettings.elementAt(i).getSetting() + " " + dbSettings.elementAt(i).getVal() + "\n").getBytes() );
        }

        outFileSettings.close();

        //Write to upgrades.txt
        FileOutputStream outFileUpgrades = openFileOutput(upgradesFile, MODE_PRIVATE);

        for (int i = 0; i < dbUpgrades.size(); i++)
        {
            outFileUpgrades.write( (dbUpgrades.elementAt(i).getUpgrade() + " " + dbUpgrades.elementAt(i).getLevel() + " " + dbUpgrades.elementAt(i).getPrice() + "\n").getBytes() );
        }
        outFileUpgrades.close();
    }

    //Returns the players cash
    public int getCash()
    {
        for (int i = 0; i < dbSettings.size(); i++)
        {
            if(dbSettings.elementAt(i).getSetting().equalsIgnoreCase("cash"))
            {
                return dbSettings.elementAt(i).getVal();
            }
        }
        return 0;
    }

    //Returns the level of the input upgrade
    public int getLvl(String upgrade)
    {
        for(int i = 0; i < dbUpgrades.size(); i++)
        {
            if(dbUpgrades.elementAt(i).getUpgrade().equalsIgnoreCase(upgrade))
            {
                return dbUpgrades.elementAt(i).getLevel();
            }
        }
        return 0;
    }

    //Used when buying stuff
    public void removeCash(int cash)
    {
        int currentCash = getCash();

        for (int i = 0; i < dbSettings.size(); i++)
        {
            if(dbSettings.elementAt(i).getSetting().equalsIgnoreCase("cash"))
            {
                dbSettings.elementAt(i).setVal(currentCash - cash);
                return;
            }
        }
    }

    //Used when player want to get more cash
    public void addCash(int cash)
    {
        int currentCash = getCash();
        Log.d("TextLog", "Current cash: " + currentCash);

        currentCash += cash;

        Log.d("TextLog", "After add: " + currentCash);

        for (int i = 0; i < dbSettings.size(); i++)
        {
            if(dbSettings.elementAt(i).getSetting().equalsIgnoreCase("cash"))
            {
                dbSettings.elementAt(i).setVal(currentCash);
                return;
            }
        }
    }

    //Used when the players chose to buy something, checks if there are enough money for the upgrade
    public boolean enoughCash(String upgrade)
    {
        int cash = getCash();

        for(int i = 0; i < dbUpgrades.size(); i++)
        {
            if(dbUpgrades.elementAt(i).getUpgrade().equalsIgnoreCase(upgrade) && (dbUpgrades.elementAt(i).getPrice() <= cash))
            {
                return true;
            }
        }
        Log.d("TextLog", "Not enough cash bro!");
        return false;
    }

    //Called when player chose to buy an upgrade
    public boolean buyUpgrade(String upgrade)
    {
        Log.d("TextLog", "Buying your shit");
        if(enoughCash(upgrade))
        {
            for (int i = 0; i < dbUpgrades.size(); i++)
            {
                if(dbUpgrades.elementAt(i).getUpgrade().equalsIgnoreCase(upgrade))
                {
                    removeCash(dbUpgrades.elementAt(i).getPrice());
                    dbUpgrades.elementAt(i).addLevel();
                    Log.d("TextLog", "Upgrade for " + upgrade + " succesfully bought, now your at lvl " + dbUpgrades.elementAt(i).getLevel());
                    return true;
                }
            }
        }

        Log.d("TextLog", "Couldn't buy your shit");
        return false;
    }

    //Updates the txt in the tables
    public void updateTable()
    {
        TextView cashTxt = (TextView)findViewById(R.id.cashTxt);
        cashTxt.setText("" + getCash());

        TextView gunsLvl = (TextView)findViewById(R.id.gunsLvlVal);
        gunsLvl.setText("" + getLvl("guns"));

        TextView engineLvl = (TextView)findViewById(R.id.engineLvlVal);
        engineLvl.setText("" + getLvl("engine"));

        TextView shieldLvl = (TextView)findViewById(R.id.shieldLvlVal);
        shieldLvl.setText("" + getLvl("shield"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        Log.d("TextLog", "Upgrade start\n");

        //Log.d("TextLog", "Filer sparas här: " + getFilesDir());

        try { readFile(); Log.d("TextLog", "Databasefile read!"); }
        catch (IOException e) { e.printStackTrace(); }

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Initiate buttons
        Button gunsBtn = (Button)findViewById(R.id.gunsBtn);
        Button shieldBtn = (Button)findViewById(R.id.shieldBtn);
        Button engineBtn = (Button)findViewById(R.id.engineBtn);
        Button backBtn = (Button)findViewById(R.id.backBtn);
        Button cashBtn = (Button)findViewById(R.id.cashBtn);

        //Add values from the database
        TextView cashTxt = (TextView)findViewById(R.id.cashTxt);
        cashTxt.setText("" + getCash());

        TextView gunsLvl = (TextView)findViewById(R.id.gunsLvlVal);
        gunsLvl.setText("" + getLvl("guns"));

        TextView engineLvl = (TextView)findViewById(R.id.engineLvlVal);
        engineLvl.setText("" + getLvl("engine"));

        TextView shieldLvl = (TextView)findViewById(R.id.shieldLvlVal);
        shieldLvl.setText("" + getLvl("shield"));

        //Add listeners to all the buttons
        gunsBtn.setOnClickListener(buttonListener);
        shieldBtn.setOnClickListener(buttonListener);
        engineBtn.setOnClickListener(buttonListener);
        backBtn.setOnClickListener(buttonListener);
        cashBtn.setOnClickListener(buttonListener);

    }

    public View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            if(v.getId() == R.id.gunsBtn)
            {
                Log.d("TextLog", "Guns");
                if( buyUpgrade("Guns") )
                {
                    try { writeFile(); } catch (IOException e) { e.printStackTrace(); }
                    try { readFile(); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.shieldBtn)
            {
                Log.d("TextLog", "Shield");

                if(buyUpgrade("Shield"))
                {
                    try { writeFile(); } catch (IOException e) { e.printStackTrace(); }
                    try { readFile(); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.engineBtn)
            {
                Log.d("TextLog", "Engine");

                if( buyUpgrade("Engine") )
                {
                    try { writeFile(); } catch (IOException e) { e.printStackTrace(); }
                    try { readFile(); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.cashBtn)
            {
                addCash(500);

                try { writeFile(); } catch (IOException e) { e.printStackTrace(); }
                try { readFile(); } catch (IOException e) { e.printStackTrace(); }

                updateTable();

            }

            if(v.getId() == R.id.backBtn)
            {
                startActivity(new Intent (getApplicationContext(), Huvudmeny.class));
            }
        }
    };

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
