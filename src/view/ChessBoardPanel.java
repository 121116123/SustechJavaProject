package view;

import components.ChessGridComponent;
import model.ChessPiece;
import model.Save;

import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    private final int CHESS_COUNT = 8;
    private ChessGridComponent[][] chessGrids;

    public ChessBoardPanel(int width, int height) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        int length = Math.min(width, height);
        this.setSize(length, length);
        ChessGridComponent.gridSize = length / CHESS_COUNT;
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, ChessGridComponent.gridSize, ChessGridComponent.chessSize);
        initialChessGrids();//return empty chessboard
        initialGame();//add initial four chess
        repaint();

    }

    /**
     * set an empty chessboard
     */
    //这个不用改
    public void initialChessGrids() {
        chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];

        //draw all chess grids
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                //创建格子
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                //设置格子位置
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                //小格子加到数组里
                chessGrids[i][j] = gridComponent;
                //小组件加进去
                this.add(chessGrids[i][j]);
            }
        }
    }

    /**
     * initial origin four chess
     */
    public void initialGame() {
        clearChessPieces();
        chessGrids[3][3].setChessPiece(ChessPiece.BLACK);
        chessGrids[3][4].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][3].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][4].setChessPiece(ChessPiece.BLACK);
        chessGrids[2][4].setChessPiece(ChessPiece.PINK);
        chessGrids[3][5].setChessPiece(ChessPiece.PINK);
        chessGrids[5][3].setChessPiece(ChessPiece.PINK);
        chessGrids[4][2].setChessPiece(ChessPiece.PINK);
    }

    private void clearChessPieces() {
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                chessGrids[i][j].setChessPiece(null);
            }
        }
        repaint();
    }

    public ChessGridComponent[][] getChessGrids() {
        return chessGrids;
    }

    public ChessGridComponent[][] setChessGrids(int row, int col, ChessPiece chessPiece) {
        chessGrids[row][col].setChessPiece(chessPiece);
//        System.out.println("set this"+chessPiece);
        return chessGrids;
    }


    public void showSteps(Save save, int i) {
//        try {
//            Thread.sleep(3000);
            int x = save.getStep(i)[0], y = save.getStep(i)[1];
            ChessPiece player = save.getPlayer(i);
            GameFrame.controller.setCurrentPlayer(player);
            setChessGrids(x, y, player);
            GameFrame.controller.flipChess(x, y);
            save.addBoard(chessGrids);
            if (save.getMode() != 1){
                GameFrame.controller.whereCanPut();
            }
//            repaint();
//        } catch(Exception e){}

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    //新建一个game，存上现在的状态
    public void loadGameByData(int[][] datas, String playerTurn) {
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                if (datas[i][j] == 0) {
                    chessGrids[i][j].setChessPiece(null);
                } else if (datas[i][j] == -1) {
                    chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                } else if (datas[i][j] == 1) {
                    chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                }
            }
        }
        if (playerTurn.equals("#Black's turn")) {
            GameFrame.controller.setCurrentPlayer(ChessPiece.BLACK);
        } else if (playerTurn.equals("#White's turn")) {
            GameFrame.controller.setCurrentPlayer(ChessPiece.WHITE);
        }
        repaint();
    }

    public int saveDataByGame(int i, int j) {
        if (chessGrids[i][j].getChessPiece() == ChessPiece.WHITE) {
            return 1;
        } else if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
            return -1;
        } else {
            return 0;
        }
    }

    //每一步存的时候都是当前棋盘 ，pop出来都是最后存的那个
    public void regretStep() {
        if (!GameFrame.controller.game.getStepStack().isEmpty()) {
            GameFrame.controller.game.getStep();
            GameFrame.controller.game.getPlayer();
            GameFrame.controller.game.getMode();
            if (!GameFrame.controller.game.getStepStack().isEmpty()) {
                ChessGridComponent[][] step = new ChessGridComponent[8][8];
                step = GameFrame.controller.game.readStep();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        setChessGrids(i, j, step[i][j].getChessPiece());
                    }
                }
                GameFrame.controller.setCurrentPlayer(GameFrame.controller.game.readPlayer());
                GameFrame.controller.mode = GameFrame.controller.game.readMode();
                System.out.println("player" + GameFrame.controller.currentPlayer);
            } else {
                GameFrame.rightPanel.setInformLabelText("no step can regret ");
            }
        }
       else GameFrame.rightPanel.setInformLabelText("no step can regret ");
    }
}