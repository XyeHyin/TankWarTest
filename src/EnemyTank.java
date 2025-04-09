/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 10:59
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;
import java.util.Random;

public abstract class EnemyTank extends Tank {
    protected Random random;
    protected int moveCounter;
    protected int moveDelay;
    protected int shootingRate; // 射击频率，值越大越频繁（0-100）
    protected int scoreValue; // 击败该坦克获得的分数

    public EnemyTank(int x, int y, int health, int speed, int moveDelay, int shootingRate, int scoreValue) {
        super(x, y);
        this.health = health;
        this.speed = speed;
        this.moveDelay = moveDelay;
        this.shootingRate = shootingRate;
        this.scoreValue = scoreValue;
        this.random = new Random();
        this.moveCounter = 0;
    }

    @Override
    public void update() {
        moveCounter++;

        if (moveCounter >= moveDelay) {
            changeDirection();
            moveCounter = 0;
        }

        move();
    }

    public void changeDirection() {
        // 随机改变方向
        int randomDirection = random.nextInt(4);

        switch (randomDirection) {
            case 0:
                dx = 0;
                dy = -speed;
                direction = Direction.UP;
                break;
            case 1:
                dx = 0;
                dy = speed;
                direction = Direction.DOWN;
                break;
            case 2:
                dx = -speed;
                dy = 0;
                direction = Direction.LEFT;
                break;
            case 3:
                dx = speed;
                dy = 0;
                direction = Direction.RIGHT;
                break;
        }
    }

    public int getShootingRate() {
        return shootingRate;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    @Override
    protected boolean isPlayerBullet() {
        return false;
    }
}
