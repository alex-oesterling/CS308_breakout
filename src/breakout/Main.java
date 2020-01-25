package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class, runs application, sets up stage, general scene instantiation, and object instantiation.
 * No assumptions made
 * It depends on the JavaFX library
 * @author Alex Oesterling, axo
 * @version 1/19/20
 */
public class Main extends Application {
    public static final String TITLE = "PortalBreaker";
    public static final int SIZE = 400;
    public static final Paint BACKGROUND = Color.AZURE; //change to image?
    public static final int FRAMES_PER_SECOND = 120;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String BOUNCER_IMAGE = "ball.png";
    public static final String BASIC_BRICK_IMAGE = "basic_brick.png";
    public static final String DURA_BRICK_IMAGE = "dura_brick.png";
    public static final String BUMPER_IMAGE = "paddle.gif";
    public static final String HEALTH_BRICK_IMAGE = "health.png";
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
    private Text levelText;
    private Scanner scanner;
    private int level;
    private String levelString;

    /**
     * Start method. Runs game loop after setting up stage and scene data.
     * @param stage the window in which the game runs
     * @throws Exception when game does not work
     */
    @Override
    public void start(Stage stage) throws Exception {
        myStage = stage;
        initialize(stage);
        setLevel(0);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> update(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     * Initializes necessary objects such as the ball, bumper, score, and lives which carry on throughout the game,
     * as well as creating a subgroup for which these variables will stay in even as they transfer between stages and root groups.
     * @param stage the stage in which the game takes place
     */
    private void initialize(Stage stage) {
        ball = new Ball(BOUNCER_IMAGE);
        bumper = new Bumper(BUMPER_IMAGE);
        essentials = new Group();
        score = new Text();
        lives = new Text();
        levelText = new Text();
        levelText.setText(""+level);
        levelText.setFont(Font.font ("Verdana", 16));
        levelText.setFill(Color.RED);
        score.setFont(Font.font ("Verdana", 20));
        score.setFill(Color.RED);
        lives.setFont(Font.font ("Verdana", 20));
        lives.setFill(Color.RED);
        essentials.getChildren().add(ball.getImage());
        essentials.getChildren().add(bumper.getImage());
        essentials.getChildren().add(score);
        essentials.getChildren().add(lives);
        essentials.getChildren().add(levelText);
        ball.setGroup(essentials);
        bumper.setGroup(essentials);
    }

    /**
     * The method that is constantly called in the game loop. Updates object positions, checks collisions,
     * causes effects such as power ups or teleporting. This is where the magic happens.
     * @param elapsedTime The time between calls of update (helps smooth motion)
     */
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
            levelText.setText("Level " + level);
            levelText.setX(bumper.getImage().getBoundsInLocal().getCenterX()-levelText.getLayoutBounds().getWidth()/2);
            if(ball.getLives() <= 0){
                gameOver = true;
                myScene = endGame(false);
                myStage.setScene(myScene);
            }
            if(bricksLeft == 0){
                System.out.println(level);
                if(level > LEVEL_NUM) {
                    gameOver = true;
                    myScene = endGame(true);
                    myStage.setScene(myScene);
                } else {
                    setLevel(level+1);
                }
            }
        }
    }


    /**
     * Sets up each individual level by loading a .txt file in and creating all the objects, groups, and other
     * data structures for object management. Resets ball, bricks, and bumper and ties them to the new scene.
     * @param filename the filename of the .txt file to construct the bricks from
     * @return the new scene which the stage should display.
     */
    private Scene setScene(String filename) {
        roots = new ArrayList<Group>();
        roots.add(new Group());
        brickList = new ArrayList<List>();
        brickList.add(new ArrayList<Brick>());
        ball.setLaunched(false);
        ball.updateBricks(brickList.get(0));
        roots.get(0).getChildren().add(essentials);
        loadLevel(filename);
        Scene scene = new Scene(roots.get(0), SIZE, SIZE, BACKGROUND);
        scene.setOnKeyPressed(e -> {
            ball.ballKeyInput(e.getCode());
            bumper.bumperKeyInput(e.getCode());
            handleKeyInput(e.getCode());
        });
        scene.setOnMouseMoved(e ->{
            ball.ballMouseTracker(e);
        });
        ball.setScene(scene);
        bumper.setScene(scene);
        for(int i = 0; i < brickList.size(); i++){
            for(Object b : brickList.get(i)) {
                if(b instanceof Brick){
                    ((Brick) b).setScene(scene);
                }
            }
        }
        score.setY(myScene.getHeight()-score.getLayoutBounds().getHeight());
        lives.setY(myScene.getHeight()-lives.getLayoutBounds().getHeight());
        levelText.setY(myScene.getHeight()-4);
        bumper.setX(bumper.getScene().getWidth()/2 - bumper.getImage().getBoundsInLocal().getWidth()/2);
        return scene;
    }

    /**
     * Runs scanner to load .txt file and creates brick objects to put in the brick lists as well
     * as positioning them in the scene
     * @param filename the filename of the .txt file to construct the bricks from
     */
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
                    temp = new BasicBrick(BASIC_BRICK_IMAGE, ball);
                } else if (c == '1'){
                    temp = new DuraBrick(DURA_BRICK_IMAGE, ball);
                } else if (entry == -1 && c == '2'){
                    entry = current;
                    entryXPos = 50*i;
                    entryYPos = 50*rownum;
                } else if ((entry != -1 && entry != current) && c == '2') {
                    exit = current;
                    PortalBrick entrancePortal = new PortalBrick("portal1.png", roots.get(entry), ball, roots.get(exit), brickList.get(exit));
                    temp = new PortalBrick("portal2.png", roots.get(exit), ball, roots.get(entry), brickList.get(entry));
                    brickList.get(entry).add(0, entrancePortal);
                    brickList.get(exit).add(0, temp);
                    roots.get(entry).getChildren().add(entrancePortal.getImage());
                    entrancePortal.setGroup(roots.get(entry));
                    entrancePortal.setX(entryXPos);
                    entrancePortal.setY(entryYPos);
                } else if (c == '3') {
                    temp = new PowerBrick(BASIC_BRICK_IMAGE, ball);
                } else if (c == '4'){
                    temp = new HealthBrick(HEALTH_BRICK_IMAGE, ball);
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
        if(code == KeyCode.DIGIT1 ||
                code == KeyCode.DIGIT2 ||
                code == KeyCode.DIGIT3 ||
                code == KeyCode.DIGIT4 ||
                code == KeyCode.DIGIT5 ||
                code == KeyCode.DIGIT6 ||
                code == KeyCode.DIGIT7 ||
                code == KeyCode.DIGIT8 ||
                code == KeyCode.DIGIT9 ||
                code == KeyCode.DIGIT0){
            setLevel(code.getCode()-48);
        }
    }

    /**
     * Sets myScene value to desired level number and sets the stage's scene to the desired level
     * @param l - the level of the game desired. if 0, returns to the main menu
     */
    private void setLevel(int l) throws IllegalArgumentException{
        if(l > LEVEL_NUM){
            throw new IllegalArgumentException("Level higher than the number of levels:" + LEVEL_NUM);
        }
        else if(l == 0){
            myScene = mainMenu();
            myStage.setScene(myScene);
            gameStart = false;
            gameOver = false;
        } else {
            level = l;
            levelString = "./resources/Level" + level + ".txt";
            myScene = setScene(levelString);
            myStage.setScene(myScene);
            gameStart = true;
            gameOver = false;
        }
    }

    /**
     * Creates the main menu scene, adding buttons with triggers to start the game
     * @return the main menu scene to be displayed by the stage
     */
    public Scene mainMenu(){
        Button start = new Button("Start Game");
        Text welcome = new Text("Welcome to PortalBreaker!\nUse the arrow keys (or wasd) to move. Press space to start." +
                "\n\nFor testers: \'s\' toggles \"seeker\" mode,\nnumpad selects a level (0) for main,\n\'l\' adds lives and \'r\' resets ball" +
                "\n\nHave fun playing with portals!!!");
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
        scene.setOnKeyPressed(e -> {
            handleKeyInput(e.getCode());
        });
        return scene;
    }

    /**
     * Constructs the final scene to be displayed when the game ends,
     * adding buttons to restart the game or return to the main menu.
     * @param win - a boolean, which changes the text of the final screen depending on if the player won or lost
     * @return the end game scene to be displayed by the stage
     */
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
        scene.setOnKeyPressed(e -> {
            handleKeyInput(e.getCode());
        });
        return scene;
    }

    /**
     * the main method. runs the game.
     * @param args
     */
    public static void main (String[] args) {
        launch(args);
    }
}
