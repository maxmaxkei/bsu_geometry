package by.bsu.geometry.navigation.items;

import by.bsu.geometry.solution.Base;

public class NavigationItem<T extends Base> {

    private String label;
    private T solutionFrame;

    public NavigationItem(String label, T solutionFrame) {
        this.label = label;
        this.solutionFrame = solutionFrame;
        solutionFrame.setLabel(label);
    }

    public T getSolutionFrame() {
        return solutionFrame;
    }

    @Override
    public String toString() {
        return label;
    }
}
