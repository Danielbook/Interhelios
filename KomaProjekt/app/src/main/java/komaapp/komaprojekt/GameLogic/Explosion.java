package komaapp.komaprojekt.GameLogic;

import org.andengine.opengl.texture.region.ITiledTextureRegion;

/**
 * Created by benjamin on 14-12-07.
 */
public abstract class Explosion{
    private static ITiledTextureRegion explosionTex;
    public static void setExplosionTex(ITiledTextureRegion tex)
    {
        explosionTex = tex;
    }

    public static ITiledTextureRegion getExplosionTex()
    {
        return explosionTex;
    }

    public static float getTextureWidth()
    {
        if (explosionTex != null)
        {
            return explosionTex.getWidth();
        }
        else return -1;
    }

    public static float getTextureHeight()
    {
        if (explosionTex != null)
        {
            return explosionTex.getHeight();
        }
        else return -1;
    }

}
