/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 10:57
 * @packageName:IntelliJ IDEA
 * @ClassNam:
 * @Description: TODO
 * @Version:1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private Timer timer;
    private final int DELAY = 10;

    private PlayerTank playerTank;
    private List<EnemyTank> enemies;
    private List<Bullet> bullets;
    private List<Obstacle> obstacles;
    private List<PowerUp> powerUps;
    private List<Explosion> explosions;

    private boolean inGame = true;
    private int score = 0;
    private int level = 1;

    private TankGame tankGame;
    private Random random;

    // 坦克碰撞伤害和冷却时间
    private final int COLLISION_DAMAGE = 10;
    private int collisionCooldown = 0;
    private final int COLLISION_COOLDOWN_TIME = 50; // 0.5秒

    private int powerUpSpawnCounter = 0;
    private final int POWER_UP_SPAWN_DELAY = 600; // 10秒

    public GamePanel(TankGame tankGame) {
        this.tankGame = tankGame;
        this.random = new Random();
        initPanel();
    }

    private void initPanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new TAdapter());

        // 设置游戏循环
        timer = new Timer(DELAY, this);
    }

    public void initGame() {
        initGame(1); // 默认从第1关开始
    }

    public void initGame(int startLevel) {
        // 重置游戏状态
        inGame = true;
        score = 0;
        level = startLevel;
        collisionCooldown = 0;

        // 创建玩家坦克
        playerTank = new PlayerTank(400, 500);

        // 初始化敌人坦克列表
        enemies = new ArrayList<>();
        spawnEnemiesForLevel(level);

        // 初始化子弹列表
        bullets = new ArrayList<>();

        // 初始化道具列表
        powerUps = new ArrayList<>();

        // 初始化爆炸效果列表
        explosions = new ArrayList<>();

        // 初始化障碍物
        obstacles = new ArrayList<>();
        createObstacles();

        timer.start();
    }

    private void spawnEnemiesForLevel(int level) {
        int baseEnemies = 3 + level;

        for (int i = 0; i < baseEnemies; i++) {
            int enemyType = random.nextInt(3); // 0: 普通, 1: 快速, 2: 重型
            int x = 50 + random.nextInt(700);
            int y = 50 + random.nextInt(150);

            switch (enemyType) {
                case 0:
                    enemies.add(new NormalEnemyTank(x, y));
                    break;
                case 1:
                    enemies.add(new FastEnemyTank(x, y));
                    break;
                case 2:
                    enemies.add(new HeavyEnemyTank(x, y));
                    break;
            }
        }
    }

    private void createObstacles() {
        // 创建一些障碍物
        for (int i = 0; i < 5; i++) {
            obstacles.add(new Obstacle(100 + i * 150, 300, 50, 50));
        }

        // 添加一些随机障碍物
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(700) + 50;
            int y = random.nextInt(400) + 100;
            int width = 30 + random.nextInt(40);
            int height = 30 + random.nextInt(40);

            obstacles.add(new Obstacle(x, y, width, height));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            drawGame(g);
        } else {
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGame(Graphics g) {
        // 绘制玩家坦克
        playerTank.draw(g);

        // 绘制敌人坦克
        for (EnemyTank enemy : enemies) {
            if (enemy.isVisible()) {
                enemy.draw(g);
            }
        }

        // 绘制子弹
        for (Bullet bullet : bullets) {
            if (bullet.isVisible()) {
                bullet.draw(g);
            }
        }

        // 绘制障碍物
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }

        // 绘制道具
        for (PowerUp powerUp : powerUps) {
            if (powerUp.isVisible()) {
                powerUp.draw(g);
            }
        }

        // 绘制爆炸效果
        for (Explosion explosion : explosions) {
            if (explosion.isActive()) {
                explosion.draw(g);
            }
        }

        // 绘制状态信息
        drawStatusInfo(g);
    }

    private void drawStatusInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("HarmonyOS Sans", Font.BOLD, 16));
        g.drawString("分数: " + score, 20, 30);
        g.drawString("等级: " + level, 20, 50);

        // 绘制生命条
        g.drawString("生命值: ", 600, 30);
        g.setColor(Color.RED);
        g.fillRect(670, 18, playerTank.getHealth(), 15);
        g.setColor(Color.WHITE);
        g.drawRect(670, 18, 100, 15);

        // 绘制当前道具状态
        StringBuilder powerUpsText = new StringBuilder("道具: ");
        if (playerTank.hasShield()) {
            powerUpsText.append("护盾 ");
        }
        if (playerTank.hasSpeedBoost()) {
            powerUpsText.append("加速 ");
        }
        if (playerTank.hasScatterShot()) {
            powerUpsText.append("散弹 ");
        }
        g.drawString(powerUpsText.toString(), 600, 50);
    }

    private void drawGameOver(Graphics g) {
        String msg;

        if (playerTank.isAlive() && enemies.isEmpty()) {
            msg = "Level " + level + " 完成!";
        } else {
            msg = "游戏结束 - 最终分数: " + score;
        }

        Font font = new Font("Helvetica", Font.BOLD, 48);
        FontMetrics fm = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2 - 50);

        // 添加继续/重新开始按钮提示
        Font smallFont = new Font("Helvetica", Font.BOLD, 20);
        g.setFont(smallFont);

        String continueMsg;
        if (playerTank.isAlive() && enemies.isEmpty()) {
            continueMsg = "按 ENTER 继续下一关";
        } else {
            continueMsg = "按 ENTER 重新开始";
        }

        g.drawString(continueMsg, (getWidth() - getFontMetrics(smallFont).stringWidth(continueMsg)) / 2, getHeight() / 2 + 50);

        String menuMsg = "按 ESC 返回主菜单";
        g.drawString(menuMsg, (getWidth() - getFontMetrics(smallFont).stringWidth(menuMsg)) / 2, getHeight() / 2 + 100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            updateGame();
        }

        repaint();
    }

    private void updateGame() {
        // 更新冷却时间
        if (collisionCooldown > 0) {
            collisionCooldown--;
        }

        // 更新玩家坦克
        playerTank.update();

        // 检测玩家与障碍物的碰撞
        checkObstacleCollisionForTank(playerTank);

        // 更新敌人坦克
        updateEnemies();

        // 更新子弹
        updateBullets();

        // 更新爆炸效果
        updateExplosions();

        // 更新道具
        updatePowerUps();

        // 尝试生成新道具
        spawnPowerUp();

        // 检查游戏是否结束
        checkGameState();
    }

    private void updateEnemies() {
        for (EnemyTank enemy : enemies) {
            if (enemy.isVisible()) {
                // 保存当前位置
                int oldX = enemy.getX();
                int oldY = enemy.getY();

                // 更新敌人状态
                enemy.update();

                // 检测坦克之间的碰撞
                checkTankCollision(enemy);

                // 检测坦克与障碍物的碰撞
                if (checkObstacleCollisionForTank(enemy)) {
                    // 如果发生碰撞，恢复到原位置并更改方向
                    enemy.setX(oldX);
                    enemy.setY(oldY);
                    enemy.changeDirection();
                }

                // 敌人发射子弹
                if (random.nextInt(100) < enemy.getShootingRate()) {
                    bullets.addAll(enemy.fire());
                }
            }
        }
    }

    private void updateBullets() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();

            if (bullet.isVisible()) {
                bullet.move();

                // 检测子弹与坦克的碰撞
                checkBulletCollisions(bullet);

                // 检测子弹与障碍物的碰撞
                checkObstacleCollisions(bullet);
            } else {
                it.remove();
            }
        }
    }

    private void updateExplosions() {
        Iterator<Explosion> it = explosions.iterator();
        while (it.hasNext()) {
            Explosion explosion = it.next();
            explosion.update();

            if (!explosion.isActive()) {
                it.remove();
            }
        }
    }

    private void updatePowerUps() {
        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp powerUp = it.next();

            if (powerUp.isVisible()) {
                // 检测玩家与道具的碰撞
                if (playerTank.getBounds().intersects(powerUp.getBounds())) {
                    applyPowerUp(powerUp);
                    powerUp.setVisible(false);
                }
            } else {
                it.remove();
            }
        }
    }

    private void spawnPowerUp() {
        powerUpSpawnCounter++;

        if (powerUpSpawnCounter >= POWER_UP_SPAWN_DELAY && random.nextInt(100) < 5) {
            int x = random.nextInt(700) + 50;
            int y = random.nextInt(400) + 100;

            PowerUp.PowerUpType type = PowerUp.PowerUpType.values()[random.nextInt(PowerUp.PowerUpType.values().length)];
            powerUps.add(new PowerUp(x, y, type));

            powerUpSpawnCounter = 0;
        }
    }

    private void applyPowerUp(PowerUp powerUp) {
        switch (powerUp.getType()) {
            case HEALTH:
                playerTank.heal(30);
                break;
            case SPEED:
                playerTank.activateSpeedBoost(500); // 5秒加速
                break;
            case SHIELD:
                playerTank.activateShield(800); // 8秒护盾
                break;
            case SCATTER:
                playerTank.activateScatterShot(300); // 3秒散弹
                break;
        }
    }

    private void checkTankCollision(Tank tank) {
        Rectangle tankBounds = tank.getBounds();

        // 检查与玩家坦克的碰撞
        if (tank != playerTank && playerTank.getBounds().intersects(tankBounds)) {
            // 碰撞响应 - 推开两个坦克
            int dx = tank.getX() - playerTank.getX();
            int dy = tank.getY() - playerTank.getY();

            // 简单的碰撞响应
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    tank.setX(tank.getX() + 2);
                    playerTank.setX(playerTank.getX() - 2);
                } else {
                    tank.setX(tank.getX() - 2);
                    playerTank.setX(playerTank.getX() + 2);
                }
            } else {
                if (dy > 0) {
                    tank.setY(tank.getY() + 2);
                    playerTank.setY(playerTank.getY() - 2);
                } else {
                    tank.setY(tank.getY() - 2);
                    playerTank.setY(playerTank.getY() + 2);
                }
            }

            // 如果冷却时间结束，对玩家造成伤害（除非有护盾）
            if (collisionCooldown == 0 && !playerTank.hasShield()) {
                playerTank.takeDamage(COLLISION_DAMAGE);
                collisionCooldown = COLLISION_COOLDOWN_TIME;

                // 创建小型爆炸效果表示碰撞
                createExplosion(
                        (playerTank.getX() + tank.getX()) / 2,
                        (playerTank.getY() + tank.getY()) / 2,
                        false
                );
            }
        }

        // 检查与其他敌人坦克的碰撞
        for (EnemyTank otherTank : enemies) {
            if (tank != otherTank && otherTank.isVisible() && otherTank.getBounds().intersects(tankBounds)) {
                // 碰撞响应 - 改变方向
                if (tank instanceof EnemyTank) {
                    ((EnemyTank) tank).changeDirection();
                }

                // 将两个坦克分开
                int dx = tank.getX() - otherTank.getX();
                int dy = tank.getY() - otherTank.getY();

                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        tank.setX(tank.getX() + 1);
                        otherTank.setX(otherTank.getX() - 1);
                    } else {
                        tank.setX(tank.getX() - 1);
                        otherTank.setX(otherTank.getX() + 1);
                    }
                } else {
                    if (dy > 0) {
                        tank.setY(tank.getY() + 1);
                        otherTank.setY(otherTank.getY() - 1);
                    } else {
                        tank.setY(tank.getY() - 1);
                        otherTank.setY(otherTank.getY() + 1);
                    }
                }
                break;
            }
        }
    }

    // 返回是否发生了碰撞
    private boolean checkObstacleCollisionForTank(Tank tank) {
        Rectangle tankBounds = tank.getBounds();
        boolean collisionDetected = false;

        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(tankBounds)) {
                collisionDetected = true;

                // 计算碰撞响应（推开坦克）
                Rectangle intersection = tankBounds.intersection(obstacle.getBounds());

                // 存储坦克当前位置用于推开
                int currentX = tank.getX();
                int currentY = tank.getY();

                // 根据碰撞区域的形状确定推动方向
                if (intersection.width < intersection.height) {
                    // 水平碰撞（左或右）
                    if (tankBounds.x < obstacle.x) {
                        // 从右侧碰撞，向左推
                        tank.setX(currentX - intersection.width);
                    } else {
                        // 从左侧碰撞，向右推
                        tank.setX(currentX + intersection.width);
                    }
                } else {
                    // 垂直碰撞（上或下）
                    if (tankBounds.y < obstacle.y) {
                        // 从下方碰撞，向上推
                        tank.setY(currentY - intersection.height);
                    } else {
                        // 从上方碰撞，向下推
                        tank.setY(currentY + intersection.height);
                    }
                }

                // 如果是敌人坦克，改变方向
                if (tank instanceof EnemyTank) {
                    ((EnemyTank) tank).changeDirection();
                }
            }
        }

        return collisionDetected;
    }

    private void checkBulletCollisions(Bullet bullet) {
        Rectangle bulletBounds = bullet.getBounds();

        // 检查与玩家坦克的碰撞
        if (!bullet.isPlayerBullet() && playerTank.getBounds().intersects(bulletBounds)) {
            if (!playerTank.hasShield()) {
                playerTank.hit(bullet.getDamage());
                createExplosion(bullet.getX(), bullet.getY(), false);
            }
            bullet.setVisible(false);
            return;
        }

        // 检查与敌人坦克的碰撞
        Iterator<EnemyTank> it = enemies.iterator();
        while (it.hasNext()) {
            EnemyTank enemy = it.next();
            if (enemy.isVisible() && bullet.isPlayerBullet() && enemy.getBounds().intersects(bulletBounds)) {
                enemy.hit(bullet.getDamage());
                bullet.setVisible(false);

                if (!enemy.isVisible()) {
                    // 坦克被摧毁，增加分数
                    score += enemy.getScoreValue();
                    // 创建爆炸效果
                    createExplosion(enemy.getX(), enemy.getY(), true);
                    it.remove();
                }

                return;
            }
        }
    }

    private void checkObstacleCollisions(Bullet bullet) {
        Rectangle bulletBounds = bullet.getBounds();

        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(bulletBounds)) {
                bullet.setVisible(false);
                createExplosion(bullet.getX(), bullet.getY(), false);
                return;
            }
        }
    }

    private void createExplosion(int x, int y, boolean large) {
        explosions.add(new Explosion(x - 20, y - 20, large ? 60 : 30));
    }

    private void checkGameState() {
        if (!playerTank.isAlive()) {
            inGame = false;
            return;
        }

        // 如果所有敌人都被消灭，关卡完成
        if (enemies.isEmpty()) {
            inGame = false;
        }
    }

    public void nextLevel() {
        if (!playerTank.isAlive()) {
            initGame(1); // 如果玩家死亡，重新开始游戏
        } else {
            level++; // 进入下一关

            // 保留玩家的生命值和得分
            int savedHealth = playerTank.getHealth();

            // 初始化新关卡
            enemies.clear();
            bullets.clear();
            powerUps.clear();
            explosions.clear();
            obstacles.clear();

            spawnEnemiesForLevel(level);
            createObstacles();

            // 重置玩家坦克位置，但保持生命值
            playerTank = new PlayerTank(400, 500);
            playerTank.setHealth(savedHealth);

            inGame = true;
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (inGame) {
                playerTank.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    List<Bullet> firedBullets = playerTank.fire();
                    bullets.addAll(firedBullets);
                }
            } else {
                // 游戏结束或关卡完成状态下的按键处理
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nextLevel();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    timer.stop();
                    tankGame.returnToMenu();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (inGame) {
                playerTank.keyReleased(e);
            }
        }
    }
}