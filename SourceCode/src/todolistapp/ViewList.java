package todolistapp;

import datastructure.*;
import java.awt.BorderLayout;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Dam Linh
 */
public class ViewList extends JPanel {

    private LinkedPriorityQueue<Task> tasks;
    private JScrollPane scroll;
    private Box allTaskBox;
    private JPanel subCenterPanel = new JPanel(new BorderLayout());
    private ButtonBar buttonBar;
    private MenuBar menuBar = new MenuBar();
    private Model model;
    private JPanel centerPanel;

    public ViewList(Model model) {
        this.model = model;
        tasks = model.getTasks();
        setLayout(new BorderLayout());

        menuBar.addListener(new MenuBarListener(model));
        buttonBar = new ButtonBar(model);
        centerPanel = new JPanel(new BorderLayout());

        add(centerPanel);
        add(menuBar, BorderLayout.NORTH);
        centerPanel.add(subCenterPanel);
        centerPanel.add(buttonBar, BorderLayout.NORTH);

        drawSubCenterPanel();
    }

    private void drawSubCenterPanel() {
        allTaskBox = new Box(BoxLayout.Y_AXIS);
        scroll = new JScrollPane(allTaskBox);
        scroll.getViewport().setBackground(Template.getBackground());
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        subCenterPanel.add(scroll);

        drawTitleBar();
        drawAllTasksInCenterPanel();
    }

    private void drawAllTasksInCenterPanel() {
        allTaskBox.removeAll();
        LineTask.resetCount();
        Iterator<Task> iter = tasks.getIterator();
        while (iter.hasNext()) {
            allTaskBox.add(new LineTask(iter.next(), model));
        }
    }

    private void drawTitleBar() {
        subCenterPanel.add(new TitleBar(model), BorderLayout.NORTH);
    }

    public void refresh() {
        drawAllTasksInCenterPanel();
        allTaskBox.revalidate();
        allTaskBox.repaint();
    }
}
