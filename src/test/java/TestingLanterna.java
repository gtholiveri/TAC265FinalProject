import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class TestingLanterna {
    public static void runTest() {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        try (Terminal terminal = factory.createTerminal()){
            terminal.putCharacter('c');
            terminal.flush();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
