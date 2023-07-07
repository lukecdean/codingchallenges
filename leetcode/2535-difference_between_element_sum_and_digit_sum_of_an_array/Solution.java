class Solution {
    public int differenceOfSum(int[] nums) {
        int elementSum = 0;
        for (int i : nums) {
            elementSum += i;
        } // for i

        int digitSum = 0;
        for (int i : nums) {
            while (i > 0) {
                digitSum += i % 10;
                i = i / 10;
            } // while i > 0
        } // for i

        int res = elementSum - digitSum;
        return res * (res >= 0 ? 1 : -1);
    } // differenceOfSum()
}
