package breakout;

import javafx.scene.Group;

import java.util.List;

/**
 * A healing brick which gives you an additional life.
 */
public class HealthBrick extends Brick {
    /**
     * Constructor, sets image and health parameters
     * @param imagefile - the filename of the image used to render the brick
     * @param a - the ball which will be interacting with the brick
     */
    public HealthBrick(String imagefile, Ball a) {
        super(imagefile, a);
        this.setHealth(1);
    }

    /**
     * Removes 1 health from the brick and calculates its remaining health.
     * @return the remaining health of the brick
     */
    @Override
    public int damage(){
        this.setHealth(this.getHealth()-1);
        return this.getHealth();
    }

    /**
     * Called when ball intersects a brick, triggers the loss of health and subsequent removal from scene
     * @param b - the list of bricks which the brick is in, allowing it to remove itself from the scene
     */
    @Override
    public void collide(List<Brick> b) {
        if(this.damage() <= 0) {
            this.getBall().setLives(this.getBall().getLives()+1);
            this.getGroup().getChildren().remove(this.getImage());
            b.remove(this);
            this.getBall().setDestroyedBrick(true);
        }
    }
}
