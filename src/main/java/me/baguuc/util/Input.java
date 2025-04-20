package me.baguuc.util;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    public static String inputString(String msg) {
        System.out.print(msg);
        String in = scanner.next();

        return in;
    }

    public static int inputInteger(String msg) {
        System.out.print(msg);
        int in = scanner.nextInt();

        return in;
    }

    public static float inputFloat(String msg) {
        System.out.print(msg);
        float in = scanner.nextFloat();

        return in;
    }

    public static boolean inputBoolean(String msg) {
        System.out.print(msg);
        boolean in = scanner.nextBoolean();

        return in;
    }
}
