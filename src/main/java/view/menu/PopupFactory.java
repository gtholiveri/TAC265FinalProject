package view.menu;

import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import controller.PageRankApp;

public class PopupFactory {
    public static void showPopup(String title, String text) {
        new MessageDialogBuilder()
                .setTitle(title)
                .setText(text)
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(PageRankApp.getInstance().guiManager.getGUI());
    }
}
