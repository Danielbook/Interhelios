package komaapp.komaprojekt;

/**
 * Created by Adam on 2014-11-13.
*/

        import java.io.IOException;

        import org.andengine.engine.Engine;
        import org.andengine.engine.LimitedFPSEngine;
        import org.andengine.engine.camera.BoundCamera;
        import org.andengine.engine.handler.timer.ITimerCallback;
        import org.andengine.engine.handler.timer.TimerHandler;
        import org.andengine.engine.options.EngineOptions;
        import org.andengine.engine.options.ScreenOrientation;
        import org.andengine.engine.options.WakeLockOptions;
        import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
        import org.andengine.entity.scene.Scene;
        import org.andengine.ui.activity.BaseGameActivity;

        import android.view.KeyEvent;

        import komaapp.komaprojekt.manager.ResourcesManager;
        import komaapp.komaprojekt.manager.SceneManager;

public class GameActivity extends BaseGameActivity //Vad vara detta?
{
    private BoundCamera camera;
    private ResourcesManager resourcesManager; //Behövs detta?

    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions)
    {
        return new LimitedFPSEngine(pEngineOptions, 60);
    }

    public EngineOptions onCreateEngineOptions()
    {
        camera = new BoundCamera(0, 0, 800, 480);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), this.camera);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        return false;
    }

    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
    {
        ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
        pOnCreateResourcesCallback.onCreateResourcesFinished();
        resourcesManager = ResourcesManager.getInstance(); //Behövs detta?
    }

    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
    {
        //Set Splash Screen when app is created
        SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);

    }
    //Vad som händer under tiden SplashScreen visas
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
    {
        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
                // load menu resources, create menu scene
                // set menu scene using scene manager
                // disposeSplashScene();
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    //Ser till att appen avslutas när spelet avslutas
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.exit(0);
    }
}