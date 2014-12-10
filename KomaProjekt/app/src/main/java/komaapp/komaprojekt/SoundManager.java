package komaapp.komaprojekt;

import android.content.Context;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;

import java.io.IOException;

public class SoundManager {
    private Music menuMusic, buttonSound;
    private Database database = new Database();
    private boolean backgroundMusicIsplaying = false;

    public SoundManager(Context ctx)
    {
        try
        {
            buttonSound = MusicFactory.createMusicFromAsset(new MusicManager(), ctx, "mfx/button_sound.ogg");
            buttonSound = MusicFactory.createMusicFromAsset(new MusicManager(), ctx, "mfx/button_sound.ogg");
            menuMusic = MusicFactory.createMusicFromAsset(new MusicManager(), ctx, "mfx/menu_music.ogg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////MENU
    public void buttonSound()
    {
        buttonSound.setVolume( (float)database.getSoundVolume()/10);
        buttonSound.play();
    }

    public void startSound()
    {
        buttonSound.setVolume( (float)database.getSoundVolume()/10 );
        buttonSound.play();
    }

    public void startBackgroundMusic()
    {
        menuMusic.setLooping(true);
        menuMusic.setVolume((float) database.getMusicVolume() / 10);
        menuMusic.play();
        backgroundMusicIsplaying = true;
    }

    public void changeBackgroundVolume()
    {
        menuMusic.setVolume( (float)database.getMusicVolume()/10 );
    }

    public boolean isBackgroundMusicIsplaying()
    {
        return backgroundMusicIsplaying;
    }

    public void setBackgroundMusicIsplaying(boolean backgroundMusicIsplaying)
    {
        this.backgroundMusicIsplaying = backgroundMusicIsplaying;
    }

    public void pauseBackgroundMusic() { menuMusic.pause(); }

}
