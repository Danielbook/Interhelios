package komaapp.komaprojekt;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


public class Huvudmeny extends Activity {

    private DBHandler dbSettings = new DBHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huvudmeny);

        dbSettings.open();

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.upgradeBtn){

                    startActivity(new Intent (getApplicationContext(), Upgrades.class));

                }
                else if (v.getId() == R.id.settingsBtn){

                   startActivity(new Intent (getApplicationContext(), Settings.class));
                }
                else if (v.getId() == R.id.startBtn){
                    startActivity(new Intent (getApplicationContext(), Game.class));
                }
            }
        };

        Button startBtn = (Button)findViewById(R.id.startBtn);
        Button upgradeBtn = (Button)findViewById(R.id.upgradeBtn);
        Button settingsBtn = (Button)findViewById(R.id.settingsBtn);

        startBtn.setOnClickListener(buttonListener);
        upgradeBtn.setOnClickListener(buttonListener);
        settingsBtn.setOnClickListener(buttonListener);


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
