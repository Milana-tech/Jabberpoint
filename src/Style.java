import java.awt.*;

public class Style
{
    private static Style[] styles; // de styles

    private static final String FONTNAME = "Helvetica";
    int indent;
    Color color;
    Font font;
    int fontSize;
    int leading;

    public Style(int indent, Color color, int points, int leading)
    {
        this.indent = indent;
        this.color = color;
        this.font = new Font(FONTNAME, Font.BOLD, this.fontSize = points);
        this.leading = leading;
    }

    public static void createStyles()
    {
        styles = new Style[5];

        styles[0] = new Style(0, Color.red, 48, 20);
        styles[1] = new Style(20, Color.blue, 40, 10);
        styles[2] = new Style(50, Color.black, 36, 10);
        styles[3] = new Style(70, Color.black, 30, 10);
        styles[4] = new Style(90, Color.black, 24, 10);
    }

    public static Style getStyle(int level)
    {
        if (level >= styles.length)
        {
            level = styles.length - 1;
        }
        return styles[level];
    }

    public String toString()
    {
        return "[" + this.indent + "," + this.color + "; " + this.fontSize + " on " + this.leading + "]";
    }

    public Font getFont(float scale)
    {
        return this.font.deriveFont(this.fontSize * scale);
    }
}
