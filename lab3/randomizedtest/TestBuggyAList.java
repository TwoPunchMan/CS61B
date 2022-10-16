package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
	@Test
	public void testThreeAddThreeRemove() {
		AListNoResizing<Integer> a_list = new AListNoResizing<>();
		BuggyAList<Integer> b_list = new BuggyAList<>();
		int[] three_ints = {4,5,6};
		for (int i = 0; i < 3; i++) {
			a_list.addLast(three_ints[i]);
			b_list.addLast(three_ints[i]);
		}
		assertEquals(a_list.size(), b_list.size());
		assertEquals(a_list.removeLast(), b_list.removeLast());
		assertEquals(a_list.removeLast(), b_list.removeLast());
		assertEquals(a_list.removeLast(), b_list.removeLast());
	}
	
	@Test
	public void randomizedTest() {
		AListNoResizing<Integer> L = new AListNoResizing<>();
		BuggyAList<Integer> BL = new BuggyAList<>();
		
		int N = 5000;
		for (int i = 0; i < N; i += 1) {
			int operationNumber = StdRandom.uniform(0, 4);
			if (operationNumber == 0) {
				// addLast
				int randVal = StdRandom.uniform(0, 100);
				L.addLast(randVal);
				BL.addLast(randVal);
			} else if (operationNumber == 1) {
				// size
				int size = L.size();
				int size_b = BL.size();
			} else if (operationNumber == 2) {
				// getLast
				if (L.size() != 0) {
					int last = L.getLast();
					int b_last = BL.getLast();
				}
			} else if (operationNumber == 3) {
				// removeLast
				if (L.size() != 0) {
					int last = L.removeLast();
					int b_last = BL.removeLast();
				}
			}
		}
	}
}
