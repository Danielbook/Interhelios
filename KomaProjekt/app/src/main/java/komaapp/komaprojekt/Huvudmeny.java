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

import java.io.FileNotFoundException;
import java.io.IOException;

public class Huvudmeny extends Activity
{
    private Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Context ctx = getBaseContext();

        //Log.d("TextLog", "App Context: " + ctx);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huvudmeny);

        Log.d("TextLog", "App start\n");

        try{ database.readFile(ctx); Log.d("TextLog", "Databasefile read!"); }
            catch (FileNotFoundException e){
                try { database.newPlayer(ctx); Log.d("TextLog", "New Player"); }
                catch (IOException e1) { e1.printStackTrace(); }
        } catch (IOException e) { e.printStackTrace(); }

        //Hides the ugly status bar
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Initiate the buttons and add listeners to them
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


    //Takes care of the button clicks
    public View.OnClickListener buttonListener = new View.OnClickListener()
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
              //  startActivity( new Intent (getApplicationContext(), Launcher.class));
                startActivity(new Intent (getApplicationContext(), Game.class));
            }

            else if(v.getId() == R.id.howToBtn)
            {
                Log.d("TextLog", "Inte implementerad än");
            }
        }
    };
}
