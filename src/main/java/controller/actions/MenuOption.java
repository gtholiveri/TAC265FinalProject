package controller.actions;


public abstract class MenuOption implements Labeled {
    protected PageRankApp app;
    protected String label;

    public MenuOption(PageRankApp app, String label) {
        this.app = app;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract void fire();
}
