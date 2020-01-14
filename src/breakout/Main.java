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

    private Scene myScene;
    private Ball ball;
    private Bumper bumper;
    private Brick brick;
    private Group root;

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
        ball.update(elapsedTime);
        //Collision with bumper
        bumper.update(elapsedTime);
        brick.update(elapsedTime);

        if(ball.getImage().getBoundsInLocal().intersects(bumper.getImage().getBoundsInLocal())){
            ball.setYVel(ball.getYVel()*-1);
        }
        if(ball.getY() >= ball.getScene().getHeight()){
            ball.setY(200);
            //root.getChildren().remove(ball.getImage());
        }

    }

    private Scene setupGame(int width, int height, Paint background) {
        root = new Group();

        ball = new Ball(BOUNCER_IMAGE, root);
        bumper = new Bumper(BOUNCER_IMAGE, root);
        brick = new Brick("brick.png", root);

        root.getChildren().add(ball.getImage());
        root.getChildren().add(bumper.getImage());
        root.getChildren().add(brick.getImage());

        Scene scene = new Scene(root, width, height, background);

        ball.setScene(scene);
        bumper.setScene(scene);
        brick.setScene(scene);

        brick.setX(0);
        brick.setY(0);
        bumper.setX(bumper.getScene().getWidth()/2 - bumper.getImage().getBoundsInLocal().getWidth()/2);
        bumper.setY(bumper.getScene().getHeight()-bumper.getImage().getBoundsInLocal().getHeight());
        return scene;
    }



    public static void main (String[] args) {
        launch(args);
    }
}
