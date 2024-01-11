package com.company;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        System.out.print(PrintColor.yellow + "Press 1 to start, 2 to quit!\n" + PrintColor.cyan);
        while (Input.Shrt("choice", (short) 1, (short) 2) == 1) menu.mainMenu();
    }
}
