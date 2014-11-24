package komaapp.komaprojekt.GameLogic;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-23.
 *
 * Abstract base class for enemies. Extend it to create enemy types
 */
abstract class BaseEnemy extends Sprite {

    private final int ID;

    protected BaseEnemy(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int ID) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.ID = ID;
    }

    abstract void update(float dt);
    abstract void fire();
    abstract void destroy();

    protected int getID() { return this.ID; } // Basic identifying integer, unique for each spawned enemy
    public float getCenterX()
    {
        return getX() + getWidth() / 2;
    }
    public float getCenterY()
    {
        return getY() + getHeight() / 2;
    }

    protected void setCenterPosition(float X, float Y) { setPosition(X - getWidth() / 2, Y - getHeight() / 2); }


}