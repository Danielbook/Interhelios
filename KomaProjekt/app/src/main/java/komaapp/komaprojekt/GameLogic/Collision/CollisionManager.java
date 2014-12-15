package komaapp.komaprojekt.GameLogic.Collision;

import org.andengine.util.IDisposable.AlreadyDisposedException;

import java.util.ArrayList;

import komaapp.komaprojekt.Game;
import komaapp.komaprojekt.GameLogic.BaseEnemy;
import komaapp.komaprojekt.GameLogic.EnemyManager;
import komaapp.komaprojekt.GameLogic.Player;
import komaapp.komaprojekt.GameLogic.Shot;
import komaapp.komaprojekt.GameLogic.ShotManager;

/**
 * Created by benjamin on 14-12-01.
 */
public class CollisionManager {

    public static boolean circleBodiesCollide(CircleBody b1, CircleBody b2)
    {
        float dst = ( b1.getCenterPosition().sub( b2.getCenterPosition() ) ).len();
        return ( dst < b1.getRadius() + b2.getRadius() );
    }

    public static void collidePlayerWithEnemies(Player player, EnemyManager enemyManager)
    {
        ArrayList<BaseEnemy> enemiesToRemove = new ArrayList<BaseEnemy>();
        ArrayList<BaseEnemy> enemies = enemyManager.getEnemies();

        CircleBody playerBody = player.getCircleBody();

        for (BaseEnemy enemy : enemies)
        {
            CircleBody enemyBody = enemy.getCircleBody();

            if (circleBodiesCollide(playerBody, enemyBody))
            {
                // PLAYER HAS HIT AN ENEMY, SHIIIIIET
                if (!player.isDisposed())
                {
                    //TODO make game quit here
                    Player.shield = 0;
                }


            }
        }
        enemies.removeAll(enemiesToRemove); // Remove all enemies colliding with the player
    }

    public static void collideEnemiesWithShots(EnemyManager enemyManager, ShotManager playerShotManager)
    {
        ArrayList<Shot> shotsToRemove = new ArrayList<Shot>();
        ArrayList<BaseEnemy> enemiesToRemove = new ArrayList<BaseEnemy>();

        // Nested for-loop comparing each shot with each enemy
        for (Shot shot : playerShotManager.getShots())
        {
            CircleBody shotCircleBody = new CircleBody(CircleBody.calcRadiusFromWidthAndHeight(shot.getWidth(), shot.getHeight()), shot.getCenterX(), shot.getCenterY());

            for (BaseEnemy enemy : enemyManager.getEnemies())
            {
                if (!enemy.isDisposed() ||!shot.isDisposed()) // Failsafe
                {
                    if (circleBodiesCollide(shotCircleBody, enemy.getCircleBody()))
                    {
                        // Affect enemy
                        try
                        {
                            if (enemy.addDamage(shot.damage)) {
                                enemiesToRemove.add(enemy); // If destroyed, add to list to remove
                                Game.database.addCash(100);
                            }
                        }
                        catch (AlreadyDisposedException e)
                        {
                            e.printStackTrace();
                        }

                        // Current shot hit the enemy, DEAL WITH IT BELOW
                        shotsToRemove.add(shot);
                    }
                }
            }
        }

        // Clean up by removing all shots that hit the enemy, and removing all enemies that were destroyed
        playerShotManager.removeShots(shotsToRemove);
        enemyManager.removeEnemies(enemiesToRemove);
    }

    public static void collidePlayerWithShots(Player player, ShotManager enemyShotManager) {
        CircleBody playerBody = player.getCircleBody();
        ArrayList<Shot> shotsToRemove = new ArrayList<Shot>();

        for (Shot shot : enemyShotManager.getShots())
        {
            if (shot != null)
            {
                CircleBody shotCircleBody = new CircleBody(CircleBody.calcRadiusFromWidthAndHeight(shot.getWidth(), shot.getHeight()), shot.getCenterX(), shot.getCenterY());
                if (circleBodiesCollide(playerBody, shotCircleBody))
                {
                    if (!player.isDisposed())
                    {
                        player.addDamage(shot.damage);
                        shotsToRemove.add(shot);
                    }
                }
            }
        }
        enemyShotManager.removeShots(shotsToRemove);
    }

    public static void collidePlayerWithBoss(Player player, EnemyManager enemyManager) {
        CircleBody playerBody = player.getCircleBody();
        BaseEnemy boss = enemyManager.getBoss();

        CircleBody bossBody = boss.getCircleBody();

        if (circleBodiesCollide(playerBody, bossBody))
        {
            if (!player.isDisposed())
            {
                Player.shield = 0;
            }
        }
    }

    public static void collideBossWithShots(ShotManager playerShotManager, EnemyManager enemyManager) {
        ArrayList<Shot> shotsToRemove = new ArrayList<Shot>();

        CircleBody bossBody = enemyManager.getBoss().getCircleBody();

        for (Shot shot : playerShotManager.getShots())
        {
            if (circleBodiesCollide(new CircleBody(CircleBody.calcRadiusFromWidthAndHeight(shot.getWidth(), shot.getHeight()), shot.getCenterX(), shot.getCenterY()), bossBody))
            {
                // Affect enemy
                try
                {
                    if (enemyManager.getBoss().addDamage(shot.damage)) {
                        Game.database.addCash(100);
                    }
                }
                catch (AlreadyDisposedException e)
                {
                    e.printStackTrace();
                }

                // Current shot hit the enemy, DEAL WITH IT BELOW
                shotsToRemove.add(shot);
            }
        }

        playerShotManager.removeShots(shotsToRemove);
    }
}
