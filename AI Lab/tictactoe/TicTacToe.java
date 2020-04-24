import java.util.Scanner;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.swing.*;

class MySwing extends JFrame
{
    JTextField tf;

    MySwing(int p, boolean isDraw)
    {
        setSize(500, 500);
        if(!isDraw)
            tf = new JTextField ("player " +  (p+1) + " is the winner!");
        else
            tf = new JTextField ("!!Draw!!");
        getContentPane().add(tf);
        setVisible(true);
    }
}


public class TicTacToe
{
    final int SIZE = 3;

    private char board[][];
    private int count = 0;
    
    private void setData()
    {
	    for (int i = 0; i < SIZE; i++) {
		    for (int j = 0; j < SIZE; j++) 
			    board[i][j] = ' ';
	    }
    }

    TicTacToe()
    {
        board = new char[SIZE][SIZE];
        this.setData();
    }

    private void disp()
    {
        System.out.print("   |");
	    for (int i = 0; i < SIZE; i++) 
		    System.out.print(" " + (i+1) + " |");
    	System.out.println();
	    for (int i = 0; i <= SIZE; i++)
		    System.out.print("----");
    	System.out.println();

    	for (int i = 0; i < SIZE; i++) {
	    	System.out.print(" " + (i+1) + " |");
		    for (int j = 0; j < SIZE; j++)
		    	System.out.print(" " + board[i][j]+ " |");
		    System.out.println();
		    for (int j = 0; j <= SIZE; j++)
			    System.out.print("----");
		    System.out.println();
	    }
    	System.out.println("^");
	    System.out.println("|");
	    System.out.println("Y/X--->");
    	System.out.println();
    }

    private int getPosition(char c) {
	    boolean isbadinput = true;
	    int x = 0;
        Scanner input = new Scanner(System.in);
	    while (isbadinput) {
		    System.out.printf("Enter the %c Co-ordinate: ",c);
		    x = input.nextInt();
		    if (x <= 0 || x > SIZE) 
		    	System.out.printf(" Invalid %c Co-ordinate!\n",c);
		    else
		    	isbadinput = false;
	    }
	    return x-1;
    }

    boolean placeMarker(int x,int y) 
    {
	    if (board[y][x] != ' ')
		    return false;
	    return true;
    }

    boolean checkForVictory(int p) {
        char c;
        if(p == 0)  c = 'O';
        else    c = 'X';
	    for (int k = 0; k < SIZE; k++) {
	    	for (int j = 0; j < SIZE-2; j++) {
    			if (board[k][j] == c && board[k][j + 1] == c && board[k][j + 2] == c)
			    	return true;
		    }
		    for (int j = 0; j < SIZE; j++) {
	    		for (int i = 0; i < SIZE - 2; i++) {
    				if (board[i][j] == c && board[i + 1][j] == c && board[i + 2][j] == c)
				    	return true;
			    }		
		    }
		    for (int i = 0; i < SIZE - 2; i++) {
	    		for (int j = 0; j < SIZE-2; j++) {
    				if (board[i][j] == c && board[i + 1][j+1] == c && board[i + 2][j+2] == c)
				    	return true;
			    }
		    }
	    	for (int i = 2; i < SIZE ; i++) {
    			for (int j = 0; j < SIZE-2 ; j++) {
				    if (board[i][j] == c && board[i - 1][j + 1] == c && board[i - 2][j + 2] == c)
				    	return true;
	          		    }
		    }
	    }
	    return false;
    }

    private void drawMsg()
    {
        new MySwing(0, true);
        System.out.println(" !! This is a Tie !!");
        this.logToFile(0, 0, 0, false, false, true, false, false, false);
    }

    private void logToFile(int p, int x, int y, 
            boolean positionPrint, boolean invalidPrint,
            boolean winPrint, boolean drawPrint,
            boolean startPrint, boolean endPrint) 
    {   
        DateTimeFormatter myFormatObj =
	        DateTimeFormatter.ofPattern ("dd-MM-yyyy HH:mm:ss");

        LocalDateTime myDateObj = LocalDateTime.now ();
        String formattedDate = myDateObj.format (myFormatObj);
        
        try{
        BufferedWriter out =
	        new BufferedWriter (new FileWriter ("tictactoe.txt", true));

        if(positionPrint)
            out.write(formattedDate + " :: Player " + (p+1) + " turns: x = " + x + " y = " + y + "\n");

        if(invalidPrint)
            out.write("\t\t\t\tInvalid Position\n");

        if(winPrint)
            out.write(formattedDate + " :: Player " + (p+1) + " is the Winner!!\n");

        if(drawPrint)
            out.write(formattedDate + " :: !!DRAW!!\n");

        if(startPrint)
            out.write("\n" + formattedDate + "============NEW GAME============\n");

        if(endPrint)
            out.write(formattedDate + "============END GAME============\n");    

        out.close();
        }
        catch (IOException e)
    {
      
        System.out.println ("An error occurred while logging");
      
        e.printStackTrace ();
    
    }
    }

    public void play()
    {
        boolean isOver = false;
        this.disp();  
        int x, y;
        int i = 0;
        this.logToFile(0, 0, 0, false, false, false, false, true, false);
        while(!isOver) {
            System.out.println("PLayer " + (i+1) + ", Its Your Turn : ");
            x = this.getPosition('X');
            y = this.getPosition('Y');
            
            this.logToFile (i, x, y, true, false, false, false, false, false);

            if (this.placeMarker(x,y)) 
            {
                this.count++;
                if(i == 0)  this.board[y][x] = 'O';
                else    this.board[y][x] = 'X';
                System.out.print("\033\143");

                this.disp();
                if((isOver = checkForVictory(i)))
                {
                    System.out.println("Player " + (i+1) + " Is the Winner\n\t\tCONGRATULATION!!");
                    this.logToFile(i, 0, 0, false, false, true, false, false, false);
                    new MySwing(i, false);
                }
                if(++i == 2)
                    i = 0;
            }
            else{ 
                System.out.println("Its Already Occupoied\nTry Again");
                this.logToFile(0, 0, 0, false, true, false, false, false, false );
            }

            if(count == 9){
                isOver = true;
                this.drawMsg();
            }
        }
        this.logToFile(0, 0, 0, false, false, false, false, false, true);
    }

    public static void main (String args[])
    {
        int PlayAgain = 1;

        while (PlayAgain == 1)
        {
            TicTacToe game = new TicTacToe();
            game.play();
            
            Scanner input = new Scanner (System.in);

            System.out.print("Press '0' to Quit The Game & '1' For Continue: ");
            PlayAgain = input.nextInt();
        }

    }
}
