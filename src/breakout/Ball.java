package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends PortalObject {
    private static final int VELOCITY = 50;
    private double xVel, yVel;
    private ImageView myBall;

    /**
     * CLEAN UP
     * Constructor
     * @param imagefile
     */
    public Ball(String imagefile){
        xVel = Math.random()*50;
        yVel = Math.sqrt(Math.pow(VELOCITY, 2) + Math.pow(xVel, 2));
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(imagefile));
        myBall = new ImageView(image);
        myBall.setFitHeight(50);
        myBall.setFitWidth(50);
        this.setImage(myBall);
    }

    /**
     * CASTING XVEL AND YVEL TO INT ARE NOT IDEAL... CHANGE?
     * @param scene - Scene in which ball is placed
     * @param elapsedTime - Time between update frames
     */
    @Override
    public void update(Scene scene, double elapsedTime) {
        this.setX(this.getX() + xVel * elapsedTime);
        this.setY(this.getY() + yVel * elapsedTime);
        myBall.setX(this.getX());
        myBall.setY(this.getY());

        if(myBall.getX() >= scene.getWidth()-myBall.getBoundsInLocal().getWidth()){
            xVel *=-1;
        }
        if(myBall.getX() <= 0){
            xVel *=-1;
        }
        if(myBall.getY() >= scene.getHeight()-myBall.getBoundsInLocal().getHeight()){
            yVel *=-1;
        }
        if(myBall.getY() <= 0){
            yVel *=-1;
        }
    }

    /**
     * Changes image of ball - useful for when ball becomes powered up
     * CLEAN UP
     * @param imagefile - Image to replace ball's current image.
     */
    public void changeImage(String imagefile){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(imagefile));
        myBall = new ImageView(image);
        this.setImage(myBall);
    }
    public double getXVel(){return xVel;}
    public double getYVel(){return yVel;}
    public void setXVel(double a){xVel = a;}
    public void setYVel(double b){yVel = b;}
}
