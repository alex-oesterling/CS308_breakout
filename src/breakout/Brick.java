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

    public Brick (String imagefile, Group root, Ball a){
        super(imagefile, root);
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
        ball = a;
    }
    @Override
    /**
     * code duplication
     */
    public void update(double elapsedTime) {
        secondsElapsed+=elapsedTime;
        /*
        if(secondsElapsed % 3 <= 0.01){
            state++;
            secondsElapsed=0;
            if(state%4 == 1) {
                this.changeImage("brick2.png");
            }
            if(state%4 == 2) {
                this.changeImage("brick3.png");
            }
            if(state%4 == 3) {
                this.changeImage("brick4.png");
            }
            if(state%4 == 0) {
                this.changeImage("brick.png");
            }
        }
         */
    }
    public int damage(){return 0;}
    public abstract void collide(List<Brick> b);
    public int getHealth(){return health;}
    public void setHealth(int h){health = h;}
    public Ball getBall(){return ball;}
}
