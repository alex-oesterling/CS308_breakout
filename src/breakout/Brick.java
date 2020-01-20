package breakout;

import java.util.List;

/**
 * Abstract Brick class which allows me to put all the different types of bricks into one list
 * It allows other objects to universally access the unique collision methods of
 * different bricks.
 * It assumes there is a scene in which it can be placed.
 * It depends on the JavaFX library
 * @author Alex Oesterling, axo
 * @version 1/19/20
 */
public abstract class Brick extends PortalObject {
    private int health;
    private Ball ball;

    /**
     * Brick constructor, sets bricksize and gives it access to the ball object
     * @param imagefile - filename for the image rendered for each brick
     * @param a - ball object in scene
     */
    public Brick (String imagefile, Ball a){
        super(imagefile);
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
        ball = a;
    }
    @Override
    /**
     * Updater method inherited from PortalObject and passed down to each individual brick
     */
    public void update(double elapsedTime) {
    }

    /**
     * General damage method overwritten by certain blocks which break
     * @return the remaining health of the block after damaging
     */
    public int damage(){return 0;}

    /**
     * Called by ball when it intersects with a brick.
     * @param b - the list of bricks which the brick is in, allowing it to remove itself from the scene
     */
    public abstract void collide(List<Brick> b);

    /**
     * @return the health of the brick
     */
    public int getHealth(){return health;}

    /**
     * Sets the health of the brick
     * @param h - the new health of the brick
     */
    public void setHealth(int h){health = h;}

    /**
     * Gets the ball object for use by each child Brick class
     * @return the Ball object in the scene
     */
    public Ball getBall(){return ball;}
}
