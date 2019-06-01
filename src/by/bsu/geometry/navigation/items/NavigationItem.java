package by.bsu.geometry.navigation.items;

public class NavigationItem {

    private String label;

    public NavigationItem(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }
}
