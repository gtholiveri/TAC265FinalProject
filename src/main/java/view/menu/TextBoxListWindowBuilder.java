package view.menu;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder for creating windowed forms with a mix of textbox and button inputs
 * The design for this I basically just ripped by reading through a bunch of the lanternan source
 * code since it struck me as clever and quite convenient, and it ended up saving me a *ton* of boilerplate
 */
public class TextBoxListWindowBuilder {
    private BasicWindow window;
    Panel mainPanel;
    Panel inputPanel;
    private Panel buttonPanel;

    private List<TextBox> textBoxes = new ArrayList<>();

    /**
     * This constructor basically just initializes all the top-level lanterna components and stuffs them in instance variables so the later actual builder methods can mutate them
     * @param title
     */
    public TextBoxListWindowBuilder(String title) {
        window = new BasicWindow(title);
        window.setHints(Arrays.asList(Window.Hint.EXPANDED));

        mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        inputPanel = new Panel(new GridLayout(2));

        this.buttonPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        mainPanel.addComponent(inputPanel);
        mainPanel.addComponent(buttonPanel);

        window.setComponent(mainPanel);
    }

    /**
     * Adds a text field to the form
     *
     * @param label  Label that goes next to the field
     * @param width  Width of text box (chars)
     * @param masked Whether to mask the input (for passwords)
     */
    public void addTextBox(String label, int width, boolean masked) {
        inputPanel.addComponent(new Label(label));
        TextBox textBox = new TextBox(new TerminalSize(width, 1));
        inputPanel.addComponent(textBox);
        if (masked) {
            textBox.setMask('*');
        }
        textBoxes.add(textBox);
    }

    /**
     * Adds button to the form
     */
    public void addButton(String label, Runnable action) {
        buttonPanel.addComponent(new Button(label, action));
    }

    /**
     * @return The constructed window
     */
    public Window build() {
        return window;
    }

    /**
     * Gets a text box by its index (order they were added)<br>
     * This is useful since sometimes we want to use this form and also pass its text boxes
     * to encapsulated actions that need to evaluate the box at press time
     */
    public TextBox getTextBox(int index) {
        return textBoxes.get(index);
    }

}
