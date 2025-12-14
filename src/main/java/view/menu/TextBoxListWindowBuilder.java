package view.menu;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder for creating windows with a list of labeled text boxes and buttons.
 * Useful for login forms, account creation forms, and data entry dialogs.
 */
public class TextBoxListWindowBuilder {
    private BasicWindow window;
    Panel mainPanel;
    Panel inputPanel;
    private Panel buttonPanel;

    private List<TextBox> textBoxes = new ArrayList<>();

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
     * Adds a text field to the form.
     *
     * @param label  The label to display next to the field
     * @param width  The width of the text box in characters
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
     * Adds a button to the form.
     * Can be called before or after build().
     *
     * @param label  The button label
     * @param action The action to execute when clicked
     */
    public void addButton(String label, Runnable action) {
        buttonPanel.addComponent(new Button(label, action));
    }

    /**
     * Builds the window with all specified fields and buttons.
     *
     * @return The constructed window
     */
    public Window build() {
        return window;
    }

    /**
     * Gets a text box by its index (in the order fields were added).
     *
     * @param index The field index
     * @return The text box at that index
     */
    public TextBox getTextBox(int index) {
        return textBoxes.get(index);
    }

    /**
     * Gets all text boxes.
     *
     * @return A copy of the text box list
     */
    public List<TextBox> getTextBoxes() {
        return new ArrayList<>(textBoxes);
    }

}
