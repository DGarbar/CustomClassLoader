package Test.StreamParallelBenchmark;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/*
testArrayListParallelStreamMapFilter  avgt    5   35996.702 ±   421.297  ns/op
testArrayListStreamMapFilter          avgt    5   42574.013 ±  1196.481  ns/op
testArrayListParallelStreamMax        avgt    5   28256.340 ±   382.306  ns/op
testArrayListStreamMax                avgt    5   68029.672 ± 14220.925  ns/op
testArrayListParallelStreamReduce     avgt    5   29412.950 ±   396.265  ns/op
testArrayListStreamReduce             avgt    5   32539.445 ±  8082.030  ns/op
testArrayListParallelStreamSortred    avgt    5  279753.423 ±  1162.762  ns/op
testArrayListStreamSortred            avgt    5  283684.437 ±  1660.399  ns/op
testHashSetParallelStreamMapFilter    avgt    5   48414.229 ±   500.547  ns/op
testHashSetStreamMapFilter            avgt    5  163827.179 ±  1326.979  ns/op
testHashSetParallelStreamMax          avgt    5   43001.326 ±  1534.815  ns/op
testHashSetStreamMax                  avgt    5  122437.855 ±  1633.506  ns/op
testHashSetParallelStreamReduce       avgt    5   46896.092 ±   357.650  ns/op
testHashSetStreamReduce               avgt    5  109720.866 ±  5662.424  ns/op
testHashSetParallelStreamSortred      avgt    5  338358.590 ± 15917.092  ns/op
testHashSetStreamSortred              avgt    5  359697.607 ± 47219.876  ns/op
*/
public class StreamParallelBenchamrkMain {

  public static void main(String[] args) throws RunnerException {
    final Options options = new OptionsBuilder()
        .include(StreamParallelBenchmark.class.getSimpleName())
        .forks(1)
        .build();

    new Runner(options).run();
  }
}
