package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;

public class Ball extends PortalObject {
    public static final String BOUNCER_IMAGE = "ball.png";

    private int VELOCITY = 50;
    private double xVel, yVel;

    public Ball(){
        xVel = Math.random()*50;
        yVel = Math.sqrt(Math.pow(50, 2) + Math.pow(xVel, 2));
        image = new Group;
    }

    /**
     * CASTING XVEL AND YVEL TO INT ARE NOT IDEAL... CHANGE?
     * @param scene
     * @param elapsedTime
     */
    @Override
    public void update(Scene scene, int elapsedTime) {
        this.setX(this.getX() + (int)xVel * elapsedTime);
        this.setY(this.getY() + (int)yVel * elapsedTime);

        if(this.getX() >= scene.getWidth()-this.getWidth()){
            xVel *=-1;
        }
        if(this.getX() <= 0){
            xVel *=-1;
        }
        if(this.getY() >= scene.getHeight()-this.getHeight()){
            yVel *=-1;
        }
        if(this.getY() <= 0){
            yVel *=-1;
        }
    }

    @Override
    public void getImage() {

    }

    public int getWidth(){return 0;}
    public int getHeight(){return 0;}
}
