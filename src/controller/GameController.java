package controller;

import components.ChessGridComponent;
import components.Music;
import model.ChessPiece;
import model.Game;
import model.Save;
import view.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameController {

    public ChessPiece currentPlayer;
    public ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;
    public RightPanel rightPanel;
    public Game game;
    //    private ChessPiece currentPlayer;
    private ChessPiece oppositePlayer;
    private int blackScore;
    private int whiteScore;
//    public Music music;

    public static int endgame = 2;
    public int mode;
    public Save save;

    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel, RightPanel rightPanel, Game game) {
        this.chessBoardPanel = gamePanel;
        this.statusPanel = statusPanel;
        this.rightPanel = rightPanel;
        this.currentPlayer = ChessPiece.BLACK;
        this.game = game;
        save=new Save();
//        this.music=new Music();
        blackScore = 0;
        whiteScore = 0;
    }


    public int getBlackScore() {
        countScore();
        return blackScore;
    }

    public int getWhiteScore() {
        countScore();
        return whiteScore;
    }

    public void setOppositePlayer() {
        oppositePlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
    }

    public void swapPlayer() {
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
//        System.out.println("swap player");
    }


    //把该翻转的棋子转过来,包括自己
    public void flipChess(int row, int col) {
        setOppositePlayer();
        //
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                boolean checkDirection = false;
                int flipChessNum = 0;
                int rowCheck = row + i, colCheck = col + j;
                if (rowCheck >= 0 && rowCheck < 8 && colCheck >= 0 && colCheck < 8 && chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == oppositePlayer) {
                    flipChessNum++;
                    for (rowCheck += i, colCheck += j; rowCheck >= 0 && rowCheck < 8 && colCheck >= 0 && colCheck < 8; rowCheck += i, colCheck += j) {
                        if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == oppositePlayer) {
                            flipChessNum++;
                        }
                        if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == currentPlayer) {
                            checkDirection = true;
                            break;
                        }
                        if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == null) {
                            break;
                        }
                    }
                }
                if (mode == 0||mode==2||mode==3||mode==4) {
                    if (checkDirection) {
//                            chessBoardPanel.setChessGrids(row, col, currentPlayer);
                        rightPanel.setInformLabelText("normal mode,put in pink");
                        endgame = 2;
                        for (rowCheck -= i, colCheck -= j; flipChessNum > 0; rowCheck -= i, colCheck -= j, flipChessNum--) {
                            chessBoardPanel.setChessGrids(rowCheck, colCheck, currentPlayer);
                            System.out.println(rowCheck+""+colCheck+"change to"+currentPlayer);
                        }
                    }
                } else {
                    if (checkDirection) {
//                            chessBoardPanel.setChessGrids(row, col, currentPlayer);
                        endgame = 2;
                        for (rowCheck -= i, colCheck -= j; flipChessNum > 0; rowCheck -= i, colCheck -= j, flipChessNum--) {
                            chessBoardPanel.setChessGrids(rowCheck, colCheck, currentPlayer);
                        }
                    } else {
                        chessBoardPanel.setChessGrids(row, col, currentPlayer);
                        rightPanel.setInformLabelText("cheat mode,put everywhere");
                    }
                }
            }
        }
    }

    public ArrayList<int[]> whereCanPut() {
        ArrayList<int[]> putList;
        if (mode == 0||mode==2||mode==3||mode==4) {
            System.out.println("whereCAnPut");
            putList = new ArrayList<>();
            while (endgame > 0) {
                setOppositePlayer();
                putList = new ArrayList<>();
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        boolean thisGrid = false;
                        if (chessBoardPanel.getChessGrids()[row][col].getChessPiece() == null) {
                            for (int k = -1; k < 2 && !thisGrid; k++) {
                                for (int l = -1; l < 2 && !thisGrid; l++) {
                                    int rowCheck = row + k, colCheck = col + l;
                                    if (rowCheck >= 0 && rowCheck < 8 && colCheck >= 0 && colCheck < 8 && chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == oppositePlayer) {
                                        for (rowCheck += k, colCheck += l; rowCheck >= 0 && rowCheck < 8 && colCheck >= 0 && colCheck < 8; rowCheck += k, colCheck += l) {
                                            if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == oppositePlayer) {
                                            }
                                            if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == currentPlayer) {
                                                thisGrid = true;
                                                break;
                                            }
                                            if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == null) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (thisGrid) {
                            int[] gridNum = new int[2];
                            gridNum[0] = row;
                            gridNum[1] = col;
                            putList.add(gridNum);
                        }
                    }
                }
                for (int[] ints : putList) {
                    chessBoardPanel.setChessGrids(ints[0], ints[1], ChessPiece.PINK);
//                    System.out.println("pink");
                }
                if (!putList.isEmpty()) {
//                    System.out.println("break");
                    break;
                }
                endgame--;
                rightPanel.setInformLabelText("you can't put, swap player");
                swapPlayer();
            }
            if (endgame == 0) {
                setEndGame();
            }
        } else {
            putList = new ArrayList<>();
            boolean b = true;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessBoardPanel.getChessGrids()[i][j].getChessPiece() == null) {
                        b = false;
                    }
                }
            }
            if (b) {
                setEndGame();
            }
        }
        return putList;
    }

    public int[] aiFindBestPosition(ArrayList<int[]> putList) {
        int bestFlipNum = 0;
        int[] bestPosition = new int[2];
        for (int[] position : putList) {
            if (mode==3) {
                if (position[0] == 0 && position[1] == 0) {
                    bestPosition = position;
                    break;
                }
                if (position[0] == 0 && position[1] == 7) {
                    bestPosition = position;
                    break;
                }
                if (position[0] == 7 && position[1] == 0) {
                    bestPosition = position;
                    break;
                }
                if (position[0] == 7 && position[1] == 7) {
                    bestPosition = position;
                    break;
                }
            }
            int row = position[0], col = position[1];
            int flipNum = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int rowCheck = row + i, colCheck = col + j;
                    if (rowCheck >= 0 && rowCheck < 8 && colCheck >= 0 && colCheck < 8 && chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == oppositePlayer) {
                        flipNum++;
                        for (rowCheck += i, colCheck += j; rowCheck >= 0 && rowCheck < 8 && colCheck >= 0 && colCheck < 8; rowCheck += i, colCheck += j) {
                            if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == oppositePlayer) {
                                flipNum++;
                            }
                            if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == currentPlayer) {
                                break;
                            }
                            if (chessBoardPanel.getChessGrids()[rowCheck][colCheck].getChessPiece() == null) {
                                break;
                            }
                        }
                    }
                }
            }
            if (flipNum > bestFlipNum) {
                bestFlipNum = flipNum;
                bestPosition = position;
            }
        }
        System.out.println(Arrays.toString(bestPosition));
        return bestPosition;
    }

    public void aiPutChess(int[]bestPosition){
        chessBoardPanel.setChessGrids(bestPosition[0],bestPosition[1],currentPlayer);
        flipChess(bestPosition[0],bestPosition[1]);
    }

    public void countScore() {
        //todo: modify the countScore method
        blackScore = 0;
        whiteScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoardPanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.BLACK) {
                    blackScore++;
                }
                if (chessBoardPanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.WHITE) {
                    whiteScore++;
                }
            }
        }
    }


    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessPiece getOppositePlayer() {
        return oppositePlayer;
    }

    public ChessBoardPanel getChessBoardPanel() {
        return chessBoardPanel;
    }


    public void setChessBoardPanel(ChessBoardPanel chessBoardPanel) {
        this.chessBoardPanel = chessBoardPanel;
    }

    public boolean isValidBoard(int[][] data) {
        int[][] board = data;
        int count = 0;
        if (board.length != 8) {
            count++;
        } else {
            for (int i = 0; i < 8; i++) {
                if (board[i].length != 8) {
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidChess(int[][] data) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (data[i][j] == 0 || data[i][j] == 1 || data[i][j] == -1) {
                } else {
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }


    }



    public void readFileData(String fileName) {
        //todo: read data from file
        List<String> fileData = new ArrayList<>();
        String playerTurn = new String();
        int[][] datas;
        List<String> stepList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.charAt(0) == 'm') {
                    mode = Integer.parseInt(line.substring(5));
                    System.out.println(mode);
                } else if (line.charAt(0) == '#'){
                    playerTurn = line;
                } else if (line.charAt(0) == 'B' || line.charAt(0) == 'W'){
                    stepList.add(line);
                }else {
                    fileData.add(line);
                }
            }


            for (int i = 0; i < stepList.size(); i++) {
                String[] s = stepList.get(i).split(" ");
                int x = Integer.parseInt(s[2].substring(1, 2)), y = Integer.parseInt(s[3].substring(0, 1));
                if (s[0].equals("BLACK")){
                    save.addPlayer(ChessPiece.BLACK);
                    System.out.println(-1);
                }else {
                    save.addPlayer(ChessPiece.WHITE);
                    System.out.println(1);
                }
                save.addStep(x, y);
            }

            datas = new int[fileData.size()][];
            for (int i = 0; i < fileData.size(); i++) {
                String[] str = fileData.get(i).split(" ");
                datas[i] = new int[str.length];
                for (int j = 0; j < str.length; j++) {
                    datas[i][j] = Integer.parseInt(str[j]);
                }
            }

            if (playerTurn.equals("#Black's turn")) {
                GameFrame.controller.setCurrentPlayer(ChessPiece.BLACK);
            } else if (playerTurn.equals("#White's turn")) {
                GameFrame.controller.setCurrentPlayer(ChessPiece.WHITE);
                System.out.println("set white");
            }
            if (isValidBoard(datas) == false) {
                System.out.println("error 101");
            } else if (isValidChess(datas) == false) {
                System.out.println("error 102");
            } else if (playerTurn.isEmpty()) {
                System.out.println("error 103");
            } else {
//                System.out.println("in else");
                //construct game
                for (int i = 0; i < stepList.size(); i++) {
                    chessBoardPanel.showSteps(save, i);
//                    chessBoardPanel.repaint();
                }

                chessBoardPanel.loadGameByData(datas,playerTurn);
//                System.out.println("finish else");
                System.out.println(playerTurn);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCurrentPlayer(ChessPiece player) {
        this.currentPlayer = player;
    }

//
//    public void writeDataToFile(String fileName) {
//        //todo: write data into file
//        File file = new File(fileName);
//        try {
//            FileWriter writer = new FileWriter(fileName);
//            BufferedWriter out = new BufferedWriter(writer);
//            {
//                for (int i = 0; i < 8; i++) {
//                    StringBuilder line = new StringBuilder();
//                    for (int j = 0; j < 8; j++) {
//                        line.append(chessBoardPanel.saveDataByGame(i, j) + " ");
//                    }
//                    line.setLength(line.length() - 1);
//                    out.write(String.valueOf(line) + "\r\n");
//                }
//            }
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


    public void writeDataToFile(String fileName) {
        //todo: write data into file
        File file = new File(fileName);
        try {
            FileWriter writer = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(writer);
            {
                out.write("mode:" + save.getMode() + "\r\n");
                for (int i = 0; i < save.getSteps().size(); i++) {
                    String step = String.format("%s clicked (%d, %d)"
                            ,save.getPlayer(i),save.getSteps().get(i)[0],save.getSteps().get(i)[1]);
                    out.write(step + "\r\n");
                }
                for (int i = 0; i < 8; i++) {
                    StringBuilder line = new StringBuilder();
                    for (int j = 0; j < 8; j++) {
                        line.append(chessBoardPanel.saveDataByGame(i, j) + " ");
                    }
                    line.setLength(line.length() - 1);
                    out.write(String.valueOf(line) + "\r\n");

                }
                String turn = String.format("#%s's turn", currentPlayer);
                out.write(turn);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean canClick(int row, int col) {
        if (mode == 0||mode==2||mode==3||mode==4) {
            setOppositePlayer();
            if (chessBoardPanel.getChessGrids()[row][col].getChessPiece() == ChessPiece.PINK) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (chessBoardPanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.PINK) {
                            chessBoardPanel.setChessGrids(i, j, null);
//                            System.out.println("clean pink");
                        }
                    }
                }
                return true;
            }
            return false;
        } else if (mode == 1) {
            setOppositePlayer();
            if (chessBoardPanel.getChessGrids()[row][col].getChessPiece() == null) {
                return true;
            }
        }
        return false;
    }

    public void cleanPinkChess() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoardPanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.PINK) {
                    chessBoardPanel.setChessGrids(i, j, null);
                }
            }
        }
    }

    public void setEndGame() {
        String string = "";
        System.out.println("endgame" + endgame);
        if (mode!=4) {
            if (blackScore > whiteScore) {
                string = "blackPlayer win";
            }
            if (whiteScore > blackScore) {
                string = "whitePlayer win";
            }
            if (blackScore == whiteScore) {
                string = "平局";
            }
        }
        if (mode==4){
            if (blackScore < whiteScore) {
                string = "blackPlayer win";
            }
            if (whiteScore < blackScore) {
                string = "whitePlayer win";
            }
            if (blackScore == whiteScore) {
                string = "平局";
            }
        }
        endgame = 2;
        rightPanel.setInformLabelText("End");
        String str = String.format("white \t %d,black \t %d\n%s", whiteScore, blackScore, string);
        JOptionPane.showMessageDialog(null, str, "Game is over", JOptionPane.PLAIN_MESSAGE);
    }

    public Save getSave() {
        return save;
    }

    public boolean isTxt(String filePath){
        System.out.println("in isTxt");
        String txt = filePath.substring(filePath.length() - 3);
        System.out.println(txt);
        if (txt.equals("txt")){
            System.out.println("equals txt" );
            return true;
        }else {
            return false;
        }
    }
}