package view.menu;

import com.googlecode.lanterna.gui2.*;

import java.util.Arrays;

public class OptionListWindowBuilder {
    private final BasicWindow window;
    private final Panel panel;

    public OptionListWindowBuilder(String title) {
        this(title, null);
    }

    public OptionListWindowBuilder(String title, String subtitle) {
        this.window = new BasicWindow(title);
        //noinspection ArraysAsListWithZeroOrOneArgument
        this.window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
//        panel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));

        if (subtitle != null) {
            panel.addComponent(new Label(subtitle));
        }

        window.setComponent(panel);
    }

    public OptionListWindowBuilder addAction(String label, Runnable action) {
        Button button = new Button(label, action);
        panel.addComponent(button);
        return this;
    }

    public BasicWindow build() {
        return window;
    }
}
