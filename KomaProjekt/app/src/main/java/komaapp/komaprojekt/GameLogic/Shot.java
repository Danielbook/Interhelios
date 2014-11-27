package komaapp.komaprojekt.GameLogic;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-26.
 */
public class Shot extends Rectangle {

    private float speed = 50.0f;
    private Vector2 normDir;

    public Shot(float pX, float pY, Vector2 direction, float pWidth, float pHeight, VertexBufferObjectManager VBOmanager, final float pRed, final float pGreen, final float pBlue) {
        super(pX, pY, pWidth, pHeight, VBOmanager);

        normDir = direction.nor();
        this.setColor(pRed, pGreen, pBlue);
    }

    public void update(float dt)
    {
        this.setPosition( this.getX() + normDir.x*speed, this.getY() + normDir.y*speed );
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