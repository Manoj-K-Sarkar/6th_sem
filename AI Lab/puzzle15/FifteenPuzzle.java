import java.util.*;
import java.io.*;
import javax.swing.*;

public class FifteenPuzzle
{	
	final private int SIZE = 4;
	private int iPuzzle[][];
	private int fPuzzle[][];
	private int xpos, ypos;
	private int count;

	private void init()
	{
		List<Integer> list = new ArrayList<Integer>();

		try {
			BufferedReader br = new BufferedReader(new FileReader("inputFile.txt"));
			String data = "";

			while((data = br.readLine()) != null){ 
				String nums[] = data.split("\\t");
				for(int i = 0; i < nums.length; i++){
					if(!nums[i].equals(""))
						list.add(Integer.parseInt(nums[i]));
				}
			}
		}catch(IOException e) {
			System.out.println("Error Occured While Opening the Input File!!");
		}

		int pos = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++){
				iPuzzle[i][j] = list.get(pos++);
				if(iPuzzle[i][j] == 0) {
					ypos = i;
					xpos = j;
				}
			}
		}
		
	        for (int i = 0; i < SIZE; i++) {
                        for (int j = 0; j < SIZE; j++){
                                fPuzzle[i][j] = list.get(pos++);
                        }
                } 
	}

	FifteenPuzzle()
	{
		this.iPuzzle = new int[SIZE][SIZE];
		this.fPuzzle = new int[SIZE][SIZE];
		this.init();
		this.count = 0;
	}

	private void printOptions() 
	{
		if(xpos != 0)
			System.out.println("Press A for left");
		if(xpos != SIZE-1)
			System.out.println("Press D for right");
		if(ypos != 0)
                        System.out.println("Press W for up");
                if(ypos != SIZE-1)
                        System.out.println("Press S for down");
	}


	private void disp()
	{
		for (int i = 0; i < SIZE; i++) {
                        for (int j = 0; j < SIZE; j++){
                                System.out.print("\t" + iPuzzle[i][j]);
                        }
			System.out.println();
                } 
	}
	
	private boolean placeMaker(char turn)
	{
		int r = this.ypos;
		int c = this.xpos;
		int flag = 0;

		if(turn == 'W' && (this.ypos-1) >= 0) {
			this.ypos--;
			flag = 1;
		}
                if(turn == 'A' && (this.xpos-1) >= 0) {
                        this.xpos--;
                        flag = 1;
                }
                if(turn == 'S' && (this.ypos+1) < SIZE) {
                        this.ypos++;
                        flag = 1;
                }
                if(turn == 'D' && (this.xpos+1) < SIZE) {
                        this.xpos++;
                        flag = 1;
                }
		this.iPuzzle[r][c] = this.iPuzzle[this.ypos][this.xpos];
		this.iPuzzle[this.ypos][this.xpos] = 0;
	
		if(flag==1) return true;
		return false;
	}
	
	public void logToFile(boolean isDisp)
	{
		try {
			BufferedWriter bw = 
				new BufferedWriter(new FileWriter("outputFile.txt",true));
		
			bw.write("Iteration : " + this.count + "\n");
			if(isDisp) {
		                for (int i = 0; i < SIZE; i++) {
                        		for (int j = 0; j < SIZE; j++){
                                		bw.write("\t" + iPuzzle[i][j]);
                        		}
                        		bw.write("\n");
                		}
			}
			else {
				bw.write("Congrats You Win!!\n\n");
			}
			
			bw.close();
		}catch(IOException e) {
			System.out.println("Error Occured When Writing on Output File.");
		}
	}

	private boolean checkPattern() 
	{
		for(int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if(iPuzzle[i][j] != fPuzzle[i][j])
					return false;
			}
		}
		return true;
	}
	
	public void play()
	{
		boolean isComplete = false;
		this.disp();
		while(!isComplete) {
			this.printOptions();
			Scanner input = new Scanner(System.in);
			char c = input.next().charAt(0);
			
			if(this.placeMaker(c)) {
				this.count++;
				System.out.print("\033\143");
				
				this.disp();
				this.logToFile(true);
				
				if((isComplete = checkPattern())) { 
					this.logToFile(false);
					JOptionPane.showMessageDialog(null,"Congratulation!! Pattern Matched");
				}
				if(this.count%10 == 0) {
					int res = JOptionPane.showConfirmDialog(null,"Want To Play More??","Continue",JOptionPane.YES_NO_OPTION);
					if(res == 1) System.exit(0);
				}
			}
			else 
				System.out.println("Invalid Turn!! Try Again.");
		}

	}


	public static void main (String args[])
	{
		
		FifteenPuzzle game = new FifteenPuzzle();
		game.play();
	}
}
