/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 11:00
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;

public class Obstacle extends GameObject {

    public Obstacle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
    }
}