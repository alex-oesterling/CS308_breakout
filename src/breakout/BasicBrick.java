package breakout;

import javafx.scene.Group;

import java.util.List;

public class BasicBrick extends Brick {
    public BasicBrick(String imagefile, Group root) {
        super(imagefile, root);
        this.setHealth(1);
    }

    @Override
    public void collide(List<Brick> b) {
        if(this.damage() == 0) {
            this.getGroup().getChildren().remove(this.getImage());
            b.remove(this);
        }
    }
}
