package komaapp.komaprojekt.GameLogic;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-26.
 */
public class Shot extends Rectangle {

    protected float speed;
    protected Vector2 normDir;

    public int damage;

    public Shot(float pX, float pY, Vector2 direction, float pWidth, float pHeight, float speed, VertexBufferObjectManager VBOmanager, final float pRed, final float pGreen, final float pBlue) {
        super(pX, pY, pWidth, pHeight, VBOmanager);

        normDir = direction.nor();
        this.setColor(pRed, pGreen, pBlue);
        this.speed = speed;

        this.damage = 5;
    }

    public void update(float dt)
    {
        this.setPosition( this.getX() + 60*dt*normDir.x*speed, this.getY() + 60*dt*normDir.y*speed );
    }

    public float getCenterX()
    {
        return getX() + getWidth() / 2;
    }
    public float getCenterY()
    {
        return getY() + getHeight() / 2;
    }

}