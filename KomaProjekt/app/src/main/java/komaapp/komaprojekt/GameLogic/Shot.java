package komaapp.komaprojekt.GameLogic;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Shot extends Sprite {
    protected float speed;

    protected Vector2 normDir;

    public int damage;

    public Shot(float pX, float pY, Vector2 direction, float speed, VertexBufferObjectManager VBOmanager, ITextureRegion shotTex) {
        super(pX, pY, shotTex, VBOmanager);

        normDir = direction.nor();

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