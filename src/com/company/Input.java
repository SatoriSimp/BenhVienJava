package com.company;

import java.util.Scanner;

public class Input {
    private static final Scanner sc = new Scanner(System.in);
    private static boolean fineInp;
    public static String Str(String message) {
        String res = null;
        fineInp = false;
        while (!fineInp) {
            fineInp = true;
            System.out.print("Enter " + message + ": ");
            try {
                res = sc.nextLine();
                if (res.isEmpty()) throw new IndexOutOfBoundsException();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid input! Try again");
                fineInp = false;
            }
        }
        return res;
    }

    // illegalarguement contains numberformatexception
    public static int Int(String message) {
        int res = Integer.MAX_VALUE;
        fineInp = false;
        while (!fineInp) {
            fineInp = true;
            System.out.print("Enter " + message + ": ");
            try {
                res = Integer.parseInt(sc.nextLine());
                if (res < 0) throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Try again");
                fineInp = false;
            }
        }
        return res;
    }

    public static int Int(String message, int lowerEnd, int upperEnd) {
        int res = Integer.MAX_VALUE;
        fineInp = false;
        while (!fineInp) {
            fineInp = true;
            System.out.print("Enter " + message + ": ");
            try {
                res = Integer.parseInt(sc.nextLine());
                if (res < lowerEnd || res > upperEnd)
                    throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Try again");
                fineInp = false;
            }
        }
        return res;
    }

    public static short Shrt(String message) {
        short res = Short.MAX_VALUE;
        fineInp = false;
        while (!fineInp) {
            fineInp = true;
            System.out.print("Enter " + message + ": ");
            try {
                res = Short.parseShort(sc.nextLine());
                if (res < 0) throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Try again");
                fineInp = false;
            }
        }
        return res;
    }

    public static short Shrt(short lowerEnd, short upperEnd) {
        short res = Short.MAX_VALUE;
        fineInp = false;
        while (!fineInp) {
            fineInp = true;
            System.out.print("Enter your choice "
                    + "(between " + lowerEnd + "-" + upperEnd + "): ");
            try {
                res = Short.parseShort(sc.nextLine());
                if (res < lowerEnd || res > upperEnd)
                    throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Try again");
                fineInp = false;
            }
        }
        return res;
    }

    public static short Shrt(String message, short lowerEnd, short upperEnd) {
        short res = Short.MAX_VALUE;
        fineInp = false;
        while (!fineInp) {
            fineInp = true;
            System.out.print("Enter " + message
                    + " (between " + lowerEnd + "-" + upperEnd + "): ");
            try {
                res = Short.parseShort(sc.nextLine());
                if (res < lowerEnd || res > upperEnd)
                    throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Try again");
                fineInp = false;
            }
        }
        return res;
    }

    public static double Doub(String message) {
        double res = Double.MAX_VALUE;
        fineInp = false;
        while (!fineInp) {
            fineInp = true;
            System.out.print("Enter " + message + ": ");
            try {
                res = Double.parseDouble(sc.nextLine());
                if (res < 0) throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Try again");
                fineInp = false;
            }
        }
        return res;
    }

    public static double Doub(int lowerEnd, int upperEnd) {
        double res = Double.MAX_VALUE;
        fineInp = false;
        while (!fineInp) {
            fineInp = true;
            System.out.print("Enter your choice "
                    + " (between " + lowerEnd + "-" + upperEnd + "): ");
            try {
                res = Double.parseDouble(sc.nextLine());
                if (res < lowerEnd || res > upperEnd)
                    throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Try again");
                fineInp = false;
            }
        }
        return res;
    }
}