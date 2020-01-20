package breakout;

import java.util.List;

/**
 * A child of the Brick class. This brick provides powerups when destroyed.
 */
public class PowerBrick extends Brick{
    public static final String FIREBALL = "fireball.png";
    public static final String WRECKINGBALL = "wreckingball.png";
    public static final String GHOST = "ghost.png";
    private int state;
    private String[] filenames;

    /**
     * Constructor, calls parent constructor and then randomly sets the "powerup" given by the block on instantiation.
     * @param imagefile
     * @param a
     */
    public PowerBrick(String imagefile, Ball a) {
        super(imagefile, a);
        this.setHealth(1);
        state = (int) Math.round(Math.random()*3);
        filenames = new String[]{FIREBALL, WRECKINGBALL, GHOST, "brick4.png"};
        this.changeImage(filenames[state]);
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
     * Called when ball intersects a brick, triggers the loss of health and subsequent removal from scene as well as
     * giving the ball a specific powerup based on the state variable
     * @param b - the list of bricks which the brick is in, allowing it to remove itself from the scene
     */
    @Override
    public void collide(List<Brick> b) {
        if(this.damage() <= 0) {
            this.getBall().setPoweredUp(true);
            if(state == 0){
                this.getBall().setMode("fireball");
            } else if (state == 1){
                this.getBall().setMode("wrecking ball");
            } else if (state == 2){
                this.getBall().setMode("fireball");
            } else if (state == 3){
                this.getBall().setMode("wrecking ball");
            }
            this.getGroup().getChildren().remove(this.getImage());
            b.remove(this);
            this.getBall().setDestroyedBrick(true);
        }
    }
}
