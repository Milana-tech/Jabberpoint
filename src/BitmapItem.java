import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;


/** <p>De klasse voor een Bitmap item</p>
 * <p>Bitmap items hebben de verantwoordelijkheid om zichzelf te tekenen.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

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
        g.drawImage(this.bufferedImage, width, height,(int) (this.bufferedImage.getWidth(observer) * scale),
                (int) (this.bufferedImage.getHeight(observer) * scale), observer);
    }

    public String toString()
    {
        return "BitmapItem[" + getLevel() + "," + this.imageName + "]";
    }
}
