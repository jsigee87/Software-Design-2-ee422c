package assignment1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class SortToolsTest {
	
	@Test(timeout = 2000)
	public void testFindFoundFull(){
		int[] x = new int[]{-2, -1, 0, 1, 2, 3};
		assertEquals(3, SortTools.find(x, 6, 1));
	}
	
	@Test(timeout = 2000)
	public void testInsertGeneralPartialEnd(){
		int[] x = new int[]{10, 20, 30, 40, 50};
		int[] expected = new int[]{10, 20, 30, 35};
		assertArrayEquals(expected, SortTools.insertGeneral(x, 3, 35));
	}
	
	@Test(timeout = 2000)
	public void test01() {
		int[] x = new int[] {0, 0, 0, 0, 0, 0, 1};
		int[] expected = new int[] {0, 0, 0, 0, 0, 0, 1};
		assertArrayEquals(expected, SortTools.insertGeneral(x, 7, 1));
	}
	
	@Test(timeout = 2000)
	public void test02() {
		int[] x = new int[] {0, 0, 0, 0, 0, 0, 1};
		int[] expected = new int[] {0, 0, 0, 0, 0, 0, 1};
		assertArrayEquals(expected, SortTools.insertGeneral(x, 6, 1));
	}
	
	@Test(timeout = 2000)
	public void test03() {
		int[] x = new int[] {0, 0, 0, 0, 0, 0, 1};
		int[] expected = new int[] {-1, 0, 0, 0, 0, 0};
		assertArrayEquals(expected, SortTools.insertGeneral(x, 5, -1));
	}
	
	@Test()
	public void test04() {
		int[] x = new int[] {1, 2, 3, 4, 5};
		int[] expected = new int[] {0, 1, 2, 3, 4, 5};
		assertArrayEquals(expected, SortTools.insertGeneral(x, 5, 0));
	}
	
	@Test(timeout = 2000)
	public void test05(){
		int[] x = new int[]{-2, -1, 0, 1, 2, 3};
		assertEquals(-1, SortTools.find(x, 3, 1));
	}
	
	@Test(timeout = 2000)
	public void test06(){
		int[] x = new int[]{-2, -1, 0, 1, 2, 3};
		assertEquals(-1, SortTools.find(x, 1, -1));
	}
	
	@Test(timeout = 2000)
	public void test07(){
		int[] x = new int[]{-2, -1, 0, 1, 2, 3};
		assertEquals(5, SortTools.find(x, 6, 3));
	}
	
	@Test(timeout = 2000)
	public void test08(){
		int[] x = new int[]{-2, -1, 0, 1, 2, 3};
		assertEquals(-1, SortTools.find(x, 6, -3));
	}
	

	
	//@Test(timeout = 2000)
	//public void test10(){
	//	int[] x = new int[]{-2, -1, 0, 1, 2, 3};
	//	assertEquals(3, SortTools.find(x, 6, 1));
	//}
	
	
	
}
