package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * @author me
 */
public abstract class PortalObject {
    private int x, y;
    private Group image;
    public PortalObject(){
    }
    public int getX(){return x;}
    public int getY(){return y;}

    public void setX(int a){x = a;}
    public void setY(int b){y = b;}

    public abstract void update(Scene scene, int elapsedTime);

    public abstract void getImage();
}
