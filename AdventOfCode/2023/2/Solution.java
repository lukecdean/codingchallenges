import java.nio.file.*;
class Solution {
    public static void main(String[] args) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("input.txt")));
        } catch (Exception e) {
            System.exit(0);
        } // try
        if (content == null) { return;}

        String[] games = content.split("\n");

        System.out.println(games.length);

        int res = 0;

        for (int game = 0; game < games.length; game++) {
            String[] tokens = games[game].split(" ");
            int red = 0;
            int green = 0;
            int blue = 0;

            int curr = 0;
            for (int token = 2; token < tokens.length; token++) {
                System.out.println(tokens[token]);
                if (Character.isDigit(tokens[token].charAt(0))) {
                    curr = Integer.parseInt(tokens[token]);
                } else {
                    switch (tokens[token].charAt(0)) {
                        case 'r':
                            red = curr;
                            break;
                        case 'g':
                            green = curr;
                            break;
                        case 'b':
                            blue = curr;
                            break;
                    } // switch
                } // if

                if (tokens[token].charAt(tokens[token].length() - 1) == ';') {
                    if (red <= 12 && green <= 13 && blue <= 14) {
                        res += game + 1;
                    } // if

                    red = 0;
                    green = 0;
                    blue = 0;
                    curr = 0;
                } // if
            } // for token
        } // for game

        System.out.println(res);
    } // main()
} // class
