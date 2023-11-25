class Solution {
    // 99/55 @ 27ms
    public int maxCoins(int[] piles) {
        Arrays.sort(piles);
        int res = 0;
        int lowerThird = piles.length / 3;
        for (int pile = piles.length - 2; pile >= lowerThird; pile -= 2) {
            res += piles[pile];
        }
        return res;
    }
}
