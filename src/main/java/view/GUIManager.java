package view;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import view.menu.TitleMenuFactory;

import java.io.IOException;
import java.util.List;


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
            // doesn't for some reason)
            Terminal t = tf.createTerminal();
            Screen scr = new TerminalScreen(t);
            scr.startScreen();
            gui = new MultiWindowTextGUI(scr);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currWindow = TitleMenuFactory.create();
    }

    public void start() {
        // 1. Add the first window (Non-blocking add)
        gui.addWindow(currWindow);

        // 2. The Main Loop: Keep running while any window is open
        while (!gui.getWindows().isEmpty()) {
            // Pick one of the active windows (doesn't matter which one)
            Window w = gui.getWindows().iterator().next();

            // Block the main thread here until that specific window closes
            gui.waitForWindowToClose(w);
        }
        // If we get here, no windows are left, so the app exits gracefully.
    }

    public void stop() {
        gui.getWindows().iterator().forEachRemaining(Window::close);
    }

    public void transitionTo(Window newWindow) {
        // 1. Add the new window to the GUI (it appears immediately)
        gui.addWindow(newWindow);

        // 2. Close the old window
        if (currWindow != null) {
            currWindow.close();
        }

        // 3. Update your reference
        currWindow = newWindow;
    }

    public MultiWindowTextGUI getGUI() {
        return gui;
    }
}
