package com.company;

public class PrintColor {
    public static final String def = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String black = "\033[0;30m";   // BLACK
    public static final String red = "\033[0;31m";     // RED
    public static final String green = "\033[0;32m";   // GREEN
    public static final String yellow = "\033[0;33m";  // YELLOW
    public static final String blue = "\033[0;34m";    // BLUE
    public static final String purple = "\033[0;35m";  // PURPLE
    public static final String cyan = "\033[0;36m";    // CYAN
    public static final String white = "\033[0;37m";   // WHITE

    // Bold
    public static final String Bblack = "\033[1;30m";  // BLACK
    public static final String Bred = "\033[1;31m";    // RED
    public static final String Bgreen = "\033[1;32m";  // GREEN
    public static final String Byellow = "\033[1;33m"; // YELLOW
    public static final String BBlue = "\033[1;34m";   // BLUE
    public static final String Bpurple = "\033[1;35m"; // PURPLE
    public static final String Bcyan = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    public static String Green(String msg) { return green + msg + def; }
    public static String Red(String msg) { return red + msg + def; }
    public static String Purple(String msg) { return purple + msg + def; }
    public static String Cyan(String msg) { return cyan + msg + def; }
    public static String Blue(String msg) { return blue + msg + def; }
    public static String Yellow(String msg) { return yellow + msg + def; }
    public static String White(String msg) { return white + msg + def; }

    public static String BGreen(String msg) { return Bgreen + msg + def; }
    public static String BRed(String msg) { return Bred + msg + def; }
    public static String BPurple(String msg) { return Bpurple + msg + def; }
    public static String BCyan(String msg) { return Bcyan + msg + def; }
    public static String BBlue(String msg) { return BBlue + msg + def; }
    public static String BYellow(String msg) { return Byellow + msg + def; }

    public static String UYellow(String msg) { return YELLOW_UNDERLINED + msg + def; }
    public static String UGreen(String msg) { return GREEN_UNDERLINED + msg + def; }
}
