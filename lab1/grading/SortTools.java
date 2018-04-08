// SortTools.java 
/*
 * EE422C Project 1 submission by
 * Written by: John Sigmon
 * EID: js85773
 * 15505
 * Spring 2018
 * Slip days used: 0 
 */

package assignment1;
public class SortTools {
	/**
	  * This method tests to see if the given array is sorted.
	  * @param x is the array
	  * @param n is the size of the input to be checked
	  * @return true if array is sorted
	  */
	public static boolean isSorted(int[] x, int n) {
		/* Given Assumptions:
         * - n <= len(x)
         * - n >= 0
         * - x is not null
         */

        // Corner Cases (given by assignment instructions)
        if (x.len == 0){
            return false;
        }
        if (n == 0){
            return false;
        }
        if (n == 1){
            return true;
        }

        // Grab first array element and then compare
        // succeeding elements
        int prev = x[0];
        
        for (int i = 1; i < n - 1; i ++){
            if (x[i] < prev){
                return false;
            }
            else {
                prev = x[i];
            }
        }
        
        return true;
	}

    public static int find(int[] x, int n, int target) {
        /* Given Assumptions:
         * - n <= len(x)
         * - n > 0
         * - x is not null
         */

        if (x.len == 0){
           return -1;
        }
        if (n == 0){
           return -1;
        }
        if (n == 1){
            if (target == x[0]){
                return 0;
            }
        }

        // Else we do binary search
        int start = 0;
        int end = n;
        while (start <= end){
            if (x[n/2] > target){
                end = n/2;
            }
            else if(x[n/2] < target){
                start = n/2;
            }
            else if(x[n/2] == target){
                return n/2;
            }
        }
        
        // If we end binary search and reach here
        // then our target is not in x.
        return -1;

    }

    public static int insertGeneral(int[] x, int n, int target) {



    }    
}
