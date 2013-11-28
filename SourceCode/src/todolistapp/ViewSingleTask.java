package todolistapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

/**
 *
 * @author Dam Linh - s3372757
 */
public class ViewSingleTask extends JPanel {

    private Task task;
    private Font f = Template.getFont();
    private JLabel title;
    private Box boxDate = new Box(BoxLayout.X_AXIS);
    private JTextArea description;
    private JScrollPane scroll;
    private JLabel time;
    private JLabel date;
    private JLabel prio;
    private KulButton edit = new KulButton("Edit");
    private KulButton delete = new KulButton("Delete");
    private KulButton back = new KulButton("Back");
    private Model model;

    public ViewSingleTask(Model model) {
        this.model = model;
    }

    public void setTask(Task task) {
        this.task = task;
        initialize();
    }

    private void initialize() {
        removeAll();
        boxDate.removeAll();

        SpringLayout sprLayout = new SpringLayout();
        setLayout(sprLayout);
        setBackground(Template.getBackground());

        drawTitle();
        drawDateTime();
        drawDescription();
        drawButton();

        sprLayout.putConstraint(SpringLayout.NORTH, title, 40, SpringLayout.NORTH, this);
        sprLayout.putConstraint(SpringLayout.WEST, title, 40, SpringLayout.WEST, this);
        sprLayout.putConstraint(SpringLayout.SOUTH, boxDate, 27, SpringLayout.SOUTH, title);
        sprLayout.putConstraint(SpringLayout.WEST, boxDate, 100, SpringLayout.WEST, this);
        sprLayout.putConstraint(SpringLayout.NORTH, scroll, 15, SpringLayout.SOUTH, boxDate);
        sprLayout.putConstraint(SpringLayout.SOUTH, scroll, -30, SpringLayout.SOUTH, this);
        sprLayout.putConstraint(SpringLayout.WEST, scroll, 100, SpringLayout.WEST, this);
        sprLayout.putConstraint(SpringLayout.EAST, scroll, -50, SpringLayout.EAST, this);
        sprLayout.putConstraint(SpringLayout.EAST, delete, -30, SpringLayout.EAST, this);
        sprLayout.putConstraint(SpringLayout.NORTH, delete, 10, SpringLayout.NORTH, this);
        sprLayout.putConstraint(SpringLayout.NORTH, edit, 10, SpringLayout.NORTH, this);
        sprLayout.putConstraint(SpringLayout.EAST, edit, -150, SpringLayout.EAST, this);
        sprLayout.putConstraint(SpringLayout.EAST, back, -270, SpringLayout.EAST, this);
        sprLayout.putConstraint(SpringLayout.NORTH, back, 10, SpringLayout.NORTH, this);
    }

    private void drawTitle() {
        title = new JLabel(task.getTitle());
        title.setFont(f.deriveFont(1, 55));
        add(title);
    }

    private void drawDateTime() {
        add(boxDate);
        Font font = f.deriveFont(0, 15);

        time = new JLabel(task.getTimeString());
        time.setFont(font);
        date = new JLabel(task.getDateString());
        date.setFont(font);
        prio = new JLabel("Priority: " + task.getPriority());
        prio.setFont(font);

        boxDate.add(time);
        boxDate.add(Box.createHorizontalStrut(25));
        boxDate.add(date);
        boxDate.add(Box.createHorizontalStrut(25));
        boxDate.add(prio);
    }

    private void drawDescription() {
        description = new JTextArea(task.getDescription());
        scroll = new JScrollPane(description);
        scroll.setBorder(null);
        add(scroll);

        description.setBackground(Template.getBackground());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setDisabledTextColor(Color.BLACK);
        description.setFont(f.deriveFont(0, 24));
    }

    private void drawButton() {
        add(edit);
        add(delete);
        add(back);

        delete.setFont(f.deriveFont(0, 20));
        delete.setPreferredSize(new Dimension(100, 30));
        edit.setFont(f.deriveFont(0, 20));
        edit.setPreferredSize(new Dimension(100, 30));
        back.setFont(f.deriveFont(0, 20));
        back.setPreferredSize(new Dimension(100, 30));

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.cancelAndBack();
            }
        });

        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.showFormTask(task);
            }
        });

        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.delete(task);
            }
        });
    }
}
