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

        //Collision with bumper
        if(myBouncer.getBoundsInLocal().intersects(myBumper.getBoundsInLocal())){
            yVel*=-1;
        }
    }

    private Scene setupGame(int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();

        // make bouncer
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new ImageView(image);
        myBouncer.setPreserveRatio(true);
        //myBouncer.setFitWidth(50);
        myBouncer.setFitHeight(50);
        // x and y represent the top left corner, so center it in window
        myBouncer.setX(width / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(height / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
        xVel = 50;
        yVel = 50;

        // make bumper
        Image bumps = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBumper = new ImageView(bumps);
        myBumper.setFitWidth(100);
        myBumper.setFitHeight(20);
        myBumper.setX(width/2 - myBumper.getBoundsInLocal().getWidth()/2);
        myBumper.setY(height-myBumper.getBoundsInLocal().getHeight());
        /*
        myMover = new Rectangle(width / 2 - MOVER_SIZE / 2, height / 2 - 100, MOVER_SIZE, MOVER_SIZE);
        myMover.setFill(MOVER_COLOR);
        myGrower = new Rectangle(width / 2 - GROWER_SIZE / 2, height / 2 + 50, GROWER_SIZE, GROWER_SIZE);
        myGrower.setFill(GROWER_COLOR);
         */
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBouncer);
        root.getChildren().add(myBumper);

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyInput(event.getCode());
            }
        });

        scene.setOnKeyPressed(e -> {
            System.out.println(e);
        });

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        //scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void handleKeyInput(KeyCode code) {
        //avoid "jittering" by preventing the move in the first place
        if(myBumper.getX() < myScene.getWidth()-myBumper.getBoundsInLocal().getWidth() && (code == KeyCode.RIGHT || code == KeyCode.D)){
            myBumper.setX(myBumper.getX() + BUMPER_SPEED);
        } else if(myBumper.getX() >0 && (code == KeyCode.LEFT || code == KeyCode.A)) {
            myBumper.setX(myBumper.getX() - BUMPER_SPEED);
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
