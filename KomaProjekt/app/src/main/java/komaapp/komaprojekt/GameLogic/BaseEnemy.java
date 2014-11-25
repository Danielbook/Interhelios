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
    private final float speed;

    protected BaseEnemy(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int ID, float speed) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.ID = ID;
        this.speed = speed;
    }

    abstract void update(float dt);
    abstract void fire();
    abstract void destroy();

    protected int getID() { return this.ID; } // Basic identifying integer, unique for each spawned enemy
    protected float getSpeed() { return this.speed; }
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