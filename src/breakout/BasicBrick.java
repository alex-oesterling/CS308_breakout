package breakout;

import javafx.scene.Group;

import java.util.List;

public class BasicBrick extends Brick {
    public BasicBrick(String imagefile, Ball a) {
        super(imagefile, a);
        this.setHealth(1);
    }
    @Override
    public int damage(){
        this.setHealth(this.getHealth()-1);
        return this.getHealth();
    }
    @Override
    public void collide(List<Brick> b) {
        if(this.damage() <= 0) {
            this.getGroup().getChildren().remove(this.getImage());
            b.remove(this);
            this.getBall().setDestroyedBrick(true);
        }
    }
}
