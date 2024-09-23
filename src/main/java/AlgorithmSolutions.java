import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgorithmSolutions {

    // Method to merge overlapping intervals
    public static List<int[]> mergeIntervals(int[][] intervals) {
        // Resultant merged intervals list
        List<int[]> merged = new ArrayList<>();

        // Edge case: if no intervals, return empty list
        if (intervals.length == 0) return merged;

        // Initialize the first interval as the starting point
        int[] currentInterval = intervals[0];
        merged.add(currentInterval);

        // Iterate over all intervals
        for (int[] interval : intervals) {
            // If the current interval overlaps with the previous one, merge them
            if (interval[0] <= currentInterval[1]) {
                // Extend the end time of the current interval if needed
                currentInterval[1] = Math.max(currentInterval[1], interval[1]);
            } else {
                // Otherwise, add a new non-overlapping interval
                currentInterval = interval;
                merged.add(currentInterval);
            }
        }

        return merged;
    }

    static class TrieNode {
        TrieNode[] children = new TrieNode[2]; // Binary trie (0 and 1)
    }

    // Method to insert a number into the Trie
    private static void insert(TrieNode root, int num) {
        TrieNode node = root;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (node.children[bit] == null) {
                node.children[bit] = new TrieNode();
            }
            node = node.children[bit];
        }
    }

    // Method to find the maximum XOR for a given number
    private static int findMaxXor(TrieNode root, int num) {
        TrieNode node = root;
        int maxXor = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            // We try to take the opposite bit for maximizing XOR
            int oppositeBit = 1 - bit;
            if (node.children[oppositeBit] != null) {
                maxXor |= (1 << i); // Update result if opposite bit exists
                node = node.children[oppositeBit];
            } else {
                node = node.children[bit];
            }
        }
        return maxXor;
    }

    // Method to find subarray with maximum XOR
    public static int maxSubarrayXOR(int[] arr) {
        TrieNode root = new TrieNode();
        insert(root, 0); // Insert 0 to handle the case of subarray starting at index 0

        int maxXor = 0, prefix = 0;

        for (int num : arr) {
            // Prefix stores the cumulative XOR up to the current element
            prefix ^= num;
            // Insert current prefix XOR into the Trie
            insert(root, prefix);
            // Find the maximum XOR with the current prefix
            maxXor = Math.max(maxXor, findMaxXor(root, prefix));
        }

        return maxXor;
    }

    public static void main(String[] args) {
        // Test mergeIntervals
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        List<int[]> mergedIntervals = mergeIntervals(intervals);
        System.out.println("Merged Intervals:");
        for (int[] interval : mergedIntervals) {
            System.out.println(Arrays.toString(interval));
        }

        // Test maxSubarrayXOR
        int[] arr = {1, 2, 3, 4};
        int maxXor = maxSubarrayXOR(arr);
        System.out.println("Maximum Subarray XOR: " + maxXor);
    }
}
