package search;
public class BinarySearchClosestD {
    // Pre: args.len > 1 && for all 0 < i < j => args[i] >= args[j]
    // Post: R
    public static void main(String[] args) {
        // args.len > 1 && for all 0 < i < j => args[i] >= args[j]
        int x = Integer.parseInt(args[0]);
        // args.len > 1 && for all 0 < i < j => args[i] >= args[j]
        int[] a = new int[args.length - 1];
        // a.len > 0 && for all 0 < i < j => args[i] >= args[j]
        int sum = x;
        // a.len > 0 && for all 0 < i < j => args[i] >= args[j] && sum == x
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
            sum = (sum + a[i - 1])%2;
        }
        // a.len > 0 && for all 0 < i < j => a[i] >= a[j]
        System.out.println(sum == 0 ?
                recursiveBinarySearch(a, x, -1, a.length) :
                iterativeBinarySearch(a, x));
        // R
    }


    // Pre: a.len > 0 && for all i < j => a[i] >= a[j]
    // Post: | a[right] - x | = min( for all i : 0 <= i <= a.len - 1 : | a[i] - x | )
    private static int iterativeBinarySearch(int[] a, int x) {
        int left = -1;
        int right = a.length;
        // left < right - 1 && -1 < a.len - 1
        while (1 < right - left) {
            // left' < right' - 1
            int mid = (left + right) / 2;
            // right' - left' > 1 && right' <= right && left' >= left && right' > 0 && left' < a.len - 1
            // mid' < right' && mid' > left' && right' - left' = alfa'
            if (a[mid] <= x) {
                // right' > mid' && a[mid'] <= x
                // a[right'] <= a[mid'] && a[mid'] <= x
                // a[right'] <= x && a[mid'] <= x && mid' < right'
                right = mid;
                // a[right'] <= x
            } else {
                // mig' > left' && a[mid'] > x
                // a[mid'] <= a[left'] && x < a[mid']
                // x < a[left'] && x < a[mid'] && mid' > left'
                left = mid;
                // a[left'] > x
            }
            // right' - left' < alfa'
        }
        return findClosest(a, x, right);
    }

    // Pre: a.len > 0 && for all i < j => a[i] >= a[j] && left < right
    // Post: | a[right] - x | = min( for all i : 0 <= i <= a.len - 1 : | a[i] - x | )
    private static int recursiveBinarySearch(int[] a, int x, int left, int right) {
        if (left < right - 1) {
            // left < right - 1
            int mid = (left + right) / 2;
            // right - left > 1 && && right > 0 && left < a.len - 1
            // mid' < right' && mid' > left' && mid' right' - left' = alfa'
            if (a[mid] <= x) {
                // right' > mid' && a[mid'] <= x
                // a[right'] <= a[mid'] && a[mid'] <= x
                // a[right'] <= x && a[mid'] <= x && mid' < right'
                return recursiveBinarySearch(a, x, left, mid);
            } else {
                // mig' > left' && a[mid'] > x
                // a[mid'] <= a[left'] && x < a[mid']
                // x < a[left'] && x < a[mid'] && mid' > left'
                return recursiveBinarySearch(a, x, mid, right);
            }
        }
        return findClosest(a, x, right);
    }
    // Pre: a.len > 0
    // Post: | a[right] - x | = min( for all i : 0 <= i <= a.len - 1 : | a[i] - x | )
    private static int findClosest(int[] a, int x, int index) {
        if (index <= 0) {
            return a[0];
        }
        // index > 0
        if (index >= a.length) {
            return a[a.length - 1];
        }
        //  index > 0 && index < a.length
        return Math.abs(a[index - 1] - x) <= Math.abs(a[index] - x) ? a[index - 1] : a[index];
    }
}
