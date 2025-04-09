/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 11:03
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;

public class FastEnemyTank extends EnemyTank {

    public FastEnemyTank(int x, int y) {
        super(x, y, 70, 4, 40, 1, 150); // 生命值, 速度, 移动延迟, 射击频率, 分数
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);

        // 绘制炮管
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(4));

        int centerX = x + width / 2;
        int centerY = y + height / 2;

        switch (direction) {
            case UP:
                g2d.drawLine(centerX, centerY, centerX, y - 15);
                break;
            case DOWN:
                g2d.drawLine(centerX, centerY, centerX, y + height + 15);
                break;
            case LEFT:
                g2d.drawLine(centerX, centerY, x - 15, centerY);
                break;
            case RIGHT:
                g2d.drawLine(centerX, centerY, x + width + 15, centerY);
                break;
        }
    }
}
