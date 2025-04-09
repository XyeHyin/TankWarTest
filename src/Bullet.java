/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 11:00
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;

public class Bullet extends GameObject {
    private int speed = 5;
    private int dx = 0;
    private int dy = 0;
    private boolean isPlayerBullet;
    private int damage = 1;

    public Bullet(int x, int y, boolean isPlayerBullet) {
        super(x, y, 10, 10);
        this.isPlayerBullet = isPlayerBullet;
    }

    public void setUp() {
        dx = 0;
        dy = -speed;
    }

    public void setDown() {
        dx = 0;
        dy = speed;
    }

    public void setLeft() {
        dx = -speed;
        dy = 0;
    }

    public void setRight() {
        dx = speed;
        dy = 0;
    }

    public void move() {
        x += dx;
        y += dy;

        // 如果子弹超出游戏区域，则设置为不可见
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            setVisible(false);
        }
    }

    @Override
    public void draw(Graphics g) {
        Color bulletColor;
        if (isPlayerBullet) {
            bulletColor = Color.YELLOW;
        } else {
            // 敌人子弹颜色根据伤害变化
            if (damage > 1) {
                bulletColor = new Color(255, 100, 0); // 橙色 - 高伤害
            } else {
                bulletColor = Color.WHITE; // 白色 - 标准伤害
            }
        }

        g.setColor(bulletColor);
        g.fillOval(x, y, width, height);
    }

    public boolean isPlayerBullet() {
        return isPlayerBullet;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}