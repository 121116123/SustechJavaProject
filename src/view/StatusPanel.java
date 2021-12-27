package view;

import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    //上边那个贴纸，player，score之类的
    private JLabel playerLabel;
    private JLabel scoreLabel;

    public StatusPanel(int width, int height) {
        this.setSize(width, height);
        this.setLayout(null);
        this.setBackground(new Color(70, 130, 180));
        this.setVisible(true);

        this.playerLabel = new JLabel();
        this.playerLabel.setLocation(0, 10);
        this.playerLabel.setSize((int) (width * 0.4), height);
        this.playerLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        playerLabel.setForeground(new Color(240, 248, 255));
        this.setPlayerText(ChessPiece.BLACK.name());
        add(playerLabel);

        this.scoreLabel = new JLabel();
        this.scoreLabel.setLocation((int) (width * 0.4), 10);
        this.scoreLabel.setSize((int) (width * 0.5), height);
        this.scoreLabel.setFont(new Font("Calibri", Font.ITALIC, 25));
        scoreLabel.setForeground(new Color(240, 248, 255));
        this.setScoreText(2, 2);
        add(scoreLabel);

    }

    //但凡有点击棋盘成功的事件，就想办法调用这里
    public void setScoreText(int black, int white) {
        this.scoreLabel.setText(String.format("BLACK: %d\tWHITE: %d", black, white));
    }

    //点击都用，除非一方无路可走
    public void setPlayerText(String playerText) {
        this.playerLabel.setText(playerText + "'s turn");
    }

}
