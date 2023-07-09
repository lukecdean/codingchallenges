class Solution {
    public int largestVariance(String s) {
        int[] chars = new int[26];
        // get the counts of each char in c
        for (char c : s) {
            chars[c - 'a']++;
        } // for c

        // set up Kadane's for each pair of chars
        int globalMax = 0;
        for (int major = 0; major < chars.length(); major++) {
            int majorCount = chars[major];
            if (majorCount == 0) {
                // char not in s
                continue;;
            } // if 

            for (int minor = 0; minor < chars.length(); minor++) {
                int minorCount = chars[minor];
                if (major == minor || minorCount == 0) {
                    // comparing the same char or char not in s
                    continue;
                } // if 

                // Run modified Kadane's on the pair
                int l = 0;
                int r = 0;
                // int majorCount = 0;
                int minorCount = 0;
                int minorsRemaining = chars[minor];
                int count = 0;

                while (r < s.length()) {
                    if (s.charAt(r) == major + 'a') {
                        // next char is major
                        count++;
                    } else if (s.charAt(r) == minor + 'a') {
                        // next char is minor
                        count--;
                        minorCount++;
                        minorsRemaining--;
                    } // if

                    if (minorCount > 0 && count > globalMax) {
                        // minor must be present for a valid count
                        // found greater count than current globalMax
                        globalMax = count;
                    } // if

                    if (count < 0) {
                        // if count is negative, discard window
                        count = 0;
                        minorCount = 0;
                        if (minorsRemaining == 0) {
                            // no more minors so end this search
                            break;
                        } // if
                    } // if

                    r++;
                } // while r < s.length

            } // for minor
        } // for major
        return globalMax;
    } // largestVariance()
} // Solution
