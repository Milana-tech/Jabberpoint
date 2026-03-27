package slide;

import presentation.PresentationComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

/**
 * slide.BitmapItem renders an image file on a slide.
 * <p>
 * Decorator pattern role: Concrete Decorator - extends slide.SlideItem to add image rendering on top of whatever presentation.PresentationComponent it wraps.
 * Factory Method pattern role: ConcreteProduct — the specific product created by slide.BitmapItemFactory. Returned wherever an image item is requested from the factory.
 * <p>
 * SRP: Responsible only for loading and drawing a bitmap image.
 * LSP: Fully substitutes slide.SlideItem — honours the drawing contract.
 */
public class BitmapItem extends SlideItem
{

    private final String imageName;
    private final Image image;

    public BitmapItem(PresentationComponent wrapped, int level, String imageName)
    {
        super(wrapped, level);
        this.imageName = imageName;
        this.image = loadImage(imageName);
    }

    public BitmapItem(int level, String imageName )
    {
        super(level);
        this.imageName = imageName;
        this.image = loadImage(imageName);
    }

    public String getName()
    {
        return this.imageName;
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style)
    {
        if (this.image == null)
        {
            return new Rectangle((int) (style.indent * scale), 0, 0, 0);
        }

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
        if (this.image == null)
        {
            return;
        }
        
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
    public String getContent()
    {
        return this.getName();
    }

    @Override
    public String toString()
    {
        return "slide.BitmapItem[" + getLevel() + "," + this.imageName + "]";
    }

    private static Image loadImage(String imageName)
    {
        if (imageName == null)
        {
            return null;
        }
        File file = new File(imageName);
        if (!file.exists()) {
            System.err.println("Image file not found: " + imageName);
            return null;
        }
        return new ImageIcon(imageName).getImage();
    }
}