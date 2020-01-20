package breakout;

import javafx.scene.input.KeyCode;

/**
 * The bumper class which is controlled by the arrow keys and bounces the ball
 */
public class Bumper extends PortalObject {
    private static final int VELOCITY = 5;

    /**
     * The bumper constructor, setting its ImageView size
     * @param imagefile - the filename of the image used by the bumper
     */
    public Bumper(String imagefile){
        super(imagefile);

        this.getImage().setFitHeight(20);
        this.getImage().setFitWidth(100);
    }

    /**
     * Update function, not used by bumper (all update code is collected in the ball update() method)
     * @param elapsedTime - the time between calls of update (to smooth motion with varying timesteps)
     */
    @Override
    public void update(double elapsedTime) {return;}

    /**
     * Key input function, moves the bumper when certain keyboard keys are pressed
     * @param code - the key pressed and passed by the EventHandler
     */
    public void bumperKeyInput(KeyCode code) {
        //avoid "jittering" by preventing the move in the first place
        if(this.getImage().getX() < this.getScene().getWidth()-this.getImage().getBoundsInLocal().getWidth() && (code == KeyCode.RIGHT || code == KeyCode.D)){
            this.setX(this.getImage().getX() + VELOCITY);
        } else if(this.getImage().getX() >0 && (code == KeyCode.LEFT || code == KeyCode.A)) {
            this.setX(this.getImage().getX() - VELOCITY);
        }
    }
}
