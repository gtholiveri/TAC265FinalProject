package controller.ui;

import controller.ui.login.LoginMenu;
import view.InputListener;
import view.Printer;

import java.util.concurrent.CountDownLatch;

import static controller.ui.AppState.*;

public class PageRankApp {
    private Menu currMenu;

    private AppState state = AppState.WAITING_FOR_INPUT;

    private CountDownLatch inputLatch;
    private CountDownLatch stallLatch;

    public static void main(String[] args) {
        PageRankApp app = new PageRankApp();
        app.execute();

    }

    public PageRankApp() {
        currMenu = new LoginMenu(this);
        new InputListener(this);

        inputLatch = new CountDownLatch(0);
        stallLatch = new CountDownLatch(0);

    }

    /**
     * Executes the main loop of the application<br>
     * - Render the current menu<br>
     * - wait for user input<br>
     */

    /*
     * GAHHH
     * this is pissing me off so bad
     * What I have discovered: Unintentionally making multithreaded applications makes things HARD
     * I think I know exactly what the issue is:
     * - upon receiving valid input while state = WAITING_FOR_INPUT, the InputListener fires, so the call stack goes:
     *         - nativeKeyPressed() -> handleKey() -> currMenu.fire(int) -> option.fire() -> stall()
     * - that somehow forces stall() to execute ON THE LISTENER'S THREAD
     * - which blocks the listener itself from receiving any further input
     *
     * Solution: I think I need to use a BlockingQueue of some sort to have like a producer consumer pattern to decouple the InputListener from
     * actually calling methods in PageRankApp, which I really shouldn't have been doing in the first place if I had been properly enforcing MVC separation
     *
     * I think I shied away from this previously because I was like "but what if multiple inputs stack in the queue?"
     * But that really won't be an issue / won't happen, it's fast enough that it's a non issue and real apps
     * let inputs stack up all the time, and it actually enhances the experience a lot of the time
     *
     * But that took me way too long to figure out so I'm going to just go to bed and fix later.
     * Basic UI concept is very solid I think, simultaneously massively enhances the usual terminal UI functionality
     * and involves multiple interlacing hierarchies

     */
    public void execute() {
        // TODO: implement the main execution loop of the whole app
        Printer.println("Welcome to PageRank, the competitive reading app!");
        stall();
        while (state != EXIT) {
            Printer.print(currMenu.getDisplay());
            waitForInput();
        }
    }

    public void handleKey(String keyText) {
        switch (state) {
            case WAITING_FOR_INPUT -> {
                inputLatch.countDown();
//                Printer.println("LATCH WAS COUNTED DOWN, IT'S NOW AT: " + inputLatch.getCount());


                try {
//                    Printer.println("ABOUT TO FIRE MENU WITH KEY " + keyText);
                    currMenu.fire(Integer.parseInt(keyText));
//                    Printer.println("JUST FIRED MENU WITH KEY " + keyText);
                } catch (NumberFormatException e) {
                    // do nothing, since non-numeric input isn't valid input
//                    Printer.println("WE TRIED TO PARSE IT BUT IT WAS BAD");
                }
            }
            case STALLING -> {
                state = WAITING_FOR_INPUT;
                stallLatch.countDown();
            }
            case EXIT -> {
                // do nothing, the main loop will naturally terminate
                Printer.println("What the fuck are we doing in here");
            }
        }
    }

    private void waitForInput() {
        state = WAITING_FOR_INPUT;
        Printer.println("WAITING FOR INPUT");
        inputLatch = new CountDownLatch(1);


        try {
            inputLatch.await();
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for input.");
            throw new RuntimeException(e);
        }
        Printer.println("WENT THROUGH INPUT LATCH");
    }

    public void stall() {
//        Printer.println("Press anything to continue.");
        Printer.println("STALLING FOR ANY KEY PRESS");
        state = STALLING;
        stallLatch = new CountDownLatch(1);

        try {
//            Printer.println("WAITING FOR STALLED CONTINUE CONFIRMATION");

            stallLatch.await();
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for input.");
            throw new RuntimeException(e);
        }
        Printer.println("WENT THROUGH STALL LATCH");
    }
}
