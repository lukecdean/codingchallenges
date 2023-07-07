class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int res = 0;
        for (int i : arr1) {
            boolean passes = true;
            for (int j : arr2) {
                int k = i - j;
                if (k < 0) k *= -1;
                if (k <= d) {
                passes = false;
                break;
                } // if
            }
            if (passes) res++;
        }
        return res;
    }
}
