import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequentialExecution {

    private static final int LIST_SIZE = 5;
    static int[][] resultArray = new int[LIST_SIZE][LIST_SIZE];
    private static final Random random = new Random();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        List<Integer> numbers = initialiseArray();

        for (int i = 0; i < LIST_SIZE; i++) {
            for (int j = 0; j < LIST_SIZE; j++) {
                expensiveCall(numbers.get(i), numbers.get(j), i, j);
            }
        }

        System.out.println();
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
    }

    private static List<Integer> initialiseArray() {
        List<Integer> numbers = new ArrayList<>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++) {
            numbers.add(random.nextInt(10));
        }
        return numbers;
    }

    private static void printResultArray() {
        System.out.println("ResultArray:");
        for (int i = 0; i < LIST_SIZE; i++) {
            for (int j = 0; j < LIST_SIZE; j++) {
                System.out.print(resultArray[i][j] + " ");
            }
            System.out.println();
        }
    }

}