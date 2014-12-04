package komaapp.komaprojekt.GameLogic;

import com.badlogic.gdx.math.Vector2;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-12-04.
 */
public class Missile extends Shot {

    private float acceleration = 30f;

    public Missile(float pX, float pY, Vector2 direction, float pWidth, float pHeight, float initialSpeed, VertexBufferObjectManager VBOmanager, float pRed, float pGreen, float pBlue) {
        super(pX, pY, direction, pWidth, pHeight, initialSpeed, VBOmanager, pRed, pGreen, pBlue);

        this.speed = initialSpeed;
        this.damage = 1000;
    }

    @Override
    public void update(float dt)
    {
        this.speed += acceleration*dt;

        this.setPosition( this.getX() + normDir.x*speed, this.getY() + normDir.y*speed);
    }
}