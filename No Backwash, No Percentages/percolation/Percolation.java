package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	WeightedQuickUnionUF union;
	int N;
	byte grid[];//-1 filled, -2 dummy, >0 checked for fullness but not full
	boolean percolates;
	
	public Percolation(int N){
		union=new WeightedQuickUnionUF(N*N+2);		//create the union find guy with 2 extra nodes for top and bottom
		grid=new byte[N*N];						//create my own array (no need for top and bottom dummy nodes)
		this.N=N;									//save N's size for dimensional conversions
	}
	public void open(int i, int j){//open a node and check for open neighboring nodes to unite with. (changed 9-17 dw)
		boundTest(i,j);											
		if(isOpen(i,j))return;								//if the node is already open there's nothing to do here										
		if(i==0){											//if the node is in the top row
			union.union(get1D(i,j),N*N);//top dummy			//connect it to the top dummy node for percolation testing
			grid[get1D(i,j)]=1;								//this is a dummy variable, fill will set it to -1 (full)
			fill(i,j);										//fill() sets this node and any connected to it to full
		}
		else{												//if the node is not in the top row
			grid[get1D(i,j)]=-2;							//this is a dummy variable, cF will set it to either 1 (checked) or -1 (full)
			cF(i,j);										//cF() checks to see if it's touching any full nodes
			if(isOpen(i-1,j))								//connect this node to its neighbor if its open
				union.union(get1D(i,j),get1D(i-1,j));		//  for percolation testing
		}
		if(i==N-1)											//If the node is in the bottom row unite it with
			union.union(get1D(i,j),N*N+1);//bottom dummy	//the bottom dummy node
		else if(isOpen(i+1,j))								//If the node is not in the bottom row check the row below
			union.union(get1D(i,j),get1D(i+1,j));			//it to see if it is open, if so unite them
		if(j>0&&isOpen(i,j-1))								//then check left if not on the edge
			union.union(get1D(i,j),get1D(i,j-1));
		if(j<N-1&&isOpen(i,j+1))							//then check right if not on the other edge
			union.union(get1D(i,j),get1D(i,j+1));
	}
	public boolean isOpen(int i, int j){// check if a node is open or closed made 9-6 dw
		boundTest(i,j);
		if(grid[get1D(i,j)]==0)return false;
		else return true;					
	}
	public boolean isFull(int i, int j){
		boundTest(i,j);
		if(grid[get1D(i,j)]==-1)return true; 	//I chose to store a "full" value because union find
		return false;							//runs 2 while loops for each node's union.connected() call
	}											
	public boolean percolates(){//made 9-6 dw
		if(percolates) return true;
		else if(union.connected(N*N, N*N+1)){	//if top dummy node is connected to bottom dummy node, it percolates
			percolates=true;
			return true;
		}
		return false;
	}
	
	
	
	
	private int get1D(int i, int j){  return i*N+j;  } //just here for readability
	private void boundTest(int i,int j){// make sure i and j aren't larger than the array made 9-7 dw
		if(i+j<0||i*N+j>N*N)throw 
			new IndexOutOfBoundsException(
				"col index " + j + " must be between 0 and " + (N-1)+
				" and row index " + i + " must be between 0 and " + (N-1));
	}
	private boolean cF(int i, int j){				//check if a new node connects to any full nodes (made 9-17 dw)
		if(grid[get1D(i,j)]>=0)return false;		//this is to keep recursive checks from checking closed or already checked nodes
		if(grid[get1D(i,j)]==-1)return true;		//if the function finds a full node, YAY
		grid[get1D(i,j)]=1;							//set this node to "checked"
		if((i>0&&cF(i-1,j))||(j<N-1&&cF(i,j+1))||(i<N-1&&cF(i+1,j))||(j>0&&cF(i,j-1))){
			fill(i,j);								//then recursively check surrounding nodes
			return true;							//and if any are full fill all checked nodes in this "cloud"
		}
		return false;
	}
	private void fill(int i, int j){		//function fills any cells which equal 1 (the checked value) (made 9-17 dw)
		if(grid[get1D(i,j)]>0){				//if the function is open and not full
			grid[get1D(i,j)]=-1;			//set it to full
			if(i>0)fill(i-1,j);				//and recursively do the same to any connected nodes
			if(j<N-1)fill(i,j+1);
			if(i<N-1)fill(i+1,j);
			if(j>0)fill(i,j-1);
		}
	}
}