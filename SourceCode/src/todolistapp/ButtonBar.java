package todolistapp;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 *
 * @author Dam Linh - s3372757
 */
public class ButtonBar extends JPanel {

    private KulButton add = new KulButton("New");
    private KulButton delete = new KulButton("Delete");
    private KulButton all = new KulButton("All");
    private KulButton switchView = new KulButton("Month");
    private Font f = Template.getFont().deriveFont(0, 20);
    private Box box = new Box(BoxLayout.X_AXIS);
    private Model model;

    public ButtonBar(Model model) {
        this.model = model;
        setBackground(Template.getBackground());
        setPreferredSize(new Dimension(2000, 50));
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        add(box);

        springLayout.putConstraint(SpringLayout.WEST, box, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, box, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.EAST, box, -20, SpringLayout.EAST, this);

        drawAddButton();
        box.add(Box.createHorizontalStrut(20));
        drawAllButton();
        box.add(Box.createHorizontalStrut(20));
        drawDeleteButton();
        box.add(Box.createHorizontalGlue());
        drawSwitchViewButton();
        addListener();
    }

    private void drawAddButton() {
        box.add(add);

        add.setPreferredSize(new Dimension(100, 30));
        add.setMaximumSize(new Dimension(100, 30));
        add.setFont(f);
        add.setToolTipText("Create a new TODO");
    }

    private void drawAllButton() {
        box.add(all);

        all.setPreferredSize(new Dimension(100, 30));
        all.setMaximumSize(new Dimension(100, 30));
        all.setFont(f);
        all.setToolTipText("Select/Deselect All");
    }

    private void drawDeleteButton() {
        box.add(delete);

        delete.setPreferredSize(new Dimension(100, 30));
        delete.setMaximumSize(new Dimension(100, 30));
        delete.setFont(f);
        delete.setToolTipText("Delete selected task(s)");
    }

    private void drawSwitchViewButton() {
        box.add(switchView);

        switchView.setToolTipText("Switch to Month View");
        switchView.setPreferredSize(new Dimension(100, 30));
        switchView.setMaximumSize(new Dimension(100, 30));
        switchView.setFont(f);
    }

    private void addListener() {
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.showFormTask();
            }
        });


        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.deleteSelected();
            }
        });

        all.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.selectAll();
            }
        });

        switchView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.switchView();
            }
        });
    }
}
