package controller.ui;

import controller.ui.login.LoginMenu;
import view.InputListener;
import view.Printer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static controller.ui.AppState.*;

public class PageRankApp {
    private Menu currMenu;

    private AppState state = AppState.WAITING_FOR_INPUT;

    private BlockingQueue<String> inputQueue;

    public static void main(String[] args) {
        PageRankApp app = new PageRankApp();
        app.execute();

    }

    public PageRankApp() {
        currMenu = new LoginMenu(this);
        new InputListener(this);

        inputQueue = new ArrayBlockingQueue<>(1);

    }

    /**
     * Executes the main loop of the application<br>
     * - Render the current menu<br>
     * - wait for user input<br>
     */
    public void execute() {
        Printer.println("Welcome to PageRank, the competitive reading app!");
        stall();
        while (state != EXIT) {
            Printer.clearPrintln(currMenu.getDisplay());
            waitForInput();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void queueInput(String input) {
        inputQueue.offer(input);
    }

    private void waitForInput() {
        state = WAITING_FOR_INPUT;

        try {
            // block until we get some input that is valid
            String input = "";
            while (!currMenu.validInput(input)) {
                input = inputQueue.take();
            }

            // once we get valid input, fire the menu off of that
            // this might be fragile or there might be a cleaner way to do this but I think it's fairly readable
            currMenu.fire(Integer.parseInt(input));
            
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for input.");
            throw new RuntimeException(e);
        }
    }

    public void stall() {
        Printer.println("Press anything to continue.");
        state = STALLING;

        try {
            // block until we get some input, doesn't matter what it is
            inputQueue.take();
        } catch (InterruptedException e) {
            System.err.println("Interrupted while stalling for confirmation key press.");
            throw new RuntimeException(e);
        }

    }
}
