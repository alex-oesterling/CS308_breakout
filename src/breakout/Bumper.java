package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.List;

public class Bumper extends PortalObject {
    private static final int VELOCITY = 5;

    public Bumper(String imagefile, Group root){
        super(imagefile, root);

        this.getImage().setFitHeight(20);
        this.getImage().setFitWidth(100);

    }


    @Override
    public void update(double elapsedTime) {return;
    }

    public void bumperKeyInput(KeyCode code) {
        //avoid "jittering" by preventing the move in the first place
        if(this.getImage().getX() < this.getScene().getWidth()-this.getImage().getBoundsInLocal().getWidth() && (code == KeyCode.RIGHT || code == KeyCode.D)){
            this.setX(this.getImage().getX() + VELOCITY);
        } else if(this.getImage().getX() >0 && (code == KeyCode.LEFT || code == KeyCode.A)) {
            this.setX(this.getImage().getX() - VELOCITY);
        }
    }
}
