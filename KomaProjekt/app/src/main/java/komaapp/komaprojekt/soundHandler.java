package komaapp.komaprojekt;

import android.content.Context;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;

import java.io.IOException;

public class soundHandler {
    private Music menuMusic;
    private Sound buttonSound, startSound;
    private Database database = new Database();
    private static SoundManager soundMngr = new SoundManager();
    private static MusicManager musicMngr = new MusicManager();

    public soundHandler(Context ctx)
    {
        try
        {
            buttonSound = SoundFactory.createSoundFromAsset(soundMngr, ctx, "mfx/button_sound.ogg");
            startSound = SoundFactory.createSoundFromAsset(soundMngr, ctx, "mfx/start_sound.ogg");
            menuMusic = MusicFactory.createMusicFromAsset(musicMngr, ctx, "mfx/menu_music.ogg");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////MENU
    public void buttonSound()
    {
        buttonSound.setVolume( (float)database.getSoundVolume()/100);
        buttonSound.play();
    }

    public void startSound()
    {
        startSound.setVolume( (float)database.getSoundVolume()/100);
        startSound.play();
    }

    public void startBackgroundMusic()
    {
        if(!menuMusic.isPlaying())
        {
            menuMusic.setLooping(true);
            menuMusic.setVolume((float) database.getMusicVolume() / 100);
            menuMusic.play();
        }
    }

    public void changeBackgroundVolume()
    {
        menuMusic.setVolume( (float)database.getMusicVolume()/100);
    }

    public void stopBackgroundMusic() { menuMusic.pause(); }

}
