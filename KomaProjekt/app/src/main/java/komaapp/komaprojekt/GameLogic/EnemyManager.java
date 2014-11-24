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

    // List of currently active enemies
    private ArrayList<BaseEnemy> enemies;

    // Timer stuffsies
    private float spawnInterval;
    private int enemyCounter;

    public EnemyManager(Scene scene, VertexBufferObjectManager VBOmanager) {
        this.scene = scene;
        this.VBOmanager = VBOmanager;
        enemies = new ArrayList<BaseEnemy>(10);

        enemyTextures = new Hashtable<String, ITextureRegion>();

        spawnInterval = 2.0f;
        enemyCounter = 0;
    }

    public void update(float currentTime, float dt) {
        ArrayList<Integer> toRemoveIDs = new ArrayList<Integer>();

        //Update each enemy
        for (int i=0; i<enemies.size(); i++)
        {
            BaseEnemy enemy = enemies.get(i);

            //Update the enemy
            enemy.update(dt);

            //Check if out of screen
            if (enemy.getY() - enemy.getHeight()/2 >= Game.CAMERA_HEIGHT) toRemoveIDs.add(i);
        }

        //Remove enemies too far down
        for (final Integer ID : toRemoveIDs)
        {
            Log.d("EnemyLog", "Will try to remove enemy with ID=" + String.valueOf(ID));
            removeEnemy(enemies.get(ID));
        }

        //Try to spawn enemy
        if (currentTime >= spawnInterval*enemyCounter)
        {
            float spawnX = randGen.nextInt(Game.CAMERA_WIDTH+1);
            float spawnY = -150f;
            float speed = 150 + (randGen.nextFloat()-0.5f)*100;

            addEnemy("tie_fighter", spawnX, spawnY, speed);

            enemyCounter++;
        }
    }

    // texName is the key in the HashTable enemyTextures for the wanted ITextureRegion
    public void addEnemy(String texName, float spawnX, float spawnY, float speed) {
        SimpleEnemy newEnemy = new SimpleEnemy(spawnX, spawnY, enemyTextures.get(texName), VBOmanager, enemyCounter, speed);
        //WavingEnemy newEnemy = new WavingEnemy(spawnX, spawnY, enemyTextures.get(texName), VBOmanager, enemyCounter, speed, 0.01f, 5000.0f);

        enemyCounter++;

        this.enemies.add(newEnemy);
        this.scene.attachChild(newEnemy);

        Log.d("EnemyLog", "Enemy added: textureName=" + texName + " spawnX=" + spawnX + " spawnY=" + spawnY);
    }

    // Remove an enemy, returns true on success and false on failure
    public boolean removeEnemy(BaseEnemy remEnemy) {
        this.scene.detachChild(remEnemy);
        remEnemy.destroy();

        Log.d("EnemyLog", "Enemy removed: ID=" + remEnemy.getID() + " removeX=" + remEnemy.getX() + " removeY=" + remEnemy.getY() );

        return this.enemies.remove(remEnemy);
    }

    // Set the rate of enemy spawns
    public void setSpawnInterval(float dt) {
        this.spawnInterval = dt;
    }

    // Method to add a texture to the "library" of enemy textures
    public void addEnemyTexture(ITextureRegion tex, String textureName) {
        enemyTextures.put(textureName, tex);
    }
}
