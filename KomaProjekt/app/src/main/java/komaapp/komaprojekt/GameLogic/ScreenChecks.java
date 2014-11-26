package komaapp.komaprojekt.GameLogic;

import komaapp.komaprojekt.Game;

/**
 * Created by benjamin on 14-11-26.
 */
public class ScreenChecks {
    public static boolean isOutOfScreen(float centerX, float centerY, float halfWidth, float halfHeight)
    {
        /*
        if (centerX + halfWidth < 0) return true;
        if (centerX - halfWidth > Game.CAMERA_WIDTH) return true;
        if (centerY + halfHeight < 0) return true;
        if (centerY - halfHeight > Game.CAMERA_HEIGHT) return true;
        */

        return (centerX+halfWidth < 0) || (centerX - halfWidth > Game.CAMERA_WIDTH) || (centerY + halfHeight < 0) || (centerY - halfHeight > Game.CAMERA_HEIGHT);
    }
}
