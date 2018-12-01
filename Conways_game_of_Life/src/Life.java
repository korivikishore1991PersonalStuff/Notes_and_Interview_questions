import java.util.Arrays;
import java.util.Random;


public class Life {

    // 1
    private static int ROW = 4;
    private static int COL = 4;
    public static void main(String[] args) {
        if(args.length>2){
            ROW = Integer.parseInt(args[1]);
        }
        if(args.length>2){
            COL = Integer.parseInt(args[2]);
        }
        int number_of_iterations = 2;
        if(args.length>1){
            number_of_iterations = Integer.parseInt(args[0]);
        }
        Random randGen = new Random(1);
        boolean[][] currBoard = new boolean[ROW+2][COL+2];
        // 2
        for (int k = 1; k < currBoard.length - 1; k++) {
            for (int l = 1; l < currBoard.length - 1; l++) {
                int random = randGen.nextInt(10);
                if (random<5) {
                    currBoard[k][l] = true;
                }
                if (random >5) {
                    currBoard[k][l] = false;
                }
                //currBoard[k][l] = true;
            }
        }
        runMain(currBoard, number_of_iterations);
    }
    private static void runMain (boolean[][] currBoard, int number_of_iterations) {
        boolean[][] nextBoard = new boolean[ROW+2][COL+2];
        nextBoard = copyBoard(currBoard, nextBoard);
        printBoard(nextBoard);
        for (int g = 0; g <= number_of_iterations; g++) {
            boolean[][] comapreBoard = new boolean[ROW+2][COL+2];
            comapreBoard = copyBoard(currBoard, comapreBoard);
            nextBoard = runStep(currBoard, nextBoard);
            printBoard(nextBoard);
            if (compare_Convergence(comapreBoard, nextBoard)){
                printBoard(runStep(currBoard, nextBoard));
                System.out.println("equality attained");
                break;
            }
            currBoard = copyBoard(nextBoard, currBoard);
        }
    }

    private static boolean[][] runStep(boolean[][] currBoard, boolean[][] nextBoard){
        for (int f = 1; f < currBoard.length - 1; f++){
            for (int p = 1; p < currBoard.length - 1; p++) {
                if(countLiveNeighbors(f, p, currBoard) == 3) {
                    System.out.println("count of nextBoard[" + f + "," + p + "] is " + countLiveNeighbors(f, p, currBoard));
                    nextBoard[f][p] = true;
                }
                else if(countLiveNeighbors(f, p, currBoard) < 2) {
                    System.out.println("count of nextBoard[" + f + "," + p + "] is " + countLiveNeighbors(f, p, currBoard));
                    nextBoard[f][p] = false;
                }
                else if(countLiveNeighbors(f, p, currBoard) > 3) {
                    System.out.println("count of nextBoard[" + f + "," + p + "] is " + countLiveNeighbors(f, p, currBoard));
                    nextBoard[f][p] = false;
                }
                else{
                    System.out.println("count of nextBoard[" + f + "," + p + "] is " + countLiveNeighbors(f, p, currBoard));
                }
            }
        }
        return nextBoard;
    }

    private static void printBoard(boolean[][] nextBoard) {
        for (int a = 0; a <= nextBoard.length-1; a++) {
            for (int b = 0; b <= nextBoard.length-1; b++) {
                if (nextBoard[a][b]){
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private static boolean[][] copyBoard(boolean[][] copying, boolean[][] copy) {
        for (int y = 0; y <= copying.length - 1; y++) {
            for (int z = 0; z <= copying.length - 1; z++) {
                copy[y][z] = copying[y][z];
            }
        }
        return copy;
    }

    private static boolean compare_Convergence(boolean[][] nextBoard, boolean[][] currBoard){
        boolean compare = false;
        for (int y = 0; y <= nextBoard.length - 1; y++) {
            for (int z = 0; z <= nextBoard.length - 1; z++) {
                if (nextBoard[y][z] != currBoard[y][z]){
                    compare = false;
                    return compare;
                }
                else{
                    compare = true;
                }
            }
        }
        System.out.println(compare);
        return compare;
    }

    private static int countLiveNeighbors(int row, int col, boolean[][] board){
        int count = 0;
        if(board[row-1][col]){
            count++;
        }
        if(board[row+1][col]){
            count++;
        }
        if(board[row][col-1]){
            count++;
        }
        if(board[row][col+1]){
            count++;
        }
        if(board[row-1][col-1]){
            count++;
        }
        if(board[row-1][col+1]){
            count++;
        }
        if(board[row+1][col-1]){
            count++;
        }
        if(board[row+1][col+1]){
            count++;
        }
        return count;
    }
}