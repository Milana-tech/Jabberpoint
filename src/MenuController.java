import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuController extends MenuBar
{
    private static final long serialVersionUID = 227L;

    private static final String ABOUT = "About";
    private static final String FILE = "File";
    private static final String EXIT = "Exit";
    private static final String GOTO = "Go to";
    private static final String HELP = "Help";
    private static final String NEW = "New";
    private static final String NEXT = "Next";
    private static final String OPEN = "Open";
    private static final String PAGENR = "Page number?";
    private static final String PREV = "Prev";
    private static final String SAVE = "Save";
    private static final String VIEW = "View";

    private static final String TESTFILE = "test.xml";
    private static final String SAVEFILE = "dump.xml";
    private static final String IOEX = "IO Exception: ";
    private static final String LOADERR = "Load Error";
    private static final String SAVEERR = "Save Error";

    private final Frame parent;
    private final Presentation presentation;

    public MenuController(Frame frame, Presentation pres)
    {
        this.parent = frame;
        this.presentation = pres;
        MenuItem menuItem;
        Menu fileMenu = new Menu(FILE);
        fileMenu.add(menuItem = this.mkMenuItem(OPEN));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.presentation.clear();
                Accessor xmlAccessor = new XMLAccessor();
                try
                {
                    xmlAccessor.loadFile(MenuController.this.presentation, TESTFILE);
                    MenuController.this.presentation.setSlideNumber(0);
                }
                catch (IOException exc)
                {
                    JOptionPane.showMessageDialog(MenuController.this.parent, IOEX + exc,
                            LOADERR, JOptionPane.ERROR_MESSAGE);
                }
                MenuController.this.parent.repaint();
            }
        });
        fileMenu.add(menuItem = this.mkMenuItem(NEW));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.presentation.clear();
                MenuController.this.parent.repaint();
            }
        });
        fileMenu.add(menuItem = this.mkMenuItem(SAVE));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Accessor xmlAccessor = new XMLAccessor();
                try
                {
                    xmlAccessor.saveFile(MenuController.this.presentation, SAVEFILE);
                }
                catch (IOException exc)
                {
                    JOptionPane.showMessageDialog(MenuController.this.parent, IOEX + exc,
                            SAVEERR, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        fileMenu.addSeparator();
        fileMenu.add(menuItem = this.mkMenuItem(EXIT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.presentation.exit(0);
            }
        });
        add(fileMenu);
        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(menuItem = this.mkMenuItem(NEXT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.presentation.nextSlide();
            }
        });
        viewMenu.add(menuItem = this.mkMenuItem(PREV));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.presentation.prevSlide();
            }
        });
        viewMenu.add(menuItem = this.mkMenuItem(GOTO));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
                int pageNumber = Integer.parseInt(pageNumberStr);
                MenuController.this.presentation.setSlideNumber(pageNumber - 1);
            }
        });
        add(viewMenu);
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = this.mkMenuItem(ABOUT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                AboutBox.show(MenuController.this.parent);
            }
        });
        setHelpMenu(helpMenu);
    }

    public MenuItem mkMenuItem(String name)
    {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
