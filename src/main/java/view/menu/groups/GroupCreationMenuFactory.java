package view.menu.groups;

import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import controller.PageRankApp;
import controller.actions.groups.SubmitCreateGroupAction;
import model.Book;
import view.menu.PopupFactory;
import view.menu.TextBoxListWindowBuilder;

/**
 * The factory for the group creation menu
 */
public class GroupCreationMenuFactory {
    private Book selectedBook;

    public GroupCreationMenuFactory() {
        this.selectedBook = null;
    }

    public Window create() {
        PageRankApp app = PageRankApp.getInstance();

        // use our own custom builder that's for mixed text box / button forms
        TextBoxListWindowBuilder b = new TextBoxListWindowBuilder("Create Group");
        b.addTextBox("Group Name:", 30, false);

        // we need to pass this in
        TextBox groupNameBox = b.getTextBox(0);

        // select book button: the lambda makes it pop up a dialog with all
        // the books in their library
        // didn't encapsulate this because it would've been very annoying
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

            // pop the selection dialog onto the gui and capture the selected output
            // this is why we had the toString() method in Book
            selectedBook = dialogBuilder.build().showDialog(app.guiManager.getGUI());
        });

        // this seems really cursed but it actually makes sense
        // wanted to encapsulate the long logic of this action but it needs the result of the popup's
        // Book selection *at button press time*
        // which won't happen if it receives a variable with a null value first (was thinking originally it would get
        // side-effected and updated but no)
        // so the wrapping it in the lambda so makes it so selectedBook gets
        // read only when the button is pressed, not when the window is created
        b.addButton("Create Group", () -> {
            new SubmitCreateGroupAction(groupNameBox, selectedBook).run();
        });


        b.addButton("Back", () -> {
            // me when I started realizing maybe not every one-line lambda needs its own class
            app.transitionTo(GroupsMenuFactory.create());
        });

        return b.build();
    }
}
