package controller.ui;

/**
 * Interface for objects that can be displayed to the user in some way.
 * It might not be strictly necessary, but it could be useful in the ereader context since that well
 * have a very heterogeneous set of elements, has pagination nonsense etc so idk<br>
 * For now it's just a fancy label
 */
public interface Labeled {
    String getLabel();
}
