package komaapp.komaprojekt.GameLogic;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import komaapp.komaprojekt.Game;

/**
 * Created by Matthias on 26/11/2014.
 */
public class MovingBackground extends Sprite
{
    private float backgroundSpeed = 100;

    public MovingBackground (float bX, float bY, ITextureRegion bTextureRegion, VertexBufferObjectManager bVertexBufferObjectManager)
    {
        super(bX, bY, bTextureRegion, bVertexBufferObjectManager);
    }

    public void updatePosition(float v)
    {
        this.setY(this.getY() + backgroundSpeed*v);
        if( this.getY() > Game.CAMERA_HEIGHT )
        {
            this.setY(-1920);
        }
    }
}
