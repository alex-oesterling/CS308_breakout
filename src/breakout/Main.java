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

import java.util.ArrayList;
import java.util.List;

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
    public static final int NUM_BRICKS = 5;

    private Scene myScene;
    private Ball ball;
    private Bumper bumper;
    private List<Brick> bricks;
    private List<Brick> bricks2;
    private List<Group> roots;

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
        bumper.update(elapsedTime);
        for(Brick b: bricks){
            b.update(elapsedTime);
        }
        //check collisions
        if(ball.getYVel()>=0 && ball.getImage().getBoundsInLocal().intersects(bumper.getImage().getBoundsInLocal())){
            double degree = Math.abs(ball.getImage().getBoundsInLocal().getCenterX() - bumper.getImage().getBoundsInLocal().getCenterX())/(bumper.getImage().getBoundsInLocal().getWidth()/2);
            degree = Math.toRadians(90*degree);
            ball.setYVel(Math.abs(Math.cos(degree)*Ball.BALL_VELOCITY)*-1);
            if(ball.getImage().getBoundsInLocal().getCenterX() < bumper.getImage().getBoundsInLocal().getCenterX()){
                ball.setXVel(Math.abs(Math.sin(degree)*Ball.BALL_VELOCITY)*-1);
            }
            else if(ball.getImage().getBoundsInLocal().getCenterX() > bumper.getImage().getBoundsInLocal().getCenterX()) {
                ball.setXVel(Math.abs(Math.sin(degree) * Ball.BALL_VELOCITY));
            }
        }
        if(ball.getY() >= ball.getScene().getHeight()){
            ball.setY(200);
        }

    }

    private Scene setupGame(int width, int height, Paint background) {
        roots = new ArrayList<Group>();
        roots.add(new Group());
        roots.add(1, new Group());
        bricks = new ArrayList<Brick>();
        bricks2 = new ArrayList<Brick>();
        ball = new Ball(BOUNCER_IMAGE, roots.get(0), bricks);
        bumper = new Bumper(BOUNCER_IMAGE, roots.get(0));

        roots.get(0).getChildren().add(ball.getImage());
        roots.get(0).getChildren().add(bumper.getImage());
        for(int i = 0; i < NUM_BRICKS; i++){
            bricks.add(new DuraBrick("brick.png", roots.get(0)));
            roots.get(0).getChildren().add(bricks.get(i).getImage());
        }
        bricks.add(0, new PortalBrick("brick.png", roots.get(0), ball, roots.get(1), bricks2));
        bricks2.add(0, new PortalBrick("brick.png", roots.get(1), ball,  roots.get(0), bricks));

        roots.get(0).getChildren().add(bricks.get(0).getImage());
        roots.get(1).getChildren().add(bricks2.get(0).getImage());

        Scene scene = new Scene(roots.get(0), width, height, background);

        ball.setScene(scene);
        bumper.setScene(scene);

        for(int i = 0; i < bricks.size(); i++){
            bricks.get(i).setScene(scene);
            bricks.get(i).setX(i*50%400);
            bricks.get(i).setY((i*50/400)*50);
        }
        bricks2.get(0).setScene(scene);
        //temp
        bricks.get(0).setX(150);
        bricks.get(0).setY(200);
        bricks2.get(0).setX(150);
        bricks2.get(0).setY(200);

        bumper.setX(bumper.getScene().getWidth()/2 - bumper.getImage().getBoundsInLocal().getWidth()/2);
        bumper.setY(bumper.getScene().getHeight()-bumper.getImage().getBoundsInLocal().getHeight());
        ball.setX(bumper.getImage().getX());
        ball.setY(bumper.getImage().getY()-100);
        return scene;
    }



    public static void main (String[] args) {
        launch(args);
    }
}
