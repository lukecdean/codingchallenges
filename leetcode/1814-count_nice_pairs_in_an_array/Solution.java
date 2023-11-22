class Solution {
    public int countNicePairs(int[] nums) {
        int res = 0;
        int mod = 1_000_000_007;

        // a pair will be nice if both numbers change value by the same
        // amount when reversing (eg both +27 or both -3)
        Map<Integer, Integer> numsWithDelta = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];

            // 20% speed
            //int r = Integer.valueOf(new StringBuilder(Integer.toString(n)).reverse().toString());
            // 30% speed
            //int r = getReverseString(n);
            // 80% speed
            int r = getReverseMod(n);

            int delta = r - n;

            int matchingDeltas = numsWithDelta.getOrDefault(delta, 0);
            res += matchingDeltas;

            if (res >= mod) {
                res -= mod;
            } // if

            numsWithDelta.put(delta, matchingDeltas + 1);
        } // for i

        return res;
    }

    private int getReverseMod(int n) {
        int r = 0;
        while (n > 0) {
            r *= 10;
            r += n % 10;
            n /= 10;
        } // while
        return r;
    }

    private int getReverseString(int n) {
        String ns = Integer.toString(n);
        int r = 0;
        for (int i = 0; i < ns.length(); i++) {
            r *= 10;
            r += Character.getNumericValue(ns.charAt(i));
        } // for i

        return r;
    } // getReverse()
}
