import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class BitmapItem extends SlideItem
{
    protected static final String FILE = "Bestand ";
    protected static final String NOTFOUND = " niet gevonden";
    private BufferedImage bufferedImage;
    private String imageName;

    public BitmapItem(int level, String name)
    {
        super(level);
        this.imageName = name;
        try
        {
            this.bufferedImage = ImageIO.read(new File(this.imageName));
        }
        catch (IOException e)
        {
            System.err.println(FILE + this.imageName + NOTFOUND) ;
        }
    }

    public BitmapItem()
    {
        this(0, null);
    }

    public String getName()
    {
        return this.imageName;
    }

    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle)
    {
        return new Rectangle((int) (myStyle.indent * scale), 0,
                (int) (this.bufferedImage.getWidth(observer) * scale),
                ((int) (myStyle.leading * scale)) +
                (int) (this.bufferedImage.getHeight(observer) * scale));
    }

    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer)
    {
        int width = x + (int) (myStyle.indent * scale);
        int height = y + (int) (myStyle.leading * scale);
        g.drawImage(this.bufferedImage, width, height, (int) (this.bufferedImage.getWidth(observer) * scale),
                (int) (this.bufferedImage.getHeight(observer) * scale), observer);
    }

    public String toString()
    {
        return "BitmapItem[" + getLevel() + "," + this.imageName + "]";
    }
}