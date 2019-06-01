import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to the Togyz Kumalak World!");
        System.out.println("Enter the mode: (h - human play, m - random machine, r - against random AI) ");
        Scanner in = new Scanner(System.in);
        String ch = in.nextLine();

        if (ch.equals("h")) {
        	
            ToguzBoard tBoard = new ToguzBoard();
            tBoard.printPos();

            Scanner in2 = new Scanner(System.in);
            boolean cancelled = false;

            while ((!cancelled) && (!tBoard.isGameFinished())) {
                System.out.println("Enter your move (1-9, 0 - exit): ");
                int num = in2.nextInt();

                if (num == 0)
                    cancelled = true;
                else if ((num > 0) && (num < 10)) {
                    tBoard.makeMove(num);
                    tBoard.printNotation(true, false);
                    tBoard.printPos();
                }
            }

            System.out.println("Game over");
            System.out.println(tBoard.getScore());

        } else if (ch.equals("m")) {
            System.out.println("Enter numIterations: ");
            Scanner in2 = new Scanner(System.in);
            int numIterations = in2.nextInt();

            int win = 0;
            int draw = 0;
            int lose = 0;
            double stime = System.currentTimeMillis();

            for (int i = 0; i < numIterations; i++) {
                ToguzBoard tBoard = new ToguzBoard();
                int gRes = tBoard.playRandomGame();

                if (gRes == 1) win++;
                else if (gRes == 0) draw++;
                else if (gRes == -1) lose++;

            }

            double TotalTime = System.currentTimeMillis() - stime;
            System.out.println("Win: " + win + ", draw: " + draw + ", lose: " + lose);
            System.out.println("Time: " + TotalTime + " ms");

        } 

        else if (ch.equals("r")) {
            System.out.println("AI color (0 - white, 1 - black): ");
            Scanner in2 = new Scanner(System.in);
            int compColor = in2.nextInt();

            boolean cancelled = false;
            int currentColor;

            ToguzBoard tBoard = new ToguzBoard();
            tBoard.printPos();

            while ((!cancelled) && (!tBoard.isGameFinished())) {
                currentColor = tBoard.getCurrentColor();
                if (((currentColor == 0) && (compColor == 1)) |
                        ((currentColor == 1) && (compColor == 0))) {
                    System.out.println("Enter your move (1-9, 0 - exit): ");
                    int num = in2.nextInt();

                    if (num == 0)
                        cancelled = true;
                    else if ((num > 0) && (num < 10)) {
                        tBoard.makeMove(num);
                        tBoard.printNotation(true, false);
                        tBoard.printPos();
                    }
                } else {
                    tBoard.makeRandomMove();
                    tBoard.printNotation(true, false);
                    tBoard.printPos();
                }
            }

            System.out.println("Game over");
            System.out.println(tBoard.getScore());
        } 

    }
}
