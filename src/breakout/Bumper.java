package breakout;

import javafx.scene.input.KeyCode;

/**
 * The bumper class is used to create and control bumpers in the game.
 * It assumes that there is a scene for it to be placed into
 * It depends on the JavaFX library
 * @author Alex Oesterling, axo
 * @version 1/19/20
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
    public void update(double elapsedTime) {
        this.setY(this.getScene().getHeight()-this.getImage().getBoundsInLocal().getHeight());
    }

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
