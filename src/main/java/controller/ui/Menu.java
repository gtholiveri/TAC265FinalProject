package controller.ui;

import java.util.ArrayList;
import java.util.List;

import static util.Utils.validIndex;

public abstract class Menu implements Displayable {
    protected PageRankApp app;
    protected List<MenuOption> options;

    public Menu(PageRankApp app) {
        this.app = app;
        options = new ArrayList<>();
        addOptions();
    }

    protected abstract void addOptions();


    public void fire(int keyPressed) {
        int i = keyPressed - 1;
        if (validIndex(i, options.size())) {
            options.get(i).fire();
        }
    }
    
    @Override
    public String getDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            sb.append("(").append(i + 1).append(") ").append(options.get(i).getDisplay()).append("\n");
        }
        return sb.toString();
    }

    /**
     * @param input A String containing a key pressed by the user
     * @return true if it's an int within the valid range of menu options, false otherwise
     */
    public boolean validInput(String input) {
        try {
            int sel = Integer.parseInt(input);
            int i= sel - 1;
            return validIndex(i, options.size());

        } catch (NumberFormatException e) {
            // not a number
            return false;
        }
    }
}
