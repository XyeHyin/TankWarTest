/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 11:04
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;

public class PowerUp extends GameObject {

    public enum PowerUpType {
        HEALTH, SPEED, SHIELD, SCATTER
    }

    private PowerUpType type;

    public PowerUp(int x, int y, PowerUpType type) {
        super(x, y, 20, 20);
        this.type = type;
    }

    public PowerUpType getType() {
        return type;
    }

    @Override
    public void draw(Graphics g) {
        switch (type) {
            case HEALTH:
                // 红心 - 生命恢复
                g.setColor(Color.RED);
                g.fillOval(x, y, width, height);
                g.setColor(Color.WHITE);
                g.drawOval(x, y, width, height);
                break;

            case SPEED:
                // 闪电 - 速度提升
                g.setColor(Color.YELLOW);
                int[] xPoints = {x + width/2, x + width, x + width/2, x};
                int[] yPoints = {y, y + height/3, y + height/2, y + height*2/3};
                g.fillPolygon(xPoints, yPoints, 4);
                g.setColor(Color.WHITE);
                g.drawPolygon(xPoints, yPoints, 4);
                break;

            case SHIELD:
                // 盾牌 - 无敌
                g.setColor(Color.CYAN);
                g.fillOval(x, y, width, height);
                g.setColor(Color.WHITE);
                g.drawOval(x, y, width, height);
                g.drawOval(x + 5, y + 5, width - 10, height - 10);
                break;

            case SCATTER:
                // 星星 - 散弹
                g.setColor(Color.MAGENTA);
                int[] starX = new int[10];
                int[] starY = new int[10];

                int centerX = x + width / 2;
                int centerY = y + height / 2;
                int outerRadius = width / 2;
                int innerRadius = width / 4;

                for (int i = 0; i < 10; i++) {
                    double angle = Math.PI / 5 * i;
                    int radius = (i % 2 == 0) ? outerRadius : innerRadius;

                    starX[i] = (int) (centerX + radius * Math.cos(angle));
                    starY[i] = (int) (centerY + radius * Math.sin(angle));
                }

                g.fillPolygon(starX, starY, 10);
                g.setColor(Color.WHITE);
                g.drawPolygon(starX, starY, 10);
                break;
        }
    }
}
