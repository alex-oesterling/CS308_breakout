package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends PortalObject {
    public static final int BALL_VELOCITY = 100;
    private double xVel, yVel;
    private Brick[] bricks;

    /**
     * CLEAN UP
     * Constructor
     * @param imagefile
     */
    public Ball(String imagefile, Group root, Brick[] b){
        super(imagefile, root);
        xVel = Math.random()*200-100;
        yVel = Math.sqrt(Math.pow(BALL_VELOCITY, 2) - Math.pow(xVel, 2));
        bricks = b;
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
        if(this.getX() >= this.getScene().getWidth()-this.getImage().getBoundsInLocal().getWidth()){
            xVel *=-1;
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
        for(Brick brick : bricks){
            if(this.getImage().getBoundsInLocal().intersects(brick.getImage().getBoundsInLocal())) {
                if (this.getImage().getBoundsInLocal().getCenterX() <= brick.getImage().getBoundsInLocal().getMinX()) {
                    this.setXVel(Math.abs(this.getXVel()) * -1);
                } else if (this.getImage().getBoundsInLocal().getCenterX() >= brick.getImage().getBoundsInLocal().getMaxX()) {
                    this.setXVel(Math.abs(this.getXVel()));
                } else if (this.getImage().getBoundsInLocal().getCenterY() <= brick.getImage().getBoundsInLocal().getMinY()) {
                    this.setYVel(Math.abs(this.getYVel()) * -1);
                } else if (this.getImage().getBoundsInLocal().getCenterY() >= brick.getImage().getBoundsInLocal().getMaxY()) {
                    this.setYVel(Math.abs(this.getYVel()));
                }
                //this.getGroup().getChildren().get(brick.getImage()) = null;
                this.getGroup().getChildren().remove(brick.getImage());

                //array of bricks parameter
            }
        }
    }

    public double getXVel(){return xVel;}
    public double getYVel(){return yVel;}
    public void setXVel(double a){xVel = a;}
    public void setYVel(double b){yVel = b;}
}
