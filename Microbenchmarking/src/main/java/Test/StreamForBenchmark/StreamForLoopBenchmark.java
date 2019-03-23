package Test.StreamForBenchmark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class StreamForLoopBenchmark {

  private static final int SIZE = 10000;
  private Set<String> hashSetStrings;

  private ArrayList<String> arrayListString;

  @Setup
  public void setupCollections() {
    hashSetStrings = new HashSet<>(SIZE);
    arrayListString = new ArrayList<>(SIZE);

    for (int i = 0; i < SIZE; i++) {

      final String value = String.valueOf(i);
      hashSetStrings.add(value);
      arrayListString.add(value);
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetStringStream() {
    hashSetStrings.forEach(s -> {
    });
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testHashSetStringFor() {
    int i = 0;
    for (String hashSetString : hashSetStrings) {
      i++;
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListStringStream() {
    arrayListString.forEach(s -> {
    });
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testArrayListStringFor() {
    int i = 0;
    for (String str : arrayListString) {
      i++;
    }
  }
}
