package view;

import model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BookPaginator {
    Book book;
    String bookText;

    public BookPaginator(Book book) {
        this.book = book;
        loadFullBookText();
    }

    private void loadFullBookText() {
        try {
            bookText = Files.readString(Paths.get(book.getPersistentPath()));
        } catch (IOException e) {
            bookText = "";
        }
    }

    public String prevPage(int rows, int cols) {
        // Calculate the start of previous page by going backwards
        String prevPageText = currentPage(rows, cols);
        int currentPos = book.getPosition();

        // Go back approximately one page worth of characters
        int estimatedCharsPerPage = rows * cols / 2; // rough estimate
        int newPos = Math.max(0, currentPos - estimatedCharsPerPage * 2);

        book.setPosition(newPos);

        // Now find the actual position that gives us the page before current
        return currentPage(rows, cols);
    }

    public String nextPage(int rows, int cols) {
        currentPage(rows, cols); // This updates position to end of current page
        return currentPage(rows, cols); // Get next page
    }

    /**
     * Return a String that adheres to the specified number of rows and columns:<br>
     * - basically, start at position and go through the text adding one word at a time to the to-be-returned thing, inserting a newline
     * whenever necessary to wrap the text while respecting the specified number of columns<br>
     * - respect and account for existing newlines<br>
     * - stop when the output string has the specified number of rows
     * - update position to be the position of the first letter on the page relative to the whole book (so like character 20,000 or whatever)
     *
     */
    public String currentPage(int rows, int cols) {
        int position = book.getPosition();

        if (position >= bookText.length()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int currentRow = 0;
        int currentCol = 0;
        int textIndex = position;

        while (currentRow < rows && textIndex < bookText.length()) {
            // Find the next word or newline
            int wordStart = textIndex;

            // Check if we're at a newline
            if (bookText.charAt(textIndex) == '\n') {
                result.append('\n');
                currentRow++;
                currentCol = 0;
                textIndex++;
                continue;
            }

            // Skip leading spaces at start of line
            while (textIndex < bookText.length() && bookText.charAt(textIndex) == ' ' && currentCol == 0) {
                textIndex++;
            }

            if (textIndex >= bookText.length()) break;

            // Find end of current word
            int wordEnd = textIndex;
            while (wordEnd < bookText.length() && bookText.charAt(wordEnd) != ' ' && bookText.charAt(wordEnd) != '\n') {
                wordEnd++;
            }

            String word = bookText.substring(textIndex, wordEnd);
            int wordLength = word.length();

            // Check if word fits on current line
            if (currentCol + wordLength > cols) {
                // Word doesn't fit, move to next line
                if (currentCol > 0) {
                    result.append('\n');
                    currentRow++;
                    currentCol = 0;
                }

                if (currentRow >= rows) break;

                // Handle word longer than line width
                if (wordLength > cols) {
                    // Split word across lines
                    int remaining = wordLength;
                    int wordPos = 0;
                    while (remaining > 0 && currentRow < rows) {
                        int chunkSize = Math.min(cols, remaining);
                        result.append(word, wordPos, wordPos + chunkSize);
                        wordPos += chunkSize;
                        remaining -= chunkSize;

                        if (remaining > 0) {
                            result.append('\n');
                            currentRow++;
                            currentCol = 0;
                        } else {
                            currentCol = chunkSize;
                        }
                    }
                    textIndex = wordEnd;
                } else {
                    // Word fits on new line
                    result.append(word);
                    currentCol = wordLength;
                    textIndex = wordEnd;
                }
            } else {
                // Word fits on current line
                if (currentCol > 0 && textIndex > wordStart) {
                    // Add space before word if not at start of line
                    result.append(' ');
                    currentCol++;
                }
                result.append(word);
                currentCol += wordLength;
                textIndex = wordEnd;
            }

            // Move past any trailing spaces
            if (textIndex < bookText.length() && bookText.charAt(textIndex) == ' ') {
                textIndex++;
            }
        }

        // Update book position to where we stopped
        book.setPosition(textIndex);

        return result.toString();
    }

}
