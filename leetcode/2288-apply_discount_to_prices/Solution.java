class Solution {
    public String discountPrices(String sentence, int discount) {
        StringBuilder sb = new StringBuilder();
        boolean whitespacePreceding = true;
        for (int i = 0; i < sentence.length(); i++) {
            if (whitespacePreceding && sentence.charAt(i) == '$') {
                // price found
                double price = 0;
                int j;
                for (j = i + 1; j < sentence.length() && Character.isDigit(sentence.charAt(j)); j++) {
                    price *= 10;
                    price += Character.getNumericValue(sentence.charAt(j));
                } // for j
                if (j > i + 1 && (j == sentence.length() || Character.isWhitespace(sentence.charAt(j)))) {

                    double discounted = ((double) price)
                    * (1 - (((double) discount) / 100));

                    sb.append('$');
                    sb.append(String.format("%.2f", discounted));
                    whitespacePreceding = false;
                    i = j - 1;
                } else {
                    // turned out to not be a price
                    whitespacePreceding = false;
                    i--;
                }
            } else {
                whitespacePreceding = Character.isWhitespace(sentence.charAt(i));
                sb.append(sentence.charAt(i));
            } // if start of price
        } // for i in sentence

        return sb.toString();
    }
}
