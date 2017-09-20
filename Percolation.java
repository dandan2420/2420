package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	WeightedQuickUnionUF union;
	byte grid[];//0 not open, -1 filled, 1 open
	boolean percolates;
	private int fillMe[], fillIndex=0,spot[],N;
	
	public Percolation(int N){
		fillMe=new int[N*N/2+1];						//an array to store nodes which need to be filled
		union=new WeightedQuickUnionUF(N*N+2);		//create the union find guy with 2 extra nodes for top and bottom
		grid=new byte[N*N];							//create my own array (no need for top and bottom dummy nodes)
		this.N=N;									//save N's size for dimensional conversions
	}
	public void open(int i, int j){//open a node and check for open neighboring nodes to unite with. (changed 9-17 dw)
		if(isOpen(i,j))return;								//if the node is already open there's nothing to do here
		checkIfFull(i,j);									//now check if the node should be "full".										
		
		if(i==0)											//Check to see if I can create unions with the top
			union.union(get1D(i,j),N*N);//top dummy			//  dummy node, or the neighbors of the current node
		else if(isOpen(i-1,j))								//  as well as the bottom node.
			union.union(get1D(i,j),get1D(i-1,j));			
		if(i==N-1)											
			union.union(get1D(i,j),N*N+1);//bottom dummy	
		else if(isOpen(i+1,j))								
			union.union(get1D(i,j),get1D(i+1,j));			
		if(j>0&&isOpen(i,j-1))								
			union.union(get1D(i,j),get1D(i,j-1));
		if(j<N-1&&isOpen(i,j+1))							
			union.union(get1D(i,j),get1D(i,j+1));						//then check if the top and bottom are connected, if so the system
		if(!percolates&&union.connected(N*N, N*N+1))percolates=true;	// percolates. I realize there are more efficient ways, but I'm 
	}																	// leaving this in because the rules want union find used.
	public boolean isOpen(int i, int j){// check if a node is open or closed made 9-6 dw
		if(grid[get1D(i,j)]==0)return false;
		else return true;					
	}
	public boolean isFull(int i, int j){// check if a node is full (wet)
		if(grid[get1D(i,j)]==-1)return true; 	
		return false;							
	}											
	public boolean percolates(){		// check if the system percolates
		return percolates;				// the actual check is performed in open() so that
	}									// all of my union find calls are together
	
	
	
	private int get1D(int i,int j){// make sure i and j aren't larger than the array (made 9-7 dw)
		if(i+j<0||i*N+j>N*N)throw  // and output the 1 dimensional variant of i and j
			new IndexOutOfBoundsException(
				"col index " + j + " must be between 0 and " + (N-1)+
				" and row index " + i + " must be between 0 and " + (N-1));
		return i*N+j;
	}
	private void checkIfFull(int i, int j){			//check if a newly opened node is connected to any full nodes (changed 9-18 dw)
		grid[get1D(i,j)]=1;							// set the node to 1 indicating an open node
		if(i>0&&isFull(i-1,j))fill(i,j);			// now check the surrounding nodes to see if it
		else if(j<N-1&&isFull(i,j+1))fill(i,j);		// should be full
		else if(i<N-1&&isFull(i+1,j))fill(i,j);
		else if(j>0&&isFull(i,j-1))fill(i,j);
		else if(i==0)fill(i,j);
	}
	private void fill(int i, int j){		//a fill method which uses a stack (made dw 9-20)
		fillAdd(get1D(i,j));										//add the first node to the stack
		while(somethingNeedsFilled()){								// run a loop to empty the stack
			grid[(spot=fillPopLast())[0]]=-1;						//  pull the last node, set it to full (-1), check its 
			if(spot[1]>0&&grid[spot[0]-N]>0)fillAdd(spot[0]-N);		//  neighbors, and list any open ones to be filled.
			//if(spot[1]==N-1) percolates=true; else				//   You can uncomment this line and remove union find
			if(spot[1]<N-1&&grid[spot[0]+N]>0)fillAdd(spot[0]+N);	//   entirely for significantly better performance.
			if(spot[2]<N-1&&grid[spot[0]+1]>0)fillAdd(spot[0]+1);	//    That line just checks to see if a bottom node is getting
			if(spot[2]>0&&grid[spot[0]-1]>0)fillAdd(spot[0]-1);		//    filled, if so, the system must be percolating.
		}
	}
	private boolean somethingNeedsFilled(){  return (fillIndex>0);  }		//just some array managing functions
	private void fillAdd(int input){  fillMe[fillIndex++]=input;  }
	private int[] fillPopLast(){														//this guy removes an item and outputs 
		return new int[]{fillMe[--fillIndex],fillMe[fillIndex]/N,fillMe[fillIndex]%N};	//it's whole value, Y value, and X value
	}
}