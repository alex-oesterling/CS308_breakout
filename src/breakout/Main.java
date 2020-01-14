package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * Start of the program.
     */
    public static final String TITLE = "PortalBreaker";
    public static final int SIZE = 400;
    public static final Paint BACKGROUND = Color.AZURE; //change to image?
    public static final int FRAMES_PER_SECOND = 120;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String BOUNCER_IMAGE = "ball.png";
    private static final int BUMPER_SPEED = 10;

    public int xVel, yVel;
    private Scene myScene;
    private ImageView myBouncer;
    private Ball ball;
    private Bumper bumper;
    private ImageView myBumper;

    @Override
    public void start(Stage stage) throws Exception {
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        //game loop
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> update(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    private void update(double elapsedTime) {
        //BOUNCER MOTION
        /*
        myBouncer.setX(myBouncer.getX() + xVel * elapsedTime);
        myBouncer.setY(myBouncer.getY() + yVel * elapsedTime);

        if(myBouncer.getX() >= myScene.getWidth()-myBouncer.getBoundsInLocal().getWidth()){
            xVel *=-1;
        }
        if(myBouncer.getX() <= 0){
            xVel *=-1;
        }
        if(myBouncer.getY() >= myScene.getHeight()-myBouncer.getBoundsInLocal().getHeight()){
            yVel *=-1;
        }
        if(myBouncer.getY() <= 0){
            yVel *=-1;
        }

         */
        ball.update(elapsedTime);
        //Collision with bumper
        bumper.update(elapsedTime);


    }

    private Scene setupGame(int width, int height, Paint background) {
        Group root = new Group();
        Group rootemp = new Group();
        Scene temp = new Scene(rootemp, width, height, background);
        ball = new Ball(temp, BOUNCER_IMAGE);
        bumper = new Bumper(temp, BOUNCER_IMAGE);
        root.getChildren().add(ball.getImage());
        root.getChildren().add(bumper.getImage());
        Scene scene = new Scene(root, width, height, background);
        return scene;
    }



    public static void main (String[] args) {
        launch(args);
    }
}
