package todolistapp;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Dam Linh - s3372757
 */
public class KulInvisibleButton extends KulButton {

    private String textLabel;

    public KulInvisibleButton(String text, Color defaultColor, Color hoverColor) {
        super("", defaultColor, hoverColor);

        textLabel = text;
        setBorder(null);
        setDefaultBorder(null);
        setHoverBorder(null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setTextDisplay(textLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setTextDisplay("");
            }
        });
    }

    public KulInvisibleButton(String text) {
        this(text, Template.getBorderDefault(), Template.getBorderMouseOver());
    }

    public String getTextLabel() {
        return textLabel;
    }

    public void setTextLabel(String textLabel) {
        this.textLabel = textLabel;
    }
}
