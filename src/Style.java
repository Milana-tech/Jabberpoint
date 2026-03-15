import java.awt.*;

/**
 * Style defines the visual appearance for each slide item level.
 * SRP: Responsible only for holding and providing style data per level.
 * The static factory method createStyles() initialises all levels at application startup.
 */
public class Style
{
    private static final String FONT_NAME = "Helvetica";
    private static final int STYLE_COUNT = 5;

    private static Style[] styles;

    public final int indent;
    public final Color color;
    public final Font font;
    public final int fontSize;
    public final int leading;

    public Style(int indent, Color color, int fontSize, int leading)
    {
        this.indent = indent;
        this.color = color;
        this.fontSize = fontSize;
        this.font = new Font(FONT_NAME, Font.BOLD, fontSize);
        this.leading = leading;
    }

    /**
     * Initialises all predefined styles. Must be called once at application startup.
     */
    public static void createStyles()
    {
        styles = new Style[STYLE_COUNT];
        styles[0] = new Style(0, Color.RED, 48, 20);
        styles[1] = new Style(20, Color.BLUE, 40, 10);
        styles[2] = new Style(50, Color.BLACK, 36, 10);
        styles[3] = new Style(70, Color.BLACK, 30, 10);
        styles[4] = new Style(90, Color.BLACK, 24, 10);
    }

    /**
     * Returns the style for the given level.
     * If the level exceeds the available styles, the deepest style is returned.
     */
    public static Style getStyle(int level)
    {
        int clampedLevel = Math.min(level, styles.length - 1);
        return styles[clampedLevel];
    }

    public Font getFont(float scale)
    {
        return this.font.deriveFont(this.fontSize * scale);
    }

    @Override
    public String toString()
    {
        return "[indent=" + this.indent + ", color=" + this.color
                + ", fontSize=" + this.fontSize + ", leading=" + this.leading + "]";
    }
}