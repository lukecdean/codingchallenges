// 68/87 @ 3ms
class Solution {
    public String reorganizeString(String s) {
        int[] charFrequencies = new int['z' - 'a' + 1];
        for (int i = 0; i < s.length(); i++) {
            charFrequencies[s.charAt(i) - 'a']++;
        } // for i

        PriorityQueue<Character> mostFrequentChar = 
            new PriorityQueue<>((Character a, Character b) -> charFrequencies[b - 'a'] - charFrequencies[a - 'a']);

        for (char c = 'a'; c <= 'z'; c++) {
            //System.out.println(c + " freq " + charFrequencies[c - 'a']);
            if (charFrequencies[c - 'a'] > 0) {
                mostFrequentChar.offer(c);
            } // if
        } // for c

        StringBuilder res = new StringBuilder(s.length());

        char witheld = mostFrequentChar.poll();
        res.append(witheld);
        charFrequencies[witheld - 'a']--;
        while (!mostFrequentChar.isEmpty()) {
            char temp = mostFrequentChar.poll();
            //System.out.println("witheld: " + witheld + " temp: " + temp);
            res.append(temp);
            charFrequencies[temp - 'a']--;
            if (charFrequencies[witheld - 'a'] > 0) {
                mostFrequentChar.offer(witheld);
            } // if
            witheld = temp;
        } // while

        if (charFrequencies[witheld - 'a'] > 1) {
            // cannot separate this char
            return "";
        } else if (s.length() > 1 && res.charAt(s.length() - 2) == witheld) {
            // last char is witheld
            return "";
        } else {
            // can separate
            return res.toString();
        } // if
    }
}
