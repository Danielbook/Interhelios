package komaapp.komaprojekt.scene;

/**
 * Created by Adam on 2014-11-13.
 */

        import android.graphics.Typeface;

        import org.andengine.engine.camera.Camera;
        import org.andengine.engine.handler.timer.ITimerCallback;
        import org.andengine.engine.handler.timer.TimerHandler;
        import org.andengine.engine.options.EngineOptions;
        import org.andengine.engine.options.ScreenOrientation;
        import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
        import org.andengine.entity.scene.IOnSceneTouchListener;
        import org.andengine.entity.scene.Scene;
        import org.andengine.entity.scene.background.Background;
        import org.andengine.entity.text.Text;
        import org.andengine.input.touch.TouchEvent;
        import org.andengine.opengl.font.Font;
        import org.andengine.opengl.font.FontFactory;
        import org.andengine.opengl.texture.TextureOptions;
        import org.andengine.ui.activity.SimpleBaseGameActivity;

        import komaapp.komaprojekt.GameActivity;
        import komaapp.komaprojekt.base.BaseScene;
        import komaapp.komaprojekt.manager.SceneManager;
        import komaapp.komaprojekt.manager.SceneManager.SceneType;

public class GameScene extends SimpleBaseGameActivity //extends BaseScene implements IOnSceneTouchListener
{

    private Camera camera;
    private static final int CAMERA_WIDTH = 768;
    private static final int CAMERA_HEIGHT = 1280;

    private Font mFont;

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new FillResolutionPolicy(), camera);
        return engineOptions;
    }

    @Override
    protected void onCreateResources() {
        this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48);
        this.mFont.load();
    }

    @Override
    protected Scene onCreateScene() {
        final Scene scene = new Scene();
        scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

        final int text_x = 100;
        final int text_y = 160;

        final Text helloWorld = new Text(text_x, text_y, this.mFont, "Hello, world!", this.getVertexBufferObjectManager());

        scene.attachChild(helloWorld);

        return scene;
    }
}
/*
    //Score
    //private int score = 0;

    //Used for displaying game controller and score text
    //private HUD gameHUD;
    //private Text scoreText;
    /* //Variables in Game
    private static final String TAG_ENTITY = "entity";
    private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
    private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
    private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM1 = "platform1";
    *
    //private Text gameOverText;
    //private boolean gameOverDisplayed = false;

    private boolean firstTouch = false;

    @Override
    public void createScene()
    {
        createBackground();
        //createHUD();
        //loadLevel(1);
        //Vad som händer när spelaren dör
        //createGameOverText();

        //Lyssna på vad som händer på skärmen
        setOnSceneTouchListener(this);
    }

    @Override
    public void onBackKeyPressed()
    {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_GAME;
    }

    //Close the game
    @Override
    public void disposeScene()
    {
        camera.setHUD(null);
        camera.setChaseEntity(null); //Stop chasing the player
        camera.setCenter(400, 240);

        // TODO code responsible for disposing scene
        // removing all game scene objects.
    }

    //Function for what to do when something is pressed on screen
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
    {
        if (pSceneTouchEvent.isActionDown())
        {

        }
        return false;
    }


    private void createGameOverText()
    {
        //gameOverText = new Text(0, 0, resourcesManager.font, "Game Over!", vbom);
    }


    private void displayGameOverText()
    {
        //camera.setChaseEntity(null);
        //gameOverText.setPosition(camera.getCenterX(), camera.getCenterY());
        //attachChild(gameOverText);
        //gameOverDisplayed = true;
    }
    //Läs in level, behandlar det mesta med spelet (OnDie, Score mm)
    private void loadLevel(int levelID)
    {

    }

/*  //Function to display score
    private void createHUD()
    {
        gameHUD = new HUD();

        //Create score HUD
        scoreText = new Text(20, 420, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        scoreText.setAnchorCenter(0, 0);
        scoreText.setText("Score: 0");
        gameHUD.attachChild(scoreText);

        camera.setHUD(gameHUD);
    }
*
    private void createBackground()
    {
        setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
    }

/*  //Fuction to add score
    private void addToScore(int i)
    {
        score += i;
        scoreText.setText("Score: " + score);
    }
*

    // ---------------------------------------------
    // INTERNAL CLASSES
    // ---------------------------------------------

/* //Function for reading contact by the player
    private ContactListener contactListener()
    {
        ContactListener contactListener = new ContactListener()
        {
            public void beginContact(Contact contact)
            {

            }

            public void endContact(Contact contact)
            {

            }

            public void preSolve(Contact contact, Manifold oldManifold)
            {

            }

            public void postSolve(Contact contact, ContactImpulse impulse)
            {

            }
        };
        return contactListener;
    }*
}
*/