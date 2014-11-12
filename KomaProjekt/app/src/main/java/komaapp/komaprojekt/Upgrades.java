package komaapp.komaprojekt;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class Upgrades extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        final LinearLayout gunsRadio = (LinearLayout)findViewById(R.id.gunsRadio);
        gunsRadio.setVisibility(View.GONE);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(v.getId() == R.id.gunsBtn)
                {
                    Log.d("TextLog", "Guns");

                    if(gunsRadio.getVisibility() == View.GONE)
                    {
                        gunsRadio.setVisibility(View.VISIBLE);
                    }

                    else if(gunsRadio.getVisibility() == View.VISIBLE)
                    {
                        gunsRadio.setVisibility(View.GONE);
                    }


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

        gunsBtn.setOnClickListener(buttonListener);
        shieldBtn.setOnClickListener(buttonListener);
        engineBtn.setOnClickListener(buttonListener);
        backBtn.setOnClickListener(buttonListener);

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
