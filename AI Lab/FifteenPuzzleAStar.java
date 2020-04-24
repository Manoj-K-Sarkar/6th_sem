import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class FifteenPuzzleAStar
{	
	final private int SIZE = 4;
	private int iPuzzle[][];
	private int fPuzzle[][];
	private int xpos, ypos;
    private int count;
    Set<String> set;
    View view;

	private void init()
	{
		java.util.List<Integer> list = new ArrayList<Integer>();

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
    
    class View extends JFrame
    {
        JButton button[];

        void setButton(int mat[][])
        {
            int count = 0;
            for(int i = 0 ; i < SIZE; i ++) {
                for (int j = 0; j < SIZE; j++) {
                    button[count++].setText(String.valueOf(mat[i][j]));
                }
            }
        }

        View(int mat[][]){
            Container container =getContentPane();
            container.setLayout(new GridLayout(SIZE,SIZE));
            button = new JButton[SIZE*SIZE];
            for(int i=0; i<SIZE*SIZE; i++){
                button[i]=new JButton();
                container.add(button[i]);
            }
            this.setButton(mat);
            setVisible(true);
            setTitle("15Puzzle Using A*");
            setSize(500,500);
        }
    }

	FifteenPuzzleAStar()
	{
		this.iPuzzle = new int[SIZE][SIZE];
        this.fPuzzle = new int[SIZE][SIZE];
        this.set = new HashSet<>();
        this.init();
        view = new View(this.iPuzzle);
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

    int[] getCurrentPosition(int mat[][],int value)
    {
        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[i].length;j++)
            {
                if(mat[i][j]==value)
                    return new int[]{i,j};
            }
            
        }
        return new int[]{-1,-1};
    }
    
    private int calculateDistance(int mat[][])
    {
        int dist=0;
        for(int i=0; i < SIZE*SIZE; i++){
            int pos[]=getCurrentPosition(mat,i);
            int ans_pos[]=getCurrentPosition(fPuzzle,i);
            dist+=Math.abs(Math.abs(pos[0]-ans_pos[0])+Math.abs(pos[1]-ans_pos[1]));
        }

        System.out.println("Dist : " + dist);
        return dist;
    }    

    public String convertToString(int mat[][]){
        String s="";
        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[i].length;j++)
            {
                s+=String.valueOf(mat[i][j])+",";
            }
        }
        return s;
    }
	
	private void selectMove()
	{
        int mat[][] ;
        int currmat[][] = null;
		int r, c;
		int dist = Integer.MAX_VALUE;

		if(this.ypos-1 >= 0) {
            mat = this.clone();
            r = this.ypos;
            c = this.xpos;
            mat[r][c] = mat[r-1][c];
            mat[r-1][c] = 0;

            int d=calculateDistance(mat);
            if(d<dist && !this.set.contains(convertToString(mat))) {
                currmat = mat;
                dist=d;
                System.out.println("UP");
            }
        }
        if(this.xpos+1 < SIZE) {
            mat = this.clone();
            r = this.ypos;
            c = this.xpos;
            mat[r][c] = mat[r][c+1];
            mat[r][c+1] = 0;
            
            int d=calculateDistance(mat);
            if(d<dist && !this.set.contains(convertToString(mat))) {
                currmat = mat;
                dist=d;
                System.out.println("Right");
            }
        }
        if(this.ypos+1 < SIZE) {
            mat = this.clone();
            r = this.ypos;
            c = this.xpos;
            mat[r][c] = mat[r+1][c];
            mat[r+1][c] = 0;
            
            int d=calculateDistance(mat);
            if(d<dist && !this.set.contains(convertToString(mat))) {
                currmat = mat;
                dist=d;
                System.out.println("Down");
            }
        }
        if(this.xpos-1 >= 0) {
            mat = this.clone();
            r = this.ypos;
            c = this.xpos;
            mat[r][c] = mat[r][c-1];
            mat[r][c-1] = 0;
            
            int d=calculateDistance(mat);
            if(d<dist && !this.set.contains(convertToString(mat))) {
                currmat = mat;
                dist=d;
                System.out.println("Left");
            }
        }

        try {
            Thread.sleep(1000);    
        } catch (Exception e) {
            //TODO: handle exception
        }
        
        this.iPuzzle = currmat;
        this.view.setButton(this.iPuzzle);
        this.set.add(convertToString(this.iPuzzle));
        int pos[] = this.getCurrentPosition(iPuzzle,0);
        this.ypos = pos[0];
        this.xpos = pos[1];
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
    
    public int[][] clone() 
    {
        int mat[][] = new int[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mat[i][j] = this.iPuzzle[i][j];
            }
        }
        return mat;
    }
	
	public void play()
	{
		boolean isComplete = false;
		this.disp();
		while(!isComplete) {

            
			// this.printOptions();
			// Scanner input = new Scanner(System.in);
			// char c = input.next().charAt(0);
			
			this.selectMove();
			this.count++;
				System.out.print("\033\143");
				
				this.disp();
				this.logToFile(true);
				
				if((isComplete = checkPattern())) { 
					this.logToFile(false);
                    JOptionPane.showMessageDialog(null,"Congratulation!! Pattern Matched");
                    System.exit(0);
				}
				if(this.count%10 == 0) {
					int res = JOptionPane.showConfirmDialog(null,"Want To Play More??","Continue",JOptionPane.YES_NO_OPTION);
					if(res == 1) System.exit(0);
				}
		}

	}


	public static void main (String args[])
	{
		
		FifteenPuzzleAStar game = new FifteenPuzzleAStar();
		game.play();
	}
}
