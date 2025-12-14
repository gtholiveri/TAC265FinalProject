package view;

import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import view.menu.title.TitleMenuFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the Lanterna GUI lifecycle and window transition<br>
 * Disclosure: I had AI run me through some code exercises on how this library works
 * and how to use it since I had forgotten all my Swing and needed to understand the specifics of
 * how this was working<br>
 * I implemented actual code on my own though
 */
public class GUIManager {
    private MultiWindowTextGUI gui;
    private Window currWindow;

    public GUIManager() {
        TerminalFactory tf = new DefaultTerminalFactory();

        try {
            // setting up all Lanterna's layers of abstraction
            // we will only need to deal with the gui object
            // *important note*: this will not run successfully in the
            // built-in intellij terminal, since that terminal does not
            // support ansi escape codes and it breaks Lanterna (there is
            // a Swing terminal emulator that theoretically should run but it
            // doesn't for some reason, even when you just force initialize a Swing
            // terminal without using the factory)
            Terminal t = tf.createTerminal(); // the lowest-level thing: basic putChar() and flush() type stuff
            Screen scr = new TerminalScreen(t); // one level up: treats the terminal like a screen, we can start putting things at row, col positions and drawing shapes and stuff
            scr.startScreen();
            gui = new MultiWindowTextGUI(scr); // the actually cool layer: Swing but in the terminal
            gui.setTheme(LanternaThemes.getRegisteredTheme("defrost")); // the least ugly theme

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // start off at the title menu
        currWindow = TitleMenuFactory.create();
    }

    public void start() {
        // add the window to the GUI's collection
        gui.addWindow(currWindow);

        // as long as the gui has *some* window
        while (!gui.getWindows().isEmpty()) {
            // snag a window (doesn't matter which one)
            Window w = gui.getWindows().iterator().next();

            // Block the main thread here until that specific window closes
            gui.waitForWindowToClose(w);
        }
        // If we get here, no windows are left, so the app exits
    }

    /**
     * Closes all the window(s) to exit the program
     */
    public void stop() {
        // make a copy and iterate backwards just to be safe
        // since I've been fighting with concurrent modification exceptions
        List<Window> windowsToClose = new ArrayList<>(gui.getWindows());

        for (int i = windowsToClose.size() - 1; i >= 0; i--) {
            windowsToClose.get(i).close();
        }
    }

    /**
     * The main transition method (since we are using only full screen windows for simplicity)
     * @param newWindow The window to transition to
     */
    public void transitionTo(Window newWindow) {
        // Add the new window to the GUI (it goes instantly, stacked on top)
        gui.addWindow(newWindow);

        // Close the old one
        if (currWindow != null) {
            currWindow.close();
        }

        // Update the reference
        currWindow = newWindow;
    }

    public MultiWindowTextGUI getGUI() {
        return gui;
    }
}
