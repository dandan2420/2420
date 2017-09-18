package percolation;
/******************************************************************************
 *  Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size n of the percolation system.
 *    - Creates an n-by-n grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row, col) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (0, 0) in the upper left-hand corner.
 *
 ******************************************************************************/

import java.awt.Font;
import java.util.Random;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationVisualizer {

    // delay in miliseconds (controls animation speed)
	public static final int SIZE =20;
    private static final int DELAY = 5;
    private static boolean perkelcheck=true;
    private static boolean wait=false;
    public static String output;

    // draw n-by-n percolation system
    public static void draw(Percolation percolation, int n) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05*n, 1.05*n);
        StdDraw.setYscale(-0.05*n, 1.05*n);   // leave a border to write text
        StdDraw.filledSquare(n/2.0, n/2.0, n/2.0);

        // draw n-by-n grid
	        for (int row = 0; row < n; row++) {
	            for (int col = 0; col < n; col++) {
	                if (percolation.isFull(row, col)) {
	                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
	                }
	                else if (percolation.isOpen(row, col)) {
	                    StdDraw.setPenColor(StdDraw.WHITE);
	                }
	                else {
	                    StdDraw.setPenColor(StdDraw.BLACK);
	                }
	                StdDraw.filledSquare(col + 0.5, n - row - 0.5, 0.45);
	            }
	        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
//        StdDraw.text(0.25*n, -0.025*n, percolation.numberOfOpenSites() + " open sites");
        StdDraw.text(0.5*n, -0.025*n, output);
//        if(wait){
//        	if (percolation.percolates())StdDraw.text(0.75*n, -0.025*n, "click mouse to restart");
//        	else StdDraw.text(0.75*n, -0.025*n, "click mouse to continue");
//        }
//        else if (percolation.percolates()){
//        	StdDraw.text(0.75*n, -0.025*n, "percolates");
//        	if(perkelcheck){
//        		perkelcheck=false;
//        		wait=true;
//        	}
//        }
//        else                          StdDraw.text(0.75*n, -0.025*n, "does not percolate");

    }

    private static void simulateFromFile(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        Percolation percolation = new Percolation(n);

        // turn on animation mode
//        StdDraw.enableDoubleBuffering();

        // repeatedly read in sites to open and draw resulting system
        draw(percolation, n);
        StdDraw.show(DELAY);
//        StdDraw.pause(DELAY);

        while (!in.isEmpty()) {
            int row = in.readInt();
            int col = in.readInt();
            percolation.open(row, col);
            draw(percolation, n);
            StdDraw.show(DELAY);
//            StdDraw.pause(DELAY);
        }
    }

    public static void main(String[] args) {
        //String filename = args[0];
        //simulateFromFile(filename);
        simulateFromScratch(SIZE);
    }
    
    

    
    private static void simulateFromScratch(int n) {
		boolean mouseOnce=true;
    	bigBoy: while (true){
    		output="Does not percolate";
    		perkelcheck=true;
    		wait=false;
	        Percolation percolation = new Percolation(n);
	
	
	        // turn on animation mode
	//        StdDraw.enableDoubleBuffering();
	
	        // repeatedly read in sites to open and draw resulting system
	        draw(percolation, n);
	        StdDraw.show(DELAY);
	//        StdDraw.pause(DELAY);
	        int []a =randomArray(n*n);
	        
	        for(int i=n*n-1;i>=0;){
	            draw(percolation, n);
	            StdDraw.show(DELAY);
	        	if(!wait){
	        		percolation.open(a[i]/n,a[i] % n);
		            if (percolation.percolates()){
	            		output="Percolates";
		            	if(perkelcheck){
		            		perkelcheck=false;
		            		wait=true;
		            	}
		            }
		            else output="Does not percolate";
		            i--;
	        	}
	        	else{
//	        		if(StdDraw.mouseX()==0&&StdDraw.mouseY()==0){
//	        			
//	        		}
	        		if (percolation.percolates())output="Percolates. Click to restart";
	        		else output="Click to continue";
	        		if((int)(n-StdDraw.mouseY())>=0&&(int)(n-StdDraw.mouseY())<SIZE)
	        			if((int)(StdDraw.mouseX())>=0&&(int)(StdDraw.mouseX())<SIZE){
	        				if(percolation.isFull((int)(n-StdDraw.mouseY()),(int)(StdDraw.mouseX())))
	        					output+="Node ("+(int)(n-StdDraw.mouseY())+","+(int)(StdDraw.mouseX())+") is Full";
	        				else if(percolation.isOpen((int)(n-StdDraw.mouseY()),(int)(StdDraw.mouseX())))
	        					output+="Node ("+(int)(n-StdDraw.mouseY())+","+(int)(StdDraw.mouseX())+") is Open";
	        				else
	        					output+="Node ("+(int)(n-StdDraw.mouseY())+","+(int)(StdDraw.mouseX())+") is Closed";
	        			}
	        		

	                //StdDraw.filledSquare(col + 0.5, n - row - 0.5, 0.45);
	        	}
	
	        	if(StdDraw.mousePressed()){
					if(mouseOnce){
						
	        			wait=!wait;
	        			mouseOnce=false;
	        			if(percolation.percolates())continue bigBoy;
	        		}
	        	}
	        	else{//mouse released
	        		mouseOnce=true;
	        	}
	        }
		}
        
        
//        while (!in.isEmpty()) {
//            int row = in.readInt();
//            int col = in.readInt();
//            percolation.open(row, col);
//            draw(percolation, n);
//            StdDraw.show(DELAY);
////            StdDraw.pause(DELAY);
//        }
    }
    
    public static int[] randomArray1(int N){//backwards array
    	int [] b=new int[N];
		for(int i=N-1; i>=0; --i)
			b[i]=i;
		return b;
    }
    
    public static int[] randomArray(int N){
		int [] b=new int[N];
		for(int i=0; i<N; ++i)
			b[i]=i;
		
		StdRandom.shuffle(b);
		return b;
//		Random rnd = new Random();
//	    for (int i = N - 1; i >= 0; i--){
//		    int index = rnd.nextInt(N);
//		    int t = b[index];
//		    b[index] = b[i];
//		    b[i] = t;
//	    }
//		return b;
	
	}
}