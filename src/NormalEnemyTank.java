/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 11:02
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;

public class NormalEnemyTank extends EnemyTank {

    public NormalEnemyTank(int x, int y) {
        super(x, y, 100, 2, 60, 2, 100); // 生命值, 速度, 移动延迟, 射击频率, 分数
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);

        // 绘制炮管
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
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
    }
}
