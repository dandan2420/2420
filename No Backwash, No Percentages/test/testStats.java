package test;

import percolation.PercolationStats;
import junit.framework.TestCase;

public class testStats extends TestCase {

	public void testPercolationStats() {//Going over N=7000 might run out of memory
		int N=400, T=300;							//adjust N for larger grid size, and T for more sequential tests
		PercolationStats s= new PercolationStats(N,T);		//
		System.out.println("mean "+s.mean());
		System.out.println("Half N x N "+(N*N)/2);
		System.out.println("Standard Deviation "+s.stddev());
		System.out.println("Low Confidence "+s.confidenceLow());
		System.out.println("High Confidence "+s.confidenceHigh());
		//I'm not actually comparing anything since I'm not sure if there is a "real" answer
		
	}

}
