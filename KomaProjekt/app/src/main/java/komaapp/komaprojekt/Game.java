package komaapp.komaprojekt;

import android.content.Intent;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
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
import komaapp.komaprojekt.GameLogic.ShotManager;

public class Game extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    private Camera camera;
    public static  int CAMERA_WIDTH;
    public static  int CAMERA_HEIGHT;
    private float backX = 0, backY1 = 0,backY2= -3000;

    private Sprite pauseButton, resumeButton, restartButton, quitButton;

    private Font mFont;

    //TEXTURES
    private BitmapTextureAtlas texAtlas;

    private ITextureRegion xWing_tex, background_tex_clouds1,background_tex_clouds2, background_tex_stars, pause_tex;
    private ITextureRegion resume_tex, restart_tex, quit_tex;
    private MovingBackground background_clouds1,background_clouds2, background_stars;
   // private RelativeLayout pause;

    private Player player;

    //ENEMIES
    private EnemyManager enemyManager;
    private ShotManager shotManager;

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
                    Log.d("ShotLog", "Player tried to shoot!");
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
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        CAMERA_HEIGHT= displayMetrics.heightPixels;
        CAMERA_WIDTH= displayMetrics.widthPixels;

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
        pause_tex = loadITextureRegion("btn_pause.png", 146, 146);

        resume_tex = loadITextureRegion("btn_resume.png",632, 306 );
        restart_tex = loadITextureRegion("btn_restart.png",632, 306 );
       quit_tex = loadITextureRegion("btn_quit.png",632, 306 );

        background_tex_stars = loadITextureRegion("bkgrnd_stars.png", CAMERA_WIDTH, 3000);

        xWing_tex = loadITextureRegion("xwing_sprite.png", 200, 217);

        createHUD();
    }

    @Override
    protected Scene onCreateScene()
    {
        //Create pause background rectangle
        final Rectangle pauseRectangle = new Rectangle (0, 0 , CAMERA_WIDTH, CAMERA_HEIGHT, this.getVertexBufferObjectManager());
        pauseRectangle.setColor(org.andengine.util.color.Color.BLACK);
        pauseRectangle.setAlpha(0.8f);

        //Create the scene
        final Scene scene = new Scene();
        scene.setOnSceneTouchListener(this);

        //The backgrounds
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
        this.shotManager = new ShotManager( scene, this.getVertexBufferObjectManager() );
        this.enemyManager = new EnemyManager( scene, this.getVertexBufferObjectManager(), this.shotManager );
        enemyManager.addEnemyTexture(loadITextureRegion("tie_sprite_small.png", 200, 257), "tie_fighter");

        //Instantiate the player object
        player = new Player(camera.getWidth()/2, 1000, xWing_tex, this.getVertexBufferObjectManager())
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

                background_clouds1.updatePosition(v);
                background_clouds2.updatePosition(v);

                enemyManager.update(currentTime, v);
                shotManager.update(v);
            }

            @Override
            public void reset() {}
        });

        resumeButton = new Sprite (((CAMERA_WIDTH/2)-316), ((CAMERA_HEIGHT/2)-600), this.resume_tex, this.getVertexBufferObjectManager())
        {
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
            {
                if (touchEvent.isActionDown())
                {
                    scene.detachChild(resumeButton);
                    scene.detachChild(restartButton);
                    scene.detachChild(quitButton);
                    scene.detachChild(pauseRectangle);
                    scene.attachChild(pauseButton);
                    scene.setIgnoreUpdate(false);

                }
                return true;
            };
        };
        scene.registerTouchArea(resumeButton);

        //Button to restart the game, calls startActivity once again
        restartButton = new Sprite (((CAMERA_WIDTH/2)-316),((CAMERA_HEIGHT/2)-153), this.restart_tex, this.getVertexBufferObjectManager())
        {
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
            {
                if (touchEvent.isActionDown())
                {
                    startActivity(new Intent(getApplicationContext(), Game.class));
                }
                return true;
            };
        };
        scene.registerTouchArea(restartButton);

        //Button to quit game and go to main menu
        quitButton = new Sprite (((CAMERA_WIDTH/2)-316), ((CAMERA_HEIGHT/2)+294), this.quit_tex, this.getVertexBufferObjectManager())
        {
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
            {
                if (touchEvent.isActionDown())
                {
                    startActivity(new Intent(getApplicationContext(), Huvudmeny.class));
                }
                return true;
            };
        };
        scene.registerTouchArea(quitButton);

        pauseButton = new Sprite(0, 0, this.pause_tex, this.getVertexBufferObjectManager())
        {
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
            {
                if (touchEvent.isActionDown())
                {
                    scene.detachChild(pauseButton);
                    scene.setIgnoreUpdate(true);
                    scene.attachChild(pauseRectangle);
                    scene.attachChild(resumeButton);
                    scene.attachChild(restartButton);
                    scene.attachChild(quitButton);
                }
                return true;
            };
        };
        scene.registerTouchArea(pauseButton);
        scene.attachChild(pauseButton);

        return scene;
    }

    @Override
    public boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent) {
         if (touchEvent.isActionDown() || touchEvent.isActionMove())
        {
            Log.d("TextLog", "Screen touched at X=" + touchEvent.getX() + ", Y=" + touchEvent.getY());
            //Execute touch event
            player.setTargetPosition(new Vector2(touchEvent.getX(), touchEvent.getY()));
            player.setIsMoving(true);
        }
        else if (touchEvent.isActionUp() || touchEvent.isActionOutside())
        {
            player.setIsMoving(false);
        }
        return false;
    }
}