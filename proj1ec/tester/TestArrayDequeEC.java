package tester;

import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    private StudentArrayDeque<Integer> S = new StudentArrayDeque<Integer>();
    private ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<Integer>();
    private final int methodCallAmt = 1000000;
    private String[] methodCalls = new String[methodCallAmt];
    private boolean flag = false;

    @Test
    public void randomNumTest() {
        while (flag == false) {
            runTest();
            methodCalls = new String[methodCallAmt];
        }
    }

    private void runTest() {
        int i = 0;
        while (i < methodCallAmt) {
            String methodName = null;
            Integer x = null;
            Integer y = null;
            int randNumberMethod = StdRandom.uniform(4);
            int randNumberDeque = StdRandom.uniform(9) + 1;
            if (randNumberMethod == 0) {
                S.addFirst(randNumberDeque);
                ad.addFirst(randNumberDeque);
                methodName = String.format("addFirst(%d)",randNumberDeque);
            } else if (randNumberMethod == 1) {
                S.addLast(randNumberDeque);
                ad.addLast(randNumberDeque);
                methodName = String.format("addLast(%d)",randNumberDeque);
            } else if (randNumberMethod == 2 || randNumberMethod == 3) {
                if (ad.size() == 0) {
                    continue;
                }

                if (randNumberMethod == 2) {
                    x = S.removeFirst();
                    y = ad.removeFirst();
                    methodName = "removeFirst()";
                } else if (randNumberMethod == 3) {
                    x = S.removeLast();
                    y = ad.removeLast();
                    methodName = "removeLast()";
                }
            }

            if (methodName != null) {
                methodCalls[i] = methodName;
            }

            if ((x != null && y != null) && x != y) {
                flag = true;
                String msg = printMsg(methodCalls);
                assertEquals(msg ,x, y);
            }

            i++;
        }
    }

    private String printMsg(String[] methodCalls) {
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < methodCalls.length; i++) {
            if (methodCalls[i] != null) {
                msg.append(methodCalls[i]);
                msg.append("\n");
            }
        }

        return msg.toString();
    }
}
