package komaapp.komaprojekt;

import android.content.Context;
import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;

import java.io.IOException;

/**
 * Created by Daniel on 14-12-10.
 */
public class SoundManager
{
    private Music menuMusic, playerSounds, enemySounds, sound;
    private Database database = new Database();
    private boolean backgroundMusicIsplaying = false;

    ////MENU
    public void buttonSound(Context ctx)
    {
        try {
            sound = MusicFactory.createMusicFromAsset(new MusicManager(), ctx, "mfx/button_sound.ogg");
            sound.setVolume( (float)database.getSoundVolume()/10);
            sound.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSound(Context ctx)
    {
        try {
            sound = MusicFactory.createMusicFromAsset(new MusicManager(), ctx, "mfx/start_sound.ogg");
            sound.setVolume( (float)database.getSoundVolume()/10 );

            sound.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startBackgroundMusic(Context ctx)
    {
        try
        {
            menuMusic = MusicFactory.createMusicFromAsset(new MusicManager(), ctx, "mfx/menu_music.ogg");
            menuMusic.setLooping(true);
            menuMusic.setVolume((float) database.getMusicVolume() / 10);
            menuMusic.play();
            backgroundMusicIsplaying = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeBackgroundVolume()
    {
        menuMusic.setVolume( (float)database.getMusicVolume()/10 );
        Log.d("TextLog", "Bkgrnd Volume: " + menuMusic.getVolume());
    }

    public boolean isBackgroundMusicIsplaying()
    {
        return backgroundMusicIsplaying;
    }

    public void setBackgroundMusicIsplaying(boolean backgroundMusicIsplaying)
    {
        this.backgroundMusicIsplaying = backgroundMusicIsplaying;
    }

    public void pauseBackgroundMusic() { menuMusic.stop(); }

////GAME
/*
    public void playerExplosion()
    {
        try
        {
            playerSounds = MusicFactory.createMusicFromAsset(new MusicManager(), Game.ctx, "mfx/player_explosion.ogg");
            playerSounds.setVolume( (float)database.getSoundVolume()/10 );
            playerSounds.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playerLaser()
    {
        try
        {
            playerSounds = MusicFactory.createMusicFromAsset(new MusicManager(), Game.ctx, "mfx/player_laser.ogg");
            playerSounds.setVolume( (float)database.getSoundVolume()/10 );
            playerSounds.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playerDamage()
    {
        try
        {
            playerSounds = MusicFactory.createMusicFromAsset(new MusicManager(), Game.ctx, "mfx/player_damage.ogg");
            playerSounds.setVolume( (float)database.getSoundVolume()/10 );
            playerSounds.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void enemyExplosion()
    {
        try
        {
            enemySounds = MusicFactory.createMusicFromAsset(new MusicManager(), Game.ctx, "mfx/enemy_explosion.ogg");
            enemySounds.setVolume( (float)database.getSoundVolume()/10 );
            enemySounds.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enemyLaser()
    {
        try
        {
            enemySounds = MusicFactory.createMusicFromAsset(new MusicManager(), Game.ctx, "mfx/enemy_laser.ogg");
            enemySounds.setVolume( (float)database.getSoundVolume()/10 );
            enemySounds.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}
