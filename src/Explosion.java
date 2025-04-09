/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 11:04
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;

public class Explosion {
    private int x;
    private int y;
    private int size;
    private int currentFrame;
    private int maxFrames;
    private boolean active;

    private Color[] colors = {
            Color.WHITE,
            Color.YELLOW,
            new Color(255, 165, 0), // Orange
            Color.RED,
            new Color(128, 0, 0)    // Dark Red
    };

    public Explosion(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.currentFrame = 0;
        this.maxFrames = 15; // 爆炸动画持续15帧
        this.active = true;
    }

    public void update() {
        currentFrame++;

        if (currentFrame >= maxFrames) {
            active = false;
        }
    }

    public void draw(Graphics g) {
        if (!active) return;

        float progress = (float)currentFrame / maxFrames;
        int colorIndex = Math.min((int)(progress * colors.length), colors.length - 1);
        int currentSize = (int)(size * (1.0 - 0.7 * progress));

        g.setColor(colors[colorIndex]);

        // 绘制爆炸圆形
        g.fillOval(x + (size - currentSize) / 2, y + (size - currentSize) / 2,
                currentSize, currentSize);

        // 添加爆炸射线
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));

        for (int i = 0; i < 8; i++) {
            double angle = Math.PI / 4 * i;
            int rayLength = (int)(size * 0.8 * (1.0 - progress));
            int startX = x + size / 2;
            int startY = y + size / 2;
            int endX = (int)(startX + rayLength * Math.cos(angle));
            int endY = (int)(startY + rayLength * Math.sin(angle));

            g2d.drawLine(startX, startY, endX, endY);
        }
    }

    public boolean isActive() {
        return active;
    }
}
