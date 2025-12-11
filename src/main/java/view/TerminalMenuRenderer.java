package view;

import controller.ui.Menu;
import controller.ui.MenuOption;

import java.util.EnumSet;
import java.util.List;

import static view.TextFormat.BOLD;
import static view.TextFormat.ITALIC;

public class TerminalMenuRenderer implements MenuRenderer {
    @Override
    public void render(Menu menu) {
        Printer.clearPrint("");

        Printer.printFormatted(menu.getLabel(), EnumSet.of(BOLD, ITALIC));
        
        List<MenuOption> options = menu.getOptions();

        @SuppressWarnings("ReassignedVariable")
        int i = 1;
        for (MenuOption option : options) {
            Printer.print("\n(" + i + ") " + option.getLabel());
            i++;
        }
    }
}
