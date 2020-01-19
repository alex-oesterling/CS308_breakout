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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public static final int LEVEL_NUM = 3;

    private Scene myScene;
    private Ball ball;
    private Bumper bumper;
    private List<List> brickList;
    private List<Group> roots;
    private Group essentials;
    private Stage myStage;
    private boolean gameStart;
    private boolean gameOver;
    private Text lives;
    private Text score;
    private Scanner scanner;
    private int level;
    private String levelString;

    @Override
    public void start(Stage stage) throws Exception {
        myStage = stage;
        initialize(stage);
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

    /**
     * update this. make it for setting mainmenu? or for resetting ball?
     * @param stage
     */
    private void initialize(Stage stage) {
        level = 0;
        levelString = "./resources/Level" + level + ".txt";
        gameStart = false;
        gameOver = false;

        ball = new Ball(BOUNCER_IMAGE);
        bumper = new Bumper(BOUNCER_IMAGE);
        essentials = new Group();
        score = new Text();
        lives = new Text();
        score.setText("" + ball.getScore());
        score.setFont(Font.font ("Verdana", 20));
        score.setFill(Color.RED);
        lives.setText("" + ball.getLives());
        lives.setFont(Font.font ("Verdana", 20));
        lives.setFill(Color.RED);
        essentials.getChildren().add(ball.getImage());
        essentials.getChildren().add(bumper.getImage());
        essentials.getChildren().add(score);
        essentials.getChildren().add(lives);
        myScene = mainMenu();
    }

    private void update(double elapsedTime) {
        if(gameStart && !gameOver) {
            int bricksLeft = 0;
            ball.update(elapsedTime);
            bumper.update(elapsedTime);
            for (List list : brickList) {
                for(Object b : list){
                    if(b instanceof Brick){
                        ((Brick)b).update(elapsedTime);
                        if(!(b instanceof PortalBrick)){
                            bricksLeft++;
                        }
                    }
                }
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
            if(ball.getLives() <= 0){
                gameOver = true;
                myScene = endGame(false);
                myStage.setScene(myScene);
            }
            if(bricksLeft == 0){ //fix this shit
                if(level == LEVEL_NUM) {
                    gameOver = true;
                    myScene = endGame(true);
                    myStage.setScene(myScene);
                } else {
                    setLevel(level+1);
                }
            }
        }
    }



    private Scene setupGame(String filename) {
        roots = new ArrayList<Group>();
        roots.add(new Group());
        brickList = new ArrayList<List>();
        brickList.add(new ArrayList<Brick>());
        /*
        bricks = new ArrayList<Brick>();
        bricks2 = new ArrayList<Brick>();
         */
        ball.setLaunched(false);
        ball.setGroup(roots.get(0));
        bumper.setGroup(roots.get(0));
        ball.updateBricks(brickList.get(0));
        roots.get(0).getChildren().add(essentials);

        loadLevel(filename);
        /*
        for(int i = 0; i < NUM_BRICKS; i++){
            bricks.add(new DuraBrick("brick.png", ball));
            roots.get(0).getChildren().add(bricks.get(i).getImage());
        }
        bricks.add(0, new PortalBrick("portal1.png", roots.get(0), ball, roots.get(1), bricks2));
        bricks2.add(0, new PortalBrick("portal2.png", roots.get(1), ball,  roots.get(0), bricks));

        roots.get(0).getChildren().add(bricks.get(0).getImage());
        roots.get(1).getChildren().add(bricks2.get(0).getImage());

         */

        Scene scene = new Scene(roots.get(0), SIZE, SIZE, BACKGROUND);
        scene.setOnKeyPressed(e -> {
            ball.ballKeyInput(e.getCode());
            bumper.bumperKeyInput(e.getCode());
            handleKeyInput(e.getCode());
        });
        ball.setScene(scene);
        bumper.setScene(scene);
        for(int i = 0; i < brickList.size(); i++){
            for(Object b : brickList.get(i)) {
                if(b instanceof Brick){
                    ((Brick) b).setScene(scene);
                }
                /*
                bricks.get(i).setGroup(roots.get(0));
                bricks.get(i).setScene(scene);
                bricks.get(i).setX(i * 50 % 400);
                bricks.get(i).setY((i * 50 / 400) * 50);

                 */
            }
        }
        /*
        bricks2.get(0).setScene(scene);
        //temp
        bricks.get(0).setX(150);
        bricks.get(0).setY(200);
        bricks2.get(0).setX(300);
        bricks2.get(0).setY(200);

         */
        score.setX(myScene.getWidth()-score.getLayoutBounds().getWidth());
        score.setY(myScene.getHeight()-score.getLayoutBounds().getHeight());
        lives.setX(0);
        lives.setY(myScene.getHeight()-lives.getLayoutBounds().getHeight());

        bumper.setX(bumper.getScene().getWidth()/2 - bumper.getImage().getBoundsInLocal().getWidth()/2);
        bumper.setY(bumper.getScene().getHeight()-bumper.getImage().getBoundsInLocal().getHeight());
        ball.setX(bumper.getImage().getX());
        ball.setY(bumper.getImage().getY()-100);
        return scene;
    }

    private void loadLevel(String filename) {
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("no file found!");
        }
        int current = 0;
        int entry = -1;
        int exit = -1;
        int rownum = 0;
        double entryXPos = 0.0;
        double entryYPos = 0.0;
        while(scanner.hasNext()){
            String row = scanner.nextLine();
            for(int i = 0; i < row.length(); i++) {
                Brick temp = null;
                char c = row.charAt(i);
                if(c == '-'){
                    continue;
                } else if(c == '0'){
                    temp = new BasicBrick("brick.png", ball);
                } else if (c == '1'){
                    temp = new DuraBrick("brick.png", ball);
                } else if (entry == -1 && c == '2'){
                    entry = current;
                    entryXPos = 50*i;
                    entryYPos = 50*rownum;
                } else if ((entry != -1 && entry != current) && c == '2'){
                    exit = current;
                    PortalBrick entrancePortal = new PortalBrick("portal1.png", roots.get(entry), ball, roots.get(exit), brickList.get(exit));
                    temp = new PortalBrick("portal2.png", roots.get(exit), ball, roots.get(entry), brickList.get(entry));
                    brickList.get(entry).add(0, entrancePortal);
                    brickList.get(exit).add(0, temp);
                    roots.get(entry).getChildren().add(entrancePortal.getImage());
                    entrancePortal.setGroup(roots.get(entry));
                    entrancePortal.setX(entryXPos);
                    entrancePortal.setY(entryYPos);
                } else if (c == '/'){
                    current++;
                    brickList.add(new ArrayList<Brick>());
                    roots.add(new Group());
                    rownum = 0;
                    break;
                }
                if(temp != null) {
                    brickList.get(current).add(temp);
                    roots.get(current).getChildren().add(temp.getImage());
                    temp.setGroup(roots.get(current));
                    temp.setX(50 * i);
                    temp.setY(50 * rownum);
                }
            }
            rownum++;
        }
    }

    private void handleKeyInput(KeyCode code) {
        if(code == KeyCode.DIGIT1){
            setLevel(1);
        }
    }

    private void setLevel(int l) {
        if(l == 0){
            myScene = mainMenu();
            myStage.setScene(myScene);
            gameStart = false;
            gameOver = false;
        } else {
            level = l;
            levelString = "./resources/Level" + level + ".txt";
            myScene = setupGame(levelString);
            myStage.setScene(myScene);
            gameStart = true;
            gameOver = false;
        }
    }

    public Scene mainMenu(){
        Button start = new Button("Start Game");
        Text welcome = new Text("Welcome to PortalBreaker!\nUse the arrow keys (or wasd) to move. Press space to start.");
        start.setOnAction(e -> {
            setLevel(1);
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

    private Scene endGame(boolean win){
        Button restart = new Button("Restart");
        Button menu = new Button("Main Menu");
        Text message = new Text();
        if(win){
            message.setText("Congratulations! You won!");
        } else{
            message.setText("Sorry, try again next time!");
        }
        restart.setOnAction(e -> {
            setLevel(1);
        });

        menu.setOnAction(e ->{
            setLevel(0);
        });
        StackPane pane2 = new StackPane();
        pane2.setMinSize(200, 300);
        pane2.setPadding(new Insets(10, 10, 10, 10));
        pane2.getChildren().add(restart);
        pane2.setAlignment(restart, Pos.BOTTOM_CENTER);
        pane2.getChildren().add(menu);
        pane2.setAlignment(menu, Pos.TOP_CENTER);
        pane2.getChildren().add(message);
        pane2.setAlignment(message, Pos.CENTER);

        Scene scene = new Scene(pane2, SIZE, SIZE, BACKGROUND);
        return scene;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
