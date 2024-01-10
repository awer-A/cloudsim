package GUITEST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class Test {
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
        JRadioButton j1 = new JRadioButton("顺序分配调度");
        JRadioButton j2 = new JRadioButton("贪心算法调度");
        JRadioButton j3 = new JRadioButton("GA遗传算法调度");
        ButtonGroup group = new ButtonGroup();
        group.add(j1);
        group.add(j2);
        group.add(j3);
        JLabel jLabel1 = new JLabel("Cloudlets number:",SwingConstants.LEFT);
        JLabel jLabel2 = new JLabel("Virtual Machine number:",SwingConstants.LEFT);
        JPanel jp = new JPanel(new FlowLayout());
        JTextField text1 = new JTextField(22);
        JTextField text2 = new JTextField(22);
        JButton b1=new JButton("运行");
        b1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String s = text1.getText();
                int a = Integer.parseInt(s);
                System.out.println(a);
                Enumeration<AbstractButton> radioBtns=group.getElements();

                while (radioBtns.hasMoreElements()) {
                    AbstractButton btn = radioBtns.nextElement();
                    if(btn.isSelected()){
                        System.out.println(btn.getText());
                        if(btn.getText()=="顺序分配调度"){
                            int index = 1;
                            System.out.println(index);
                        }//jta2为文本域对象
                        break;
                    }
                }}
        });
        jp.add(jLabel1);
        jp.add(text1);
        jp.add(jLabel2);
        jp.add(text2);
        jp.add(j1);
        jp.add(j2);
        jp.add(j3);
        jp.add(b1);
        jf.add(jp);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
