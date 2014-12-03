package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-23.
 *
 *
 */

public class SimpleEnemy extends BaseEnemy {
    public SimpleEnemy(float startX, float startY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShotManager theShotManager, int ID, float speed) {
        super(startX, startY, pTextureRegion, pVertexBufferObjectManager, theShotManager, ID, speed);
        this.setCenterPosition(startX, startY);
    }

    @Override
    void updatePosition(float dt)
    {
        this.setCenterPosition(this.getCenterX(), this.getCenterY()+dt*this.getSpeed());
    }

    @Override
    void fire() {
        //Log.d("EnemyLog", "Shot fired by ID=" + this.getID());

        this.addShotToShotManager(getCenterX(), getCenterY(), new Vector2(0f, 1f), 15f, 60f, 10f, 100f, 0, 0);
    }
}
