package komaapp.komaprojekt.scene;

/**
 * Created by Adam on 2014-11-13.
 */
        import android.app.ActionBar;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;

        import komaapp.komaprojekt.R;

public class MainMenuScene extends Activity // implements IOnMenuItemClickListener //extends BaseScene
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
/*
    private MenuScene menuChildScene;

    private final int MENU_PLAY = 0;
    private final int MENU_SETTINGS = 1;
    private final int MENU_UPGRADES = 2;

    //---------------------------------------------
    // METHODS FROM SUPERCLASS
    //---------------------------------------------

    @Override
    public void createScene()
    {
        createBackground();
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed()
    {
        //Avsluta appen avtivity.finish() funkar inte alltid
        System.exit(0);
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_MENU;
    }


    @Override
    public void disposeScene()
    {
        // TODO Auto-generated method stub
    }

    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
    {
        switch(pMenuItem.getID())
        {
            case MENU_PLAY:
                //Load Game Scene!
                SceneManager.getInstance().loadGameScene(engine);
                return true;
            case MENU_SETTINGS:
                //Load SettingsScene
                return true;
            case MENU_UPGRADES:
                //Load UpgradesScene
                return true;
            default:
                return false;
        }
    }

    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    private void createBackground()
    {
        attachChild(new Sprite(400, 240, resourcesManager.menu_background_region, vbom)
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera)
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }

    private void createMenuChildScene()
    {
        menuChildScene = new MenuScene(camera);
        menuChildScene.setPosition(400, 240);

        //Lägg till knapparna och animationer på dem
        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
        final IMenuItem settingsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_SETTINGS, resourcesManager.settings_region, vbom), 1.2f, 1);
        final IMenuItem upgradesMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_UPGRADES, resourcesManager.upgrades_region, vbom), 1.2f, 1);

        menuChildScene.addMenuItem(playMenuItem);
        menuChildScene.addMenuItem(settingsMenuItem);
        menuChildScene.addMenuItem(upgradesMenuItem);

        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);

        playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() + 100);
        settingsMenuItem.setPosition(settingsMenuItem.getX(), settingsMenuItem.getY() - 0);
        upgradesMenuItem.setPosition(upgradesMenuItem.getX(), upgradesMenuItem.getY() - 100);

        menuChildScene.setOnMenuItemClickListener(this);

        setChildScene(menuChildScene);
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huvudmeny);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.upgradeBtn){

                    startActivity(new Intent(getApplicationContext(), UpgradesScene.class));

                }
                else if (v.getId() == R.id.settingsBtn){

                    startActivity(new Intent (getApplicationContext(), SettingsScene.class));
                }
                else if (v.getId() == R.id.startBtn){
                    startActivity(new Intent (getApplicationContext(), GameScene.class));
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
