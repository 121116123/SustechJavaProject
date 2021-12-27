package view;

import model.Game;

import javax.swing.*;
import java.awt.*;

import static view.GameFrame.rightPanel;

public class StartFrame extends JFrame {

    JButton player1Btn, player2Btn;

    public StartFrame() {


        this.setLayout(null);

        JOptionPane.showMessageDialog(null, "click blackPlayer button");

        GameFrame mainFrame = new GameFrame(800);

//        this.setBackground(Color.PINK);?
        this.getContentPane().setBackground(Color.pink);
        this.getContentPane().setVisible(true);//如果改为true那么就变成了红色。
        player1Btn = new JButton("blackPlayer");
        player1Btn.setSize(200, 100);
        player1Btn.setLocation(0, 0);
        player1Btn.setForeground(new Color(240, 248, 255));
        player1Btn.setBackground(new Color(132, 112, 255));
        add(player1Btn);
        player1Btn.addActionListener(e -> {
            System.out.println("Add black player");
            String name = JOptionPane.showInputDialog(this, "input the black name here");
            System.out.println(name);
            String picFile = JOptionPane.showInputDialog(this, "input the picFile here");
            if (name != null) {
                rightPanel.setNameLabelTextBlack(name);
            }
            if (picFile != null) {
                System.out.println("blackJpg");
                rightPanel.paintBlack(picFile);
            }

        });


        player2Btn = new JButton("whitePlayer");
        player2Btn.setSize(200, 100);
        player2Btn.setLocation(player1Btn.getX() + 250, player1Btn.getY());
        player2Btn.setForeground(new Color(240, 248, 255));
        player2Btn.setBackground(new Color(132, 112, 255));
        add(player2Btn);
        player2Btn.setLayout(null);
        this.setVisible(true);
        player2Btn.addActionListener(e -> {
            System.out.println("Add white player");
            String name = JOptionPane.showInputDialog(this, "input the name here");
            String picFile = JOptionPane.showInputDialog(this, "input the picFile here");
            if (name != null) {
                rightPanel.setNameLabelTextWhite(name);
            }
            if (picFile != null) {
                rightPanel.paintWhite(picFile);
            }

        });


        //mode==0,normal，modeBtn
        //mode==1,cheat 2
        //mode==2,ai 3
        //mode==3,hard ai 4
        //mode==4,反转黑白棋 5
        JButton modeBtn = new JButton("NormalMode");
        modeBtn.setSize(300, 100);
        modeBtn.setLocation(0, player2Btn.getY()+150);
        modeBtn.setForeground(new Color(240, 248, 255));
        modeBtn.setBackground(new Color(150, 25, 50));
        add(modeBtn);
        modeBtn.addActionListener(e -> {
            GameFrame.controller.cleanPinkChess();
            GameFrame.controller.mode = 0;
            rightPanel.setInformLabelText("normal mode,put in pink");
            System.out.println("Click NormalMode Btn");
            GameFrame.controller.setChessBoardPanel(GameFrame.controller.chessBoardPanel);
            GameFrame.controller.whereCanPut();
            repaint();
        });


        JButton modeBtn5 = new JButton("反转模式");
        modeBtn5.setSize(300, 100);
        modeBtn5.setLocation(modeBtn.getX()+modeBtn.getWidth(), player2Btn.getY()+150);
        modeBtn5.setForeground(new Color(240, 248, 255));
        modeBtn5.setBackground(new Color(150, 25, 50));
        add(modeBtn5);
        modeBtn5.addActionListener(e -> {
            GameFrame.controller.cleanPinkChess();
            GameFrame.controller.mode = 4;
            rightPanel.setInformLabelText("反转模式,put in pink");
            System.out.println("Click 反转 Btn");
            GameFrame.controller.setChessBoardPanel(GameFrame.controller.chessBoardPanel);
            GameFrame.controller.whereCanPut();
            repaint();
        });

        JButton modeBtn2 = new JButton("CheatMode");
        modeBtn2.setSize(200, 100);
        modeBtn2.setLocation(player1Btn.getX(), modeBtn.getY() + 150);
        modeBtn2.setForeground(new Color(240, 248, 255));
        modeBtn2.setBackground(new Color(70, 130, 180));
        add(modeBtn2);
        modeBtn2.addActionListener(e -> {
            rightPanel.setInformLabelText("cheat mode,put everywhere");
            GameFrame.controller.mode = 1;
            System.out.println("Click CheatMode Btn");
            GameFrame.controller.setChessBoardPanel(GameFrame.controller.chessBoardPanel);
            GameFrame.controller.cleanPinkChess();
            GameFrame.controller.game.addPlayer(GameFrame.controller.getCurrentPlayer());
            GameFrame.controller.game.addStep(GameFrame.controller.getChessBoardPanel().getChessGrids());
            GameFrame.controller.game.addMode(GameFrame.controller.mode);
            repaint();
        });


        JButton modeBtn3 = new JButton("AiMode");
        modeBtn3.setSize(200, 100);
        modeBtn3.setLocation(player2Btn.getX(), modeBtn.getY() + 150);
        modeBtn3.setForeground(new Color(240, 248, 255));
        modeBtn3.setBackground(new Color(70, 130, 180));
        add(modeBtn3);
        modeBtn3.addActionListener(e -> {
            GameFrame.controller.cleanPinkChess();
            rightPanel.setInformLabelText("Ai mode,I'm here");
            GameFrame.controller.mode = 2;
            System.out.println("Click AiMode Btn");
            GameFrame.controller.setChessBoardPanel(GameFrame.controller.chessBoardPanel);
            GameFrame.controller.whereCanPut();
            GameFrame.controller.game.addPlayer(GameFrame.controller.getCurrentPlayer());
            GameFrame.controller.game.addStep(GameFrame.controller.getChessBoardPanel().getChessGrids());
            GameFrame.controller.game.addMode(GameFrame.controller.mode);
            repaint();
        });

        JButton modeBtn4 = new JButton("High AiMode");
        modeBtn4.setSize(200, 100);
        modeBtn4.setLocation(modeBtn3.getX()+250, modeBtn3.getY());
        modeBtn4.setForeground(new Color(240, 248, 255));
        modeBtn4.setBackground(new Color(70, 130, 180));
        add(modeBtn4);
        modeBtn4.addActionListener(e -> {
            GameFrame.controller.cleanPinkChess();
            rightPanel.setInformLabelText(" High Ai mode,I'm here");
            GameFrame.controller.mode = 3;
            System.out.println("Click High AiMode Btn");
            GameFrame.controller.setChessBoardPanel(GameFrame.controller.chessBoardPanel);
            GameFrame.controller.whereCanPut();
            GameFrame.controller.game.addPlayer(GameFrame.controller.getCurrentPlayer());
            GameFrame.controller.game.addStep(GameFrame.controller.getChessBoardPanel().getChessGrids());
            GameFrame.controller.game.addMode(GameFrame.controller.mode);
            repaint();
        });




        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }


}
