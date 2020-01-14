package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends PortalObject {
    private static final int VELOCITY = 50;
    private double xVel, yVel;

    /**
     * CLEAN UP
     * Constructor
     * @param imagefile
     */
    public Ball(String imagefile, Group root){
        super(imagefile, root);
        xVel = Math.random()*50;
        yVel = Math.sqrt(Math.pow(VELOCITY, 2) + Math.pow(xVel, 2));
        this.getImage().setFitWidth(50);
        this.getImage().setFitHeight(50);
    }

    /**
     * CASTING XVEL AND YVEL TO INT ARE NOT IDEAL... CHANGE?
     * @param elapsedTime - Time between update frames
     */
    @Override
    public void update(double elapsedTime) {
        this.setX(this.getX() + xVel * elapsedTime);
        this.setY(this.getY() + yVel * elapsedTime);
        this.getScene().getWidth();
        if(this.getX() >= this.getScene().getWidth()-this.getImage().getBoundsInLocal().getWidth()){
            xVel *=-1;
            this.changeImage("brick3.png");
        }
        if(this.getX() <= 0){
            xVel *=-1;
        }
        /*
        if(this.getY() >= this.getScene().getHeight()-this.getImage().getBoundsInLocal().getHeight()){
            yVel *=-1;
        }

         */
        if(this.getY() <= 0){
            yVel *=-1;
        }
    }


    public double getXVel(){return xVel;}
    public double getYVel(){return yVel;}
    public void setXVel(double a){xVel = a;}
    public void setYVel(double b){yVel = b;}

    @Override
    public void changeImage(String imagefile){
        super.changeImage(imagefile);
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
    }
}
