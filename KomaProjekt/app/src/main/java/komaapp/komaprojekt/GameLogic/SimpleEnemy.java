package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-23.
 *
 *
 */

public class SimpleEnemy extends BaseEnemy {
    public SimpleEnemy(float startX, float startY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int ID, float speed) {
        super(startX, startY, pTextureRegion, pVertexBufferObjectManager, ID, speed);
        this.setCenterPosition(startX, startY);
    }

    @Override
    void update(float dt) { this.setCenterPosition(this.getCenterX(), this.getCenterY()+dt*this.getSpeed()); }

    @Override
    void fire() { Log.d("EnemyLog", "Shot fired by ID=" + this.getID()); }

    @Override
    void destroy() { Log.d("EnemyLog", "ID=" + this.getID() + " destroyed."); }
}
