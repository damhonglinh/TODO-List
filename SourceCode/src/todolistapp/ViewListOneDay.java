package todolistapp;

import datastructure.LinkedPriorityQueue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Dam Linh
 */
public class ViewListOneDay extends JPanel {

    private LinkedPriorityQueue<Task> tasks;
    private JScrollPane scroll;
    private KulButton back;
    private KulButton add;
    private Box allTaskBox;
    private JPanel centerPanel = new JPanel(new BorderLayout());
    private Model model;

    public ViewListOneDay(Model model) {
        this.model = model;
        setLayout(new BorderLayout());
        setBackground(Template.getBackground());

        add(centerPanel);

        drawButtonBar();
        drawCenterPanel();
        drawTitleBar();
    }

    public void setTasks(LinkedPriorityQueue<Task> tasks) {
        this.tasks = tasks;
        drawAllTasksInCenterPanel();
    }

    private void drawButtonBar() {
        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.setPreferredSize(new Dimension(2000, 50));

        back = new KulButton("Back");
        back.setPreferredSize(new Dimension(100, 30));
        back.setMaximumSize(new Dimension(100, 30));
        back.setFont(Template.getFont().deriveFont(0, 18));
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.cancelAndBack();
            }
        });

        add = new KulButton("New");
        add.setToolTipText("Create a new TODO");
        add.setPreferredSize(new Dimension(100, 30));
        add.setMaximumSize(new Dimension(100, 30));
        add.setFont(Template.getFont().deriveFont(0, 18));
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.showFormTask();
            }
        });
        buttonBox.add(Box.createHorizontalStrut(25));
        buttonBox.add(back);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(add);

        add(buttonBox, BorderLayout.NORTH);
    }

    private void drawCenterPanel() {
        allTaskBox = new Box(BoxLayout.Y_AXIS);
        scroll = new JScrollPane(allTaskBox);
        scroll.getViewport().setBackground(Template.getBackground());
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        centerPanel.add(scroll);
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
        centerPanel.add(new TitleBar(model), BorderLayout.NORTH);
    }
}
