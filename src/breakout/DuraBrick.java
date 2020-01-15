package breakout;

import javafx.scene.Group;

public class DuraBrick extends Brick {
    public DuraBrick(String imagefile, Group root) {
        super(imagefile, root);
        this.setHealth(2);
    }
    @Override
    public int damage(){
        this.changeImage("Brick_Damaged.png");
        this.setHealth(this.getHealth()-1);
        return this.getHealth();
    }
}
