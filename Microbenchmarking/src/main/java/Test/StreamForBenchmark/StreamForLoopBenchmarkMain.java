package Test.StreamForBenchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StreamForLoopBenchmarkMain {

  /*
  testArrayListStringFor     avgt   20  11777.864 ± 226.358  ns/op
  testArrayListStringStream  avgt   20  11313.690 ± 200.181  ns/op
  testHashSetStringFor       avgt   20  86158.522 ± 760.528  ns/op
  testHashSetStringStream    avgt   20  82397.453 ± 883.193  ns/op

  */
  public static void main(String[] args) throws RunnerException {
    final Options options = new OptionsBuilder()
        .include(StreamForLoopBenchmark.class.getSimpleName())
        .forks(1)
        .build();

    new Runner(options).run();
  }
}
