package view.menu;

import com.googlecode.lanterna.gui2.*;

import java.util.Arrays;

/**
 * Custom builder that creates a basic window with a vertical list of buttons<br>
 * Again heavily inspired by Lanterna's own builders
 */
public class OptionListWindowBuilder {
    private final BasicWindow window;
    private final Panel panel;

    /**
     * Default constructor, no subtitle
     */
    public OptionListWindowBuilder(String title) {
        this(title, null);
    }

    /**
     * Main constructor with possible subtitle, sets up the top-level lanterna components (Window and Panel, adds a label with the subtitle if it's not null)
     */
    public OptionListWindowBuilder(String title, String subtitle) {
        this.window = new BasicWindow(title);
        // the expanded hint just makes it almost fill the screen but not enough
        // to wipe the nice shadow effect
        //noinspection ArraysAsListWithZeroOrOneArgument
        this.window.setHints(Arrays.asList(Window.Hint.EXPANDED));

        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
//        panel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));

        if (subtitle != null) {
            panel.addComponent(new Label(subtitle));
        }

        window.setComponent(panel);
    }

    /**
     * Adds an action to the list
     */
    public void addAction(String label, Runnable action) {
        Button button = new Button(label, action);
        panel.addComponent(button);
    }

    /**
     * @return the constructed window
     */
    public BasicWindow build() {
        return window;
    }
}
