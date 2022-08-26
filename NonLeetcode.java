class NonLeetcode {
    int[][] memo;
    public static void main(String[] args) {
        [candies.length - 1][candies.length - 1];
    } // main()

    static int maxScore(int turn, int low, int high, char[] pref, char[] candies) {
        if (high == low) return turn * candies[high];
        int eathigh =   value(turn, pref, candies[high]) +  maxScore(turn + 1, low,       high - 1,   pref, candies);
        int eatlow =    value(turn, pref, candies[low]) +   maxScore(turn + 1, low + 1,   high,       pref, candies);
        return Math.max(eathigh, eatlow);
    } // maxScore()

    static int maxScoreDyn(int turn, int low, int high, char[] pref, char[] candies) {
        if (high == low) return turn * candies[high];

        if (memo[low][high] != -1) {
            int eathigh =   value(turn, pref, candies[high]) +  maxScore(turn + 1, low,       high - 1,   pref, candies);
            int eatlow =    value(turn, pref, candies[low]) +   maxScore(turn + 1, low + 1,   high,       pref, candies);
            memo[low][high] = Math.max(eathigh, eatlow);
        } // if
        return memo[low][high];
    } // maxScore()
    
    static int maxScoreDyn2(int turn, int low, int high, char[] pref, char[] candies) {
        for (int i = 0; i < candies.length; i++) {
            memo[candies.length - 1][i] = val(candies.length, pref, candies[i]);
        } // for i
        
        for (int ctoeat = candies.length - 1; ctoeat > 0; ctoeat--) {
        } // for ctoeat



        if (high == low) return turn * candies[high];
        int eathigh =   value(turn, pref, candies[high]) +  maxScore(turn + 1, low,       high - 1,   pref, candies);
        int eatlow =    value(turn, pref, candies[low]) +   maxScore(turn + 1, low + 1,   high,       pref, candies);
        return Math.max(eathigh, eatlow);
    } // maxScore()

    static int value(int turn, char[] pref, char candy) {
        int val = 0;
        for (int i = 0; i < pref.length; i++) {
            if pref[i] == candy {
                val = pref.length - i;
            } // if
        } // for i
        return turn * val;
    } // value()

} // Class

