import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slide.Style;

import java.awt.Color;
import java.awt.Font;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Style.
 * Covers style creation, all 5 levels, clamping, font scaling, and toString.
 */
class StyleTest
{
    @BeforeEach
    void setUp()
    {
        Style.createStyles();
    }

    @Test
    void getStyle_level0_colorIsRed()
    {
        assertEquals(Color.RED, Style.getStyle(0).color);
    }

    @Test
    void getStyle_level0_fontSize48()
    {
        assertEquals(48, Style.getStyle(0).fontSize);
    }

    @Test
    void getStyle_level0_indent0()
    {
        assertEquals(0, Style.getStyle(0).indent);
    }

    @Test
    void getStyle_level1_colorIsBlue()
    {
        assertEquals(Color.BLUE, Style.getStyle(1).color);
    }

    @Test
    void getStyle_level1_fontSize40()
    {
        assertEquals(40, Style.getStyle(1).fontSize);
    }

    @Test
    void getStyle_level2_colorIsBlack()
    {
        assertEquals(Color.BLACK, Style.getStyle(2).color);
    }

    @Test
    void getStyle_level3_colorIsBlack()
    {
        assertEquals(Color.BLACK, Style.getStyle(3).color);
    }

    @Test
    void getStyle_level4_colorIsBlack()
    {
        assertEquals(Color.BLACK, Style.getStyle(4).color);
    }

    @Test
    void getStyle_level4_indent90()
    {
        assertEquals(90, Style.getStyle(4).indent);
    }

    @Test
    void getStyle_levelAboveMax_clampsToLevel4()
    {
        assertSame(Style.getStyle(4), Style.getStyle(99));
    }

    @Test
    void getFont_scaleOne_returnsFontAtFullSize()
    {
        Style style = Style.getStyle(0);
        Font font = style.getFont(1.0f);
        assertEquals(48.0f, font.getSize2D(), 0.01f);
    }

    @Test
    void getFont_halfScale_halvesFontSize()
    {
        Style style = Style.getStyle(0);
        Font font = style.getFont(0.5f);
        assertEquals(24.0f, font.getSize2D(), 0.01f);
    }

    @Test
    void toString_containsIndent()
    {
        assertTrue(Style.getStyle(0).toString().contains("indent=0"));
    }

    @Test
    void toString_containsFontSize()
    {
        assertTrue(Style.getStyle(0).toString().contains("48"));
    }

    @Test
    void constructor_setsAllFields()
    {
        Style s = new Style(15, Color.GREEN, 20, 8);
        assertEquals(15, s.indent);
        assertEquals(Color.GREEN, s.color);
        assertEquals(20, s.fontSize);
        assertEquals(8, s.leading);
    }
}
