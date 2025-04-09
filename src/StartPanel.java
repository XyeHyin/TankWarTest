/**
 * @Author: XyeHyin
 * @Date: 2025/4/9 11:02
 * @packageName:IntelliJ IDEA
 * @ClassNam: 
 * @Description: TODO
 * @Version:1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private TankGame tankGame;
    private JButton startButton;
    private JButton instructionsButton;
    private JComboBox<Integer> levelSelector;

    public StartPanel(TankGame tankGame) {
        this.tankGame = tankGame;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("坦克大战", JLabel.CENTER);
        titleLabel.setFont(new Font("HarmonyOS Sans", Font.BOLD, 48));
        titleLabel.setForeground(Color.GREEN);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 20));
        buttonPanel.setOpaque(false);

        // 创建关卡选择器
        JPanel levelPanel = new JPanel();
        levelPanel.setOpaque(false);
        JLabel levelLabel = new JLabel("选择关卡: ");
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("HarmonyOS Sans", Font.BOLD, 18));

        Integer[] levels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        levelSelector = new JComboBox<>(levels);
        levelSelector.setFont(new Font("HarmonyOS Sans", Font.BOLD, 18));
        levelSelector.setPreferredSize(new Dimension(80, 30));

        levelPanel.add(levelLabel);
        levelPanel.add(levelSelector);

        startButton = new JButton("开始游戏");
        startButton.setFont(new Font("HarmonyOS Sans", Font.BOLD, 20));
        startButton.addActionListener(e -> {
            int selectedLevel = (Integer) levelSelector.getSelectedItem();
            tankGame.startGame(selectedLevel);
        });

        instructionsButton = new JButton("游戏说明");
        instructionsButton.setFont(new Font("HarmonyOS Sans", Font.BOLD, 20));
        instructionsButton.addActionListener(e -> showInstructions());

        // 添加空白面板以创建更好的间距
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);

        // 添加所有组件
        buttonPanel.add(spacerPanel);
        buttonPanel.add(levelPanel);
        buttonPanel.add(startButton);
        buttonPanel.add(instructionsButton);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void showInstructions() {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "游戏说明", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea instructionText = new JTextArea();
        instructionText.setEditable(false);
        instructionText.setLineWrap(true);
        instructionText.setWrapStyleWord(true);
        instructionText.setFont(new Font("SansSerif", Font.PLAIN, 14));
        instructionText.setText(
                "坦克大战 - 游戏说明\n\n" +
                        "控制方式：\n" +
                        "- 方向键(↑,↓,←,→)控制玩家坦克移动\n" +
                        "- 空格键发射子弹\n\n" +
                        "敌人类型：\n" +
                        "- 普通坦克(红色)：基础敌人，移动和攻击速度适中\n" +
                        "- 快速坦克(蓝色)：高速移动，但攻击力较弱\n" +
                        "- 重型坦克(棕色)：移动缓慢，但攻击力强大且生命值高\n\n" +
                        "道具系统：\n" +
                        "- 生命值回复(红心)：恢复玩家坦克的生命值\n" +
                        "- 加速(闪电)：暂时提高玩家坦克的移动速度\n" +
                        "- 护盾(盾牌)：短时间内免疫伤害\n" +
                        "- 散弹(星星)：短时间内可以一次发射多枚子弹\n\n" +
                        "得分规则：\n" +
                        "- 普通坦克：100分\n" +
                        "- 快速坦克：150分\n" +
                        "- 重型坦克：200分\n\n" +
                        "碰撞伤害：\n" +
                        "- 与敌方坦克碰撞会受到伤害\n" +
                        "- 护盾可以阻挡碰撞伤害\n\n" +
                        "游戏目标：\n" +
                        "- 消灭所有敌人坦克，获得尽可能高的分数！"
        );

        JScrollPane scrollPane = new JScrollPane(instructionText);

        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> dialog.dispose());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}
