package komaapp.komaprojekt;

import android.graphics.Typeface;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class Game extends SimpleBaseGameActivity {

    private Camera camera;
    private static final int CAMERA_WIDTH = 768;
    private static final int CAMERA_HEIGHT = 1280;

    private Font mFont;

    @Override
    public EngineOptions onCreateEngineOptions()
    {
        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
        new FillResolutionPolicy(), camera);
        return engineOptions;
    }

    @Override
    protected void onCreateResources()
    {
        this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48);
        this.mFont.load();
    }

    @Override
    protected Scene onCreateScene()
    {
        final Scene scene = new Scene();
        scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

        final int text_x = 100;
        final int text_y = 160;

        final Text helloWorld = new Text(text_x, text_y, this.mFont, "Hello, world!", this.getVertexBufferObjectManager());

        scene.attachChild(helloWorld);

        return scene;
    }

}