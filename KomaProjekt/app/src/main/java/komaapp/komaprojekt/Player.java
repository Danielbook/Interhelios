package komaapp.komaprojekt;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by benjamin on 14-11-12.
 */
public class Player extends Sprite{

    private Vector2 targetPosition = new Vector2(0, 0);
    private Vector2 velocity_dir = new Vector2(0, 0);
    private float speed = 1.0f;

    ///// INTERFACE

    public Player(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    public float getCenterX() {return getX() + getWidth()/2;}
    public float getCenterY() {return getY() + getHeight()/2;}
    public Vector2 getCenterPosition() {return new Vector2(getCenterX(), getCenterY());}

    public void setCenterPosition(float X, float Y) {setPosition(X-getWidth()/2, Y-getHeight()/2);}

    public void setCenterPosition(Vector2 pos) {setCenterPosition(pos.x, pos.y);}

    public void update(float dt)
    {
        if (velocity_dir.x != 0 && velocity_dir.y!= 0) setCenterPosition(getCenterX() + velocity_dir.x*dt, getCenterY() + velocity_dir.y*dt);
    }

    public void setVelocityDirection(Vector2 vec)
    {
        velocity_dir = vec;
    }

    public void setTargetPosition(float tx, float ty)
    {
        
    }

}
