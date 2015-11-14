import javax.swing.*;

/**
 * Created by jacob on 11/14/15.
 */
public class Main
{

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

        DankulatorFrame dankulatorFrame = new DankulatorFrame();
        dankulatorFrame.setVisible(true);

/*        GraphulatorFrame graphulatorFrame = new GraphulatorFrame();
        graphulatorFrame.setVisible(true);*/

    }
}

