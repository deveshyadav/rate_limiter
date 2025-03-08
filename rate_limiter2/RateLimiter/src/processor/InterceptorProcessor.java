package processor;

import model.Bar;
import model.Foo;
import service.InterceptorService;

import java.util.ArrayList;


public class InterceptorProcessor {

    public void makeCall()
    {
        InterceptorService interceptorService = InterceptorService.getInterceptorService();
        processBarRequest(interceptorService);
        processFooRequest(interceptorService);
        ArrayList<Integer> arr = new ArrayList<>();
        int[] n = arr.stream().mapToInt(Integer::intValue).toArray();
    }

    private static void processBarRequest(InterceptorService interceptorService){
        System.out.println("Calling Bar methods");
        Bar bar = new Bar(interceptorService);
        bar.x();
        bar.y();
    }

    private synchronized void processFooRequest(InterceptorService interceptorService){
        System.out.println("Calling Foo methods");
        Foo foo = new Foo(interceptorService);
        foo.a();
        foo.b();
    }

    static Boolean check_if_sum_possible(ArrayList<Long> arr, Long target) {

        long[] nums = arr.stream().mapToLong(Integer::longValue).toArray();
        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][target + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                if (nums[i - 1] <= j) {
                    dp[i][j] = dp[i - 1][j - nums[i - 1]] || dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][target];
    }


}
