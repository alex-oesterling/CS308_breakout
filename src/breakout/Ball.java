package breakout;

import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.List;

/**
 * The ball class, contains most of the backend code. Controls ball motion and physics, triggers brick collisions,
 * and manages game stats of score and lives (which transfer between scenes with the ball)
 */
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
    private boolean poweredUp;
    private double secondsElapsed;
    private double mouseX;
    private double mouseY;
    /**
     * Constructor, sets ball velocity, mode, size, and sets the beginning score and lives.
     * @param imagefile - the filename for the image used to render the ball
     */
    public Ball(String imagefile){
        super(imagefile);
        xVel = Math.cos(Math.toRadians(Math.random()*Math.PI))*BALL_VELOCITY;
        yVel = Math.sin(Math.toRadians(Math.random()*Math.PI))*BALL_VELOCITY*-1;
        System.out.println(xVel + "\t" + yVel);
        this.getImage().setFitWidth(20);
        this.getImage().setFitHeight(20);
        mode = "normal";
        insideBricks = false;
        launched = false;
        destroyedBrick = false;
        score = 0;
        lives = 3;
        poweredUp = false;
        secondsElapsed = 0.0;
    }

    /**
     * Controls motion of ball, physics of collisions with bumper, walls, and bricks,
     * and determines whether the ball has been launched or not
     * @param elapsedTime - Time between update frames
     */
    @Override
    public void update(double elapsedTime) {
        if(launched) {
            move(elapsedTime);
            checkCollisionWithBumper();
            checkCollisionWithBrick();
        } else {
            super.setX(this.getGroup().getChildren().get(1).getBoundsInLocal().getCenterX()-this.getImage().getFitWidth()/2);
            super.setY(this.getGroup().getChildren().get(1).getBoundsInLocal().getCenterY() -30);
        }
        if (this.getGroup().getChildren().get(2) instanceof Text) {
            ((Text) this.getGroup().getChildren().get(2)).setText("Score: " + score);
            ((Text) this.getGroup().getChildren().get(2)).setX(this.getScene().getWidth()-((Text) this.getGroup().getChildren().get(2)).getLayoutBounds().getWidth());
        }
        if (this.getGroup().getChildren().get(3) instanceof Text) {
            ((Text) this.getGroup().getChildren().get(3)).setText("Lives: " + lives);
        }
        decayPowerUp(elapsedTime);
    }

    /**
     * Keeps powerup active for 3 seconds, then removes it.
     * @param elapsedTime - Time elapsed between update calls
     */
    private void decayPowerUp(double elapsedTime) {
        if (poweredUp) {
            secondsElapsed += elapsedTime;
            if (secondsElapsed > 10.0) {
                System.out.println("bop");
                secondsElapsed = 0;
                poweredUp = false;
                this.setMode("normal");
            }
        }
    }

    /**
     * Move ball around stage at a certain velocity, and bounce off of walls or lose a life if it falls off the
     * @param elapsedTime - Time update between frames
     */
    private void move(double elapsedTime) {
        this.setX(this.getX() + xVel * elapsedTime);
        this.setY(this.getY() + yVel * elapsedTime);
        if(mode != "seeker") {
            if (this.getX() >= this.getScene().getWidth() - this.getImage().getBoundsInLocal().getWidth()) {
                xVel *= -1;
            }
            if (this.getX() <= 0) {
                xVel *= -1;
            }
            if (this.getY() <= 0) {
                yVel *= -1;
            }
        } else {
            double angle = Math.atan2(mouseY-this.getY(), mouseX-this.getX());
            xVel = Math.cos(angle)*BALL_VELOCITY;
            yVel = Math.sin(angle)*BALL_VELOCITY;
        }
        if(this.getY() >= this.getScene().getHeight()){
            launched = false;
            lives--;
            mode = "normal";
        }
    }

    /**
     * Checks collision with bumper and changes velocity vectors as appropriate
     */
    private void checkCollisionWithBumper() {
        if (this.getYVel() >= 0 && this.getImage().getBoundsInLocal().intersects(this.getGroup().getChildren().get(1).getBoundsInLocal())) {
            double degree = Math.abs(this.getImage().getBoundsInLocal().getCenterX() - this.getGroup().getChildren().get(1).getBoundsInLocal().getCenterX()) / (this.getGroup().getChildren().get(1).getBoundsInLocal().getWidth() / 2);
            degree = Math.toRadians(90 * degree);
            this.setYVel(Math.abs(Math.cos(degree) * BALL_VELOCITY) * -1);
            double absXVel = Math.abs(Math.sin(degree) * BALL_VELOCITY);
            if (this.getImage().getBoundsInLocal().getCenterX() < this.getGroup().getChildren().get(1).getBoundsInLocal().getCenterX()) {
                this.setXVel(absXVel * -1);
            } else if (this.getImage().getBoundsInLocal().getCenterX() > this.getGroup().getChildren().get(1).getBoundsInLocal().getCenterX()) {
                this.setXVel(absXVel);
            }
        }
    }

    /**
     * Checks collision with bricks and triggers effects and bounces as necessary
     */
    private void checkCollisionWithBrick() {
        for (int i = 0; i < bricks.size(); i++) {
            if (checkIntersection(bricks.get(i)) && bricks.get(i) != inside) {
                boolean notportal = !(bricks.get(i) instanceof PortalBrick);
                Brick b = bricks.get(i);
                if (!insideBricks) {
                    insideBricks = true;
                    inside = b;
                    if(mode != "ghost") {
                        b.collide(bricks);
                    }
                }
                if (mode != "ghost" && (mode != "wrecking ball" && (notportal) || destroyedBrick == false)) {
                    bounceBrick(b);
                }
                if(destroyedBrick && notportal){score++;}
                destroyedBrick = false;
            } else if(inside != null && !checkIntersection(inside)){
                if(insideBricks) {
                    insideBricks = false;
                    if(mode == "ghost"){
                        setMode("normal");
                    }
                }
                inside = null;
            }
        }
    }

    /**
     * Handles I/O for the ball. Controls launching and some of the cheatcodes.
     * @param code - the keyboard input passed by EventHandler
     */
    public void ballKeyInput(KeyCode code) {
        if((code == KeyCode.SPACE || code == KeyCode.W) && !launched){
            launched = true;
            xVel = Math.cos(Math.toRadians(Math.random()*Math.PI))*BALL_VELOCITY;
            yVel = Math.sin(Math.toRadians(Math.random()*Math.PI))*BALL_VELOCITY;
        }
        if(code == KeyCode.R){
            launched = false;
        }
        if(code == KeyCode.L){
            lives++;
        }
    }

    /**
     * Handles mouse tracking for seeker mode on ball.
     * @param e - the mouse input passed by EventHandler
     */
    public void ballMouseTracker(MouseEvent e){
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
    }

    /**
     * Checks the intersection of the ball with a brick
     * @param brick - a single brick object from the list of bricks in the stage
     * @return a boolean of whether or not the ball intersected the passed brick
     */
    public boolean checkIntersection(Brick brick){
        Bounds bounds = this.getImage().getBoundsInLocal();
        return(brick.getImage().getBoundsInLocal().intersects(bounds.getCenterX()+bounds.getWidth()/2, bounds.getCenterY(), 1, 1)
                || brick.getImage().getBoundsInLocal().intersects(bounds.getCenterX()-bounds.getWidth()/2, bounds.getCenterY(), 1, 1)
                || brick.getImage().getBoundsInLocal().intersects(bounds.getCenterX(), bounds.getCenterY()+bounds.getHeight()/2, 1, 1))
                || brick.getImage().getBoundsInLocal().intersects(bounds.getCenterX(), bounds.getCenterY()-bounds.getHeight()/2, 1, 1);
    }

    /**
     * If called, changes ball's velocity vectors as appropriate for bouncing off of a brick
     * @param brick - the brick object being bounced off of
     */
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

    /**
     * Updates the list of bricks in the root/scene. Used with teleporter so ball
     * doesnt collide with bricks in the other dimension
     * @param b - the list of bricks which the ball can interact with in the new dimension
     */
    public void updateBricks(List<Brick> b){
        bricks = b;
    }

    /**
     * Changes mode of the brick. Used for powerups.
     * @param s - a string representing the "powerup" of the ball
     */
    public void setMode(String s) {
        mode = s;
        if(s == "normal"){
            this.changeImage("ball.png");
        }else if(s == "fireball"){
            this.changeImage("fire_ball.png");
        } else if(s == "wrecking ball"){
            this.changeImage("wrecking_ball.png");
        } else if(s == "ghost"){
            this.changeImage("ghost_ball.png");
        } else if(s == "seeker"){
            this.changeImage("seeker_ball.png");
        }
    }

    /**
     * @return the current mode of the ball
     */
    public String getMode(){return mode;}

    /**
     * Called by bricks when they destroy themselves to let the ball know whether it destroyed a brick or not
     * @param a - a boolean passed by each brick telling the ball whether it was destroyed or not
     */
    public void setDestroyedBrick(boolean a){destroyedBrick = a;}

    /**
     * @return the current score in the game
     */
    public int getScore(){return score;}

    /**
     * @return the current lives left in the game
     */
    public int getLives(){return lives;}

    /**
     * Sets whether the ball has been launched by the paddle or not
     * @param s - a boolean representing whether or not the ball has been launched
     */
    public void setLaunched(boolean s){launched = s;}

    /**
     * @return the current velocity in the X direction
     */
    public double getXVel(){return xVel;}

    /**
     * @return the current velocity in the Y direction
     */
    public double getYVel(){return yVel;}

    /**
     * Sets the X velocity of the ball
     * @param a - the new X velocity value
     */
    public void setXVel(double a){xVel = a;}

    /**
     * Sets the Y velocity of the ball
     * @param b - the new Y velocity value
     */
    public void setYVel(double b){yVel = b;}

    /**
     * sets the powerup status of the ball
     * @param b - a boolean representing whether or not the ball is powered up
     */
    public void setPoweredUp(boolean b){poweredUp = b;};
}
