package breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * BIG Question: How do I have a brick remove itself/make itself null?
 */
public class Brick extends PortalObject {
    private double secondsElapsed;
    private int state;
    private int health;

    public Brick (String imagefile, Group root){
        super(imagefile, root);
        health = 1;
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
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
    public int damage(){
        return(health--);
    }
    public int getHealth(){return health;}
    public void setHealth(int h){health = h;}
}
