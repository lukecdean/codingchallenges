// 46/ @ 13ms
int maxVowels(char * s, int k) {
    char isvowel[] = {1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0};

    int currcount = 0;
    int maxcount = 0;

    // sliding window
    int l = 0;
    int r = 0;

    char curr; // char being checked

    while (s[r]) {
        curr = s[r];

        if (isvowel[curr - 'a']) {
            currcount++;
        } // if
        r++;

        curr = s[l];
        if (r - l > k) {
            if (isvowel[curr - 'a']) {
                currcount--;
            } // if
            l++;
        } // if

        maxcount = maxcount < currcount ? currcount : maxcount;
    } // while

    return maxcount;
} // maxVowels()


// 62/77 @ 12ms (6/ @ 19ms)
int is_vowel(char curr) {
        return (curr == 'a' || curr == 'e' || curr == 'i'
            || curr == 'o' || curr == 'u');
} // if

int maxVowels(char * s, int k) {
    int currcount = 0;
    int maxcount = 0;

    // sliding window
    int l = 0;
    int r = 0;

    char curr; // char being checked

    while (s[r]) {
        curr = s[r];

        if (is_vowel(curr)) {
            currcount++;
        } // if
        r++;

        curr = s[l];
        if (r - l > k) {
            if (is_vowel(curr)) {
                currcount--;
            } // if
            l++;
        } // if

        maxcount = maxcount < currcount ? currcount : maxcount;
    } // while

    return maxcount;
} // maxVowels()
