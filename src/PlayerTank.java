
/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 10:59
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerTank extends Tank {
    private boolean shieldActive = false;
    private int shieldTimer = 0;

    private boolean speedBoostActive = false;
    private int speedBoostTimer = 0;
    private int originalSpeed;

    private boolean scatterShotActive = false;
    private int scatterShotTimer = 0;

    public PlayerTank(int x, int y) {
        super(x, y);
        speed = 3;
        originalSpeed = speed;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -speed;
            direction = Direction.LEFT;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = speed;
            direction = Direction.RIGHT;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -speed;
            direction = Direction.UP;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = speed;
            direction = Direction.DOWN;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    @Override
    public void update() {
        super.update();

        // 更新护盾状态
        if (shieldActive) {
            shieldTimer--;
            if (shieldTimer <= 0) {
                shieldActive = false;
            }
        }

        // 更新加速状态
        if (speedBoostActive) {
            speedBoostTimer--;
            if (speedBoostTimer <= 0) {
                speedBoostActive = false;
                speed = originalSpeed;
            }
        }

        // 更新散弹状态
        if (scatterShotActive) {
            scatterShotTimer--;
            if (scatterShotTimer <= 0) {
                scatterShotActive = false;
            }
        }
    }

    // 新增解除护盾方法
    public void deactivateShield() {
        shieldActive = false;
        shieldTimer = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);

        // 绘制炮管
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(5));

        int centerX = x + width / 2;
        int centerY = y + height / 2;

        switch (direction) {
            case UP:
                g2d.drawLine(centerX, centerY, centerX, y - 10);
                break;
            case DOWN:
                g2d.drawLine(centerX, centerY, centerX, y + height + 10);
                break;
            case LEFT:
                g2d.drawLine(centerX, centerY, x - 10, centerY);
                break;
            case RIGHT:
                g2d.drawLine(centerX, centerY, x + width + 10, centerY);
                break;
        }

        // 如果护盾激活，绘制护盾效果
        if (shieldActive) {
            g2d.setColor(new Color(0, 255, 255, 128)); // 半透明青色
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x - 5, y - 5, width + 10, height + 10);
        }

        // 如果加速激活，绘制加速效果
        if (speedBoostActive) {
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(2));

            // 根据移动方向绘制速度线条
            if (dx != 0 || dy != 0) {
                int trailX = x;
                int trailY = y;
                int trailWidth = width;
                int trailHeight = height;

                if (dx < 0) {
                    trailX = x + width;
                    trailWidth = 10;
                } else if (dx > 0) {
                    trailX = x - 10;
                    trailWidth = 10;
                } else if (dy < 0) {
                    trailY = y + height;
                    trailHeight = 10;
                } else if (dy > 0) {
                    trailY = y - 10;
                    trailHeight = 10;
                }

                g2d.drawRect(trailX, trailY, trailWidth, trailHeight);
            }
        }
    }

    @Override
    public List<Bullet> fire() {
        List<Bullet> bullets = new ArrayList<>();

        if (scatterShotActive) {
            // 散弹模式 - 同时发射多个子弹
            int bulletX = x + width / 2 - 5;
            int bulletY = y + height / 2 - 5;

            // 主方向子弹
            Bullet mainBullet = new Bullet(bulletX, bulletY, true);
            setDirection(mainBullet, direction);
            bullets.add(mainBullet);

            // 左侧子弹
            Bullet leftBullet = new Bullet(bulletX, bulletY, true);
            setDirection(leftBullet, getLeftDirection(direction));
            bullets.add(leftBullet);

            // 右侧子弹
            Bullet rightBullet = new Bullet(bulletX, bulletY, true);
            setDirection(rightBullet, getRightDirection(direction));
            bullets.add(rightBullet);
        } else {
            // 单发子弹
            bullets.add(super.fire().get(0));
        }

        return bullets;
    }

    private void setDirection(Bullet bullet, Direction dir) {
        switch (dir) {
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
    }

    private Direction getLeftDirection(Direction dir) {
        switch (dir) {
            case UP:
                return Direction.LEFT;
            case LEFT:
                return Direction.DOWN;
            case DOWN:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.UP;
            default:
                return dir;
        }
    }

    private Direction getRightDirection(Direction dir) {
        switch (dir) {
            case UP:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.DOWN;
            case DOWN:
                return Direction.LEFT;
            case LEFT:
                return Direction.UP;
            default:
                return dir;
        }
    }

    public void activateShield(int duration) {
        shieldActive = true;
        shieldTimer = duration;
    }

    public boolean hasShield() {
        return shieldActive;
    }

    public void activateSpeedBoost(int duration) {
        speedBoostActive = true;
        speedBoostTimer = duration;
        speed = originalSpeed * 2;
    }

    public boolean hasSpeedBoost() {
        return speedBoostActive;
    }

    public void activateScatterShot(int duration) {
        scatterShotActive = true;
        scatterShotTimer = duration;
    }

    public boolean hasScatterShot() {
        return scatterShotActive;
    }

    @Override
    protected boolean isPlayerBullet() {
        return true;
    }
}
