int longestPalindromeSubseq(char * s){
} // lPS()

/*
int longestPalindromeSubseq(char * s){
    int len = strlen(s);
    int maxLen = len >= 2 ? 1 : len;
    int num_letters = 'z' - 'a' + 1;
    int letters_leftward[num_letters];
    int remaining_leftward[num_letters];
    int letters_rightward[num_letters];
    int remaining_rightward[num_letters];

    // 0-out
    for (int i = 0; i <= num_letters; i++) {
        letters_leftward[i] = 0;
        letters_rightward[i] = 0;
    } // for i
    // get the counts of the letters in s
    for (int i = 0; i < len; i++) letters_rightward[s[i]]++;

    for (int base = 0; base < len; base++) {
        // update the counts based on current pos
        letters_leftward[s[base]]++;
        letters_rightward[s[base]]--;

        int l = base;
        int r = base;

        // expand leftward first
        // populate remaining_rightward
        for (int i = 0; i < num_letters; i++) remaining_rightward[i] = letters_rightward[i];
        while (l >= 0) {
            l--;
            // check if there is a corresponding letter remnaing rightward
            if (remaining_rightward[s[l]] > 0) {
                // if so, go to it, adjusting remaining rightward
            } // if
        } // while


        // populate remaining_leftward
        for (int i = 0; i < num_letters; i++) remaining_leftward[i] = letters_leftward[i];
    } // for base

} // lPS()
*/
