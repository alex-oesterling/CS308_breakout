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
    private ImageView image;


    public PortalObject(){}
    public double getX(){return x;}
    public double getY(){return y;}

    public void setX(double a){x = a;}
    public void setY(double b){y = b;}

    public abstract void update(Scene scene, double elapsedTime);

    public ImageView getImage(){return image;}
    public void setImage(ImageView a){image = a;}
}
