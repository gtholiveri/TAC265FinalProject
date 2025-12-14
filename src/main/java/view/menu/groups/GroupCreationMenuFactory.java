package view.menu.groups;

import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import controller.PageRankApp;
import controller.exceptions.NameTakenException;
import model.Book;
import model.persistence.GroupDatabaseManager;
import model.user.User;
import view.menu.PopupFactory;
import view.menu.TextBoxListWindowBuilder;

import java.util.ArrayList;
import java.util.List;

public class GroupCreationMenuFactory {
    private Book selectedBook;

    public GroupCreationMenuFactory() {
        this.selectedBook = null;
    }

    public Window create() {
        PageRankApp app = PageRankApp.getInstance();

        TextBoxListWindowBuilder b = new TextBoxListWindowBuilder("Create Group");
        b.addTextBox("Group Name:", 30, false);

        TextBox groupNameBox = b.getTextBox(0);

        b.addButton("Select Book", () -> {
            ListSelectDialogBuilder<Book> dialogBuilder = new ListSelectDialogBuilder<>();
            dialogBuilder.setTitle("Select Book");

            if (app.getCurrentUser().getBooks().isEmpty()) {
                PopupFactory.showPopup("Error", "You have no books to select from. Go to the library menu to add books!");
                return;
            }

            for (Book book : app.getCurrentUser().getBooks()) {
                dialogBuilder.addListItem(book);
            }

            selectedBook = dialogBuilder.build().showDialog(app.guiManager.getGUI());
        });

        b.addButton("Create Group", () -> {
            String groupName = groupNameBox.getText().trim();

            // Validate group name
            if (groupName.isEmpty()) {
                PopupFactory.showPopup("Error", "Please enter a group name.");
                return;
            }

            // Validate book selection
            if (selectedBook == null) {
                PopupFactory.showPopup("Error", "Please select a book.");
                return;
            }

            try {
                GroupDatabaseManager.createGroup(groupName, selectedBook, app.getCurrentUser());
                PopupFactory.showPopup("Success", "Group '" + groupName + "' created successfully!");
                app.transitionTo(GroupsMenuFactory.create());
            } catch (NameTakenException e) {
                PopupFactory.showPopup("Error", "Group name is taken. Please select another.");
            }
        });


        b.addButton("Back", () -> {
            app.transitionTo(GroupsMenuFactory.create());
        });

        return b.build();
    }
}
