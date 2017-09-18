package percolation;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

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
	
	private int[] randomArray(int N){							//just creates me a shuffled array
		int [] b=new int[N];									
		for(int i=0; i<N; ++i)									
			b[i]=i;												
				
		StdRandom.shuffle(b);									//not using util.Random because it's
		return b;												//against the rules
	}
	
	//TODO ask about mean not being a percentage
	public double mean(){ // sample mean of percolation threshold made on 9-7 dw
		return StdStats.mean(thresholds);
		//return (double)(total/thresholds.length)/(N*N);								//pretty obvious whats going on here
	}
	public double stddev(){ // sample standard deviation of percolation threshold made on 9-7 dw
		return StdStats.stddev(thresholds);
//		double mean=total/thresholds.length,devTotal=0;								
//		for(int i=0; i<thresholds.length; ++i)										
//			devTotal+=Math.pow(thresholds[i]-mean,2);								
//		return Math.sqrt(devTotal/thresholds.length)/(N*N);							
	}
	public double confidenceLow(){ // low endpoint of 95% confidence interval made on 9-7 dw
		return mean()-(1.96*stddev()/Math.sqrt(thresholds.length));					
	}
	public double confidenceHigh(){ // high endpoint of 95% confidence interval made on 9-7 dw
		return mean()+(1.96*stddev()/Math.sqrt(thresholds.length));
	}
	
}
