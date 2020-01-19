package breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * BIG Question: How do I have a brick remove itself/make itself null?
 */
public abstract class Brick extends PortalObject {
    private double secondsElapsed;
    private int state;
    private int health;
    private Ball ball;

    public Brick (String imagefile, Ball a){
        super(imagefile);
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
        ball = a;
    }
    @Override
    /**
     * code duplication
     */
    public void update(double elapsedTime) {
    }

    public int damage(){return 0;}
    public abstract void collide(List<Brick> b);
    public int getHealth(){return health;}
    public void setHealth(int h){health = h;}
    public Ball getBall(){return ball;}
}
