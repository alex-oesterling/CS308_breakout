package breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick extends PortalObject {
    private double secondsElapsed;
    private int state;
    public Brick (String imagefile, Group root){
        super(imagefile, root);
        //necessary?
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
    }
    @Override
    /**
     * code duplication
     */
    public void update(double elapsedTime) {
        secondsElapsed+=elapsedTime;
        //System.out.print(secondsElapsed%3 + "\t");
        if(secondsElapsed % 3 <= 0.1){
            state++;
            //System.out.println(state);
            if(state%4 == 1) {
                this.changeImage("brick2.png");
            }
            if(state%4 == 2) {
                this.changeImage("brick3.png");
            }
            if(state%4 == 3) {
                this.changeImage("brick4.png");
            }
            if(state%4 == 0) {
                this.changeImage("brick.png");
            }
        }
    }
    @Override
    public void changeImage(String imagefile){
        super.changeImage(imagefile);
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
    }
}
