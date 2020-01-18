package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    public static final int NUM_BRICKS = 20;

    private Scene myScene;
    private Ball ball;
    private Bumper bumper;
    private List<Brick> bricks; // make into list of lists?
    private List<Brick> bricks2;
    private List<Group> roots;
    private Group essentials;
    private Stage myStage;
    private boolean gameStart;
    private boolean gameOver;
    private int lives;
    private int score;

    @Override
    public void start(Stage stage) throws Exception {
        myScene = mainMenu();
        myStage = stage;
        gameStart = false;
        gameOver = false;
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
        if(gameStart) {
            ball.update(elapsedTime);
            bumper.update(elapsedTime);
            for (Brick b : bricks) {
                b.update(elapsedTime);
            }
            //check collisions
            if (ball.getYVel() >= 0 && ball.getImage().getBoundsInLocal().intersects(bumper.getImage().getBoundsInLocal())) {
                double degree = Math.abs(ball.getImage().getBoundsInLocal().getCenterX() - bumper.getImage().getBoundsInLocal().getCenterX()) / (bumper.getImage().getBoundsInLocal().getWidth() / 2);
                degree = Math.toRadians(90 * degree);
                ball.setYVel(Math.abs(Math.cos(degree) * Ball.BALL_VELOCITY) * -1);
                if (ball.getImage().getBoundsInLocal().getCenterX() < bumper.getImage().getBoundsInLocal().getCenterX()) {
                    ball.setXVel(Math.abs(Math.sin(degree) * Ball.BALL_VELOCITY) * -1);
                } else if (ball.getImage().getBoundsInLocal().getCenterX() > bumper.getImage().getBoundsInLocal().getCenterX()) {
                    ball.setXVel(Math.abs(Math.sin(degree) * Ball.BALL_VELOCITY));
                }
            }
        }
    }

    private Scene setupGame() {
        roots = new ArrayList<Group>();
        roots.add(new Group());
        roots.add(1, new Group());
        bricks = new ArrayList<Brick>();
        bricks2 = new ArrayList<Brick>();
        ball = new Ball(BOUNCER_IMAGE, roots.get(0), bricks);
        ball.setMode("fireball");
        bumper = new Bumper(BOUNCER_IMAGE, roots.get(0));
        essentials = new Group();
        Text score = new Text();

        score.setText("" + ball.getScore());
        score.setFont(Font.font ("Verdana", 20));
        score.setFill(Color.RED);



        essentials.getChildren().add(ball.getImage());
        essentials.getChildren().add(bumper.getImage());
        essentials.getChildren().add(score);
        roots.get(0).getChildren().add(essentials);


        /*
        roots.get(0).getChildren().add(ball.getImage());
        roots.get(0).getChildren().add(bumper.getImage());
        roots.get(0).getChildren().add(score);

         */


        for(int i = 0; i < NUM_BRICKS; i++){
            bricks.add(new DuraBrick("brick.png", roots.get(0), ball));
            roots.get(0).getChildren().add(bricks.get(i).getImage());
        }
        bricks.add(0, new PortalBrick("portal1.png", roots.get(0), ball, roots.get(1), bricks2));
        bricks2.add(0, new PortalBrick("portal2.png", roots.get(1), ball,  roots.get(0), bricks));

        roots.get(0).getChildren().add(bricks.get(0).getImage());
        roots.get(1).getChildren().add(bricks2.get(0).getImage());

        Scene scene = new Scene(roots.get(0), SIZE, SIZE, BACKGROUND);
        scene.setOnKeyPressed(e -> {
            ball.ballKeyInput(e.getCode());
            bumper.bumperKeyInput(e.getCode());
        });
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
        bricks2.get(0).setX(300);
        bricks2.get(0).setY(200);
        score.setX(myScene.getWidth()-score.getLayoutBounds().getWidth());
        score.setY(myScene.getHeight()-score.getLayoutBounds().getHeight());

        bumper.setX(bumper.getScene().getWidth()/2 - bumper.getImage().getBoundsInLocal().getWidth()/2);
        bumper.setY(bumper.getScene().getHeight()-bumper.getImage().getBoundsInLocal().getHeight());
        ball.setX(bumper.getImage().getX());
        ball.setY(bumper.getImage().getY()-100);
        return scene;
    }

    public Scene mainMenu(){
        Button start = new Button("Start Game");
        Text welcome = new Text("Welcome to PortalBreaker!\nUse the arrow keys (or wasd) to move. Press space to start.");
        start.setOnAction(e -> {
            myScene = setupGame();
            myStage.setScene(myScene);
            gameStart = true;
        });
        StackPane pane = new StackPane();
        pane.setMinSize(200, 300);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getChildren().add(start);
        pane.setAlignment(start, Pos.BOTTOM_CENTER);
        pane.getChildren().add(welcome);
        pane.setAlignment(welcome, Pos.TOP_CENTER);
        Scene scene = new Scene(pane, SIZE, SIZE, BACKGROUND);
        return scene;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
