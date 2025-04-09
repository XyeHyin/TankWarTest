/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 10:57
 * @packageName:IntelliJ IDEA
 * @ClassNam:
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;
import java.util.List;

public class HeavyEnemyTank extends EnemyTank {

    public HeavyEnemyTank(int x, int y) {
        super(x, y, 200, 1, 90, 3, 200); // 生命值, 速度, 移动延迟, 射击频率, 分数
    }

    @Override
    public List<Bullet> fire() {
        List<Bullet> bullets = super.fire();

        // 设置每个子弹的伤害为2
        for (Bullet bullet : bullets) {
            bullet.setDamage(2);
        }

        return bullets;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19)); // 棕色
        g.fillRect(x, y, width, height);

        // 绘制炮管
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(139, 69, 19));
        g2d.setStroke(new BasicStroke(7));

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
    }
}