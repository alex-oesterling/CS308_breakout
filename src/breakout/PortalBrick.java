package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PortalBrick extends Brick{
    private Group exit, entry;
    private List<Brick> exitList;

    public PortalBrick(String imagefile, Group root, Ball a, Group b, List c) {
        super(imagefile, root, a);
        exit = b;
        exitList = c;
        entry = this.getGroup();
    }

    /**
     * Redo collide so that no need for list?
     * @param b
     */
    @Override
    public void collide(List<Brick> b) {
        this.getBall().setDestroyedBrick(true);
        ObservableList<Node> list = entry.getChildren();
        ObservableList<Node> list2 = exit.getChildren();
        Node essentials = list.get(0);
        list.remove(0);
        this.setGroup(exit);
        list2.add(0, essentials);
        this.getBall().updateBricks(exitList);
    }
}
