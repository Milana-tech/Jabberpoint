package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * presentation.AboutBox shows the application's about dialog.
 * <p>
 * SRP: Responsible only for displaying the about message.
 */

public class AboutBox
{
    private static final String TITLE = "About JabberPoint";
    private static final String MESSAGE =
            "JabberPoint is a primitive slide-show program in Java(tm).\n" +
                    "It is freely copyable as long as you keep this notice and\n" +
                    "the splash screen intact.\n" +
                    "Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
                    "Adapted by Gert Florijn (version 1.1) and Sylvia Stuurman\n" +
                    "(version 1.2 and higher) for the Open University of the\n" +
                    "Netherlands, 2002 -- now.\n" +
                    "Author's version available from http://www.darwinsys.com/";

    public static void show(Frame parent)
    {
        JOptionPane.showMessageDialog(parent, MESSAGE, TITLE, JOptionPane.INFORMATION_MESSAGE);
    }
}