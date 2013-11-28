package todolistapp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import org.joda.time.LocalDate;

/**
 *
 * @author Dam Linh - s3372757
 *
 * This class has 2 purposes. One is for create a new task and one is for modify
 * a task but the class has 1 constructor. Therefore when modifying a task, the
 * program invokes method setTask and when creating a new task, the program does
 * not invoke setTask. The boolean createNew is to distinguish them.
 */
public class ViewFormTask extends JPanel {

    private Font f = Template.getFont();
    private JPanel centerPanel;
    private Box titleBox = new Box(BoxLayout.X_AXIS);
    private Box dateBox = new Box(BoxLayout.X_AXIS);
    private Box timeBox = new Box(BoxLayout.X_AXIS);
    private Box descrBox = new Box(BoxLayout.X_AXIS);
    private Box butBox = new Box(BoxLayout.X_AXIS);
    private JTextField title = new JTextField(22);
    private KulComboBox<Integer> year;
    private JCheckBox allDay = new JCheckBox("All-day");
    private KulComboBox<Integer> day;
    private KulComboBox<String> month;
    private KulComboBox<String> priority;
    private KulComboBox<Integer> hour;
    private KulComboBox<Integer> min;
    private JTextArea description = new JTextArea(4, 20);
    private JLabel labelHour = new JLabel("Hour");
    private JLabel labelMin = new JLabel("Minute");
    private KulButton add = new KulButton("Add");
    private KulButton cancel = new KulButton("Cancel");
    private boolean isLeap;
    private boolean createNew;
    private Task task;
    private Model model;
    private LocalDate today = new LocalDate();

    public ViewFormTask(Model model) {
        this.model = model;
        createNew = true;
        isLeap = today.year().isLeap();
        setLayout(new BorderLayout());
        setBackground(Template.getBackground());

        SpringLayout sprLayout = new SpringLayout();
        centerPanel = new JPanel(sprLayout);
        centerPanel.setBackground(Template.getBackground());
        add(centerPanel);

        sprLayout.putConstraint(SpringLayout.NORTH, titleBox, 10, SpringLayout.NORTH, centerPanel);
        sprLayout.putConstraint(SpringLayout.NORTH, dateBox, 25, SpringLayout.SOUTH, titleBox);
        sprLayout.putConstraint(SpringLayout.NORTH, timeBox, 25, SpringLayout.SOUTH, dateBox);
        sprLayout.putConstraint(SpringLayout.NORTH, descrBox, 25, SpringLayout.SOUTH, timeBox);
        sprLayout.putConstraint(SpringLayout.NORTH, butBox, 10, SpringLayout.SOUTH, descrBox);
        sprLayout.putConstraint(SpringLayout.EAST, centerPanel, 25, SpringLayout.EAST, titleBox);
        sprLayout.putConstraint(SpringLayout.EAST, descrBox, -25, SpringLayout.EAST, centerPanel);
        sprLayout.putConstraint(SpringLayout.WEST, descrBox, 0, SpringLayout.WEST, centerPanel);
        sprLayout.putConstraint(SpringLayout.EAST, butBox, -25, SpringLayout.EAST, centerPanel);
        sprLayout.putConstraint(SpringLayout.WEST, butBox, 0, SpringLayout.WEST, centerPanel);
        sprLayout.putConstraint(SpringLayout.SOUTH, centerPanel, 25, SpringLayout.SOUTH, butBox);

        drawHeading();
        drawTitle();
        drawDate();
        drawTime();
        drawDescription();
        drawButton();
        addListener();
    }

    // this method is to set default information of the components based
    // on the information of the task
    public void setTask(Task task) {
        this.task = task;
        createNew = false;
        add.setTextDisplay("Save");

        LocalDate date = task.getDate();
        int dayNo = date.getDayOfMonth();
        int monthNo = date.getMonthOfYear();
        int yearNo = date.getYear();
        if (yearNo % 4 == 0) {
            isLeap = true;
        } else {
            isLeap = false;
        }

        title.setText(task.getTitle());
        priority.setSelectedIndex(task.getPriority() - 1);
        day.setSelectedIndex(dayNo - 1);
        month.setSelectedIndex(monthNo - 1);
        year.setSelectedIndex(yearNo - 1900);
        if (task.getHour() < 0) {
            allDay.setSelected(true);
            hour.setVisible(false);
            hour.setSelectedIndex(0);
            min.setVisible(false);
            min.setSelectedIndex(0);
            labelHour.setVisible(false);
            labelMin.setVisible(false);
        } else {
            allDay.setSelected(false);
            hour.setSelectedIndex(task.getHour());
            min.setSelectedIndex(task.getMin());
        }
        description.setText(task.getDescription());
    }

    /**
     * set date when creating new task
     */
    public void setDate(LocalDate date) {
        day.setSelectedIndex(date.getDayOfMonth() - 1);
        month.setSelectedIndex(date.getMonthOfYear() - 1);
        year.setSelectedItem(date.getYear());
    }

    private void reset() {
        title.setText("");
        priority.setSelectedIndex(0);
        day.setSelectedIndex(today.getDayOfMonth() - 1);
        month.setSelectedIndex(today.getMonthOfYear() - 1);
        year.setSelectedItem(today.getYear());
        allDay.setSelected(false);
        hour.setSelectedIndex(0);
        hour.setVisible(true);
        labelHour.setVisible(true);
        labelMin.setVisible(true);
        min.setVisible(true);
        min.setSelectedIndex(0);
        description.setText("");
    }

    private void drawHeading() {
        JLabel heading = new JLabel("New TODO");
        JPanel subP = new JPanel(null);
        subP.setPreferredSize(new Dimension(1440, 80));
        subP.setBackground(Template.getBackground());
        subP.add(heading);

        heading.setFont(f.deriveFont(1, 42f));
        heading.setBounds(35, 15, 300, 50);

        add(subP, BorderLayout.NORTH);
    }

    private void drawTitle() {
        JLabel labelTitle = new JLabel("Title:");
        labelTitle.setFont(f);
        setFixedSize(labelTitle, 150, 50);
        title.setPreferredSize(new Dimension(250, 40));
        title.setMaximumSize(new Dimension(900, 40));
        title.setFont(f.deriveFont(0, 20));
        title.setBorder(new LineBorder(Template.getBorderDefault()));

        JLabel labelPrio = new JLabel("Priority:");
        labelPrio.setFont(f);
        String[] s = new String[]{"10 - Highest", "9", "8", "7", "6", "5", "4", "3", "2", "1 - Lowest"};
        priority = new KulComboBox<>(s);
        priority.setBackground(Template.getBackground());
        priority.setFont(f.deriveFont(0));
        setFixedSize(priority, 170, 40);

        titleBox.setMaximumSize(new Dimension(2000, 50));
        titleBox.add(Box.createHorizontalStrut(85));
        titleBox.add(labelTitle);
        titleBox.add(title);
        titleBox.add(Box.createHorizontalStrut(50));
        titleBox.add(labelPrio);
        titleBox.add(Box.createHorizontalStrut(10));
        titleBox.add(priority);
        centerPanel.add(titleBox);
    }

    private void drawDate() {
        JLabel label = new JLabel("Date:");
        label.setFont(f);
        setFixedSize(label, 150, 50);

        JLabel labelDay = new JLabel("Day");
        labelDay.setFont(f);
        day = new KulComboBox<>();
        for (int i = 1; i <= 31; i++) {
            day.addItem(i);
        }
        day.setSelectedIndex(today.getDayOfMonth() - 1);
        day.setFont(f.deriveFont(0));
        day.setBackground(Template.getBackground());
        setFixedSize(day, 69, 40);

        JLabel labelMonth = new JLabel("Month");
        labelMonth.setFont(f);
        month = new KulComboBox<>(new String[]{"January", "February",
                    "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December"});
        month.setSelectedIndex(today.getMonthOfYear() - 1);
        month.setFont(f.deriveFont(0));
        month.setBackground(Template.getBackground());
        setFixedSize(month, 158, 40);

        JLabel labelYear = new JLabel("Year");
        labelYear.setFont(f);
        year = new KulComboBox<>();
        for (int i = 1900; i < 2223; i++) {
            year.addItem(i);
        }
        year.setSelectedItem(today.getYear());
        year.setFont(f.deriveFont(0));
        year.setBackground(Template.getBackground());
        setFixedSize(year, 105, 40);

        dateBox.setMaximumSize(new Dimension(2000, 50));
        dateBox.add(Box.createHorizontalStrut(85));
        dateBox.add(label);
        dateBox.add(labelDay);
        dateBox.add(Box.createHorizontalStrut(15));
        dateBox.add(day);
        dateBox.add(Box.createHorizontalStrut(45));
        dateBox.add(labelMonth);
        dateBox.add(Box.createHorizontalStrut(13));
        dateBox.add(month);
        dateBox.add(Box.createHorizontalStrut(46));
        dateBox.add(labelYear);
        dateBox.add(Box.createHorizontalStrut(15));
        dateBox.add(year);
        dateBox.add(Box.createHorizontalGlue());
        centerPanel.add(dateBox);
    }

    private void drawTime() {
        JLabel label = new JLabel("Time");
        label.setFont(f);
        setFixedSize(label, 150, 50);

        allDay.setFont(f);
        allDay.setFocusable(false);
        allDay.setBackground(Template.getBackground());

        labelHour.setFont(f);
        hour = new KulComboBox<>();
        for (int i = 0; i < 24; i++) {
            hour.addItem(i);
        }
        hour.setFont(f.deriveFont(0));
        hour.setBackground(Template.getBackground());
        setFixedSize(hour, 77, 40);

        labelMin.setFont(f);
        min = new KulComboBox<>();
        for (int i = 0; i < 60; i++) {
            min.addItem(i);
        }
        min.setFont(f.deriveFont(0));
        min.setBackground(Template.getBackground());
        setFixedSize(min, 77, 40);

        timeBox.setMaximumSize(new Dimension(2000, 50));
        timeBox.add(Box.createHorizontalStrut(85));
        timeBox.add(label);
        timeBox.add(allDay);
        timeBox.add(Box.createHorizontalStrut(160));
        timeBox.add(labelHour);
        timeBox.add(Box.createHorizontalStrut(15));
        timeBox.add(hour);
        timeBox.add(Box.createHorizontalStrut(41));
        timeBox.add(labelMin);
        timeBox.add(Box.createHorizontalStrut(15));
        timeBox.add(min);
        timeBox.add(Box.createHorizontalGlue());
        centerPanel.add(timeBox);
    }

    private void drawDescription() {
        JLabel label = new JLabel("Description");
        label.setFont(f);
        setFixedSize(label, 150, 50);
        JScrollPane scroll = new JScrollPane(description);
        scroll.setBorder(new LineBorder(Template.getBorderDefault()));
        description.setFont(f.deriveFont(0, 18));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBorder(null);

        descrBox.add(Box.createHorizontalStrut(85));
        descrBox.add(label);
        descrBox.add(scroll);
        centerPanel.add(descrBox);
    }

    private void drawButton() {
        add.setFont(Template.getFont().deriveFont(0, 20));
        setFixedSize(add, 100, 30);
        cancel.setFont(Template.getFont().deriveFont(0, 20));
        setFixedSize(cancel, 100, 30);

        butBox.setMaximumSize(new Dimension(2000, 50));
        butBox.add(Box.createHorizontalGlue());
        butBox.add(add);
        butBox.add(Box.createHorizontalStrut(20));
        butBox.add(cancel);
        centerPanel.add(butBox);
    }

    private void setFixedSize(Component comp, int x, int y) {
        Dimension dim = new Dimension(x, y);
        comp.setPreferredSize(dim);
        comp.setMaximumSize(dim);
        comp.setMinimumSize(dim);
    }

    private void addListener() {
        addListenerToMonth();
        addListenerToYear();
        addListenerToDay();
        addListenerToTime();

        //add add-button listener
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int dayNo = day.getSelectedIndex() + 1;
                int monthNo = month.getSelectedIndex() + 1;
                int yearNo = year.getSelectedIndex() + 1900;
                int hourNo = hour.getSelectedIndex();
                int minNo = min.getSelectedIndex();
                String titleString = title.getText();
                int prio = 10 - priority.getSelectedIndex();
                String descr = description.getText();
                if (descr.equals("")) {
                    descr = "Description is left empty";
                }
                if (titleString.equals("")) {
                    titleString = "Title is left empty";
                }
                LocalDate date = new LocalDate(yearNo, monthNo, dayNo);
                if (allDay.isSelected()) {
                    hourNo = -1;
                    minNo = -1;
                }

                if (createNew) {
                    task = new Task(titleString, prio, descr, date, hourNo, minNo);
                    model.addNewTask(task);
                    reset();
                } else {
                    task.setTitle(titleString);
                    task.setPriority(prio);
                    task.setDescription(descr);
                    task.setDate(date);
                    task.setHour(hourNo);
                    task.setMin(minNo);
                    model.modifyTask();
                }
            }
        });

        //add cancel-button listener
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reset();
                model.cancelAndBack();
            }
        });
    }

    /**
     * add month item listener
     */
    private void addListenerToMonth() {
        month.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {//deselecting stage
                    //make number of day = 30
                    if (day.getItemCount() == 31) {
                        day.removeItemAt(30);//remove day-31 at index 30
                    }
                    if (day.getItemCount() == 28) {
                        day.addItem(29);
                    }
                    if (day.getItemCount() == 29) {
                        day.addItem(30);
                    }
                } else {//selecting stage
                    int monthNo = month.getSelectedIndex() + 1;
                    switch (monthNo) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            day.addItem(31);//re-add day-31 at last index 30
                            break;
                    }
                    if (monthNo == 2) {
                        day.removeItemAt(29);//remove day-30 at index 29
                        if (!isLeap) {
                            day.removeItemAt(28);//remove day-29
                        }
                    }
                }
            }
        });

        month.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    if (month.getSelectedIndex() <= 0) {
                        month.setSelectedIndex(11);
                    } else {
                        month.setSelectedIndex(month.getSelectedIndex() - 1);
                    }
                } else {
                    if (month.getSelectedIndex() >= 11) {
                        month.setSelectedIndex(0);
                    } else {
                        month.setSelectedIndex(month.getSelectedIndex() + 1);
                    }
                }
            }
        });
    }

    /**
     * add year listener
     */
    private void addListenerToYear() {
        year.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (year.getSelectedIndex() % 4 == 0 && year.getSelectedIndex() % 100 != 0) {
                    isLeap = true;
                } else {
                    isLeap = false;
                }
                month.setSelectedIndex(month.getSelectedIndex() - 1);//re-select switchView to
                month.setSelectedIndex(month.getSelectedIndex() + 1);//update number of day
            }
        });

        year.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    if (year.getSelectedIndex() <= 0) {
                        year.setSelectedIndex(year.getItemCount() - 1);
                    } else {
                        year.setSelectedIndex(year.getSelectedIndex() - 1);
                    }
                } else {
                    if (year.getSelectedIndex() >= year.getItemCount() - 1) {
                        year.setSelectedIndex(0);
                    } else {
                        year.setSelectedIndex(year.getSelectedIndex() + 1);
                    }
                }
            }
        });
    }

    /**
     * add day listener
     */
    private void addListenerToDay() {
        day.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    if (day.getSelectedIndex() <= 0) {
                        day.setSelectedIndex(day.getItemCount() - 1);
                    } else {
                        day.setSelectedIndex(day.getSelectedIndex() - 1);
                    }
                } else {
                    if (day.getSelectedIndex() >= day.getItemCount() - 1) {
                        day.setSelectedIndex(0);
                    } else {
                        day.setSelectedIndex(day.getSelectedIndex() + 1);
                    }
                }
            }
        });
    }

    private void addListenerToTime() {
        //add allDay checkbox listener
        allDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if allDay.isSelected() then
                //make hour and min invisible, vice versa
                labelHour.setVisible(!allDay.isSelected());
                labelMin.setVisible(!allDay.isSelected());
                hour.setVisible(!allDay.isSelected());
                min.setVisible(!allDay.isSelected());
            }
        });

        hour.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    if (hour.getSelectedIndex() <= 0) {
                        hour.setSelectedIndex(hour.getItemCount() - 1);
                    } else {
                        hour.setSelectedIndex(hour.getSelectedIndex() - 1);
                    }
                } else {
                    if (hour.getSelectedIndex() >= hour.getItemCount() - 1) {
                        hour.setSelectedIndex(0);
                    } else {
                        hour.setSelectedIndex(hour.getSelectedIndex() + 1);
                    }
                }
            }
        });

        min.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    if (min.getSelectedIndex() <= 0) {
                        min.setSelectedIndex(min.getItemCount() - 1);
                    } else {
                        min.setSelectedIndex(min.getSelectedIndex() - 1);
                    }
                } else {
                    if (min.getSelectedIndex() >= min.getItemCount() - 1) {
                        min.setSelectedIndex(0);
                    } else {
                        min.setSelectedIndex(min.getSelectedIndex() + 1);
                    }
                }
            }
        });
    }
}
