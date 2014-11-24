package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-24.
 */
public class WavingEnemy extends BaseEnemy {
    private float speed;
    private float waveFrequency, waveAmplitude;
    private float symmetryLineX;

    public WavingEnemy(float spawnX, float spawnY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int ID, float speed, float frequency, float waveAmplitude) {
        super(spawnX, spawnY, pTextureRegion, pVertexBufferObjectManager, ID);
        this.setCenterPosition(spawnX, spawnY);

        this.speed = speed;
        this.waveFrequency = frequency;

        this.symmetryLineX = spawnX;
    }

    @Override
    void update(float dt) {
        float newY = this.getCenterY() + speed*dt;
        float newX = this.symmetryLineX + waveAmplitude * ( (float)Math.sin(waveFrequency*newY) );

        this.setCenterPosition(newX, newY);
    }

    @Override
    void fire() { Log.d("EnemyLog", "Shot fired by ID=" + this.getID()); }

    @Override
    void destroy() { Log.d("EnemyLog", "ID=" + this.getID() + " destroyed."); }
}
