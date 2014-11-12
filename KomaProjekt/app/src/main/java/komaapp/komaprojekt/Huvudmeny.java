package komaapp.komaprojekt;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class Huvudmeny extends Activity {

    protected Vector<database> db;

    public void readFile() throws IOException {
        String line;
        StringTokenizer tokens;

        InputStream is = getAssets().open("settings.txt");

       // BufferedReader infile = new BufferedReader(new FileReader(fil));

        BufferedReader infile = new BufferedReader(new InputStreamReader(is));

        while( ( line = infile.readLine() ) != null)
        {
            tokens = new StringTokenizer(line, " ");

            String setting = tokens.nextToken();

            int val = Integer.parseInt(tokens.nextToken());

            db.add(new database(setting, val));
        }

        infile.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huvudmeny);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

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
                    try
                    {
                        readFile();
                        Log.d("TextLog", "Databasefile read!");
                    }

                    catch (IOException e)
                    {
                        Log.d("TextLog", "Could not read file!");
                    }
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

    public void onDestroy()
    {

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
