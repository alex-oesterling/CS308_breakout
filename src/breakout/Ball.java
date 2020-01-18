package breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

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
    private int score;
    private int lives;
    /**
     * CLEAN UP
     * Constructor
     * @param imagefile
     */
    public Ball(String imagefile, Group root, List b){
        super(imagefile, root);
        xVel = Math.cos(Math.random()*Math.PI)*BALL_VELOCITY;
        yVel = Math.sin(Math.random()*Math.PI)*BALL_VELOCITY;
        bricks = b;
        this.getImage().setFitWidth(20);
        this.getImage().setFitHeight(20);
        mode = "normal";
        insideBricks = false;
        launched = false;
        destroyedBrick = false;
        score = 0;
        lives = 3;
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
            if(this.getY() >= this.getScene().getHeight()){
                launched = false;
                System.out.println("OOF");
                lives--;
            }
            for (int i = 0; i < bricks.size(); i++) {
                if (checkIntersection(bricks.get(i)) && bricks.get(i) != inside) {
                    boolean notportal = !(bricks.get(i) instanceof PortalBrick);
                    Brick b = bricks.get(i);
                    if (!insideBricks) {
                        insideBricks = true;
                        //possible remove?
                        inside = b;
                        b.collide(bricks);
                    }
                    if ((mode != "wrecking ball" && (notportal)) || destroyedBrick == false) {
                        bounceBrick(b);
                    }
                    if(destroyedBrick && notportal){score++;}
                    destroyedBrick = false;
                } else if(!checkIntersection(bricks.get(i))){
                    insideBricks = false;
                    inside = null;
                }
            }
        } else {
            if(this.getGroup().getChildren().get(0) instanceof Group){
                super.setX(((Group)this.getGroup().getChildren().get(0)).getChildren().get(1).getBoundsInLocal().getCenterX());
                super.setY(((Group)this.getGroup().getChildren().get(0)).getChildren().get(1).getBoundsInLocal().getCenterY() -30);
            }
        }
        if(this.getGroup().getChildren().get(0) instanceof Group) {
            Group essentials = (Group)this.getGroup().getChildren().get(0);
            if (essentials.getChildren().get(2) instanceof Text) {
                ((Text) essentials.getChildren().get(2)).setText("Score: " + score);
            }
        }
    }

    public void ballKeyInput(KeyCode code) {
        if((code == KeyCode.SPACE || code == KeyCode.W) && !launched){
            launched = true;
            xVel = Math.cos(Math.random()*Math.PI)*BALL_VELOCITY;
            yVel = Math.sin(Math.random()*Math.PI)*BALL_VELOCITY;
        }
        if(code == KeyCode.Q){
            launched = false;
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

    public int getScore(){return score;}
    public int getLives(){return lives;}

    public double getXVel(){return xVel;}
    public double getYVel(){return yVel;}
    public void setXVel(double a){xVel = a;}
    public void setYVel(double b){yVel = b;}
}
