import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import controller.PageRankApp;

import java.io.IOException;
import java.util.Arrays;

import static com.googlecode.lanterna.gui2.Window.Hint.CENTERED;
import static util.Utils.randInt;

public class Main {
    public static void main(String[] args) {
        runApp();
    }

    private static void runApp() {
        try {
            PageRankApp.getInstance().execute();
        } catch (Throwable e) {
            // Write to a file since Lanterna clears the screen
            try {
                java.io.PrintWriter pw = new java.io.PrintWriter("error_log.txt");
                pw.println("FATAL ERROR at " + java.time.LocalDateTime.now());
                pw.println("Exception: " + e.getClass().getName());
                pw.println("Message: " + e.getMessage());
                pw.println("\nStack trace:");
                e.printStackTrace(pw);
                pw.close();

                // Also print to stderr
                System.err.println("\n\n=== FATAL ERROR ===");
                System.err.println("Check error_log.txt for details");
                e.printStackTrace();
            } catch (Exception logError) {
                System.err.println("Failed to write error log: " + logError.getMessage());
            }

            // Keep console open
            System.err.println("\nPress Enter to exit...");
            try {
                System.in.read();
            } catch (Exception ignored) {}
        }
    }
    // new SwingTerminal(new TerminalSize(20, 40), null, null, null);


    private static void inputTest() {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();


        // initialize
        Terminal t;
        Screen scr;
        try {
            t = factory.createTerminal();
            scr = new TerminalScreen(t);
            scr.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // do stuff
        MultiWindowTextGUI gui = new MultiWindowTextGUI(scr);
// Colors
        TextColor white = TextColor.ANSI.WHITE;
        TextColor black = TextColor.ANSI.BLACK;
        TextColor blue = TextColor.ANSI.BLUE;
        TextColor cyan = TextColor.ANSI.CYAN;
        TextColor brightBlue = TextColor.ANSI.BLUE_BRIGHT;
        TextColor green = TextColor.ANSI.GREEN;
        TextColor red = TextColor.ANSI.RED;
        TextColor yellow = TextColor.ANSI.YELLOW;

// Create the theme using the factory method
        SimpleTheme myTheme = SimpleTheme.makeTheme(
                false,   // activeIsBold: Make the active item BOLD
                white,  // baseForeground: Normal text color
                blue,   // baseBackground: Normal background
                black,  // editableForeground: Text color when typing (e.g., inside TextBox)
                green,  // editableBackground: Background when typing (High Contrast!)
                red,  // selectedForeground: Text color for selected buttons
                yellow,   // selectedBackground: Background for selected buttons
                cyan    // guiBackground: The background of the whole window
        );
        myTheme.setWindowPostRenderer(new WindowShadowRenderer());

        // Override the shadow color to get a nice dark shadow effect
        myTheme.addOverride(WindowShadowRenderer.class, white, TextColor.ANSI.BLACK);


        gui.setTheme(LanternaThemes.getRegisteredTheme("default"));


        BasicWindow window = new BasicWindow("Le Application");
        //noinspection ArraysAsListWithZeroOrOneArgument
        window.setHints(Arrays.asList(CENTERED));

        Panel panel = new Panel(new GridLayout(2));

        panel.addComponent(new Label("Username: "));
        TextBox userBox = new TextBox();
        panel.addComponent(userBox);


        panel.addComponent(new Label("Password: "));
        TextBox passBox = new TextBox();
        panel.addComponent(passBox);


        Label statusMessage = new Label("");
        Button logButton = new Button("Log In", () -> {
            panel.removeComponent(statusMessage);

            String user = userBox.getText();
            String pw = passBox.getText();

            if (user.equals("g") && pw.equals("123")) {
                statusMessage.setText("BAHAHAHA IT WORKEDDDD");
                panel.addComponent(statusMessage);
            } else {
                statusMessage.setText("Try again");
                panel.addComponent(statusMessage);
            }
        });
        panel.addComponent(logButton);

        window.setComponent(panel);
        gui.addWindowAndWait(window);
        // clean up
        try {
            scr.stopScreen();
            t.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void singleWindowTest() {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        // ok so for whatever reason, there's literally no universe in which running from intellij works on this
        // version of lanterna
        // so thanks for that google lmao

        // initialize
        Terminal t;
        Screen scr;
        try {
            t = factory.createTerminal();
            scr = new TerminalScreen(t);
            scr.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // do stuff
        MultiWindowTextGUI gui = new MultiWindowTextGUI(scr);
        BasicWindow window = new BasicWindow("Le Application");
        //noinspection ArraysAsListWithZeroOrOneArgument
        window.setHints(Arrays.asList(CENTERED));

        Panel contentPanel = new Panel(new LinearLayout());
        contentPanel.addComponent(new Label("What happens? I really don't know"));
        contentPanel.addComponent(new Button("Exit", window::close));

        String[] possibilities = {"hit me", "something", "the time has come", "come on do it", "don't quit"};
        Button coolButton = new Button(possibilities[randInt(0, possibilities.length)]);
        coolButton.addListener(buttonClickEvent -> {
            coolButton.setLabel(possibilities[randInt(0, possibilities.length)]);
        });
        contentPanel.addComponent(coolButton);


        window.setComponent(contentPanel);
        gui.addWindowAndWait(window);
        // clean up
        try {
            scr.stopScreen();
            t.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
