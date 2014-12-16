package komaapp.komaprojekt;

import android.app.Application;
import android.content.Context;

public class RunOnStart extends Application
{
    private static Context context;

    public static soundHandler soundHandler;

    public void onCreate(){
        super.onCreate();
        RunOnStart.context = getApplicationContext();
        soundHandler = new soundHandler(RunOnStart.context);
    }

    public static Context getAppContext() {
        return RunOnStart.context;
    }
}