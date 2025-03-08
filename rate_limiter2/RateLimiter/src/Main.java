import processor.InterceptorProcessor;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter number of times to call foo and bar:");
        Scanner sc = new Scanner(System.in);
        int numberOfCalls = sc.nextInt();
        sc.close();

        InterceptorProcessor interceptorProcessor = new InterceptorProcessor();

        // Use an ExecutorService to submit tasks and then shut it down
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < numberOfCalls; i++) {
            executorService.execute(() -> interceptorProcessor.makeCall());
        }
        executorService.shutdown();

        // If you need to schedule periodic calls using a Timer, uncomment the following:
        /*
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                interceptorProcessor.makeCall();
            }
        }, 0, 1000); // adjust the period (in ms) as needed
        */
    }
}
