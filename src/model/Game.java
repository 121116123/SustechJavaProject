package model;

import components.ChessGridComponent;

import java.util.Stack;

public class Game {
    private Stack<ChessGridComponent[][]> stepStack = new Stack<>();
    private Stack<ChessPiece> playerStack = new Stack<>();
    private Stack<Integer> modeStack =new Stack<>();

    public Game() {
//        System.out.println("consturator game");
        stepStack = new Stack<>();
        playerStack = new Stack<>();
        modeStack =new Stack<>();
    }


    public void setPlayerStack(Stack<ChessPiece> playerStack) {
        this.playerStack = playerStack;
    }

    public void setModeStack(Stack<Integer> modeStack) {
        this.modeStack = modeStack;
    }

    public void setStepStack(Stack<ChessGridComponent[][]> stepStack) {
        this.stepStack = stepStack;
    }

    public Stack<ChessPiece> getPlayerStack() {
        return playerStack;
    }

    public Stack<ChessGridComponent[][]> getStepStack() {
        return stepStack;
    }

    public Stack<Integer> getModeStack() {
        return modeStack;
    }

    public void addStep(ChessGridComponent[][] step) {
        ChessGridComponent[][]board=new ChessGridComponent[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j]=new ChessGridComponent(i,j);
                board[i][j].setChessPiece(step[i][j].getChessPiece());
            }
        }
        this.stepStack.push(board);
//        System.out.println("push step");
    }

    public void addPlayer(ChessPiece player) {
        this.playerStack.push(player);
//        System.out.println("push "+player);
    }

    public void addMode(int mode){
        this.modeStack.push(mode);
//        System.out.println("push"+mode);
    }

    public ChessPiece getPlayer() {
        ChessPiece player=playerStack.pop();
//        System.out.println("pop player"+player);
        return player;
    }

    public ChessGridComponent[][] getStep() {
//        System.out.println("pop step");
        return stepStack.pop();
    }

    public int getMode(){
        int mode=modeStack.pop();
//        System.out.println("pop mode"+mode);
        return mode;
    }

    public ChessPiece readPlayer(){
        ChessPiece player=playerStack.peek();
//        System.out.println("read"+player);
        return player;
    }

    public ChessGridComponent[][]readStep(){
        System.out.println("read step");
        return stepStack.peek();
    }

    public int readMode(){
        int mode=modeStack.peek();
        System.out.println("read"+mode);
        return mode;
    }
}
