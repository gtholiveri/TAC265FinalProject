package controller.ui;


public abstract class MenuOption implements Displayable {
    protected PageRankApp app;

    public MenuOption(PageRankApp app) {
        this.app = app;
    }

    public abstract void fire();
}
