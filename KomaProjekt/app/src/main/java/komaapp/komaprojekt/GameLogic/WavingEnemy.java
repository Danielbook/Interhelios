package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-24.
 */
public class WavingEnemy extends BaseEnemy {
    private float waveFrequency, waveAmplitude;
    private float symmetryLineX;

    public WavingEnemy(float spawnX, float spawnY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int ID, float speed, float frequency, float waveAmplitude) {
        super(spawnX, spawnY, pTextureRegion, pVertexBufferObjectManager, ID, speed);
        this.setCenterPosition(spawnX, spawnY);

        this.waveFrequency = frequency;
        this.waveAmplitude = waveAmplitude;
        this.symmetryLineX = spawnX;
    }

    @Override
    void update(float dt) {
        float newY = this.getCenterY() + this.getSpeed()*dt;
        float offsetX = ( (float)Math.cos( newY * waveFrequency ) ) * waveAmplitude;
        float rotationAngle = (float)Math.sin( newY*waveFrequency)*waveAmplitude*0.025f;
        float newX = this.symmetryLineX + offsetX;

        this.setCenterPosition(newX, newY);
        this.setRotation(rotationAngle);
    }

    @Override
    void fire() { Log.d("EnemyLog", "Shot fired by ID=" + this.getID()); }

    @Override
    void destroy() { Log.d("EnemyLog", "ID=" + this.getID() + " destroyed."); }
}
