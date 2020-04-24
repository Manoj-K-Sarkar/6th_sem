import java.util.*;
import java.io.*;
import javax.swing.*;

public class FifteenPuzzleBFS
{	
	final private int SIZE = 4;
	private int iPuzzle[][];
	private int fPuzzle[][];
	private int xpos, ypos, prevx, prevy;
	private int count;
	private Queue<Character> q;

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

	FifteenPuzzleBFS()
	{
		this.iPuzzle = new int[SIZE][SIZE];
		this.fPuzzle = new int[SIZE][SIZE];
		this.q = new LinkedList<Character> ();
		this.prevx = this.prevy = -1;
		this.init();
		this.count = 0;
	}

	private void setQueue() 
	{
		if(xpos != 0){
			System.out.println("A for left");
			q.add('A');	
		}
		if(ypos != 0){
			System.out.println("W for up");
			q.add('W');
		}
		if(xpos != SIZE-1){
			System.out.println("D for right");
			q.add('D');
		}
		if(ypos != SIZE-1){
			System.out.println("S for down");
			q.add('S');
		}
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
			if(this.xpos == this.prevx && this.ypos-1 == this.prevy) 
				return false;
			this.ypos--;
			flag = 1;
		}
        if(turn == 'A' && (this.xpos-1) >= 0) {
			if(this.xpos-1 == this.prevx && this.ypos == this.prevy) 
				return false;
			this.xpos--;
            flag = 1;
        }
        if(turn == 'S' && (this.ypos+1) < SIZE) {
			if(this.xpos == this.prevx && this.ypos+1 == this.prevy) 
				return false;
			this.ypos++;
            flag = 1;
        }
        if(turn == 'D' && (this.xpos+1) < SIZE) {
			if(this.xpos+1 == this.prevx && this.ypos == this.prevy) 
				return false;
            this.xpos++;
            flag = 1;
		}
		

		this.prevy = r;
		this.prevx = c;

		this.iPuzzle[r][c] = this.iPuzzle[this.ypos][this.xpos];
		this.iPuzzle[this.ypos][this.xpos] = 0;
	
		if(flag==1) return true;
		return false;
	}
	
	public void logToFile(boolean isDisp, boolean isQPrint)
	{
		try {
			BufferedWriter bw = 
				new BufferedWriter(new FileWriter("outputFile.txt",true));
		
			
			if(isQPrint)
				bw.write("\n\nQueue is : " + this.q + "\n");
			else if(isDisp) {
				bw.write("Iteration : " + this.count + "\n");		
				bw.write("Computer Choose : " + this.q.peek() + "\n");
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

	private void flushQueue() {
		while(!this.q.isEmpty()) {
			q.remove();
		}
	}

	public void play()
	{
		boolean isComplete = false;
		this.disp();
		while(!isComplete) {
			this.setQueue();
		//	Scanner input = new Scanner(System.in);
		//	char c = input.next().charAt(0);
			
			
			this.logToFile(false,true);
			while(!this.placeMaker(q.peek())) {
				q.remove();
			}
			System.out.println("Computer Chooses : " + q.peek());
		//	if() {
				this.count++;
				System.out.print("\033\143");
				
				this.disp();
				this.logToFile(true,false);
				q.remove();

				if((isComplete = checkPattern())) { 
					this.logToFile(false,false);
					JOptionPane.showMessageDialog(null,"Congratulation!! Pattern Matched");
				}
				if(this.count%10 == 0) {
					int res = JOptionPane.showConfirmDialog(null,"Want To Play More??","Continue",JOptionPane.YES_NO_OPTION);
					if(res == 1) System.exit(0);
				}
		//	}
		//	else 
		//		System.out.println("Invalid Turn!! Try Again.");
			this.flushQueue();
		}

	}


	public static void main (String args[])
	{
		
		FifteenPuzzleBFS game = new FifteenPuzzleBFS();
		game.play();
	}
}
