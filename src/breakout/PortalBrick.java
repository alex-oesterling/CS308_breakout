package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import java.util.List;

/**
 * A complex brick which teleports the player to a different dimension (in the same level)
 * More functionally it changes the root group which the scene is operating on
 * It assumes there is a scene in which it can be placed, as well as a second group and list
 * of bricks to "swap"
 * It depends on the JavaFX library
 * @author Alex Oesterling, axo
 * @version 1/19/20
 */
public class PortalBrick extends Brick{
    private Group exit, entry;
    private List<Brick> exitList;

    /**
     * The constructor for the PortalBrick, sets up destination, start, lists of bricks in both dimensions,
     * as well as calling the parent constructor for image rendering and sizing
     * @param imagefile - the filename for the image used to render the brick
     * @param root - the group which the scene is currently using
     * @param a - the ball which will interact with the brick
     * @param b - the group which the portal will teleport the ball to
     * @param c - the list of bricks in the destination location
     */
    public PortalBrick(String imagefile, Group root, Ball a, Group b, List c) {
        super(imagefile, a);
        exit = b;
        exitList = c;
        entry = root;
    }

    /**
     * Called by ball when it intersects with a portal. Switches the root group and list of bricks in the new dimension,
     * essentially allowing for a new dimension to be rendered, while also porting over essential objects
     * such as the ball, bumper, score, and lives indicators.
     * @param b - the list of bricks in which the PortalBrick resides. Unused by this implementation
     */
    @Override
    public void collide(List<Brick> b) {
        this.getBall().setDestroyedBrick(true);
        Node essentials = entry.getChildren().get(0);
        entry.getChildren().remove(0);
        exit.getChildren().add(0, essentials);
        this.getBall().setRoot(exit);
        this.getBall().updateBricks(exitList);
    }
}
