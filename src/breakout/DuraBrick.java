package breakout;

import javafx.scene.Group;

import java.util.List;

public class DuraBrick extends Brick {
    public DuraBrick(String imagefile, Group root) {
        super(imagefile, root);
        this.setHealth(2);
    }
    @Override
    public int damage(){
        this.changeImage("Brick_Damaged.png");
        this.setHealth(this.getHealth()-1);
        System.out.println(this.getHealth());
        return this.getHealth();
    }

    @Override
    public void collide(List<Brick> b) {
        if(this.damage() == 0) {
            System.out.println("yeet");
            this.getGroup().getChildren().remove(this.getImage());
            b.remove(this);
        }
    }
}
