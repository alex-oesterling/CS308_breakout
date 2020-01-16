package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.List;

/**
 * @author me
 */
public abstract class PortalObject {
    private double x, y;
    private Group root;
    private ImageView myImage;
    private Scene scene;

    public PortalObject(){}
    public PortalObject(String imagefile, Group group){
        this.setImage(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(imagefile))));
        root = group;
    }
    public double getX(){return x;}
    public double getY(){return y;}

    public void setX(double a){
        x = a;
        this.getImage().setX(a);
    }
    public void setY(double b){
        y = b;
        this.getImage().setY(b);
    }

    public abstract void update(double elapsedTime);

    public ImageView getImage(){return myImage;}
    /**
     * necessary?
     * @param a
     */
    public void setImage(ImageView a){myImage = a;}

    public Scene getScene(){return scene;}
    public void setScene(Scene a){scene = a;}

    public Group getGroup(){return root;}
    public void setGroup(Group a){
        root=a;
        this.getScene().setRoot(a);
    }

    /**
     * Changes image of ball - useful for when ball becomes powered up
     * CLEAN UP
     * @param imagefile - Image to replace ball's current image.
     */
    public void changeImage(String imagefile){
        this.getImage().setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(imagefile)));
    }
}
