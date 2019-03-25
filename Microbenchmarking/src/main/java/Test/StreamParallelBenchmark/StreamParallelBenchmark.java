package Test.StreamParallelBenchmark;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@Fork(warmups = 1)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class StreamParallelBenchmark {

  private static final int SIZE = 10000;
  private Set<Integer> hashSetIntegers;
  private ArrayList<Integer> arrayListIntegers;

  @Setup
  public void setupCollections() {
    hashSetIntegers = new Random().ints(SIZE, -10000, 10000).boxed()
        .collect(Collectors.toSet());
    arrayListIntegers = new ArrayList<>(hashSetIntegers);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetStreamReduce() {
    hashSetIntegers.stream().reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetParallelStreamReduce() {
    hashSetIntegers.parallelStream().reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListStreamReduce() {
    arrayListIntegers.stream().reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListParallelStreamReduce() {
    arrayListIntegers.parallelStream().reduce(Integer::sum);
  }


  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetStreamMapFilter() {
    hashSetIntegers.stream().filter(integer -> integer< 100).reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetParallelStreamMapFilter() {
    hashSetIntegers.parallelStream().filter(integer -> integer< 100).reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListStreamMapFilter() {
    arrayListIntegers.stream().filter(integer -> integer< 100).reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListParallelStreamMapFilter() {
    arrayListIntegers.parallelStream().filter(integer -> integer< 100).reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetStreamSortred() {
    hashSetIntegers.stream().sorted().reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetParallelStreamSorted() {
    hashSetIntegers.parallelStream().sorted().reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListStreamSorted() {
    arrayListIntegers.stream().sorted().reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListParallelStreamSorted() {
    arrayListIntegers.parallelStream().sorted().reduce(Integer::sum);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetStreamMax() {
    hashSetIntegers.stream().max(Integer::compareTo);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetParallelStreamMax() {
    hashSetIntegers.parallelStream().max(Integer::compareTo);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListStreamMax() {
    arrayListIntegers.stream().max(Integer::compareTo);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListParallelStreamMax() {
    arrayListIntegers.parallelStream().max(Integer::compareTo);
  }


}
