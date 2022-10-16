package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        SLList s_list = new SLList();
        AList a_n = new AList();
        AList a_times = new AList();
        AList a_ops = new AList();
        int interval = 1000;
        
        int i = 0;
        int j = 0;
        final int N = 128000;
        final int M = 10000;
        
        while (i <= N) {
            s_list.addLast(i);
            if (i == interval) {
                Stopwatch timer = new Stopwatch();
                while (j <= M) {
                    s_list.getLast();
                    j++;
                }
                j = 0;
                interval *= 2;
                a_n.addLast(i);
                a_times.addLast(timer.elapsedTime());
                a_ops.addLast(M);
            }
            i++;
        }
        
        printTimingTable(a_n, a_times, a_ops);
    }

}
