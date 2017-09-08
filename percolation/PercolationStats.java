package percolation;

import edu.princeton.cs.introcs.StdRandom;

//import java.util.Random;

public class PercolationStats {
	
	int[] thresholds;	//stores each runs loop count
	long total;			//stores all loop counts added together (for faster mean calculation)
	int N;				//stores N size for percentage calculations later
	
	public PercolationStats(int N, int T){// perform T independent experiments on an N­by­N grid made on 9-7 dw
		if(N<0)throw new IndexOutOfBoundsException("N is less than 0");
		if(T<0)throw new IndexOutOfBoundsException("T is less than 0");
		this.N=N;												//Save N for later calculations
		thresholds=new int[T];									//create an array for when each run reaches "percolation". 
		for(int i=0; i<T; ++i){									//I don't think there is any way to calculate that within the loop.
			Percolation p=new Percolation(N);					//
			int [] a=randomArray(N*N);							//generate a random array
			for(int j=0; j<N*N; ++j){							//run percolation T times on an N x N grid
				p.open(a[j]/N,a[j] % N);						
				if(p.percolates()){								
					thresholds[i]=j;							//when a run percolates store its iterator spot
					total+=j;									//and add it to the total iterator count (for calculating mean later)
					break;										//then start over
				}
			}
		}
	}
	
	private int[] randomArray(int N){							//for some reason I'm not allowed to use the 
		int [] b=new int[N];									//random in .util, so I'm using the shuffle
		for(int i=0; i<N; ++i)									//function provided in stdlib (even though it
			b[i]=i;												//uses .util. Oh well, it works
		
		StdRandom.shuffle(b);
		return b;
	}
	public double mean(){ // sample mean of percolation threshold made on 9-7 dw
		return (double)(total/thresholds.length)/(N*N);								//pretty obvious whats going on here
	}
	public double stddev(){ // sample standard deviation of percolation threshold made on 9-7 dw
		double mean=total/thresholds.length,devTotal=0;								//w07 TODO I don't know that this is correct
		for(int i=0; i<thresholds.length; ++i)										//mathmatically it looks fine, but it seems a
			devTotal+=Math.pow(thresholds[i]-mean,2);								//little higher than the values in the pdf
		return Math.sqrt(devTotal/thresholds.length)/(N*N);							//need to double check
	}
	public double confidenceLow(){ // low endpoint of 95% confidence interval made on 9-7 dw
		return mean()-(1.96*stddev()/Math.sqrt(thresholds.length));					
	}
	public double confidenceHigh(){ // high endpoint of 95% confidence interval made on 9-7 dw
		return mean()+(1.96*stddev()/Math.sqrt(thresholds.length));
	}
	
}
