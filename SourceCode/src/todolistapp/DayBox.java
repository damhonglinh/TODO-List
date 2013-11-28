package todolistapp;

import datastructure.LinkedPriorityQueue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Dam Linh
 */
public class DayBox extends Box {

    private int day;
    private boolean isSunday = false;
    private boolean isToday = false;
    private LinkedPriorityQueue<Task> tasks;
    private Border defaultBorder;
    private Border mouseOverBorder = new LineBorder(Template.getBorderMouseOver());
    private Border mousePressBorder = new LineBorder(Template.getBorderMouseOver(), 2);
    private Model model;
    private Color backgroundColor;

    public DayBox(boolean isSunday, boolean isEvenLine, Model model) {
        super(BoxLayout.Y_AXIS);
        this.isSunday = isSunday;
        this.model = model;
        if (isEvenLine) {
            backgroundColor = Template.getEvenLine();
        } else {
            backgroundColor = Template.getOddLine();
        }
        setToolTipText("Right click to add new task");
        setFont(Template.getFont().deriveFont(0, 16));
        addMouseListener(new DayBoxListener());
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        removeAll();

        if (day > 0) {
            if (day == 1) {
                defaultBorder = new MatteBorder(0, 1, 0, 1, Template.getBorderDefault());
            } else {
                defaultBorder = new MatteBorder(0, 0, 0, 1, Template.getBorderDefault());
            }
            setBorder(defaultBorder);
        } else {
            setBorder(null);
            tasks = null;
        }
    }

    public LinkedPriorityQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(LinkedPriorityQueue<Task> tasks) {
        this.tasks = tasks;
        if (this.tasks == null) {
            return;
        }
        add(Box.createVerticalStrut(21));

        Iterator<Task> iter = tasks.getIterator();
        while (iter.hasNext()) {
            addTask(iter.next());
        }
    }

    private void addTask(final Task task) {
        Font f = Template.getFont().deriveFont(0, 12);
        final KulButton taskButton = new KulButton(" " + task.getTimeString() + " : " + task.getTitle(),
                backgroundColor, backgroundColor);

        taskButton.setHorizontalAlignment(SwingConstants.LEFT);
        taskButton.setPreferredSize(new Dimension(500, 20));
        taskButton.setMaximumSize(new Dimension(500, 20));
        taskButton.setMinimumSize(new Dimension(20, 20));
        taskButton.setFont(f);
        taskButton.setBackground(Template.getBackgroundSelected());

        add(taskButton);

        taskButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.viewTask(task);
            }

            //for beautiful view only
            @Override
            public void mouseEntered(MouseEvent e) {
                taskButton.setOpaque(true);//to make the background to be seen
            }

            //for beautiful view only
            @Override
            public void mouseExited(MouseEvent e) {
                taskButton.setOpaque(false);//to make the background not to be seen
            }
        });
    }

    public void setIsToday(boolean isToday) {
        if (this.isToday != isToday) {//improve performance
            if (isToday) {
                defaultBorder = new LineBorder(Template.getBackgroundSelected(), 2);
            } else {
                defaultBorder = new MatteBorder(0, 0, 0, 1, Template.getBorderDefault());
            }
            setBorder(defaultBorder);
        }
        this.isToday = isToday;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(backgroundColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (day <= 0) {
            return;
        }
        String dayString = this.day + "";

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (!isSunday) {
            g2d.setColor(Template.getContrast());
        } else {
            g2d.setColor(Template.getHighContrast());
        }

        if (day > 0) {
            g2d.drawString(dayString, 5, 18);
        }
    }

    //this listener is just for making beautiful view only
    private class DayBoxListener extends MouseAdapter {

        boolean mouseOver = false;

        @Override
        public void mousePressed(MouseEvent e) {
            if (day <= 0) {
                return;
            }
            setBorder(mousePressBorder);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (day <= 0) {
                return;
            }
            if (mouseOver) {
                setBorder(mouseOverBorder);
            } else {
                setBorder(defaultBorder);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (day <= 0) {
                return;
            }
            setBorder(mouseOverBorder);
            mouseOver = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (day <= 0) {
                return;
            }
            setBorder(defaultBorder);
            mouseOver = false;
        }
    }
}
