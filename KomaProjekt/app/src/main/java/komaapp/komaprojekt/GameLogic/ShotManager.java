package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;
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
    private ITextureRegion shotTex, missileTex;

    public ShotManager(Scene scene, VertexBufferObjectManager VBOmanager, ITextureRegion shotTex, ITextureRegion missileTex)
    {
        this.scene = scene;
        this.VBOmanager = VBOmanager;
        this.shotTex = shotTex;
        this.shotList = new ArrayList<Shot>(MAX_SHOTS);
        this.missileTex = missileTex;
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
/*
    public void addShot(float pX, float pY, Vector2 direction, float pWidth, float pHeight, float speed, final float pRed, final float pGreen, final float pBlue)
    {
        addShot(new Shot(pX, pY, direction, pWidth, pHeight, speed, VBOmanager, pRed, pGreen, pBlue) );
    }
*/
    public void addShot(float pX, float pY, Vector2 direction, float speed)
    {
        addShot(new Shot(pX, pY, direction, speed, VBOmanager, shotTex));
    }


    public void addMissile(float pX, float pY, Vector2 direction, float initialSpeed)
    {
        addShot(new Missile(pX, pY, direction, initialSpeed, VBOmanager, missileTex));
    }

    public void addShot(Shot shot)
    {
        Log.d("ShotLog", "Shot added: X=" + shot.getCenterX() + ", Y=" + shot.getCenterY() );
        shotList.add(shot);
        scene.attachChild(shot);
    }

    public ArrayList<Shot> getShots()
    {
        return this.shotList;
    }

    public void removeShots(ArrayList<Shot> shotsToRemove) {
        for (Shot shot : shotsToRemove)
        {
            shot.detachSelf();
            if (!shot.isDisposed()) shot.dispose();
        }

        shotList.removeAll(shotsToRemove);
    }
}
