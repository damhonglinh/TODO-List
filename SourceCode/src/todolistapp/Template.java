package todolistapp;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Dam Linh - s3372757
 */
public class Template {

    private static Color background = new Color(255, 255, 224);
    private static Color borderMouseOver = new Color(222, 184, 135);
    private static Color backgroundSelected = new Color(255, 213, 140);
    private static Color oddLine = new Color(255, 250, 205);
    private static Color evenLine = new Color(255, 252, 232);
    private static Color titleBar = new Color(255, 230, 125);
    private static Color borderDefault = new Color(255, 230, 125);
    private static Color highContrast = new Color(139,0,0);
    private static Color contrast = new Color(205, 133, 63);
    private static Font font = new Font("Tempus Sans ITC", 1, 24);

    public static Color getBackground() {
        return background;
    }

    public static void setBackground(Color background) {
        Template.background = background;
    }

    public static Color getBorderMouseOver() {
        return borderMouseOver;
    }

    public static void setBorderMouseOver(Color borderMouseOver) {
        Template.borderMouseOver = borderMouseOver;
    }

    public static Color getBackgroundSelected() {
        return backgroundSelected;
    }

    public static void setBackgroundSelected(Color backgroundSelected) {
        Template.backgroundSelected = backgroundSelected;
    }

    public static Color getOddLine() {
        return oddLine;
    }

    public static void setOddLine(Color oddLine) {
        Template.oddLine = oddLine;
    }

    public static Color getEvenLine() {
        return evenLine;
    }

    public static void setEvenLine(Color evenLine) {
        Template.evenLine = evenLine;
    }

    public static Color getTitleBar() {
        return titleBar;
    }

    public static void setTitleBar(Color titleBar) {
        Template.titleBar = titleBar;
    }

    public static Color getBorderDefault() {
        return borderDefault;
    }

    public static void setBorderDefault(Color borderDefault) {
        Template.borderDefault = borderDefault;
    }

    public static Font getFont() {
        return font;
    }

    public static void setFont(Font font) {
        Template.font = font;
    }

    public static Color getHighContrast() {
        return highContrast;
    }

    public static void setHighContrast(Color highContrast) {
        Template.highContrast = highContrast;
    }

    public static Color getContrast() {
        return contrast;
    }

    public static void setContrast(Color contrast) {
        Template.contrast = contrast;
    }
}
