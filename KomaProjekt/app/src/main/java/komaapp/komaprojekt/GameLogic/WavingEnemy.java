package komaapp.komaprojekt.GameLogic;

import com.badlogic.gdx.math.Vector2;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by benjamin on 14-11-24.
 */
public class WavingEnemy extends BaseEnemy {
    private float waveFrequency, waveAmplitude;
    private float symmetryLineX;

    public WavingEnemy(float spawnX, float spawnY, ITextureRegion pTextureRegion, VertexBufferObjectManager VBOmanager, ShotManager shotManager, int ID, float speed, float frequency, float waveAmplitude) {
        super(spawnX, spawnY, pTextureRegion, VBOmanager, shotManager, ID, speed);
        this.setCenterPosition(spawnX, spawnY);

        this.waveFrequency = frequency;
        this.waveAmplitude = waveAmplitude;
        this.symmetryLineX = spawnX;
    }

    @Override
    void updatePosition(float dt)
    {
        float newY = this.getCenterY() + this.getSpeed()*dt;
        float offsetX = ( (float)Math.cos( newY * waveFrequency ) ) * waveAmplitude;
        float rotationAngle = (float)Math.sin( newY*waveFrequency)*waveAmplitude*0.025f;
        float newX = this.symmetryLineX + offsetX;

        this.setCenterPosition(newX, newY);
        this.setRotation(rotationAngle);
    }

    @Override
    void fire() {
        //Log.d("EnemyLog", "Shot fired by ID=" + this.getID());

        //float dirX = -(float)Math.sin(getRotation()*Math.PI/180);
        //float dirY = (float)Math.cos(getRotation()*Math.PI/180);
        //Vector2 dir = new Vector2(dirX, dirY);

        Vector2 dir = new Vector2(0, 1);

        this.addShotToShotManager(getCenterX(), getCenterY(), dir, 30f);
    }
}
