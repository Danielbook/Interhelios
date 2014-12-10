package komaapp.komaprojekt;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Upgrades extends Activity
{
    private Database database = new Database();
    ImageView gunsRadio, engineRadio, shieldRadio;
    RelativeLayout resetView;

    private Context ctx;

    //Updates the txt in the tables
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void updateTable()
    {
        int gunsLvl = database.getLvl("guns");
        int engineLvl = database.getLvl("engine");
        int shieldLvl = database.getLvl("shield");

        //Sets the value of all the properties
        TextView cashTxt = (TextView)findViewById(R.id.cashTxt);
        cashTxt.setText("" + database.getCash());

        //Sets the price of all the upgrades
        //If the price is -1, the sound is either at max level, or not
        //enough money to buy upgrade, sets text accordingly
        TextView gunsPrice = (TextView)findViewById(R.id.gunsPrice);
        if(database.getPrice("guns") != -1) {
            gunsPrice.setText("" + database.getPrice("guns"));
        }
        else {
            gunsPrice.setText("Maxed out!");
        }

        TextView enginePrice = (TextView)findViewById(R.id.enginePrice);
        if(database.getPrice("engine") != -1) {
            enginePrice.setText("" + database.getPrice("engine"));
        }
        else{
            enginePrice.setText("Maxed out!");
        }

        TextView shieldPrice = (TextView)findViewById(R.id.shieldPrice);
        if(database.getPrice("shield") != -1) {
            shieldPrice.setText("" + database.getPrice("shield"));
        }
        else{
            shieldPrice.setText("Maxed out!");
        }

        //Initiate buttons
        Button gunsBtn = (Button)findViewById(R.id.gunsBtn);
        Button shieldBtn = (Button)findViewById(R.id.shieldBtn);
        Button engineBtn = (Button)findViewById(R.id.engineBtn);
        Button backBtn = (Button)findViewById(R.id.backBtn);
        Button resetBtn = (Button)findViewById(R.id.resetBtn);

        Button yesBtn = (Button)findViewById(R.id.yesBtn);
        Button noBtn = (Button)findViewById(R.id.noBtn);

        //If there is not enough cash or reached max level, make the button red
        if(!database.enoughCash("guns") || database.getLvl("guns") >= 5){
            gunsBtn.setBackground(getResources().getDrawable(R.drawable.btn_buy_no));
        }
        else{
            gunsBtn.setBackground(getResources().getDrawable(R.drawable.btn_buy_xml));
        }

        if(!database.enoughCash("engine")|| database.getLvl("engine") >= 5){
            engineBtn.setBackground(getResources().getDrawable(R.drawable.btn_buy_no));
        }
        else{
            engineBtn.setBackground(getResources().getDrawable(R.drawable.btn_buy_xml));
        }

        if(!database.enoughCash("shield")|| database.getLvl("shield") >= 5){
            shieldBtn.setBackground(getResources().getDrawable(R.drawable.btn_buy_no));
        }
        else{
            shieldBtn.setBackground(getResources().getDrawable(R.drawable.btn_buy_xml));
        }

        //Add listeners to all the buttons
        gunsBtn.setOnClickListener(buttonListener);
        shieldBtn.setOnClickListener(buttonListener);
        engineBtn.setOnClickListener(buttonListener);
        backBtn.setOnClickListener(buttonListener);
        resetBtn.setOnClickListener(buttonListener);

        yesBtn.setOnClickListener(buttonListener);
        noBtn.setOnClickListener(buttonListener);

        ////RADIOBUTTONS
        switch(gunsLvl)
        {
            case 1:
            {
                gunsRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl1));
                break;
            }
            case 2:
            {
                gunsRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl2));
                break;
            }
            case 3:
            {
                gunsRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl3));
                break;
            }
            case 4:
            {
                gunsRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl4));
                break;
            }
            case 5:
            {
                gunsRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl5));
                break;
            }
        }

        switch(engineLvl)
        {
            case 1:
            {
                engineRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl1));
                break;
            }
            case 2:
            {
                engineRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl2));
                break;
            }
            case 3:
            {
                engineRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl3));
                break;
            }
            case 4:
            {
                engineRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl4));
                break;
            }
            case 5:
            {
                engineRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl5));
                break;
            }
        }

        switch(shieldLvl)
        {
            case 1:
            {
                shieldRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl1));
                break;
            }
            case 2:
            {
                shieldRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl2));
                break;
            }
            case 3:
            {
                shieldRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl3));
                break;
            }
            case 4:
            {
                shieldRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl4));
                break;
            }
            case 5:
            {
                shieldRadio.setBackground(getResources().getDrawable(R.drawable.radio_lvl5));
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ctx = getBaseContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        resetView = (RelativeLayout)findViewById(R.id.resetView);

        resetView.setVisibility(View.GONE);

        Log.d("TextLog", "Upgrade start\n");

        try { database.readFile(ctx); Log.d("TextLog", "Databasefile read!"); }
        catch (FileNotFoundException e) { Log.d("TextLog", "Could not read file!"); }
        catch (IOException e) { e.printStackTrace(); }

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Creates all the buttons, text and updates them according to database

        gunsRadio = (ImageView)findViewById(R.id.gunsRadio);
        engineRadio = (ImageView)findViewById(R.id.engineRadio);
        shieldRadio = (ImageView)findViewById(R.id.shieldRadio);

        updateTable();
    }

    private View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            if(v.getId() == R.id.gunsBtn)
            {
                Log.d("TextLog", "Guns");
                if( database.buyUpgrade("Guns") )
                {
                    MainMenu.soundManager.buttonSound();

                    //Write/read to database
                    try { database.writeFile(ctx); } catch (IOException e) { e.printStackTrace(); }
                    try { database.readFile(ctx); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.shieldBtn)
            {
                if(database.buyUpgrade("Shield"))
                {
                    MainMenu.soundManager.buttonSound();

                    //Write/read to database
                    try { database.writeFile(ctx); } catch (IOException e) { e.printStackTrace(); }
                    try { database.readFile(ctx); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.engineBtn)
            {
                MainMenu.soundManager.buttonSound();

                if( database.buyUpgrade("Engine") )
                {
                    //Write/read to database
                    try { database.writeFile(ctx); } catch (IOException e) { e.printStackTrace(); }
                    try { database.readFile(ctx); } catch (IOException e) { e.printStackTrace(); }

                    updateTable();
                }
            }

            if(v.getId() == R.id.backBtn)
            {
                MainMenu.soundManager.buttonSound();

                startActivity(new Intent (getApplicationContext(), MainMenu.class));
            }

            if(v.getId() == R.id.resetBtn)
            {
                MainMenu.soundManager.buttonSound();

                resetView.setVisibility(View.VISIBLE);
            }

            if(v.getId() == R.id.yesBtn)
            {
                MainMenu.soundManager.buttonSound();

                try
                {
                    database.newPlayer(ctx);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateTable();
                resetView.setVisibility(View.GONE);
            }

            if(v.getId() == R.id.noBtn)
            {
                MainMenu.soundManager.buttonSound();
                resetView.setVisibility(View.GONE);
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

    protected void onResume()
    {
        super.onResume();

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

    public void onBackPressed() {
    }
}
