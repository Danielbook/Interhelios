package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import komaapp.komaprojekt.Game;

/**
 * Created by benjamin on 14-11-12.
 */

public class Player extends Sprite {

    private Vector2 targetPosition = new Vector2(0, 0);
    private Vector2 velocity_dir = new Vector2(0, 0);
    private boolean isMoving = false;
    private float maxSpeed;
    private float currentSpeed = 0.0f;

    //DATABASE
    private int engineLvl;
    private int gunsLvl;
    private int shieldLvl;

    private int cash;
    private int sound;
    private int music;

    ///// INTERFACE

    public Player(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int engineLvl)
    {
        super(pX - pTextureRegion.getWidth() / 2, pY - pTextureRegion.getHeight() / 2, pTextureRegion, pVertexBufferObjectManager);
        Log.d("TextLog", "Engine level: " + engineLvl);
        switch (engineLvl){
            case 1:{
                maxSpeed = 200.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
            case 2:{
                maxSpeed = 600.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
            case 3:{
                maxSpeed = 1000.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
            case 4:{
                maxSpeed = 1400.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
            case 5:{
                maxSpeed = 2000.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
        }
    }

    public float getCenterX()
    {
        return getX() + getWidth() / 2;
    }

    public float getCenterY()
    {
        return getY() + getHeight() / 2;
    }

    public Vector2 getCenterPosition()
    {
        return new Vector2(getCenterX(), getCenterY());
    }

    public void setCenterPosition(Vector2 pos)
    {
        setCenterPosition(pos.x, pos.y);
    }

    public void setCenterPosition(float X, float Y)
    {
        setPosition(X - getWidth() / 2, Y - getHeight() / 2);
    }

    private void makeWithinWindow()
    {
        float newX = 0.0f;
        float newY = 0.0f;

        //Check X-axis
        if (getCenterX() - getWidth() / 2 < 0.0f) newX = 0.0f + getWidth() / 2;
        else if (getCenterX() + getWidth() / 2 > Game.CAMERA_WIDTH)
            newX = (float) Game.CAMERA_WIDTH - getWidth() / 2;

        //Check Y-axis
        if (getCenterY() - getHeight() / 2 < 0.0f) newY = 0.0f + getHeight() / 2;
        else if (getCenterY() + getHeight() / 2 > Game.CAMERA_HEIGHT)
            newY = (float) Game.CAMERA_HEIGHT - getHeight() / 2;

        if (newX != 0.0f && newY != 0.0f) setCenterPosition(newX, newY);

    }

    private void updatePosition(float dt)
    {
        velocity_dir = (getCenterPosition().sub(targetPosition).nor()).mul(-1);
        float dst = (getCenterPosition().sub(targetPosition)).len();

        if (dst < 0.01f) //Close enough, no movement
        {
            currentSpeed = 0.0f;
            return;
        } else {
            if (isMoving) {
                currentSpeed = maxSpeed + 1.5f * dst;
                if (currentSpeed * dt > dst) currentSpeed = dst / dt;
            } else {
                if (currentSpeed * dt > dst) currentSpeed = dst / dt;
                else if (currentSpeed > 0.0f) currentSpeed -= dt * maxSpeed;

                if (currentSpeed < 0.0f) currentSpeed = 0.0f;
            }

            setCenterPosition(getCenterPosition().add(velocity_dir.mul(currentSpeed * dt)));
        }
    }

    private void updateRotation()
    {
        float newAngle = (velocity_dir.x) * currentSpeed * 0.0004f;
        setRotation(newAngle);
    }

    public void update(float dt)
    {
        updatePosition(dt);
        updateRotation();
        makeWithinWindow();

    }

    public void setVelocityDirection(Vector2 vec)
    {
        velocity_dir = vec;
    }

    public void setTargetPosition(float tx, float ty)
    {
        setTargetPosition(new Vector2(tx, ty));
    }

    public void setTargetPosition(Vector2 targetPos)
    {
        targetPosition = targetPos;
    }

    public void setIsMoving(boolean willMove)
    {
        isMoving = willMove;
    }

    public boolean isMoving()
    {
        return isMoving;
    }
}
