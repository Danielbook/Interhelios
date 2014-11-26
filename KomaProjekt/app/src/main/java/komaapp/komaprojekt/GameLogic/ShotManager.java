package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

/**
 * Created by benjamin on 14-11-26.
 */
public class ShotManager {
    // Shot list
    private ArrayList<Shot> shotList;
    private final int MAX_SHOTS = 200;

    // Reference to the active scene, for attaching enemies to the scene
    private Scene scene;
    private VertexBufferObjectManager VBOmanager;

    public ShotManager(Scene scene, VertexBufferObjectManager VBOmanager)
    {
        this.scene = scene;
        this.VBOmanager = VBOmanager;

        this.shotList = new ArrayList<Shot>(MAX_SHOTS);
    }

    public void update(float dt)
    {
        //Update each Shot
        for (int i = 0; i < shotList.size(); i++)
        {
            Shot shot = shotList.get(i);

            shot.update(dt);
            if (ScreenChecks.isOutOfScreen(shot.getCenterX(), shot.getCenterY(), shot.getWidth(), shot.getHeight()))
            {
                //Remove shot
                shotList.remove(shot);
                shot.detachSelf();
                shot.dispose();
                i--;
            }
        }
    }

    public void addShot(float pX, float pY, Vector2 direction, float pWidth, float pHeight, final float pRed, final float pGreen, final float pBlue)
    {
        addShot(new Shot(pX, pY, direction, pWidth, pHeight, VBOmanager, pRed, pGreen, pBlue) );
    }

    public void addShot(Shot shot)
    {
        Log.d("ShotLog", "Shot added: X=" + shot.getCenterX() + ", Y=" + shot.getCenterY() );
        shotList.add(shot);
        scene.attachChild(shot);
    }
}
