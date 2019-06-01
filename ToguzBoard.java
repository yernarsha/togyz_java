import java.util.Arrays;
import java.util.ArrayList;

public class ToguzBoard {

    private int[] toguzFields = new int[23];
    private boolean finished = false;
    private int gameResult = -2;
    private ArrayList<String> gameMoves = new ArrayList<>();

    public ToguzBoard() {
        for (int i = 0; i < 23; i++) {
            if (i < 18)
                toguzFields[i] = 9;
            else
                toguzFields[i] = 0;
        }
    }

    public void printPos() {
        String S = YernarString.padLeft(Integer.toString(toguzFields[19]), 3, '\u00A0') + " ";

        for (int i = 17; i > 8; i--)
            if (toguzFields[i] == 255)
                S = S + "  X";
            else S = S + " " + YernarString.padLeft(Integer.toString(toguzFields[i]), 2, '\u00A0');
        System.out.println(S);

        S = "    ";
        for (int i = 0; i < 9; i++)
            if (toguzFields[i] == 255)
                S = S + "  X";
            else S = S + " " + YernarString.padLeft(Integer.toString(toguzFields[i]), 2, '\u00A0');
        S = S + "    " + toguzFields[18];
        System.out.println(S);
    }

    public void makeMove(int num) {
        int sow;
        int color = toguzFields[22];
        boolean capturedTuzdyk = false;

        int numotau = num + (9 * color) - 1;
        int numkum = toguzFields[numotau];

        if ((numkum == 0) || (numkum == 255)) return;

        if (numkum == 1) {
            toguzFields[numotau] = 0;
            sow = 1;
        } else {
            toguzFields[numotau] = 1;
            sow = numkum - 1;
        }

        for (int i = 0; i < sow; i++) {
            numotau++;
            if (numotau > 17) numotau = 0;
            if (toguzFields[numotau] == 255) {
                if (numotau > 8) toguzFields[18]++;
                else toguzFields[19]++;
            } else toguzFields[numotau]++;
        }

        if (toguzFields[numotau] == 3) {
            if ((color == 0) && (toguzFields[20] == 0) && (numotau > 8) &&
                    (numotau < 17) && (toguzFields[21] != numotau - 8)) {
                toguzFields[18] += 3;
                toguzFields[numotau] = 255;
                toguzFields[20] = numotau - 8;
                capturedTuzdyk = true;
            } else if ((color == 1) && (toguzFields[21] == 0) && (numotau < 8)
                    && (toguzFields[20] != numotau + 1)) {
                toguzFields[19] += 3;
                toguzFields[numotau] = 255;
                toguzFields[21] = numotau + 1;
                capturedTuzdyk = true;
            }
        }

        if (toguzFields[numotau] % 2 == 0) {
            if ((color == 0) && (numotau > 8)) {
                toguzFields[18] += toguzFields[numotau];
                toguzFields[numotau] = 0;
            } else if ((color == 1) && (numotau < 9)) {
                toguzFields[19] += toguzFields[numotau];
                toguzFields[numotau] = 0;
            }
        }

        int finished_otau;

        if (numotau > 8)
            finished_otau = numotau - 9 + 1;
        else
            finished_otau = numotau + 1;
        //       System.out.println("Your move is: " + num + finished_otau);

        String moveStr = Integer.toString(num) + Integer.toString(finished_otau);
        if (capturedTuzdyk)
            moveStr += "x";
        gameMoves.add(moveStr);

        toguzFields[22] = (toguzFields[22] == 0 ? 1 : 0);
        checkPos();
    }

    public void makeRandomMove() {

        int color = toguzFields[22];
        int numMove;
        ArrayList<Integer> numMoves = new ArrayList<>();

        int startRange = 0 + (color * 9);
        int finishRange = 8 + (color * 9);

        for (int i = startRange; i <= finishRange; i++) {
            if ((toguzFields[i] > 0) && (toguzFields[i] < 255))
                numMoves.add(i);
        }

        if (numMoves.size() == 0) {
            System.out.println("No moves (rand)!");
            System.out.println(Arrays.toString(toguzFields));
            finished = true;
            return;
        } else if (numMoves.size() == 1)
            numMove = numMoves.get(0);
        else {
            int numChoice = (int) (Math.random() * numMoves.size());
            numMove = numMoves.get(numChoice);
        }

        if (numMove > 8)
            numMove = numMove - 9 + 1;
        else
            numMove = numMove + 1;

        makeMove(numMove);
    }

    public void checkPos() {
        int whiteKum = 0, blackKum;

        for (int i = 0; i < 9; i++)
            if (toguzFields[i] < 255)
                whiteKum += toguzFields[i];
        blackKum = 162 - whiteKum - toguzFields[18] - toguzFields[19];

        if ((toguzFields[22] == 0) && (whiteKum == 0))
            toguzFields[19] += blackKum;

        else if ((toguzFields[22] == 1) && (blackKum == 0))
            toguzFields[18] += whiteKum;

        if (toguzFields[18] >= 82) {
            finished = true;
            gameResult = 1;
        } else if (toguzFields[19] >= 82) {
            finished = true;
            gameResult = -1;
        } else if ((toguzFields[18] == 81) & (toguzFields[19] == 81)) {
            finished = true;
            gameResult = 0;
        }
    }

    public int playRandomGame() {

        while (!finished)
            makeRandomMove();

        return gameResult;
    }

    public String printNotation(boolean needPrint, boolean needHeader) {
        String s = "";
        if (needHeader)
            s += "[Count \"" + Integer.toString(toguzFields[18]) + "-" + Integer.toString(toguzFields[19]) + "\"]" + "\n\n";

        for (int i = 0; i < gameMoves.size(); i++) {
            if (i == 0) s += "1. " + gameMoves.get(i);
            else if (i % 2 == 0)
                s += Integer.toString(i / 2 + 1) + ". " + gameMoves.get(i);

            else s += " " + gameMoves.get(i) + "\n";
        }

        if (needPrint)
            System.out.println(s);

        return s;
    }
    
    public String getScore()
    {
    	return Integer.toString(toguzFields[18]) + " - " + Integer.toString(toguzFields[19]);
    }

    public int getCurrentColor()
    {
    	return toguzFields[22];
    }

    public boolean isGameFinished()
    {
    	return finished;
    }
}
