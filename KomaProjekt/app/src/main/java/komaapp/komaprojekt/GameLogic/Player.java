package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import komaapp.komaprojekt.Game;
import komaapp.komaprojekt.GameLogic.Collision.CircleBody;

/**
 * Created by benjamin on 14-11-12.
 */

public class Player extends Sprite {

    private ShotManager shotManagerReference;
    private CircleBody body;

    private Vector2 targetPosition;
    private Vector2 velocity_dir = new Vector2(0, 0);
    private boolean touchActive = false;
    private float maxSpeed;
    private float currentSpeed = 0.0f;

    private float shootTimer;
    private float missileTimer;

    private float shootInterval;
    private float missileInterval;

    public static double maxShield = 10;

    public static double shield = 10;

    private int damage = 0;


    ///// INTERFACE

    public Player(float pX, float pY, ITextureRegion pTextureRegion,
                  VertexBufferObjectManager pVertexBufferObjectManager,
                  ShotManager playerShotManager, int gunsLvl ,int engineLvl, int shieldLvl)
    {
        super(pX - pTextureRegion.getWidth() / 2, pY - pTextureRegion.getHeight() / 2, pTextureRegion, pVertexBufferObjectManager);
        this.body = new CircleBody(CircleBody.calcRadiusFromWidthAndHeight(pTextureRegion.getWidth(), pTextureRegion.getHeight()), this.getCenterX(), this.getCenterY());

        this.shotManagerReference = playerShotManager;

        this.targetPosition = new Vector2(pX, pY);

        this.shootTimer = 0.0f;

        setPlayerAttributes(gunsLvl, engineLvl, shieldLvl);

        this.missileTimer = this.missileInterval;
    }

    public void setPlayerAttributes(int gunsLvl, int engineLvl, int shieldLvl)
    {
        Log.d("TextLog", "Guns level: " + gunsLvl);
        switch (gunsLvl)
        {
            case 1:
            {
                shootInterval = 0.50f;
                missileInterval = 25f;
                Log.d("TextLog","Guns: " + shootInterval);
                break;
            }
            case 2:
            {
                shootInterval = 0.40f;
                missileInterval = 20f;
                Log.d("TextLog","Guns: " + shootInterval);
                break;
            }
            case 3:
            {
                shootInterval = 0.30f;
                missileInterval = 15f;
                Log.d("TextLog","Guns: " + shootInterval);
                break;
            }
            case 4:
            {
                shootInterval = 0.20f;
                missileInterval = 10f;
                Log.d("TextLog","Guns: " + shootInterval);
                break;
            }
            case 5:
            {
                shootInterval = 0.10f;
                missileInterval = 5f;
                Log.d("TextLog","Guns: " + shootInterval);
                break;
            }
        }

        Log.d("TextLog", "Engine level: " + engineLvl);
        switch (engineLvl)
        {
            case 1:
            {
                maxSpeed = 200.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
            case 2:
            {
                maxSpeed = 600.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
            case 3:
            {
                maxSpeed = 1000.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
            case 4:
            {
                maxSpeed = 1400.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
            case 5:
            {
                maxSpeed = 2000.0f;
                Log.d("TextLog","Speed: " + maxSpeed);
                break;
            }
        }

        Log.d("TextLog", "Shield level: " + shieldLvl);
        switch (shieldLvl){
            case 1:{
                maxShield = 10;
                shield = 10;
                Log.d("TextLog","Shield: " + shield);
                break;
            }
            case 2:{
                maxShield = 15;
                shield = 15;
                Log.d("TextLog","Shield: " + shield);
                break;
            }
            case 3:{
                maxShield = 20;
                shield = 20;
                Log.d("TextLog","Shield: " + shield);
                break;
            }
            case 4:{
                maxShield = 25;
                shield = 25;
                Log.d("TextLog","Shield: " + shield);
                break;
            }
            case 5:{
                maxShield = 30;
                shield = 30;
                Log.d("TextLog","Speed: " + shield);
                break;
            }
        }
    }

    public float getCenterX()
    {
        return getX() + getWidth() / 2;
    }

    public float getCenterY()
    {
        return getY() + getHeight() / 2;
    }

    public Vector2 getCenterPosition()
    {
        return new Vector2(getCenterX(), getCenterY());
    }

    public CircleBody getCircleBody() { return this.body; }

    public void setCenterPosition(Vector2 pos)
    {
        setCenterPosition(pos.x, pos.y);
    }

    public void setCenterPosition(float X, float Y)
    {
        setPosition(X - getWidth() / 2, Y - getHeight() / 2);
    }

    private void makeWithinWindow()
    {
        float newX = 0.0f;
        float newY = 0.0f;

        //Check X-axis
        if (getCenterX() - getWidth() / 2 < 0.0f) newX = 0.0f + getWidth() / 2;
        else if (getCenterX() + getWidth() / 2 > Game.CAMERA_WIDTH)
            newX = (float) Game.CAMERA_WIDTH - getWidth() / 2;

        //Check Y-axis
        if (getCenterY() - getHeight() / 2 < 0.0f) newY = 0.0f + getHeight() / 2;
        else if (getCenterY() + getHeight() / 2 > Game.CAMERA_HEIGHT)
            newY = (float) Game.CAMERA_HEIGHT - getHeight() / 2;

        if (newX != 0.0f && newY != 0.0f) setCenterPosition(newX, newY);

    }

    private void updatePosition(float dt)
    {
        velocity_dir = (getCenterPosition().sub(targetPosition).nor()).mul(-1);
        float dst = (getCenterPosition().sub(targetPosition)).len();

        if (dst < 0.01f) //Close enough, no movement
        {
            currentSpeed = 0.0f;
            return;
        }
        else
        {

            currentSpeed = maxSpeed + dst;
            if (currentSpeed * dt > dst) currentSpeed = dst / dt;

            setCenterPosition(getCenterPosition().add(velocity_dir.mul(currentSpeed * dt)));
        }
    }

    private void updateRotation()
    {
        float newAngle = (velocity_dir.x) * currentSpeed * 0.001f;
        setRotation(newAngle);
    }

    protected boolean shouldFire() {
        return (shootTimer > shootInterval);
    }

    public void update(float dt)
    {
        if (shield <= 0) return;

        updatePosition(dt);
        //updateRotation();
        //makeWithinWindow();

        this.shootTimer += dt;

        if (this.shouldFire())
        {
            shootTimer = 0.0f;
            shoot();
        }

        this.body.setCenterPosition(this.getCenterX(), this.getCenterY());

        this.missileTimer += dt;

        // Update the missileTimerText
        if (missileTimer >= missileInterval) // Missile ready to be fired again
        {
            Game.shootBtn.setAlpha(1.0f);
            Game.missileTimerText.setText("");
        }
        else // Some time remaining on the clock
        {
            int secondsLeftToMissileReady = (int)Math.ceil(missileInterval - missileTimer);
            Game.missileTimerText.setText(String.valueOf(secondsLeftToMissileReady));

            Game.shootBtn.setAlpha(0.2f);
        }

    }

    public void setVelocityDirection(Vector2 vec)
    {
        velocity_dir = vec;
    }

    public void setTargetPosition(float tx, float ty) { setTargetPosition(new Vector2(tx, ty)); }

    public void setTargetPosition(Vector2 targetPos)
    {
        targetPosition = targetPos;
    }

    public void setTouchActive(boolean touchActive)
    {
        this.touchActive = touchActive;
    }

    public static double getShield(){ return shield; }

    public void shoot()
    {
        //TODO fix so that the player shoots along its rotational axis?
        //Vector2 shootDir = new Vector2( (float)(Math.sin(Math.toRadians(getRotation()))), -(float)(Math.cos(Math.toRadians(getRotation()))));

        Vector2 shootDir = new Vector2(0, -1);
        shotManagerReference.addShot(getCenterX()-getWidth()/2, getCenterY(), shootDir, 15f, 60f, 30f, 100f, 0, 0);
        shotManagerReference.addShot(getCenterX()+getWidth()/2, getCenterY(), shootDir, 15f, 60f, 30f, 100f, 0, 0);

        //MainMenu.soundManager.playerLaser();
        Game.player_laser.play();
    }

    public void shootMissile()
    {
        if (missileTimer >= missileInterval)
        {
            Vector2 shootDir = new Vector2(0, -1);
            shotManagerReference.addMissile(getCenterX(), getCenterY()-getHeight()/2, shootDir, 30f, 30f, 5f, 25f, 75f, 75f);

            missileTimer = 0.0f;
        }

    }

    public void addDamage(int damage)
    {
        shield -= damage;

        if (shield <= 0)
        {
            // TODO Player has died, make game quit here
            this.detachSelf();
            this.dispose();
            //MainMenu.soundManager.playerExplosion();
            Game.player_damage.play();
        }
        //MainMenu.soundManager.playerDamage();
        Game.player_damage.play();
    }
}
