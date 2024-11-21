package search;
public class BinarySearch {
    // Pre: args.len > 1 && for all 0 < i < j => args[i] >= args[j]
    // Post: R
    public static void main(String[] args) {
        // args.len > 1 && for all 0 < i < j => args[i] >= args[j]
        int x = Integer.parseInt(args[0]);
        // args.len > 1 && for all 0 < i < j => args[i] >= args[j]
        int[] a = new int[args.length - 1];
        // a.len > 0 && for all 0 < i < j => args[i] >= args[j]
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        // a.len > 0 && for all 0 < i < j => a[i] >= a[j]
        //recursiveBinarySearch(a, x, -1, a.length);
        System.out.println(iterativeBinarySearch(a, x));
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
        return right;
    }
    // Pre: a.len > 0 && for all i < j => a[i] >= a[j] && ( left' > left || right' < right )
    // Post: | a[right] - x | = min( for all i : 0 <= i <= a.len - 1 : | a[i] - x | )
    private static int recursiveBinarySearch(int[] a, int x, int left, int right) {
        if (left < right - 1) {
            // left' < right' - 1
            int mid = (left + right) / 2;
            // right' - left' > 1 && right' <= right && left' >= left && right' > 0 && left' < a.len - 1
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
        return right;
    }
}
