package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
//import org.checkerframework.checker.units.qual.A;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE

        AList array_list = new AList();
        AList a_time_sec = new AList();
        AList a_n_list = new AList();
        
        Stopwatch timer = new Stopwatch();

        int interval = 1000;
        int i = 0;
        final int N = 10000000;
        
        while (i <= N) {
            array_list.addLast(i);
            if (i == interval) {
                interval *= 2;
                a_n_list.addLast(i);
                a_time_sec.addLast(timer.elapsedTime());
            }
            i++;
        }
        
        printTimingTable(a_n_list, a_time_sec, a_n_list);
    }
}
