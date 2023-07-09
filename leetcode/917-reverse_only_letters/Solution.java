class Solution {
    public String reverseOnlyLetters(String s) {
        Stack<Character> chars = new Stack<>();
        for (int c = 0; c < s.length(); c++) {
            if (Character.isLetter(s.charAt(c))) {
                chars.push(s.charAt(c));
            } // if
        } // for c

        StringBuilder res = new StringBuilder();
        for (int c = 0; c < s.length(); c++) {
            if (Character.isLetter(s.charAt(c))) {
                res.append(chars.pop());
            } else {
                res.append(s.charAt(c));
            } // if
        } // for c

        return res.toString();
    }
}
