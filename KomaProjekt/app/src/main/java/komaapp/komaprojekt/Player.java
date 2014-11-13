package komaapp.komaprojekt;

import android.util.Log;

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
    private boolean isMoving = false;
    private float maxSpeed = 600.0f;
    private float currentSpeed = 0.0f;

    ///// INTERFACE

    public Player(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX-pTextureRegion.getWidth()/2, pY-pTextureRegion.getHeight()/2, pTextureRegion, pVertexBufferObjectManager);

    }

    public float getCenterX() { return getX() + getWidth()/2; }
    public float getCenterY() { return getY() + getHeight()/2; }
    public Vector2 getCenterPosition() { return new Vector2(getCenterX(), getCenterY()); }

    public void setCenterPosition(float X, float Y) { setPosition(X-getWidth()/2, Y-getHeight()/2); }
    public void setCenterPosition(Vector2 pos) { setCenterPosition(pos.x, pos.y); }

    private void updatePosition(float dt)
    {
        velocity_dir = ( getCenterPosition().sub( targetPosition ).nor() ).mul(-1);
        float dst = ( getCenterPosition().sub( targetPosition ) ).len();

        if (dst < 0.01f) //Close enough, no movement
        {
            currentSpeed = 0.0f;
            return;
        }

        else
        {
            if (isMoving)
            {
                currentSpeed = maxSpeed + 1.5f*dst;
                if (currentSpeed*dt > dst) currentSpeed = dst/dt;
            }

            else
            {
                if (currentSpeed*dt > dst) currentSpeed = dst/dt;
                else if (currentSpeed > 0.0f) currentSpeed -= dt*maxSpeed;

                if (currentSpeed < 0.0f) currentSpeed = 0.0f;
            }

            setCenterPosition( getCenterPosition().add( velocity_dir.mul(currentSpeed*dt) ) );
        }
    }

    private void updateRotation()
    {
        float angle = (velocity_dir.x)*currentSpeed*0.0004f;
        setRotation(angle);
    }

    public void update(float dt)
    {
        updatePosition(dt);
        updateRotation();

    }

    public void setVelocityDirection(Vector2 vec) { velocity_dir = vec; }

    public void setTargetPosition(float tx, float ty) {setTargetPosition(new Vector2(tx, ty)); }
    public void setTargetPosition(Vector2 targetPos) { targetPosition = targetPos; }

    public void setIsMoving(boolean willMove) { isMoving = willMove; }
    public boolean isMoving() { return isMoving; }
}
