package komaapp.komaprojekt.GameLogic;

import com.badlogic.gdx.math.Vector2;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import komaapp.komaprojekt.Game;

/**
 * Created by benjamin on 14-12-15.
 */
public class Boss extends BaseEnemy {
    private float elapsedTime;

    // Coordinate shizniz
    private float xMovementAmplitude, yMovementAmplitude;

    //HP
    HealthBar hpBar;

    // VOLLEY FIRE
    private boolean isCurrentlyFiringVolley = false;
    private int shotsInVolley = 16;
    private int currentVolley = 1;
    private float fullVolleyInterval = 6f, timeSinceLastFullVolley = 0f;
    private Vector2 firstVolleyDirection = (new Vector2(2, 1)).nor();
    private float volleySpeed = 25f;
    private float volleyShotInterval = 0.01f, timeSinceLastVolleyShot = 0f;

    public int getHealth() { return health; }

    public Boss(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShotManager theShotManager, int ID, float speed) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager, theShotManager, ID, speed);

        setCenterPosition(Game.CAMERA_WIDTH/2, -getWidth()/2);

        elapsedTime = 0.0f;
        xMovementAmplitude = Game.CAMERA_WIDTH/2 - getWidth()/2;
        yMovementAmplitude = 50f;

        health = 250;
    }

    @Override
    public void update(float dt)
    {
        this.updatePosition(dt);

        this.shootTimer += dt;

        if (this.shouldFire())
        {
            shootTimer = 0.0f;
            fire();
        }

        this.body.setCenterPosition(this.getCenterX(), this.getCenterY());

        /*** VOLLEY FIRING ***/

        if (isCurrentlyFiringVolley)
        {
            if ( timeSinceLastVolleyShot >= volleyShotInterval )
            {
                // FIRE A SHOT IN THE VOLLEY
                float oldX = firstVolleyDirection.x, oldY = firstVolleyDirection.y;
                float someAngle = 0.1f*currentVolley;

                float newX = oldX*(float)Math.cos(someAngle) - oldY*(float)Math.sin(someAngle);
                float newY = oldX*(float)Math.sin(someAngle) + oldY*(float)Math.cos(someAngle);

                Vector2 shootDir = (new Vector2(newX, newY)).nor();

                addShotToShotManager(getCenterX(), getCenterY(), shootDir, volleySpeed);

                timeSinceLastVolleyShot = 0.0f;
            }

            else timeSinceLastVolleyShot += dt;


            if (++currentVolley > shotsInVolley)
            {
                isCurrentlyFiringVolley = false;
                currentVolley = 1;
            }

        }

        else if (timeSinceLastFullVolley > fullVolleyInterval && !isCurrentlyFiringVolley)
        {
            // START FIRING VOLLEY
            isCurrentlyFiringVolley = true;
            timeSinceLastFullVolley = 0.0f;
            timeSinceLastVolleyShot = volleyShotInterval;
        }

        else
        {
            timeSinceLastFullVolley += dt;
        }

    }

    @Override
    void updatePosition(float dt) {
        elapsedTime += dt;

        float newX = Game.CAMERA_WIDTH/2 + xMovementAmplitude *(float)Math.sin(elapsedTime*0.5f);
        float newY = 100f + yMovementAmplitude*(float)Math.cos(elapsedTime*0.3f);

        setCenterPosition(newX, newY);
    }

    @Override
    void fire() {

    }

    public void setHealthBar(HealthBar hpBar)
    {
        this.hpBar = hpBar;
        attachChild(hpBar);
        hpBar.setX(0);
        hpBar.setY(getHeight() + 30f);

        hpBar.parentType = "boss";
    }

    public HealthBar getHealthBar() { return hpBar; }

    @Override
    public boolean addDamage(int damage) // Return true on destroy
    {
        this.health -= damage;

        hpBar.setAlpha(1.0f);

        if (health <= 0)
        {
            this.destroy();
            return true;
        }
        return false;
    }
}
