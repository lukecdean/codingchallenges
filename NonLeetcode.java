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


    // Good q to ask: how many words and do they fit in memory?
    public int wordsWithPrefix(String prefix) {
       
    } // wordsWithPrefix()


    public void enumerate(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append('0');
            backtrack(sb, i);
        } // for i
    } // enumerate()

    private void backtrack(StringBuilder sb, int n) {
        while (n >= 0) {
            System.out.println(sb);
            backtrack(sb, n - 1);
            sb.replaceCharAt(n, '1');
            System.out.println(sb);
            backtrack(sb, n - 1);
            sb.replaceCharAt(n, '0');
            n--;
        } // while
    } // backtrack()
} // Class

