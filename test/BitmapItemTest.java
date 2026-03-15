import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BitmapItemTest
{
    @Test
    void constructor_withLevelAndName_setsNameCorrectly()
    {
        BitmapItem item = new BitmapItem(1, "test.jpg");

        assertEquals("test.jpg", item.getName());
    }

    @Test
    void constructor_withLevelAndName_setsLevelCorrectly()
    {
        BitmapItem item = new BitmapItem(1, "test.jpg");

        assertEquals(1, item.getLevel());
    }
}