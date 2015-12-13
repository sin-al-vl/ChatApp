import javax.swing.*;
import java.awt.*;

/**
 * Created by Rogdan on 10.12.2015.
 *
 * Run first in program
 */
public class HelloWindow extends JPanel{

    public HelloWindow(){
        initAll();
    }

    private void initAll(){
        initHelloWindow();
        initHelloWindowComponents();
    }

    private void initHelloWindow(){
        setOpaque(true);
        setLayout(new BorderLayout());
    }

    private void initHelloWindowComponents(){
    }
}
