import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsAndFutures {

    private static final int LIST_SIZE = 5;
    static int[][] resultArray = new int[LIST_SIZE][LIST_SIZE];
    private static final Random random = new Random();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        List<Integer> numbers = initialiseArray();

        ExecutorService executor = Executors.newFixedThreadPool(LIST_SIZE*LIST_SIZE);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < LIST_SIZE; i++) {
            for (int j = 0; j < LIST_SIZE; j++) {
                final int row = i, col = j;
                futures.add(executor.submit(() -> expensiveCall(numbers.get(row), numbers.get(col), row, col)));
            }
        }

        for (Future<?> future : futures) {
            future.get();
        }
        executor.shutdown();

        printResultArray();

        long endTime = System.currentTimeMillis();
        double timeElapsedInSeconds = (endTime - startTime) / 1000.0;
        System.out.println("Total execution time: " + timeElapsedInSeconds + " seconds");
    }

    private static void expensiveCall(int a, int b, int i, int j) {
        int seconds = random.nextInt(5);
        System.out.printf("Sleep for %d seconds in thread [%s]%n", seconds, Thread.currentThread());
        try {
            Thread.sleep(seconds * 1000);
            resultArray[i][j] =  a + b + seconds;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread " + Thread.currentThread() + " ended");
    }

    private static List<Integer> initialiseArray() {
        List<Integer> numbers = new ArrayList<>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++) {
            numbers.add(random.nextInt(10));
        }
        return numbers;
    }

    private static void printResultArray() {
        System.out.println("ResultArray: -------------------");
        for (int i = 0; i < LIST_SIZE; i++) {
            for (int j = 0; j < LIST_SIZE; j++) {
                System.out.print(resultArray[i][j] + " ");
            }
            System.out.println();
        }
    }

}