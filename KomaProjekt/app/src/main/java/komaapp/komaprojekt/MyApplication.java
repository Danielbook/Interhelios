package komaapp.komaprojekt;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

public class MyApplication extends Application {

    private static Context context;

    public static MediaPlayer menuMusic, buttonSound, startSound;

    private static Database database = new Database();

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static void loadSound(Context ctx) {
        menuMusic = MediaPlayer.create(ctx, R.raw.menu_music);
        menuMusic.setLooping(true);
        buttonSound = MediaPlayer.create(ctx, R.raw.button_sound);
        startSound = MediaPlayer.create(ctx, R.raw.start_sound);

        menuMusic.setVolume(0.1f * (float)database.getMusicVolume(), 0.1f * (float)database.getMusicVolume());
        buttonSound.setVolume(0.1f * (float)database.getSoundVolume(), 0.1f * (float)database.getSoundVolume());
        startSound.setVolume(0.1f * (float)database.getSoundVolume(), 0.1f * (float)database.getSoundVolume());

        if(!menuMusic.isPlaying()){
            menuMusic.start();
        }
    }

    public static void playButton(){
        buttonSound.start();
    }

    public static void startButton(){
        startSound.start();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}