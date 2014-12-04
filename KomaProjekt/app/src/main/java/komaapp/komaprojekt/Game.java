package komaapp.komaprojekt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.badlogic.gdx.math.Vector2;

import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import java.io.IOException;

import komaapp.komaprojekt.GameLogic.Collision.CollisionManager;
import komaapp.komaprojekt.GameLogic.EnemyManager;
import komaapp.komaprojekt.GameLogic.MovingBackground;
import komaapp.komaprojekt.GameLogic.Player;
import komaapp.komaprojekt.GameLogic.ShotManager;

public class Game extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    private Camera camera;
    public static final int CAMERA_WIDTH = 1200;
    public static final int CAMERA_HEIGHT = 1920;
    private float backX = 0, backY1 = 0,backY2= -1920;

    public static Text missileTimerText;
    public static Sprite shootBtn;
    private Font mFont;

    //GAME OVER OVERLAY
    private RelativeLayout layoutGameOver;
    private Button btnPlayAgain, btnMainMenu;

    //TEXTURES
    private BitmapTextureAtlas texAtlas;

    private ITextureRegion xWing_tex, background_tex_clouds1, background_tex_stars;
    private MovingBackground background_clouds1,background_clouds2, background_stars;

    private Player player;
    private ShotManager playerShotManager;

    Context ctx;

    //ENEMIES
    private EnemyManager enemyManager;
    private ShotManager enemyShotManager;

    //DATABASE
    public static Database database = new Database();
    private int engineLvl;
    private int gunsLvl;
    private int shieldLvl;

    private int sound;
    private int music;
    private int cash;

    //Music and sounds
    //private Music music;

    private ITextureRegion loadITextureRegion(String filename, int width, int height)
    {
        //GRAPHICS
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        texAtlas = new BitmapTextureAtlas(getTextureManager(), width, height, TextureOptions.DEFAULT);
        ITextureRegion tex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texAtlas, this, filename, 0, 0);
        texAtlas.load();

        return tex;
    }

    private void createHUD()
    {
        HUD hud = new HUD();

/*        final Sprite mainHud = new Sprite(0, CAMERA_HEIGHT-132, loadITextureRegion("HUD.png", 1200, 132),this.getVertexBufferObjectManager() )
        {
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
            {
                return true;
            }
        };*/

        shootBtn = new Sprite(24, CAMERA_HEIGHT-363, loadITextureRegion("btn_rocket.png", 363, 363), this.getVertexBufferObjectManager() )
        {
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
            {
                if (touchEvent.isActionDown())
                {
                    //SHOOT
                    Log.d("ShotLog", "Player tried to shoot!");
                    player.shootMissile();
                }
                return true;
            }
        };

        Font daFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 512, 512, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 96, Color.WHITE.hashCode());
        daFont.load();

        missileTimerText = new Text(shootBtn.getX()+shootBtn.getWidth()/2 - 20f, shootBtn.getY()+shootBtn.getHeight()/2 - 20f, daFont, "", 2, this.getVertexBufferObjectManager());
        missileTimerText.setColor(0f, 20f, 80f);

        hud.registerTouchArea(shootBtn);
        hud.attachChild(missileTimerText);
        //hud.registerTouchArea(mainHud);
        //hud.attachChild(mainHud);
        hud.attachChild(shootBtn);

        camera.setHUD(hud);
    }

    @Override
    public EngineOptions onCreateEngineOptions()
    {
        /*final DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        CAMERA_HEIGHT= displayMetrics.heightPixels;
        CAMERA_WIDTH= displayMetrics.widthPixels;*/

        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        LimitedFPSEngine engine = new LimitedFPSEngine(engineOptions, 60);
        //engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        return engine.getEngineOptions();
    }

    @Override
    protected void onCreateResources()
    {
        //FONTS
        this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48);
        this.mFont.load();

        background_tex_clouds1 = loadITextureRegion("bkgrnd_clouds.png", 1200, 1920);
        background_tex_stars = loadITextureRegion("bkgrnd_stars.png", 1200, 1920);

        xWing_tex = loadITextureRegion("xwing_sprite.png", 200, 217);

        createHUD();

        //Music and sounds
        /*try{
            music = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "mfx/Presenterator.ogg");
            music.setLooping(true);
        }
        catch(IOException e){
            e.printStackTrace();
        }*/
       //music.play();

    }

    @Override
    protected Scene onCreateScene()
    {
        //Create the scene
        final Scene scene = new Scene();
        scene.setOnSceneTouchListener(this);

        ctx = getBaseContext();

        try {
            database.readFile(ctx);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gunsLvl = database.getLvl("guns");
        engineLvl = database.getLvl("engine");
        shieldLvl = database.getLvl("shield");

        //TODO add an overlay on death, chose to play again or go to main menu?

        //The background sprite
        background_clouds1 = new MovingBackground(backX, backY1, this.background_tex_clouds1, this.getVertexBufferObjectManager());
        background_clouds2 = new MovingBackground(backX, backY2, this.background_tex_clouds1, this.getVertexBufferObjectManager());

        background_stars = new MovingBackground(backX, backY1, this.background_tex_stars, this.getVertexBufferObjectManager());

        scene.attachChild(background_stars);
        scene.attachChild(background_clouds1);
        scene.attachChild(background_clouds2);

        //TODO Add current cash in the upper right corner

        //FPS setup
        final FPSCounter fpsCounter = new FPSCounter();
        this.mEngine.registerUpdateHandler(fpsCounter);
        final Text fpsText = new Text(10, 10, this.mFont, "FPS: ", 10, this.getVertexBufferObjectManager());
        scene.attachChild(fpsText);

        //ENEMY MANAGEMENT
        this.enemyShotManager = new ShotManager( scene, this.getVertexBufferObjectManager() );
        this.enemyManager = new EnemyManager( scene, this.getVertexBufferObjectManager(), this.enemyShotManager);
        enemyManager.addEnemyTexture(loadITextureRegion("tie_sprite_small.png", 200, 257), "tie_fighter");

        //SHOT MANAGMENT
        this.playerShotManager = new ShotManager( scene, this.getVertexBufferObjectManager() );

        //Instantiate the player object
        player = new Player(camera.getWidth()/2, 1000, xWing_tex, this.getVertexBufferObjectManager(), playerShotManager, gunsLvl, engineLvl, shieldLvl)
        {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                if (pSceneTouchEvent.isActionDown())
                {
                    Log.d("TextLog", "Player sprite touched.");
                }
                return true;
            }
        };
        scene.attachChild(player);

        scene.registerUpdateHandler(new IUpdateHandler()
        {
            float currentTime = 0;

            @Override
            public void onUpdate(float v)
            {
                currentTime += v;

                fpsText.setText(String.format("FPS: %.2f", fpsCounter.getFPS()));
                player.update(v);
                playerShotManager.update(v);

                background_clouds1.updatePosition(v);
                background_clouds2.updatePosition(v);

                enemyManager.update(currentTime, v);
                enemyShotManager.update(v);

                /* COLLISION DETECTION */

                // 1. Check for collisions between player and enemies
                CollisionManager.collidePlayerWithEnemies(player, enemyManager);

                // 2. Check for collision between player and enemy shots
                CollisionManager.collidePlayerWithShots(player, enemyShotManager);

                // 3. Check for collision between enemies and player shots
                CollisionManager.collideEnemiesWithShots(enemyManager, playerShotManager);

                //DO SOMETHING IF PLAYER DIES
                if(Player.health <= 0)
                {
                    try {
                        database.writeFile(ctx);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    startActivity(new Intent(getApplicationContext(), Huvudmeny.class));
                }
              }

            @Override
            public void reset() {}
        });

        //this.music.play();

        return scene;
    }

    @Override
    public boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent) {
        //if (!touch in HUD)
        if (touchEvent.isActionDown() || touchEvent.isActionMove())
        {
            //Execute touch event
            player.setTargetPosition(new Vector2(touchEvent.getX(), touchEvent.getY()));
            player.setTouchActive(true);

            //player.setCenterPosition(touchEvent.getX(),touchEvent.getY());
        } else if (touchEvent.isActionUp() || touchEvent.isActionOutside())
        {
            player.setTouchActive(false);
        }
        return false;
    }
}