package komaapp.komaprojekt;

import android.graphics.Typeface;
import android.util.Log;

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

import komaapp.komaprojekt.GameLogic.EnemyManager;
import komaapp.komaprojekt.GameLogic.MovingBackground;
import komaapp.komaprojekt.GameLogic.Player;
import komaapp.komaprojekt.GameLogic.Shot;
import komaapp.komaprojekt.GameLogic.ShotManager;

public class Game extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    private Camera camera;
    public static final int CAMERA_WIDTH = 1200;
    public static final int CAMERA_HEIGHT = 1920;
    private float backX = 0, backY1 = 0,backY2= -3000;

    private Font mFont;

    //TEXTURES
    private BitmapTextureAtlas texAtlas;

    private ITextureRegion xWing_tex, background_tex_clouds1,background_tex_clouds2, background_tex_stars;
    private MovingBackground background_clouds1,background_clouds2, background_stars;

    private Player player;
    private ShotManager playerShotManager;

    //ENEMIES
    private EnemyManager enemyManager;
    private ShotManager enemyShotManager;

    //DATABASE
    private Database database = new Database();
    private int engineLvl;
    private int gunsLvl;
    private int shieldLvl;

    private int sound;
    private int music;
    private int cash;

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

        final Sprite mainHud = new Sprite(0, CAMERA_HEIGHT-132, loadITextureRegion("HUD.png", 768, 132),this.getVertexBufferObjectManager() )
        {
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
            {
                return true;
            };
        };

        final Sprite shootBtn = new Sprite(24, CAMERA_HEIGHT-108, loadITextureRegion("button_shoot.png", 96, 96), this.getVertexBufferObjectManager() )
        {
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
            {
                if (touchEvent.isActionDown())
                {
                    //SHOOT
                    Log.d("ShotLog", "Player tried to shoot!");
                    playerShotManager.addShot(new Shot(player.getCenterX() - player.getWidth() / 2, player.getCenterY(), new Vector2(0f, -1f), 15f, 60f, this.getVertexBufferObjectManager(), 100f, 0, 0));
                    playerShotManager.addShot(new Shot(player.getCenterX() + player.getWidth() / 2, player.getCenterY(), new Vector2(0f, -1f), 15f, 60f, this.getVertexBufferObjectManager(), 100f, 0, 0));
                }
                return true;
            };
        };

        hud.registerTouchArea(shootBtn);
        hud.registerTouchArea(mainHud);
        hud.attachChild(mainHud);
        hud.attachChild(shootBtn);

        camera.setHUD(hud);
    }

    @Override
    public EngineOptions onCreateEngineOptions()
    {
        //final DisplayMetrics displayMetrics = new DisplayMetrics();
       // this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

       // CAMERA_HEIGHT= displayMetrics.heightPixels;
        //CAMERA_WIDTH= displayMetrics.widthPixels;

        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        LimitedFPSEngine engine = new LimitedFPSEngine(engineOptions, 60);
        return engine.getEngineOptions();
    }

    @Override
    protected void onCreateResources()
    {
        //FONTS
        this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48);
        this.mFont.load();

        background_tex_clouds1 = loadITextureRegion("bkgrnd_clouds.png", CAMERA_WIDTH, 3000);

        background_tex_clouds2 = loadITextureRegion("bkgrnd_clouds.png", CAMERA_WIDTH, 3000);

        background_tex_stars = loadITextureRegion("bkgrnd_stars.png", CAMERA_WIDTH, 3000);

        xWing_tex = loadITextureRegion("xwing_sprite.png", 200, 217);

        createHUD();
    }

    @Override
    protected Scene onCreateScene()
    {
        //Create the scene
        final Scene scene = new Scene();
        scene.setOnSceneTouchListener(this);

        //The background sprite

        background_clouds1 = new MovingBackground(backX, backY1, this.background_tex_clouds1, this.getVertexBufferObjectManager());
        background_clouds2 = new MovingBackground(backX, backY2, this.background_tex_clouds2, this.getVertexBufferObjectManager());

        background_stars = new MovingBackground(backX, backY1, this.background_tex_stars, this.getVertexBufferObjectManager());

        scene.attachChild(background_stars);
        scene.attachChild(background_clouds1);
        scene.attachChild(background_clouds2);

        //FPS setup
        final FPSCounter fpsCounter = new FPSCounter();
        this.mEngine.registerUpdateHandler(fpsCounter);
        final Text fpsText = new Text(10, 10, this.mFont, "FPS: ", 10, this.getVertexBufferObjectManager());
        scene.attachChild(fpsText);

        //ENEMY MANAGEMENT
        this.enemyShotManager = new ShotManager( scene, this.getVertexBufferObjectManager() );
        this.enemyManager = new EnemyManager( scene, this.getVertexBufferObjectManager(), this.enemyShotManager);
        enemyManager.addEnemyTexture(loadITextureRegion("tie_sprite_small.png", 200, 257), "tie_fighter");

        //Instantiate the player object
        player = new Player(camera.getWidth()/2, 1000, xWing_tex, this.getVertexBufferObjectManager(), engineLvl)
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

        //SHOT MANAGMENT
        this.playerShotManager = new ShotManager( scene, this.getVertexBufferObjectManager() );

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
              }

            @Override
            public void reset() {}
        });

        return scene;
    }

    @Override
    public boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent) {
        //if (!touch in HUD)
        if (touchEvent.isActionDown() || touchEvent.isActionMove())
        {
            //Execute touch event
            player.setTargetPosition(new Vector2(touchEvent.getX(), touchEvent.getY()));
            player.setIsMoving(true);

            //player.setCenterPosition(touchEvent.getX(),touchEvent.getY());
        } else if (touchEvent.isActionUp() || touchEvent.isActionOutside())
        {
            player.setIsMoving(false);
        }
        return false;
    }
}