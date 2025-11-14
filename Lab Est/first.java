import java.util.*;

public class Solution {
    public static List<Integer> maxSlidingWindow(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();

        // max heap: highest value comes first
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> b[0] - a[0]   // compare by value
        );

        // Insert first k elements
        for (int i = 0; i < k; i++) {
            pq.offer(new int[]{nums[i], i});
        }

        result.add(pq.peek()[0]);

        // Process remaining elements
        for (int i = k; i < nums.length; i++) {

            // Add new element
            pq.offer(new int[]{nums[i], i});

            // Remove elements out of window
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }

            // Top is max in current window
            result.add(pq.peek()[0]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1,3,-1,-3,5,3,6,7};
        int k = 3;

        System.out.println(maxSlidingWindow(arr, k));
    }
}
