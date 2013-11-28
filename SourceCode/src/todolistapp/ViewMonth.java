package todolistapp;

import datastructure.LinkedPriorityQueue;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import org.joda.time.LocalDate;

/**
 *
 * @author Dam Linh
 */
public class ViewMonth extends JPanel {

    private Model model;
    private JPanel centerPanel = new JPanel(new BorderLayout());
    private JPanel subCenterPanel = new JPanel(new BorderLayout());
    private CardLayout card = new CardLayout();
    private Font f = Template.getFont().deriveFont(0, 20);
    private KulButton next;
    private KulButton prev;
    private KulButton list;
    private KulButton add;
    private KulButton save;
    private KulButton todayBut;
    private JLabel month;
    private KulComboBox<Integer> year;
    private JPanel dayPanel;
    private JPanel titleDayPanel = new JPanel(new GridLayout(1, 7));
    private MenuBar menuBar = new MenuBar();
    private ViewListOneDay viewListOneDay;
    private DayBox[] dayBoxes;
    private LocalDate today = new LocalDate();
    private LocalDate currentDay = today;
    private LinkedPriorityQueue<Task> tasks;
    private int currentYear;
    //this property is a flag for distingusing user's and program's firing ItemChanged event
    private boolean isUserFireEvent = true;

    public ViewMonth(Model model) {
        this.model = model;
        tasks = model.getTasks();
        setLayout(card);

        viewListOneDay = new ViewListOneDay(model);
        add(centerPanel, "monthMainView");
        add(viewListOneDay, "viewListOneDay");
        centerPanel.add(subCenterPanel);

        subCenterPanel.setBackground(Template.getBackground());

        currentYear = currentDay.getYear();

        drawMenuBar();
        drawButtonPanel();
        drawSouthPanel();
        drawSubCenterPanel();
        addListener();
    }

    private void drawMenuBar() {
        centerPanel.add(menuBar, BorderLayout.NORTH);
        menuBar.getDelete().setEnabled(false);
        menuBar.getDeleteAll().setEnabled(false);
        menuBar.addListener(new MenuBarListener(model));
    }

    private void drawButtonPanel() {
        Box north = new Box(BoxLayout.X_AXIS);
        north.setPreferredSize(new Dimension(2000, 50));
        subCenterPanel.add(north, BorderLayout.NORTH);

        list = new KulButton("List");
        add = new KulButton("New");
        save = new KulButton("Save");
        todayBut = new KulButton("Today");

        list.setToolTipText("Switch to List view");
        list.setPreferredSize(new Dimension(100, 30));
        list.setMaximumSize(new Dimension(100, 30));
        list.setFont(f);
        add.setToolTipText("Create a new TODO");
        add.setPreferredSize(new Dimension(100, 30));
        add.setMaximumSize(new Dimension(100, 30));
        add.setFont(f);
        save.setToolTipText("Save");
        save.setPreferredSize(new Dimension(100, 30));
        save.setMaximumSize(new Dimension(100, 30));
        save.setFont(f);
        todayBut.setToolTipText("Go to Today");
        todayBut.setPreferredSize(new Dimension(100, 30));
        todayBut.setMaximumSize(new Dimension(100, 30));
        todayBut.setFont(f);

        north.add(Box.createHorizontalStrut(20));
        north.add(add);
        north.add(Box.createHorizontalStrut(20));
        north.add(save);
        north.add(Box.createHorizontalStrut(20));
        north.add(todayBut);
        north.add(Box.createHorizontalGlue());
        north.add(list);
        north.add(Box.createHorizontalStrut(20));
    }

    private void drawSouthPanel() {
        Box south = new Box(BoxLayout.X_AXIS);
        south.setPreferredSize(new Dimension(2000, 60));
        subCenterPanel.add(south, BorderLayout.SOUTH);

        next = new KulButton(">");
        prev = new KulButton("<");
        next.setPreferredSize(new Dimension(100, 30));
        next.setMaximumSize(new Dimension(100, 30));
        next.setFont(f.deriveFont(44f));
        next.setForeground(Template.getBorderMouseOver());
        prev.setPreferredSize(new Dimension(100, 30));
        prev.setMaximumSize(new Dimension(100, 30));
        prev.setFont(f.deriveFont(44f));
        prev.setForeground(Template.getBorderMouseOver());

        month = new JLabel();
        month.setFont(f.deriveFont(0, 36));
        year = new KulComboBox<>();
        for (int i = 1900; i < 2223; i++) {
            year.addItem(i);
        }
        year.setPreferredSize(new Dimension(135, 50));
        year.setMaximumSize(new Dimension(135, 50));
        year.setFont(f.deriveFont(0, 36));
        year.setFocusable(false);
        year.setBackground(Template.getBackground());
        year.setSelectedItem(currentYear);

        south.add(Box.createHorizontalStrut(10));
        south.add(prev);
        south.add(Box.createHorizontalGlue());
        south.add(month);
        south.add(Box.createHorizontalStrut(20));
        south.add(year);
        south.add(Box.createHorizontalGlue());
        south.add(next);
        south.add(Box.createHorizontalStrut(10));
    }

    private void drawSubCenterPanel() {
        JPanel center = new JPanel(new BorderLayout());
        subCenterPanel.add(center);
        dayPanel = new JPanel(new GridLayout(0, 7));
        center.setBackground(Template.getBackground());
        dayPanel.setBackground(Template.getBackground());

        drawTitlePanel();
        center.add(titleDayPanel, BorderLayout.NORTH);
        center.add(dayPanel);

        dayBoxes = new DayBox[42];
        boolean isEvenLine = true;
        for (int i = 0; i < dayBoxes.length; i++) {
            boolean isSunday = false;
            // this property isEvenLineTemp is to postpone isEvenLine being changed after contructor of DayBox
            boolean isEvenLineTemp = isEvenLine;
            if ((i + 1) % 7 == 0) {
                isSunday = true;
                isEvenLine = !isEvenLine;
            }
            dayBoxes[i] = new DayBox(isSunday, isEvenLineTemp, model);
            dayPanel.add(dayBoxes[i]);
        }
        drawDayBox();
    }

    // reset/initialize
    private void drawDayBox() {
        currentYear = currentDay.getYear();
        isUserFireEvent = false;
        year.setSelectedItem(currentYear);
        isUserFireEvent = true;

        month.setText(currentDay.toString("MMMM"));

        int firstDayOfMonth = currentDay.dayOfMonth().withMinimumValue().getDayOfWeek();//get the day on which the first day is
        int numberOfDay = currentDay.dayOfMonth().withMaximumValue().getDayOfMonth();
        for (int i = 0; i < dayBoxes.length; i++) {
            dayBoxes[i].setTasks(null);
            int dayNo = i + 2 - firstDayOfMonth;
            if (dayNo > numberOfDay) {
                dayBoxes[i].setDay(-1);
            } else {
                dayBoxes[i].setDay(dayNo);
                dayBoxes[i].setIsToday(false);
            }
        }
        addTaskToEachDayBox(firstDayOfMonth, numberOfDay);

        //set today:
        //check if today has the same month with currentDay
        if (today.getYear() == currentDay.getYear() && today.getMonthOfYear() == currentDay.getMonthOfYear()) {
            dayBoxes[today.getDayOfMonth() + firstDayOfMonth - 2].setIsToday(true);
        }
    }

    private void addTaskToEachDayBox(int firstDayOfMonth, int numberOfDay) {
        Iterator<Task> iter = tasks.getIterator();
        //each queue is for each DayBox
        LinkedPriorityQueue<Task>[] queuesOfMonth = new LinkedPriorityQueue[numberOfDay];

        //search task with its month same with current month
        while (iter.hasNext()) {
            Task task = iter.next();
            LocalDate taskDate = task.getDate();
            //if the month in the task is same with current month then:
            if (currentYear == taskDate.getYear() && currentDay.getMonthOfYear() == taskDate.getMonthOfYear()) {
                //enqueue with default priority so the order is the same with the order in tasks
                int day = taskDate.getDayOfMonth();//get day of task
                if (queuesOfMonth[day - 1] == null) {//queues index starts with 0
                    queuesOfMonth[day - 1] = new LinkedPriorityQueue<>();
                }
                queuesOfMonth[day - 1].enqueue(task);
            }
        }

        //add queue to DayBox
        for (int i = 0; i < queuesOfMonth.length; i++) {
            if (queuesOfMonth[i] != null) {
                dayBoxes[i + firstDayOfMonth - 1].setTasks(queuesOfMonth[i]);
            }
        }
    }

    private void drawTitlePanel() {
        titleDayPanel.setBackground(Template.getTitleBar());
        Font font = f.deriveFont(0, 30);

        JLabel mo = new JLabel("Mon");
        mo.setFont(font);
        mo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel tu = new JLabel("Tue");
        tu.setFont(font);
        tu.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel we = new JLabel("Wed");
        we.setFont(font);
        we.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel th = new JLabel("Thu");
        th.setFont(font);
        th.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel fr = new JLabel("Fri");
        fr.setFont(font);
        fr.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel sa = new JLabel("Sat");
        sa.setFont(font);
        sa.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel su = new JLabel("Sun");
        su.setFont(font);
        su.setHorizontalAlignment(SwingConstants.CENTER);

        titleDayPanel.add(mo);
        titleDayPanel.add(tu);
        titleDayPanel.add(we);
        titleDayPanel.add(th);
        titleDayPanel.add(fr);
        titleDayPanel.add(sa);
        titleDayPanel.add(su);
    }

    private void nextMonth() {
        //show message if user goes beyond month: December, year: 2222
        if (currentDay.getMonthOfYear() == 12 && currentDay.getYear() == 2222) {
            JOptionPane.showMessageDialog(this,
                    "Are you gonna live until this year?");
            return;
        }
        currentDay = currentDay.plusMonths(1);
        drawDayBox();
        subCenterPanel.repaint();
    }

    private void prevMonth() {
        //show message if user goes below month: January, year: 1900
        if (currentDay.getMonthOfYear() == 1 && currentDay.getYear() == 1900) {
            JOptionPane.showMessageDialog(this,
                    "Don't be obsessed with the past too much!");
            return;
        }
        currentDay = currentDay.minusMonths(1);
        drawDayBox();
        subCenterPanel.repaint();
    }

    private void addListener() {
        addKulButtonListener();
        addDayPanelWheelListener();
        addYearListener();
        addDayBoxListener();
    }

    private void addKulButtonListener() {
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.switchView();
            }
        });

        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.showFormTask();
            }
        });

        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.save();
            }
        });

        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextMonth();
            }
        });

        prev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                prevMonth();
            }
        });

        todayBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentDay = today;
                drawDayBox();
                repaint();
            }
        });
    }

    private void addDayPanelWheelListener() {
        dayPanel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    nextMonth();
                } else {
                    prevMonth();
                }
            }
        });
    }

    private void addYearListener() {
        year.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (isUserFireEvent) {
                        currentDay = currentDay.withYear((Integer) year.getSelectedItem());
                        drawDayBox();
                        subCenterPanel.repaint();
                    }
                    isUserFireEvent = true;
                }
            }
        });
    }

    private void addDayBoxListener() {
        for (int i = 0; i < dayBoxes.length; i++) {
            dayBoxes[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    DayBox dayBox = (DayBox) e.getSource();
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (dayBox.getTasks() != null) {
                            viewListOneDay.setTasks(dayBox.getTasks());
                            card.show(ViewMonth.this, "viewListOneDay");
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        if (dayBox.getDay() > 0) {
                            LocalDate tempDate = currentDay.withDayOfMonth(dayBox.getDay());
                            model.showFormTask(tempDate);
                        }
                    }
                }
            });
        }
    }

    public void refresh() {
        drawDayBox();
        card.show(this, "monthMainView");
    }
}
