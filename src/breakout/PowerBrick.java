package breakout;

import java.util.List;

public class PowerBrick extends Brick{
    private double secondsElapsed;
    private int state;
    private boolean poweredUp;
    public PowerBrick(String imagefile, Ball a) {
        super(imagefile, a);
        this.setHealth(1);
        poweredUp = false;
    }
    @Override
    public int damage(){
        this.setHealth(this.getHealth()-1);
        return this.getHealth();
    }
    @Override
    public void collide(List<Brick> b) {
        if(this.damage() <= 0) {
            if(state == 0){
                this.getBall().setMode("fireball");
                poweredUp = true;
                secondsElapsed = 0;
            } else if (state == 1){
                this.getBall().setMode("wrecking ball");
                poweredUp = true;
                secondsElapsed = 0;
            } else if (state == 2){
                this.getBall().setMode("fireball");
                poweredUp = true;
                secondsElapsed = 0;
            } else if (state == 3){
                this.getBall().setMode("wrecking ball");
                poweredUp = true;
                secondsElapsed = 0;
            }
            this.getGroup().getChildren().remove(this.getImage());
            b.remove(this);
            this.getBall().setDestroyedBrick(true);
        }
    }
    public void update(double elapsedTime) {
        secondsElapsed+=elapsedTime;
        if(secondsElapsed > 2.0){
            if(poweredUp){
                poweredUp = false;
                this.getBall().setMode("normal");
            }
            secondsElapsed = 0;
            state++;
            state = state%4;
        }
        if(state == 0){
            this.changeImage("brick.png");
        }
        else if(state == 1){
            this.changeImage("brick2.png");
        }
        else if(state == 2){
            this.changeImage("brick3.png");
        }
        else if(state == 3){
            this.changeImage("brick4.png");
        }
    }
}
