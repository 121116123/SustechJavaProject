package view;

import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    private JLabel informLabel;
    private JLabel blackNameLabel;
    private JLabel whiteNameLabel;
    public String inform = "choose a mode";
    public String blackName;
    public String whiteName;

    private ImageIcon imageIcon;
    private JLabel pic;
    private JPanel Pic;


    //需要一个开始界面，输入两个人的名字，选择模式和白天晚上
    public RightPanel(int width, int height) {
        this.setSize(width, height);
        this.setLayout(null);
        this.setBackground(new Color(70, 130, 180));
        this.setVisible(true);
        this.setOpaque(false);

        this.blackNameLabel = new JLabel();
        this.blackNameLabel.setLocation((int) (width * 0.4), 150);
        this.blackNameLabel.setSize((int) (width * 0.2), (int) (height * 0.5));
        blackNameLabel.setForeground(new Color(240, 248, 255));
        this.blackNameLabel.setFont(new Font("Calibri", Font.ITALIC, 25));
        this.setNameLabelTextBlack("");
        blackNameLabel.setOpaque(false);
        add(blackNameLabel);

        this.whiteNameLabel = new JLabel();
        this.whiteNameLabel.setLocation((int) (width * 0.4), blackNameLabel.getY() + 30);
        this.whiteNameLabel.setSize((int) (width * 0.2), (int) (height * 0.8));
        whiteNameLabel.setForeground(new Color(240, 248, 255));
        this.whiteNameLabel.setFont(new Font("Calibri", Font.ITALIC, 25));
        whiteNameLabel.setOpaque(false);
        this.setNameLabelTextWhite("");
        add(whiteNameLabel);

        this.informLabel = new JLabel();
        this.informLabel.setLocation((int) (width * 0.4), whiteNameLabel.getY() + 100);
        this.informLabel.setSize((int) (width * 0.4), height);
        informLabel.setForeground(new Color(240, 248, 255));
        this.informLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        this.setInformLabelText(inform);
        add(informLabel);


//        JButton player1Btn = new JButton();
//        player1Btn.setSize(30, 30);
//        player1Btn.setLocation(blackNameLabel.getX() - 50, blackNameLabel.getY() + 182);
//        player1Btn.setForeground(new Color(240, 248, 255));
//        player1Btn.setBackground(new Color(132, 112, 255));
//        add(player1Btn);
//        player1Btn.setLayout(null);
//        this.setVisible(true);
//        player1Btn.addActionListener(e -> {
//            System.out.println("Add black player");
//            String name = JOptionPane.showInputDialog(this, "input the name here");
//            String picFile = JOptionPane.showInputDialog(this, "input the picFile here");
//            if (name != null) {
//                setNameLabelTextBlack(name);
//            }
//            if (picFile != null) {
//                paintBlack(picFile);
//            }
//
//        });


//        JButton player2Btn = new JButton();
//        player2Btn.setSize(30, 30);
//        player2Btn.setLocation(whiteNameLabel.getX() - 50, whiteNameLabel.getY() + 304);
//        player2Btn.setForeground(new Color(240, 248, 255));
//        player2Btn.setBackground(new Color(132, 112, 255));
//        add(player2Btn);
//        player2Btn.setLayout(null);
//        this.setVisible(true);
//        player2Btn.addActionListener(e -> {
//            System.out.println("Add white player");
//            String name = JOptionPane.showInputDialog(this, "input the name here");
//            String picFile = JOptionPane.showInputDialog(this, "input the picFile here");
//            if (name != null) {
//                setNameLabelTextWhite(name);
//            }
//            if (picFile != null) {
//                paintWhite(picFile);
//            }
//
//        });


    }

    public void paintBlack(String path) {
        imageIcon = new ImageIcon(path);
        Pic = new JPanel();
        pic = new JLabel(imageIcon);
        pic.setBounds(blackNameLabel.getX(), blackNameLabel.getY() + 230, 100, 100);
        Pic.setBounds(blackNameLabel.getX(), blackNameLabel.getY() + 230, 100, 100);
        Pic.add(pic, new Integer(Integer.MIN_VALUE));
        Pic.setVisible(true);
        Pic.setOpaque(false);
        add(Pic);
    }

    public void paintWhite(String path) {
        imageIcon = new ImageIcon(path);
        Pic = new JPanel();
        pic = new JLabel(imageIcon);
        pic.setBounds(whiteNameLabel.getX(), whiteNameLabel.getY() + 350, 100, 100);
        Pic.setBounds(whiteNameLabel.getX(), whiteNameLabel.getY() + 350, 100, 100);
        Pic.add(pic, new Integer(Integer.MIN_VALUE));
        Pic.setVisible(true);
        Pic.setOpaque(false);
        add(Pic);
    }

    public void setInformLabelText(String inform) {
//        System.out.println("inform");
        this.inform = inform;
        this.informLabel.setText(inform);
    }

    //点击都用，除非一方无路可走
    public void setNameLabelTextBlack(String blackName) {
        this.blackName=blackName;
        this.blackNameLabel.setText("BlackPlayer: " + blackName);
    }

    public void setNameLabelTextWhite(String whiteName) {
        this.whiteNameLabel.setText("WhitePlayer: " + whiteName);
    }

}
