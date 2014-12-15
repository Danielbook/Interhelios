package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import komaapp.komaprojekt.Game;
import komaapp.komaprojekt.GameLogic.Collision.CircleBody;

/**
 * Created by benjamin on 14-11-23.
 *
 * Abstract base class for enemies. Extend it to create enemy types
 */
public abstract class BaseEnemy extends Sprite {

    protected int health = 10;

    private final int ID;
    private final float speed;
    protected float shootTimer;

    private ShotManager shotManagerReference;

    protected CircleBody body;

    protected BaseEnemy(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShotManager theShotManager, int ID, float speed) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.ID = ID;
        this.speed = speed;
        this.shootTimer = 0;
        this.shotManagerReference = theShotManager;

        this.body = new CircleBody(CircleBody.calcRadiusFromWidthAndHeight(pTextureRegion.getWidth(), pTextureRegion.getHeight()), pX, pY);
    }

    protected boolean shouldFire() {
        return (shootTimer > EnemyManager.shootInterval);
    }

    abstract void updatePosition(float dt);
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

        if (health <= 0)
        {
            this.destroy();
        }
    }
    abstract void fire();

    public void destroy()
    {
        Log.d("EnemyLog", "Enemy with ID=" + this.getID() + " destroyed.");
        this.detachSelf();
        this.dispose();
        //MainMenu.soundManager.enemyExplosion();
        Game.enemy_explosion.play();
    }

    protected void addShotToShotManager(float pX, float pY, Vector2 direction, float speed)
    {
        shotManagerReference.addShot(pX, pY, direction, speed);
        Game.enemy_laser.play();
    }

    public int getID() { return this.ID; } // Basic identifying integer, unique for each spawned enemy
    protected float getSpeed() { return this.speed; }
    public float getCenterX()
    {
        return getX() + getWidth() / 2;
    }
    public float getCenterY() { return getY() + getHeight() / 2; }
    public CircleBody getCircleBody() { return this.body; }

    protected void setCenterPosition(float X, float Y) { setPosition(X - getWidth() / 2, Y - getHeight() / 2); }

    public boolean addDamage(int damage) // Return true on destroy
    {
        this.health -= damage;

        if (health <= 0)
        {
            this.destroy();
            return true;
        }
        return false;
    }
}