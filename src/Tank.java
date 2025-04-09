import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Tank extends GameObject {
    protected int dx;
    protected int dy;
    protected int speed;
    protected int health;
    protected Direction direction;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Tank(int x, int y) {
        super(x, y, 30, 30);
        health = 100;
        speed = 2;
        direction = Direction.UP;
    }

    public void move() {
        x += dx;
        y += dy;

        // 边界检查
        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }

        if (x > 800 - width) {
            x = 800 - width;
        }

        if (y > 600 - height) {
            y = 600 - height;
        }
    }

    public void hit() {
        hit(1); // 默认伤害为1
    }

    public void hit(int damage) {
        takeDamage(damage * 20); // 每点伤害对应20点生命值
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            setVisible(false);
        }
    }

    public void heal(int amount) {
        health = Math.min(health + amount, 100);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public List<Bullet> fire() {
        List<Bullet> bullets = new ArrayList<>();

        int bulletX = x + width / 2 - 5;
        int bulletY = y + height / 2 - 5;

        Bullet bullet = new Bullet(bulletX, bulletY, isPlayerBullet());

        switch (direction) {
            case UP:
                bullet.setUp();
                break;
            case DOWN:
                bullet.setDown();
                break;
            case LEFT:
                bullet.setLeft();
                break;
            case RIGHT:
                bullet.setRight();
                break;
        }

        bullets.add(bullet);
        return bullets;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void update() {
        move();
    }

    protected abstract boolean isPlayerBullet();
}