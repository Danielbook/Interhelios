package komaapp.komaprojekt;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-12.
 */
public class Player extends Sprite{

    ///// INTERFACE

    // CONSTRUCTORS
    public Player(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    // GETTERS

    public float getCenterX() {return getX() + getWidth()/2;}
    public float getCenterY() {return getY() + getHeight()/2;}

}
