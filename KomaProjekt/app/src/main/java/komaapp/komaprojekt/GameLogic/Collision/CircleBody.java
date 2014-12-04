package komaapp.komaprojekt.GameLogic.Collision;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by benjamin on 14-12-01.
 */
public class CircleBody {
    private final float radius;
    private float centerX, centerY;

    public CircleBody(float radius, float pX, float pY) {
        this.radius = radius;
        this.centerX = pX;
        this.centerY = pY;
    }

    public float getRadius() {
        return this.radius;
    }

    public Vector2 getCenterPosition() {
        return new Vector2(centerX, centerY);
    }

    public void setCenterPosition(float pX, float pY) {
        this.centerX = pX;
        this.centerY = pY;
    }

    public static float calcRadiusFromWidthAndHeight(float width, float height)
    {
        return (width + height) / 4;
    }
}
