package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

/**
 * @author me
 */
public abstract class PortalObject {
    private double x, y;
    private Group group;
    private ImageView myImage;
    private Scene scene;

    public PortalObject(){}
    public PortalObject(Scene s, String imagefile) throws NullPointerException{
        if(s == null){
            throw new NullPointerException("ASDF");
        }
        scene = s;
        myImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(imagefile)));
        //this.changeImage(imagefile);
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

    /**
     * Changes image of ball - useful for when ball becomes powered up
     * CLEAN UP
     * @param imagefile - Image to replace ball's current image.
     */
    public void changeImage(String imagefile){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(imagefile));
        myImage = new ImageView(image);
    }
}
