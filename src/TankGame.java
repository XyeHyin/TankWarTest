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

public class TankGame extends JFrame {

    private GamePanel gamePanel;
    private StartPanel startPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public TankGame() {
        setTitle("坦克大战");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        startPanel = new StartPanel(this);
        gamePanel = new GamePanel(this);

        mainPanel.add(startPanel, "Start");
        mainPanel.add(gamePanel, "Game");

        add(mainPanel);
        cardLayout.show(mainPanel, "Start");

        // 使窗口居中显示
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame(int level) {
        gamePanel.initGame(level);
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocus();
    }

    public void returnToMenu() {
        cardLayout.show(mainPanel, "Start");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TankGame();
        });
    }
}