package komaapp.komaprojekt.GameLogic;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-12-10.
 */
public class HealthBar extends Rectangle {

    private float mWidth;

    private float mMaxHealth;
    private float mCurrentHealth;

    public String parentType; //FUUULKOD med type

    public HealthBar(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager, float maxHealth) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);

        mMaxHealth = maxHealth;
        mCurrentHealth = mMaxHealth;

        mWidth = pWidth;

        setColor(0f, 1f, 0f);

        update(0);
    }

    public void update(float dt)
    {
        if (parentType.equals("boss")) mCurrentHealth = ((Boss)getParent()).getHealth();
        else if (parentType.equals("player")) mCurrentHealth = (float)((Player)getParent()).getHealth();

        setWidth(mWidth * mCurrentHealth/mMaxHealth);

        if (mCurrentHealth > mMaxHealth*0.75f)
            setColor(0f, 1f, 0f);
        else if (mCurrentHealth > mMaxHealth*0.5f)
            setColor(0f, 0.6f, 0f);
        else if (mCurrentHealth > mMaxHealth*0.25f)
            setColor(0.4f, 0f, 0f);
        else
            setColor(1f, 0f, 0f);

        if (getAlpha() > 0.2f) setAlpha(getAlpha() - dt*0.5f);
        if (getAlpha() < 0.2f) setAlpha(0.2f);
    }
}
