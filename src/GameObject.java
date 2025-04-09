/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 10:58
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import java.awt.*;

// 所有游戏对象的基类
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public abstract void draw(Graphics g);
}
