package GUITEST;

import javax.swing.*;
import java.awt.*;

public class JFrameTest {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setFont(new Font("System", Font.PLAIN, 14));
        Font f = jf.getFont();
        FontMetrics fm = jf.getFontMetrics(f);
        int x = fm.stringWidth("FX_CloudSim");
        int y = fm.stringWidth(" ");
        int z = jf.getWidth()/2 - (x/2);
        int w = z/y;
        String pad ="";
        pad = String.format("%"+w+"s", pad);
        jf.setTitle(pad+"FX_CloudSim");
        jf.setBounds(400,300,400,250);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton jb1 = new JButton("ENTER");
        JButton jb2 = new JButton("ENTER");
        JPanel jp = new JPanel(new FlowLayout());
        JLabel jLabel1 = new JLabel("CloudSim Examples:",SwingConstants.CENTER);
        JLabel jLabel2 = new JLabel("CloudSim Simulation:",SwingConstants.CENTER);
        jp.add(jLabel1);
        jp.add(jb1);
        jp.add(jLabel2);
        jp.add(jb2);
        jf.add(jp);
    }
}
