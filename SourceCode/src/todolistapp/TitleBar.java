package todolistapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Dam Linh - s3372757
 */
public class TitleBar extends JPanel {

    private JLabel priority = new JLabel("Priority");
    private JLabel date = new JLabel("Date Due");
    private JLabel time = new JLabel("Time");
    private JLabel title = new JLabel("Title");
    private Color colorTitleBar = Template.getTitleBar();
    private Font f = Template.getFont().deriveFont(1, 20);
    private Model model;

    public TitleBar(Model model) {
        this.model = model;
        setBackground(colorTitleBar);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 20;
        c.ipady = 20;
        c.fill = GridBagConstraints.BOTH;


        //PRIORITY
        priority.setFont(f);
        priority.setHorizontalAlignment(SwingConstants.CENTER);
        priority.setPreferredSize(new Dimension(60, 20));
        priority.setBorder(new LineBorder(colorTitleBar, 2));
        add(priority, c);

        //DATE
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setFont(f);
        date.setPreferredSize(new Dimension(180, 20));
        date.setBorder(new LineBorder(colorTitleBar, 2));
        add(date, c);

        //TIME
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setFont(f);
        time.setPreferredSize(new Dimension(70, 20));
        time.setBorder(new LineBorder(colorTitleBar, 2));
        add(time, c);

        c.weightx = 0.5d;
        c.weighty = 0.5d;

        //TITLE
        title.setFont(f);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(120, 20));
        title.setBorder(new LineBorder(colorTitleBar, 2));
        add(title, c);

        addListener();
    }

    private void addListener() {
        TitleBarListener listener = new TitleBarListener();
        priority.addMouseListener(listener);
        date.addMouseListener(listener);
        time.addMouseListener(listener);
        title.addMouseListener(listener);

        priority.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.sortByPriority();
                model.refresh();
            }
        });

        date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.sortByDate();
                model.refresh();
            }
        });

        time.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.sortByTime();
                model.refresh();
            }
        });

        title.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.sortByTitle();
                model.refresh();
            }
        });
    }

    // this listener is just to make beautiful view
    private class TitleBarListener extends MouseAdapter {

        private Color borderColor = Template.getBorderMouseOver();
        private Border mouseClickedBorder = new CompoundBorder(new MatteBorder(0, 0, 0, 0, colorTitleBar), new MatteBorder(2, 2, 2, 2, borderColor));
        private Border mouseOverBorder = new CompoundBorder(new MatteBorder(1, 1, 1, 1, borderColor), new MatteBorder(1, 2, 1, 0, colorTitleBar));

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            label.setBorder(mouseOverBorder);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            label.setBorder(new LineBorder(colorTitleBar, 2));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            label.setBorder(mouseClickedBorder);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            label.setBorder(mouseOverBorder);
        }
    }
}
