package breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class Ball extends PortalObject {
    public static final int BALL_VELOCITY = 200;
    private double xVel, yVel;
    private List<Brick> bricks;
    private String mode;
    /**
     * CLEAN UP
     * Constructor
     * @param imagefile
     */
    public Ball(String imagefile, Group root, List b){
        super(imagefile, root);
        xVel = Math.random()*400-200;
        yVel = Math.sqrt(Math.pow(BALL_VELOCITY, 2) - Math.pow(xVel, 2));
        bricks = b;
        this.getImage().setFitWidth(20);
        this.getImage().setFitHeight(20);
        mode = "normal";
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
        if(this.getY() <= 0){
            yVel *=-1;
        }
        for(int i = 0; i < bricks.size(); i++){
            if(checkIntersection(bricks.get(i))) {
                //if(!(bricks.get(i) instanceof PortalBrick)){
                    bounceBrick(bricks.get(i));

                System.out.println(this.getGroup().getChildren().size());
                if(bricks.get(i) instanceof PortalBrick){
                    bricks.get(i).collide(bricks);
                } else {
                    bricks.get(i).collide(bricks);
                }
            }
        }
    }
    public boolean checkIntersection(Brick brick){
        Bounds bounds = this.getImage().getBoundsInLocal();
        return(brick.getImage().getBoundsInLocal().intersects(bounds.getCenterX()+bounds.getWidth()/2, bounds.getCenterY(), 1, 1)
                || brick.getImage().getBoundsInLocal().intersects(bounds.getCenterX()-bounds.getWidth()/2, bounds.getCenterY(), 1, 1)
                || brick.getImage().getBoundsInLocal().intersects(bounds.getCenterX(), bounds.getCenterY()+bounds.getHeight()/2, 1, 1))
                || brick.getImage().getBoundsInLocal().intersects(bounds.getCenterX(), bounds.getCenterY()-bounds.getHeight()/2, 1, 1);
    }

    public void bounceBrick(Brick brick){
        if (this.getImage().getBoundsInLocal().getCenterX() <= brick.getImage().getBoundsInLocal().getMinX()) {
            this.setXVel(Math.abs(this.getXVel()) * -1);
        } else if (this.getImage().getBoundsInLocal().getCenterX() >= brick.getImage().getBoundsInLocal().getMaxX()) {
            this.setXVel(Math.abs(this.getXVel()));
        } else if (this.getImage().getBoundsInLocal().getCenterY() <= brick.getImage().getBoundsInLocal().getMinY()) {
            this.setYVel(Math.abs(this.getYVel()) * -1);
        } else if (this.getImage().getBoundsInLocal().getCenterY() >= brick.getImage().getBoundsInLocal().getMaxY()) {
            this.setYVel(Math.abs(this.getYVel()));
        }
    }

    public void updateBricks(List<Brick> b){
        bricks = b;
    }

    public double getXVel(){return xVel;}
    public double getYVel(){return yVel;}
    public void setXVel(double a){xVel = a;}
    public void setYVel(double b){yVel = b;}
}
