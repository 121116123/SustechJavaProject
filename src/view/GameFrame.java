package view;


import components.Music;
import controller.GameController;
import model.ChessPiece;
import model.Game;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Stack;

public class GameFrame extends JFrame {
    public static GameController controller;
    //    public static GameControllerCheat controller;
    private ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;
    public static RightPanel rightPanel;
    private Game game;
    //image
//    private ifIsTXT isTXT = new ifIsTXT();
    private ImageIcon imageIcon = new ImageIcon("C:\\Users\\maxin-12345\\IdeaProjects\\class9_28\\.idea\\GameBackground.jpg");
    private JLabel bg = new JLabel(imageIcon);
    private JPanel background;
    private Music music;
    //image

    private File f;
    private URI uri;
    private URL url;


    public GameFrame(int frameSize) {
        this.setTitle("Enjoy your black and white chess");
        this.setLayout(null);
        //获取窗口边框的长度，将这些值加到主窗口大小上，这能使窗口大小和预期相符
        //image

        bg.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        background = (JPanel) this.getContentPane();
        background.setOpaque(false);
        this.getLayeredPane().add(bg, new Integer(Integer.MIN_VALUE));
        //image
        Insets inset = this.getInsets();
        this.setSize(frameSize + inset.left + inset.right + 400, frameSize + inset.top + inset.bottom + 100);
        this.setLocationRelativeTo(null);

        game = new Game();
        game.setPlayerStack(new Stack<>());
        game.setStepStack(new Stack<>());
        game.setModeStack(new Stack<>());


        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.7));
        chessBoardPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 200, (this.getHeight() - chessBoardPanel.getHeight()) / 3);

        statusPanel = new StatusPanel((int) (this.getWidth() * 0.52), (int) (this.getHeight() * 0.1));
        statusPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 200, 0);

        rightPanel = new RightPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight()));
        rightPanel.setLocation(chessBoardPanel.getX() + chessBoardPanel.getWidth() - 250, chessBoardPanel.getY() - 300);

        controller = new GameController(chessBoardPanel, statusPanel, rightPanel, game);
        controller.setChessBoardPanel(chessBoardPanel);

        music = new Music();

        this.add(chessBoardPanel);
        this.add(statusPanel);
        this.add(rightPanel);

        game.addStep(chessBoardPanel.getChessGrids());
        game.addPlayer(controller.currentPlayer);
        game.addMode(controller.mode);


        JButton regretBtn = new JButton("regret");
        regretBtn.setSize(120, 50);
        regretBtn.setLocation(chessBoardPanel.getX(), (this.getHeight() + chessBoardPanel.getHeight()) / 2 - 50);
        regretBtn.setForeground(new Color(240, 248, 255));
        regretBtn.setBackground(new Color(241, 134, 226 ));
        add(regretBtn);
        regretBtn.addActionListener(e -> {
            rightPanel.setInformLabelText("you regret");
            System.out.println("click regret Btn");
            chessBoardPanel.regretStep();
            statusPanel.setPlayerText(controller.getCurrentPlayer().name());
            repaint();
        });

        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(120, 50);
        restartBtn.setLocation((regretBtn.getX()+160), (this.getHeight() + chessBoardPanel.getHeight()) / 2 - 50);
        restartBtn.setForeground(new Color(240, 248, 255));
        restartBtn.setBackground(new Color(150, 103, 235 ));
        add(restartBtn);
        restartBtn.addActionListener(e -> {
            System.out.println("click restart Btn");
            rightPanel.setInformLabelText("restart,please choose mode");
            chessBoardPanel.initialGame();
            statusPanel.setPlayerText(controller.getCurrentPlayer().name());
            statusPanel.setScoreText(2, 2);
            //construct game
            game = new Game();
            game.setPlayerStack(new Stack<>());
            game.setStepStack(new Stack<>());
            game.setModeStack(new Stack<>());
            game.addPlayer(controller.getCurrentPlayer());
            game.addStep(controller.getChessBoardPanel().getChessGrids());
            game.addMode(controller.mode);
            controller = new GameController(chessBoardPanel, statusPanel, rightPanel, game);

        });

        JButton loadGameBtn = new JButton("Load");
        loadGameBtn.setSize(120, 50);
        loadGameBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 40, restartBtn.getY());
        loadGameBtn.setForeground(new Color(240, 248, 255));
        loadGameBtn.setBackground(new Color(41, 157, 251));
        add(loadGameBtn);
        loadGameBtn.addActionListener(e -> {
            rightPanel.setInformLabelText("load,please choose mode");
            System.out.println("clicked Load Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            if (filePath != null) {
//                System.out.println("filePath not null");
                if (controller.isTxt(filePath)) {
                    controller.readFileData(filePath);
                    statusPanel.setPlayerText(controller.getCurrentPlayer().name());
                    statusPanel.setScoreText(controller.getBlackScore(), controller.getWhiteScore());
//                    controller.whereCanPut();
                    GameFrame.controller.whereCanPut();
//                    repaint();
                    game.setPlayerStack(new Stack<>());
                    game.setStepStack(new Stack<>());
                    game.setModeStack(new Stack<>());
                    game.addPlayer(controller.getCurrentPlayer());
                    game.addStep(controller.getChessBoardPanel().getChessGrids());
                    game.addMode(controller.mode);
                } else {
                    System.out.println("error 104");
                }
            }
        });

        JButton saveGameBtn = new JButton("Save");
        saveGameBtn.setSize(120, 50);
        saveGameBtn.setLocation(loadGameBtn.getX() + restartBtn.getWidth() + 40, restartBtn.getY());
        saveGameBtn.setForeground(new Color(240, 248, 255));
        saveGameBtn.setBackground(new Color(33, 214, 51));
        add(saveGameBtn);
        saveGameBtn.addActionListener(e -> {
            rightPanel.setInformLabelText("You saved the game");
            System.out.println("clicked Save Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.writeDataToFile(filePath);
        });

        music.start();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
