package view.old;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import controller.actions.PageRankApp;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.github.kwhat.jnativehook.keyboard.NativeKeyEvent.getKeyText;



public class InputListener implements NativeKeyListener {
    private PageRankApp app;

    public InputListener() {
        app = PageRankApp.getInstance();

        try {
            GlobalScreen.registerNativeHook(); // turns on the library's central listener
        } catch (NativeHookException e) {
            System.err.println("Critical error - there was a problem booting up the hook library.");
            throw new RuntimeException(e);
        }

        GlobalScreen.addNativeKeyListener(this); // registers this object as a listener for key events
        disableJNativeHookLogging();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
//        Printer.println("KEY PRESSED (" + getKeyText(event.getKeyCode()) + ")");
//        app.queueInput(getKeyText(event.getKeyCode()));
    }


    /**
     * JNativeHook outputs a lot of logs, so this subroutine disables them.
     */
    private void disableJNativeHookLogging() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
    }

    public void close() {
        try {
            GlobalScreen.removeNativeKeyListener(this);
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            // Stuff went real wrong, we just eat this one
            System.err.println("Critical error - there was a problem shutting down the hook library.");
            throw new RuntimeException(e);
        }
    }

}
