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

    }

    public void start() {
        gui.addWindow(TitleMenuFactory.create());
    }


    public void stop() {

    }

    public void transitionTo(Window newWindow) {
        for (Window w : List.copyOf(gui.getWindows())) {
            // copy because we don't want to concurrently modify the list inside the gui
            // since it may automatically try to remove elements from the list as we iterate
            w.close();
        }
        gui.addWindow(newWindow);
    }
}
