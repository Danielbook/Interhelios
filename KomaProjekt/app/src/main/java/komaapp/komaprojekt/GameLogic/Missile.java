package komaapp.komaprojekt.GameLogic;

import com.badlogic.gdx.math.Vector2;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-12-04.
 */
public class Missile extends Shot {

    private float acceleration = 30f;

    public Missile(float pX, float pY, Vector2 direction, float initialSpeed, VertexBufferObjectManager VBOmanager, ITextureRegion missileTex) {
        super(pX, pY, direction, initialSpeed, VBOmanager, missileTex);

        this.speed = initialSpeed;
        this.damage = 100;
    }

    @Override
    public void update(float dt)
    {
        this.speed += acceleration*dt;

        this.setPosition( this.getX() + normDir.x*speed, this.getY() + normDir.y*speed);
    }
}