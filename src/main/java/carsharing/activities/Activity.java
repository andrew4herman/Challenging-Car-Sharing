package carsharing.activities;

import java.util.Scanner;

public abstract class Activity {

    public final Scanner scanner;

    public Activity(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        String option;
        do {
            showMenu();
            option = scanner.nextLine();
            processOption(option);
        } while (!"0".equals(option));
    }

    public abstract void showMenu();

    public abstract void processOption(String option);
}
