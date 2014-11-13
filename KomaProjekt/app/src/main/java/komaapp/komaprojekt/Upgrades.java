package komaapp.komaprojekt;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class Upgrades extends Activity
{

    protected Vector<Database> db = new Vector<Database>();

    // Read setting file and storing the
    // database settings in a vector
    public void readFile() throws IOException
    {
        String line;
        StringTokenizer tokens;

        InputStream is = getAssets().open("settings.txt");

        BufferedReader infile = new BufferedReader(new InputStreamReader(is));

        while( ( line = infile.readLine() ) != null)
        {
            tokens = new StringTokenizer(line, " ");

            String setting = tokens.nextToken();

            int val = Integer.parseInt(tokens.nextToken());

            //Log.d("TextLog","Setting: " + setting  + ", Value: " + val);

            db.add(new Database(setting, val));
        }

        infile.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        Log.d("TextLog", "Upgrade start\n");

        try
        {
            readFile();
            Log.d("TextLog", "Databasefile read!");
        }

        catch (IOException e)
        {
            Log.d("TextLog", "Could not read file!");
        }

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        View.OnClickListener buttonListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(v.getId() == R.id.gunsBtn)
                {
                    Log.d("TextLog", "Guns");

                    /*if(gunsRadio.getVisibility() == View.GONE)
                    {
                        gunsRadio.setVisibility(View.VISIBLE);
                    }

                    else if(gunsRadio.getVisibility() == View.VISIBLE)
                    {
                        gunsRadio.setVisibility(View.GONE);
                    }*/
                }

                if(v.getId() == R.id.shieldBtn)
                {
                    Log.d("TextLog", "Shield");
                }

                if(v.getId() == R.id.engineBtn)
                {
                    Log.d("TextLog", "Engine");
                }

                if(v.getId() == R.id.backBtn)
                {
                    startActivity(new Intent (getApplicationContext(), Huvudmeny.class));
                }
            }
        };

        Button gunsBtn = (Button)findViewById(R.id.gunsBtn);
        Button shieldBtn = (Button)findViewById(R.id.shieldBtn);
        Button engineBtn = (Button)findViewById(R.id.engineBtn);
        Button backBtn = (Button)findViewById(R.id.backBtn);

        TextView cashTxt = (TextView)findViewById(R.id.cashTxt);

        for(int i = 0; i < db.size(); i++)
        {
            Log.d("TextLog", " " + i);
            if(db.elementAt(i).getSetting().equalsIgnoreCase("cash"))
            {
                cashTxt.setText(String.valueOf( db.elementAt(i).getVal() ));
            }
        }

        gunsBtn.setOnClickListener(buttonListener);
        shieldBtn.setOnClickListener(buttonListener);
        engineBtn.setOnClickListener(buttonListener);
        backBtn.setOnClickListener(buttonListener);

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
