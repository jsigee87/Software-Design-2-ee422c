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
        if (x.length == 0){
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
         * - x is sorted in non-decreasing order
         * - n <= len(x)
         * - n > 0
         * - x is not null
         */

        if (x.length == 0){
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
        int end = n - 1;
        int mid = (end - start)/2;
        while (start <= end){
        	if(x[mid] == target){
                return mid;
            }
        	else if (x[mid] > target){
                end = mid - 1;
                mid = start + (end - start)/2;
            }
            else if(x[mid] < target){
                start = mid + 1;
                mid = start + (end - start)/2;
            }
        }
        
        // If we end binary search and reach here
        // then our target is not in x.
        return -1;

    }

    public static int[] insertGeneral(int[] x, int n, int target) {
    	/* Given Assumptions:
         * - x is sorted in non-decreasing order
         * - n <= len(x)
         * - n > 0
         * - x is not null
         */
    	if (find(x,n,target) == -1) {
    		
    		int[] appended_list = new int[n + 1];
    		int i  = 0;
    		
    		for (; i < n && x[i] < target; i ++) {
    			appended_list[i] = x[i];
    		}
    		
    		appended_list[i] = target;
    		i ++;
    		
    		for (; i < (n + 1); i ++) {
    			appended_list[i] = x[i - 1];
    		}
    		return appended_list;
    	}
    	else {
    		int[] list_copy = new int[n];
    		int i = 0;
    		for (; i < n; i ++) {
    			list_copy[i] = x[i];
    		}
    		return list_copy;
    	}
    }
    
    public static int insertInPlace(int[] x, int n, int target) {
    	
    	if (find(x, n, target) == -1){
    		
    		int i = n;
    		
    		for (; i > 0 && x[i-1] > target; i --) {
    			x[i] = x[i-1];
    		}
    		
    		x[i] = target;
    		return n + 1;
    	} 
    	else {
    		return n;
    	}
    }
    
    public static void insertSort(int[] x, int n) {
    	int i = 0;
    	while ( i < n -1) {
    		if (x[i + 1] < x[i]) {
    			int holder = x[i];
    			x[i] = x[i + 1];
    			x[i + 1] = holder;
    			i = 0;
    		}
    		else {
    			i ++;
    		}
    	}
    }       
}






























