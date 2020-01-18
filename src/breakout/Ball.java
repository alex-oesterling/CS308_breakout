package breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.List;

public class Ball extends PortalObject {
    public static final int BALL_VELOCITY = 200;
    private double xVel, yVel;
    private List<Brick> bricks;
    private String mode;
    private boolean insideBricks;
    private boolean destroyedBrick;
    private boolean launched;
    private Brick inside;
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
        insideBricks = false;
        launched = false;
        destroyedBrick = false;

    }

    /**
     * CASTING XVEL AND YVEL TO INT ARE NOT IDEAL... CHANGE?
     * @param elapsedTime - Time between update frames
     */
    @Override
    public void update(double elapsedTime) {

        if(launched) {
            this.setX(this.getX() + xVel * elapsedTime);
            this.setY(this.getY() + yVel * elapsedTime);
            if (this.getX() >= this.getScene().getWidth() - this.getImage().getBoundsInLocal().getWidth()) {
                xVel *= -1;
            }
            if (this.getX() <= 0) {
                xVel *= -1;
            }
            if (this.getY() <= 0) {
                yVel *= -1;
            }
            for (int i = 0; i < bricks.size(); i++) {
                if (checkIntersection(bricks.get(i)) && bricks.get(i) != inside) {
                    if (!insideBricks) {
                        insideBricks = true;
                        inside = bricks.get(i);
                        bricks.get(i).collide(bricks);
                        System.out.println(inside);
                    }
                    if ((mode != "wrecking ball" && !(bricks.get(i) instanceof PortalBrick)) || destroyedBrick == false) {
                        bounceBrick(bricks.get(i));
                    }
                    destroyedBrick = false;
                } else if(!checkIntersection(bricks.get(i))){
                    insideBricks = false;
                    inside = null;


                    /*
                    if(!(bricks.get(i) instanceof PortalBrick)) {
                        bounceBrick(bricks.get(i));
                    }
                    bricks.get(i).collide(bricks);

                     */
                }
            }
        } else {
            this.setX(this.getGroup().getChildren().get(1).getBoundsInLocal().getCenterX());
            this.setY(this.getGroup().getChildren().get(1).getBoundsInLocal().getCenterY()-30);
        }
    }

    public void ballKeyInput(KeyCode code) {
        if(code == KeyCode.SPACE || code == KeyCode.W){
            launched = true;
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

    public void setMode(String s){
        mode = s;
        if(s == "fireball"){
            this.changeImage("portal1.png");
        } else if(s == "wrecking ball"){
            this.changeImage("portal2.png");
        }
    }
    public String getMode(){return mode;}

    public boolean getDestroyedBrick(){return destroyedBrick;}
    public void setDestroyedBrick(boolean a){destroyedBrick = a;}

    public double getXVel(){return xVel;}
    public double getYVel(){return yVel;}
    public void setXVel(double a){xVel = a;}
    public void setYVel(double b){yVel = b;}
}
