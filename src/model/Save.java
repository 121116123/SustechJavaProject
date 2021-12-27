package model;

import components.ChessGridComponent;

import java.util.ArrayList;
import java.util.List;

public class Save {
    private List<ChessPiece> players;
    private List<ChessGridComponent[][]> chessGrids;
    private int mode;
    private List<int[]> steps;


    public Save() {
        players = new ArrayList<>();
        chessGrids = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void addStep(int x, int y){
        int[] step = new int[2];
        step[0] = x; step[1] = y;
        steps.add(step);
    }

    public void addBoard(ChessGridComponent[][] step) {
        ChessGridComponent[][] board = new ChessGridComponent[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new ChessGridComponent(i, j);
                board[i][j].setChessPiece(step[i][j].getChessPiece());
            }
        }
        chessGrids.add(board);
    }

    public void addPlayer(ChessPiece player) {
        players.add(player);
    }

    public int getMode(){
        return mode;
    }

    public List<ChessPiece> getPlayers() {
        return players;
    }

    public ChessPiece getPlayer(int i) {
        return players.get(i);
    }

    public List<ChessGridComponent[][]> getChessGrids() {
        return chessGrids;
    }

    public List<int[]> getSteps() {
        return steps;
    }

    public ChessGridComponent[][] getChessGridComponent(int i){
        return chessGrids.get(i);
    }

    public int[] getStep(int i){
        System.out.println(steps.get(i)[0] + "," + steps.get(i)[1]);//这里意思是看一下我有没有一步步执行
        return steps.get(i);
    }

}
