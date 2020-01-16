package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.List;

public class PortalBrick extends Brick{
    private Group exit;
    private List<Brick> exitList;
    private Ball ball;
    public PortalBrick(String imagefile, Group root, Ball a, Group b, List c) {
        super(imagefile, root);
        ball = a;
        exit = b;
        exitList = c;
    }

    /**
     * Redo collide so that no need for list?
     * @param b
     */
    @Override
    public void collide(List<Brick> b) {
        System.out.println(this.getGroup().getChildren());

        Node ballimg = this.getGroup().getChildren().get(0);
        Node buttonimg = this.getGroup().getChildren().get(1);
        this.getGroup().getChildren().remove(0);
        this.getGroup().getChildren().remove(0);
        
        this.setGroup(exit);
        /*
        this.getGroup().getChildren().add(0, buttonimg);
        this.getGroup().getChildren().add(0, ballimg);
        ball.updateBricks(exitList);


         */
    }
}
