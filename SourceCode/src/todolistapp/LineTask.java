package todolistapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Dam Linh
 */
public class LineTask extends JPanel {

    private static int count = 1;
    private Color defaultBackgroundColor;
    private Color currentBackgroundColor;
    private Color selectedBackgroundColor = Template.getBackgroundSelected();
    private Task task;
    private Model model;
    private Font f = Template.getFont().deriveFont(0, 20);
    private Color borderColor = Template.getBorderMouseOver();
    private Border mouseClickedBorder = new CompoundBorder(new MatteBorder(1, 0, 0, 1, currentBackgroundColor), new MatteBorder(1, 2, 1, 1, borderColor));
    // the mouseOverBorder has 3 pixels thickness but that of the "defaultBorder" is 2 so that this component can look like "bouncing"
    private Border mouseOverBorder = new CompoundBorder(new MatteBorder(1, 1, 1, 1, borderColor), new MatteBorder(1, 2, 1, 0, currentBackgroundColor));

    public LineTask(Task task, Model model) {
        this.model = model;
        this.task = task;
            drawBackground();
        setLayout(new GridBagLayout());
        setMaximumSize(new Dimension(2200, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 20;
        c.ipady = 20;
        c.fill = GridBagConstraints.BOTH;

        //PRIORITY
        Color prioColor;
        int prio = task.getPriority();
        switch (prio) {
            case 10:
                prioColor = Color.red;
                break;
            case 9:
                prioColor = new Color(255, 54, 97);
                break;
            case 8:
                prioColor = new Color(220, 79, 240);
                break;
            case 7:
                prioColor = new Color(198, 77, 222);
                break;
            case 6:
                prioColor = new Color(169, 83, 230);
                break;
            case 5:
                prioColor = new Color(113, 72, 247);
                break;
            case 4:
                prioColor = new Color(79, 106, 250);
                break;
            case 3:
                prioColor = new Color(50, 165, 255);
                break;
            case 2:
                prioColor = new Color(0, 183, 255);
                break;
            default:
                prioColor = new Color(64, 140, 255);
        }
        JLabel priority = new JLabel(prio + "");
        priority.setHorizontalAlignment(SwingConstants.CENTER);
        priority.setFont(f);
        priority.setForeground(prioColor);
        priority.setPreferredSize(new Dimension(60, 16));
        add(priority, c);

        //DATE
        JLabel date = new JLabel(task.getDateString().toString());
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setFont(f);
        date.setPreferredSize(new Dimension(180, 16));
        add(date, c);

        //TIME
        JLabel time = new JLabel(task.getTimeString());
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setFont(f);
        time.setPreferredSize(new Dimension(70, 16));
        add(time, c);

        c.weightx = 0.5d;
        c.weighty = 0.5d;

        //TITLE + DELETE + FINISH
        Box titleBox = new Box(BoxLayout.X_AXIS);
        add(titleBox, c);
        //DELETE
        JLabel delete = new KulInvisibleButton("Delete");
        delete.setMaximumSize(new Dimension(50, 30));
        delete.setPreferredSize(new Dimension(50, 30));
        delete.setFont(f.deriveFont(0, 16));
        delete.setForeground(Color.red);
        delete.addMouseListener(new ButtonListener());
        //FINISH
        JLabel finish = new KulInvisibleButton("Finish");
        finish.setMaximumSize(new Dimension(50, 30));
        finish.setPreferredSize(new Dimension(50, 30));
        finish.setFont(f.deriveFont(0, 16));
        finish.setForeground(Color.red);
        finish.addMouseListener(new ButtonListener());
        //TITLE
        JLabel title = new JLabel(task.getTitle());
        title.setFont(f);

        titleBox.setPreferredSize(new Dimension(220, 16));
        titleBox.add(title);
        titleBox.add(Box.createHorizontalGlue());
        titleBox.add(finish);
        titleBox.add(delete);

        addMouseListener(new LineTaskViewListener());
        addMouseListener(new LineTaskActionListener());
    }

    private void drawBackground() {
        if (count % 2 == 0) {
            defaultBackgroundColor = Template.getEvenLine();
        } else {
            defaultBackgroundColor = Template.getOddLine();
        }
        setCurrentBackgroundColor();
        setBackground(currentBackgroundColor);
        setBorder(BorderFactory.createLineBorder(currentBackgroundColor, 2));
        count++;
    }

    private void setCurrentBackgroundColor() {
        if (task.isSelected()) {
            currentBackgroundColor = selectedBackgroundColor;
        } else {
            currentBackgroundColor = defaultBackgroundColor;
        }
    }

    //this method is invoke the view refeshes to reset count to 1
    public static void resetCount() {
        count = 1;
    }

    //this listener is just making the LineTask view more beautiful
    private class LineTaskViewListener extends MouseAdapter {

        private boolean isMouseOvered;

        @Override
        public void mouseEntered(MouseEvent e) {
            setBorder(mouseOverBorder);
            isMouseOvered = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setBorder(BorderFactory.createLineBorder(currentBackgroundColor, 2));
            isMouseOvered = false;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                task.setSelected(!task.isSelected());//toggle select
            } else {
                setBorder(mouseClickedBorder);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setCurrentBackgroundColor();
            setBackground(currentBackgroundColor);
            if (isMouseOvered) {
                setBorder(mouseOverBorder);
            }
        }
    }

    //this listener make this LineTask perform some action
    private class LineTaskActionListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                model.viewTask(task);
            }
        }
    }

    //this is listener for delete button for both making view beautiful and 
    //perform some action
    private class ButtonListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {//accept left mouse only
                KulInvisibleButton but = (KulInvisibleButton) e.getSource();
                if (but.getTextLabel().equals("Delete")) {
                    model.delete(task);
                } else {
                    model.finishTask(task);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setBorder(mouseOverBorder);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setBorder(BorderFactory.createLineBorder(currentBackgroundColor, 2));
        }
    }

    // draw line through if the task is finished
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (task.isFinished()) {
            int y = this.getHeight() / 2;
            g.drawLine(0, y, this.getWidth(), y);
        }
    }
}
