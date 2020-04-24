import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main
{
    final private int SIZE = 4;
    private JFrame jframe;
    private JButton iPuzzle[][];
    private JButton fPuzzle[][];
    private JButton start, reset;
    private int iCounter, fCounter ;

    private void init()
    {   
        Panel iPanel = new Panel();
        iPanel.setLayout(new GridLayout(SIZE,SIZE));
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                iPuzzle[i][j] = new JButton("0");
                iPuzzle[i][j].setPreferredSize(new Dimension(100, 80));
                iPanel.add(iPuzzle[i][j]);
            }
        }

        Panel fPanel = new Panel();
        fPanel.setLayout(new GridLayout(SIZE,SIZE));
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                fPuzzle[i][j] = new JButton("0");
                fPuzzle[i][j].setPreferredSize(new Dimension(100, 80));
                fPanel.add(fPuzzle[i][j]);
            }
        }

        Panel bPanel = new Panel();
        bPanel.setLayout(new FlowLayout());
        this.start.setPreferredSize(new Dimension(100, 40));
        this.start.setEnabled(false);
        this.reset.setPreferredSize(new Dimension(100, 40));
        this.reset.setEnabled(false);
        bPanel.add(this.start);
        bPanel.add(this.reset);


        Container container = jframe.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(iPanel, BorderLayout.NORTH);
        container.add(bPanel, BorderLayout.CENTER);
        container.add(fPanel, BorderLayout.SOUTH);
    
        jframe.setSize(400,800);
        jframe.setTitle("15 Puzzle AStar");
        jframe.setVisible(true);
    } 

    private void action()
    {
        int imat[][] = new int[SIZE][SIZE];
        int fmat[][] = new int[SIZE][SIZE];

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                this.iPuzzle[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        if( ((JButton)e.getSource()).getText().equals("0") && iCounter<16)
                        {
                            ((JButton)e.getSource()).setText(String.valueOf(iCounter++));
                        }
                        if(iCounter>0 || fCounter>0 )
                            reset.setEnabled(true);
                        if(iCounter>15 && fCounter>15)
                            start.setEnabled(true);
                    }                    
                });
            }
        }

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                this.fPuzzle[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        if( ((JButton)e.getSource()).getText().equals("0") && fCounter<16)
                        {
                            ((JButton)e.getSource()).setText(String.valueOf(fCounter++));
                        }
                        if(iCounter>0 || fCounter>0 )
                            reset.setEnabled(true);
                        if(iCounter>15 && fCounter>15)
                            start.setEnabled(true);
                    }                    
                });
            }
        }

        this.start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
                for(int i = 0; i < SIZE; i++) {
                    for(int j = 0; j < SIZE; j++) {
                        imat[i][j] = Integer.parseInt(iPuzzle[i][j].getText());
                        fmat[i][j] = Integer.parseInt(fPuzzle[i][j].getText());
                    }
                } 
				
				FifteenPuzzleAStar game = new FifteenPuzzleAStar(imat,fmat);
                game.play();
                jframe.setVisible(false);
			}
        });

        this.reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
                for(int i = 0; i < SIZE; i++) {
                    for(int j = 0; j < SIZE; j++) {
                        iPuzzle[i][j].setText("0");
                        fPuzzle[i][j].setText("0");
                    }
                } 
                start.setEnabled(false);
                iCounter = fCounter = 0;
			}
        });
    }

    Main() 
    {
        this.jframe = new JFrame();
        this.iPuzzle = new JButton[SIZE][SIZE];
        this.fPuzzle = new JButton[SIZE][SIZE];
        this.start = new JButton("Start");
        this.reset = new JButton("Reset");
        this.iCounter = this.fCounter = 0;
        this.init();
        this.action();
    }

    public static void main(String[] args) {
        new Main();
     }
}

class FifteenPuzzleAStar
{	
	final private int SIZE = 4;
	private int iPuzzle[][];
	private int fPuzzle[][];
	private int cpos, rpos;
    private int count;
    Set<String> set;

	FifteenPuzzleAStar(int[][] imat, int[][] fmat)
	{
		this.iPuzzle = new int[SIZE][SIZE];
        this.fPuzzle = new int[SIZE][SIZE];
        this.set = new HashSet<>();
        this.count = 0;


        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j  < SIZE; j++) {
                this.iPuzzle[i][j] = imat[i][j];
                if(imat[i][j] == 0) {
                    this.rpos = i;
                    this.cpos = j;
                }
                this.fPuzzle[i][j] = fmat[i][j];
            }
        }
	}

	private void printOptions() 
	{
		if(cpos != 0)
			System.out.println("Press A for left");
		if(cpos != SIZE-1)
			System.out.println("Press D for right");
		if(rpos != 0)
                        System.out.println("Press W for up");
                if(rpos != SIZE-1)
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

		if(this.rpos-1 >= 0) {
            mat = this.clone();
            r = this.rpos;
            c = this.cpos;
            mat[r][c] = mat[r-1][c];
            mat[r-1][c] = 0;

            int d=calculateDistance(mat);
            if(d<dist && !this.set.contains(convertToString(mat))) {
                currmat = mat;
                dist=d;
                System.out.println("UP");
            }
        }
        if(this.cpos+1 < SIZE) {
            mat = this.clone();
            r = this.rpos;
            c = this.cpos;
            mat[r][c] = mat[r][c+1];
            mat[r][c+1] = 0;
            
            int d=calculateDistance(mat);
            if(d<dist && !this.set.contains(convertToString(mat))) {
                currmat = mat;
                dist=d;
                System.out.println("Right");
            }
        }
        if(this.rpos+1 < SIZE) {
            mat = this.clone();
            r = this.rpos;
            c = this.cpos;
            mat[r][c] = mat[r+1][c];
            mat[r+1][c] = 0;
            
            int d=calculateDistance(mat);
            if(d<dist && !this.set.contains(convertToString(mat))) {
                currmat = mat;
                dist=d;
                System.out.println("Down");
            }
        }
        if(this.cpos-1 >= 0) {
            mat = this.clone();
            r = this.rpos;
            c = this.cpos;
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
            Thread.sleep(100);    
        } catch (Exception e) {
            //TODO: handle exception
        }
        
        this.iPuzzle = currmat;
        this.set.add(convertToString(this.iPuzzle));
        int pos[] = this.getCurrentPosition(iPuzzle,0);
        this.rpos = pos[0];
        this.cpos = pos[1];
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
			
			this.selectMove();
			this.count++;
            //System.out.print("\033\143");
            
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
}
