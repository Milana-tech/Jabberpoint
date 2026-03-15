package slide;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * slide.BitmapItem renders an image file on a slide.
 * <p>
 * SRP: Responsible only for loading and drawing a bitmap image.
 * LSP: Fully substitutes slide.SlideItem — honours the drawing contract.
 */
public class BitmapItem extends SlideItem
{

    private final String imageName;
    private final BufferedImage image;

    public BitmapItem(int level, String imageName)
    {
        super(level);
        this.imageName = imageName;
        this.image = loadImage(imageName);
    }

    public BitmapItem()
    {
        this(DEFAULT_LEVEL, null);
    }

    public String getName()
    {
        return this.imageName;
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style)
    {
        return new Rectangle(
                (int) (style.indent * scale),
                0,
                (int) (this.image.getWidth(observer) * scale),
                (int) (style.leading * scale) + (int) (this.image.getHeight(observer) * scale)
        );
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer)
    {
        int drawX = x + (int) (style.indent * scale);
        int drawY = y + (int) (style.leading * scale);
        g.drawImage(
                this.image,
                drawX, drawY,
                (int) (this.image.getWidth(observer) * scale),
                (int) (this.image.getHeight(observer) * scale),
                observer
        );
    }

    @Override
    public String toString()
    {
        return "slide.BitmapItem[" + getLevel() + "," + this.imageName + "]";
    }

    private static BufferedImage loadImage(String imageName)
    {
        if (imageName == null)
        {
            return null;
        }
        try
        {
            return ImageIO.read(new File(imageName));
        }
        catch (IOException e)
        {
            System.err.println("Image file not found: " + imageName);
            return null;
        }
    }
}