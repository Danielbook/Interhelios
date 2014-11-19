package komaapp.komaprojekt.scene;

/**
 * Created by Adam on 2014-11-13.
 */
        import org.andengine.entity.scene.background.Background;
        //import org.andengine.entity.text.Text;
        //import org.andengine.util.adt.color.Color;

        import komaapp.komaprojekt.base.BaseScene;
        import komaapp.komaprojekt.manager.SceneManager.SceneType;

public class LoadingScene extends BaseScene
{
    @Override
    public void createScene()
    {
        setBackground(new Background(0.0044f, 0.6274f, 0.9984f));
        //setBackground(new Background(Color.WHITE));
        //attachChild(new Text(400, 240, resourcesManager.font, "Loading...", vbom));
    }

    @Override
    public void onBackKeyPressed()
    {
        return;
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene()
    {

    }
}