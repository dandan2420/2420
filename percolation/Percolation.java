package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdIn;

public class Percolation {
	WeightedQuickUnionUF union;
	int N;
	boolean grid[];
	
	public Percolation(int N){
		union=new WeightedQuickUnionUF(N*N+2);		//create the union find guy with 2 extra nodes for top and bottom
		grid=new boolean[N*N];						//create my own array (no need for top and bottom dummy nodes)
		this.N=N;									//save N's size for dimensional conversions
	}
	public void open(int i, int j){//open a node and check for open neighboring nodes to unite with. made 9-6 dw
		boundTest(i,j);											//make sure the inputs are valid
		if(!isOpen(i,j)){										//make sure the node isn't open already
			grid[get1D(i,j)]=true;								//mark the node in my array as open
			if(i==0)											//If it is in the top row unite it with
				union.union(get1D(i,j),N*N);//top dummy			//the top dummy node.
			else if(isOpen(i-1,j))								//If it is not in the top row check the node above 
				union.union(get1D(i,j),get1D(i-1,j));			//it to see if it is open, if so, unite them.
			if(i==N-1)											//If the node is in the bottom row unite it with
				union.union(get1D(i,j),N*N+1);//bottom dummy	//the bottom dummy node
			else if(isOpen(i+1,j))								//If the node is not in the bottom row check the row below
				union.union(get1D(i,j),get1D(i+1,j));			//it to see if it is open, if so unite them
			if(j>0&&isOpen(i,j-1))								//then check left if not on the edge
				union.union(get1D(i,j),get1D(i,j-1));
			if(j<N-1&&isOpen(i,j+1))							//then check right if not on the 
				union.union(get1D(i,j),get1D(i,j+1));
		}
	}
	public boolean isOpen(int i, int j){// check if a node is open made 9-6 dw
		boundTest(i,j);
		return grid[get1D(i,j)];		//since its a boolean it already holds the appropriate return value
	}
	public boolean isFull(int i, int j){
		// is site (row i, column j) wet?
		return union.connected(get1D(i,j), N*N);					//if connected to the top dummy node, it is filled
		//TODO perhaps this is where we get rid of backwash,
		//might need something other than a boolean array, I have no idea yet 9-7 dw
	}
	//TODO get rid of backwash efficiently
	public boolean percolates(){//made 9-6 dw
		return union.connected(N*N, N*N+1);							//if top dummy node is connected to bottom dummy node, it percolates
	}
	
	
	private int get1D(int i, int j){// simple function to turn 2 dimensional coordinates into 1
		return i*N+j;				//it's really just here for readability
	}
	private int get2Di(int in){//simple function to turn 1 dimensional coordinates into the first of two
		return in/N;		   //it's really just here for readability
	}
	private int get2Dj(int in){//simple function to turn 1 dimensional coordinates into the second of two
		return in %N;		   //it's really just here for readability
	}
	private void boundTest(int i,int j){// make sure i and j aren't larger than the array made 9-7 dw
		if(i+j<0||i*N+j>N*N)throw 
			new IndexOutOfBoundsException(
				"col index " + j + " must be between 0 and " + (N-1)+
				" and row index " + i + " must be between 0 and " + (N-1));
	}

}
