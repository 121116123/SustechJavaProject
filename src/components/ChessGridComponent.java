package components;

import controller.GameController;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import model.*;
import view.GameFrame;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;

import static java.lang.Thread.sleep;

//每个小格子
public class ChessGridComponent extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public static Color gridColor = new Color(100, 149, 237);

    private ChessPiece chessPiece;
    //每一个格子都有自己的棋子
    private int row;
    private int col;
    //每个格子都有自己的位置

    private File f;
    private URI uri;
    private URL url;



    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
    }


    //点击格子发生的事情都在这
    public void onMouseClicked() {
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
        //todo: complete mouse click method
        if (GameFrame.controller.canClick(row, col)) {
            GameFrame.controller.chessBoardPanel.setChessGrids(row, col, GameFrame.controller.getCurrentPlayer());
            GameFrame.controller.flipChess(row, col);
            GameFrame.controller.getSave().addPlayer(GameFrame.controller.getCurrentPlayer());
            GameFrame.controller.getSave().addBoard(GameFrame.controller.chessBoardPanel.getChessGrids());
            GameFrame.controller.getSave().addStep(row, col);
            GameFrame.controller.getSave().setMode(GameFrame.controller.mode);
            if (GameFrame.controller.mode==2||GameFrame.controller.mode==3) {
                GameFrame.controller.swapPlayer();
                //人机就是在这里加一个wherecanput，找到所有能放的点，循环能放的点，找每个点放完之后能翻转的个数
                GameFrame.controller.aiPutChess(GameFrame.controller.aiFindBestPosition(GameFrame.controller.whereCanPut()));
                GameFrame.controller.cleanPinkChess();
                //forList,aiFindBestPosition,row,col
                //aiPutChess,里面调用flip
            }

            try{
                f=new File("C:\\Users\\maxin-12345\\IdeaProjects\\class9_28\\.idea\\手机APP应用程序特效音-打字机类型 S08Te.1279(_爱给网_aigei_com.wav");
                uri=f.toURI();
                url= uri.toURL();
                AudioClip aau;
                aau= Applet.newAudioClip(url);
                aau.play();
            }catch (Exception e){
                e.printStackTrace();
            }
            GameFrame.controller.swapPlayer();
            GameFrame.controller.whereCanPut();
            GameFrame.controller.game.addStep(GameFrame.controller.getChessBoardPanel().getChessGrids());
            GameFrame.controller.game.addPlayer(GameFrame.controller.currentPlayer);
            GameFrame.controller.game.addMode(GameFrame.controller.mode);
//            System.out.println("flip add step and player");
            repaint();
        } else {
            System.out.println("error 105");
            GameFrame.controller.rightPanel.setInformLabelText("error 105");
        }
    }



    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
        this.repaint();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void drawPiece(Graphics g) {
        //画棋子
        g.setColor(gridColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
        if (this.chessPiece != null) {
            g.setColor(chessPiece.getColor());
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, chessSize);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        //画那个格子再画棋子
        super.printComponents(g);
        drawPiece(g);
    }


}
