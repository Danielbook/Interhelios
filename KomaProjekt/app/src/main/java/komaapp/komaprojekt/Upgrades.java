package komaapp.komaprojekt;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.io.*;

public class Upgrades extends Activity
{
    private Database database = new Database();

    //Updates the txt in the tables
    public void updateTable()
    {
        TextView cashTxt = (TextView)findViewById(R.id.cashTxt);
        cashTxt.setText("" + database.getCash());

        TextView gunsLvl = (TextView)findViewById(R.id.gunsLvlVal);
        gunsLvl.setText("" + database.getLvl("guns"));

        TextView engineLvl = (TextView)findViewById(R.id.engineLvlVal);
        engineLvl.setText("" + database.getLvl("engine"));

        TextView shieldLvl = (TextView)findViewById(R.id.shieldLvlVal);
        shieldLvl.setText("" + database.getLvl("shield"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Context ctx = getBaseContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        Log.d("TextLog", "Upgrade start\n");

        //Log.d("TextLog", "Filer sparas här: " + getFilesDir());

        try { database.readFile(ctx); Log.d("TextLog", "Databasefile read!"); }
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
        cashTxt.setText("" + database.getCash());

        TextView gunsLvl = (TextView)findViewById(R.id.gunsLvlVal);
        gunsLvl.setText("" + database.getLvl("guns"));

        TextView engineLvl = (TextView)findViewById(R.id.engineLvlVal);
        engineLvl.setText("" + database.getLvl("engine"));

        TextView shieldLvl = (TextView)findViewById(R.id.shieldLvlVal);
        shieldLvl.setText("" + database.getLvl("shield"));

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
            Context ctx = getApplicationContext();

            if(v.getId() == R.id.gunsBtn)
            {
                Log.d("TextLog", "Guns");
                if( database.buyUpgrade("Guns") )
                {
                    try { database.writeFile(ctx); } catch (IOException e) { e.printStackTrace(); }
                    try { database.readFile(ctx); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.shieldBtn)
            {
                Log.d("TextLog", "Shield");

                if(database.buyUpgrade("Shield"))
                {
                    try { database.writeFile(ctx); } catch (IOException e) { e.printStackTrace(); }
                    try { database.readFile(ctx); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.engineBtn)
            {
                Log.d("TextLog", "Engine");

                if( database.buyUpgrade("Engine") )
                {
                    try { database.writeFile(ctx); } catch (IOException e) { e.printStackTrace(); }
                    try { database.readFile(ctx); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.cashBtn)
            {
                database.addCash(500);

                try { database.writeFile(ctx); } catch (IOException e) { e.printStackTrace(); }
                try { database.readFile(ctx); } catch (IOException e) { e.printStackTrace(); }

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
