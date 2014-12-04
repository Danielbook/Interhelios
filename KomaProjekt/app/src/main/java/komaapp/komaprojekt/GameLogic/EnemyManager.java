package komaapp.komaprojekt.GameLogic;

import android.util.Log;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import komaapp.komaprojekt.Game;

/**
 * Created by benjamin on 14-11-23.
 *
 * Handler class for all enemies
 */
public class EnemyManager {

    // Enemy texture library
    Hashtable<String, ITextureRegion> enemyTextures;

    // Random number generator
    Random randGen = new Random();

    // Reference to the active scene, for attaching enemies to the scene
    private Scene scene;
    private VertexBufferObjectManager VBOmanager;
    private ShotManager shotManager;

    // List of currently active enemies
    private ArrayList<BaseEnemy> enemies;

    // Timer stuffsies
    private float spawnInterval;
    private int enemyCounter;

    //Hard-coded shoot frequency
    public static final float shootInterval = 0.75f;

    public EnemyManager(Scene scene, VertexBufferObjectManager VBOmanager, ShotManager shotManager)
    {
        this.scene = scene;
        this.VBOmanager = VBOmanager;
        this.shotManager = shotManager;
        enemies = new ArrayList<BaseEnemy>(10);

        enemyTextures = new Hashtable<String, ITextureRegion>();

        spawnInterval = 2.5f;
        enemyCounter = 0;
    }

    public void update(float currentTime, float dt)
    {
        ArrayList<BaseEnemy> enemiesToRemove = new ArrayList<BaseEnemy>();

        //Update each enemy
        for (BaseEnemy enemy : enemies)
        {
            //Update the enemy
            enemy.update(dt);

            //Check if out of screen
            if (enemy.getY() - enemy.getHeight()/2 >= Game.CAMERA_HEIGHT)
            {
                enemiesToRemove.add(enemy);
                // dispose the memory of enemy
                enemy.destroy();
            }
        }
        enemies.removeAll(enemiesToRemove); // Remove all enemies to far down from the arrayList

        //Try to spawn enemy
        if (currentTime >= spawnInterval*enemyCounter)
        {
            float spawnX = randGen.nextFloat() * Game.CAMERA_WIDTH;
            float spawnY = -150f;
            float speed = 150 + (randGen.nextFloat()-0.5f)*100;

            addEnemy("tie_fighter", spawnX, spawnY, speed);

            enemyCounter++;
        }
    }

    // texName is the key in the HashTable enemyTextures for the wanted ITextureRegion
    public void addEnemy(String texName, float spawnX, float spawnY, float speed)
    {
        BaseEnemy newEnemy;

        if (enemyCounter%4 == 0) {
            newEnemy = new WavingEnemy(spawnX, spawnY, enemyTextures.get(texName), VBOmanager, shotManager, enemyCounter, (randGen.nextFloat()+0.5f)*speed, 0.02f, 100.0f);
        }
        else newEnemy = new SimpleEnemy(spawnX, spawnY, enemyTextures.get(texName), VBOmanager, shotManager, enemyCounter, (randGen.nextFloat()+0.5f)*speed);

        this.enemies.add(newEnemy);
        this.scene.attachChild(newEnemy);

        Log.d("EnemyLog", "Enemy added: textureName=" + texName + " spawnX=" + spawnX + " spawnY=" + spawnY + " ID=" + newEnemy.getID() );
    }

    // Remove an enemy, returns true on success and false on failure
    public void removeEnemy(BaseEnemy remEnemy)
    {
        remEnemy.destroy();

        Log.d("EnemyLog", "Enemy removed: ID=" + remEnemy.getID() + " removeX=" + remEnemy.getX() + " removeY=" + remEnemy.getY() );

        this.enemies.remove(remEnemy);
    }

    public void removeEnemies(ArrayList<BaseEnemy> enemiesToRemove)
    {
        this.enemies.removeAll(enemiesToRemove);
    }

    // Set the rate of enemy spawns
    public void setSpawnInterval(float dt)
    {
        this.spawnInterval = dt;
    }

    // Method to add a texture to the "library" of enemy textures
    public void addEnemyTexture(ITextureRegion tex, String textureName)
    {
        enemyTextures.put(textureName, tex);
    }

    public final ArrayList<BaseEnemy> getEnemies()
    {
        return enemies;
    }
}
