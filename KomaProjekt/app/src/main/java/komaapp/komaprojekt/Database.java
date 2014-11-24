package komaapp.komaprojekt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import java.io.*;
import java.util.*;

public class Database extends Activity
{
    private static Vector<dbSettings> dbSettings = new Vector<dbSettings>();
    private static Vector<dbUpgrades> dbUpgrades = new Vector<dbUpgrades>();

    private final int MAX_LEVEL = 5;

    private final static String settingFile = "settings.txt";
    private final static String upgradesFile = "upgrades.txt";

    //Called when the text files are no where to be found on that fucking
    //piece of shit android device...
    //Creates a "new" player from the shitty files in the assets folder
    public void newPlayer(Context ctx) throws IOException {
        String line;
        StringTokenizer tokens;

        //Read from the fucking assets folder
        InputStream settingStream = ctx.getResources().getAssets().open(settingFile);
        BufferedReader settingsReader = new BufferedReader(new InputStreamReader(settingStream));

        InputStream upgradeStream = ctx.getResources().getAssets().open(upgradesFile);
        BufferedReader upgradesReader = new BufferedReader(new InputStreamReader(upgradeStream));

        //Save in the half assed databases
        while ((line = settingsReader.readLine()) != null) {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();
            int val = Integer.parseInt(tokens.nextToken());

            dbSettings.add(new dbSettings(setting, val));
        }

        settingStream.close();

        while ((line = upgradesReader.readLine()) != null) {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();
            int val = Integer.parseInt(tokens.nextToken());
            int price = Integer.parseInt(tokens.nextToken());

            dbUpgrades.add(new dbUpgrades(setting, val, price));
        }

        upgradeStream.close();

        //Write to the shitty devices internal fucking memory
        writeFile(ctx);
    }

    //Read setting file and storing the
    //database settings in a vector
    public void readFile(Context ctx) throws IOException {
        String line;
        StringTokenizer tokens;

        FileInputStream settingsStream = ctx.openFileInput(settingFile);
        BufferedReader settingsReader = new BufferedReader(new InputStreamReader(settingsStream));

        while ((line = settingsReader.readLine()) != null) {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();
            int val = Integer.parseInt(tokens.nextToken());
            dbSettings.add(new dbSettings(setting, val));
        }
        settingsReader.close();
        settingsStream.close();

        FileInputStream upgradesStream = ctx.openFileInput(upgradesFile);
        BufferedReader upgradesReader = new BufferedReader(new InputStreamReader(upgradesStream));

        Log.d("TextLog", "Upgrades size before add: " + dbUpgrades.size());

        while ((line = upgradesReader.readLine()) != null) {
            tokens = new StringTokenizer(line, " ");
            String setting = tokens.nextToken();
            int val = Integer.parseInt(tokens.nextToken());
            int price = Integer.parseInt(tokens.nextToken());
            dbUpgrades.add(new dbUpgrades(setting, val, price));
        }

        Log.d("TextLog", "Upgrades size after add: " + dbUpgrades.size());
        upgradesReader.close();
        upgradesStream.close();
    }

    public void writeFile(Context ctx) throws IOException
    {
        //Write to settings.txt
        BufferedWriter settingsWriter = new BufferedWriter(new PrintWriter(ctx.openFileOutput(settingFile, MODE_PRIVATE)));

        for (int i = 0; i < dbSettings.size(); i++)
        {
            settingsWriter.write("" + dbSettings.elementAt(i).getSetting() + " " + dbSettings.elementAt(i).getVal() + "\n");

        }
        settingsWriter.flush();
        settingsWriter.close();

        //Write to upgrades.txt
        BufferedWriter upgradesWriter = new BufferedWriter(new PrintWriter(ctx.openFileOutput(settingFile, MODE_PRIVATE)));

        for (int i = 0; i < dbUpgrades.size(); i++)
        {
            upgradesWriter.write("" + dbUpgrades.elementAt(i).getUpgrade() + " " + dbUpgrades.elementAt(i).getLevel() + " " + dbUpgrades.elementAt(i).getPrice() + "\n");
        }
        upgradesWriter.flush();
        upgradesWriter.close();
    }

    //Returns the volume of the setting
    public int getVolume(String setting)
    {
        for (int i = 0; i < dbSettings.size(); i++)
        {
            if (dbSettings.elementAt(i).getSetting().equalsIgnoreCase(setting))
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
            if (dbSettings.elementAt(i).getSetting().equalsIgnoreCase(setting))
            {
                dbSettings.elementAt(i).setVal(newVolume);
            }
        }
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

    //Used when buying upgrades
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
        currentCash += cash;

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
        return false;
    }

    public void addPrice(int i)
    {
        int currentPrice = dbUpgrades.elementAt(i).getPrice();
        dbUpgrades.elementAt(i).setPrice(currentPrice*2);
    }

    //Called when player chose to buy an upgrade
    public boolean buyUpgrade(String upgrade)
    {
        if (enoughCash(upgrade)) {
            for (int i = 0; i < dbUpgrades.size(); i++)
            {
                if (dbUpgrades.elementAt(i).getUpgrade().equalsIgnoreCase(upgrade))
                {
                    if(dbUpgrades.elementAt(i).getLevel() >= MAX_LEVEL)
                    {
                        return false;
                    }

                    else
                    {
                        removeCash(dbUpgrades.elementAt(i).getPrice());
                        dbUpgrades.elementAt(i).addLevel();
                        addPrice(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}