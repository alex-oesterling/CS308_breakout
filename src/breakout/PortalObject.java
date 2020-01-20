package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * PortalObject is an abstract class which all objects I instantiate in my game are created off of.
 * It is used to help organize the instantiation of game components and access universal qualities
 * such as having an ImageView to render
 * It depends on the JavaFX library
 * @author Alex Oesterling, axo
 * @version 1/19/20
 */
public abstract class PortalObject {
    private double x, y;
    private Group root;
    private ImageView myImage;
    private Scene scene;

    /**
     * PortalObject constructor. Creates a corresponding ImageView for each object, allowing
     * classes which extend PortalObject to be rendered in a JavaFX application
     * @param imagefile - A string representing the file to create an image from
     */
    public PortalObject(String imagefile){
        this.setImage(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(imagefile))));
    }

    /**
     * @return the X position of the PortalObject
     */
    public double getX(){return x;}

    /**
     * @return the Y position of the PortalObject
     */
    public double getY(){return y;}

    /**
     * Sets the X position of the PortalObject, moving both the object as well as its corresponding ImageView
     * @param a - the position to set the object to
     */
    public void setX(double a){
        x = a;
        this.getImage().setX(a);
    }
    /**
     * Sets the Y position of the PortalObject, moving both the object as well as its corresponding ImageView
     * @param b - the position to set the object to
     */
    public void setY(double b){
        y = b;
        this.getImage().setY(b);
    }

    /**
     * Abstract method allowing all PortalObjects to access an update method, which is called in the main game loop
     * @param elapsedTime - the time between calls of update (to smooth motion with varying timesteps)
     */
    public abstract void update(double elapsedTime);

    /**
     * @return the Imageview object associated with each PortalObject
     */
    public ImageView getImage(){return myImage;}

    /**
     * Sets the ImageView object assocciated with each PortalObject
     * @param a - the new ImageView to set the object to
     */
    public void setImage(ImageView a){myImage = a;}

    /**
     * @return the current scene the PortalObject is in
     */
    public Scene getScene(){return scene;}

    /**
     * Sets the current scene of the PortalObject
     * @param a - the scene to be set to
     */
    public void setScene(Scene a){scene = a;}

    /**
     * @return the group the PortalObject's ImageView class is in
     */
    public Group getGroup(){return root;}

    /**
     * Sets the current group of the PortalObject
     * @param a - the object to be set to
     */
    public void setGroup(Group a){
        root=a;
    }

    /**
     * Sets the root for the current scene.
     * Used for teleporting between "areas" of a level
     * @param a - the group to which the scene's root will be set
     */
    public void setRoot(Group a){
        this.getScene().setRoot(a);
    }

    /**
     * Changes image of PortalObject -- useful for visibly showing
     * state changes such as power ups, etc
     * @param imagefile - path to image file to replace ball's current image.
     */
    public void changeImage(String imagefile){
        this.getImage().setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(imagefile)));
    }
}
