package GUITEST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class TEST1 {
    public static void main(String[] args) {
        JFrame jf = new JFrame("FX_CloudSim");
        jf.setBounds(400,300,400,250);
        jf.setLayout(new FlowLayout(FlowLayout.LEFT));
        JComboBox box = new JComboBox();
        box.addItem("CloudSimExample1");
        box.addItem("CloudSimExample2");
        box.addItem("CloudSimExample3");
        JButton jb1 = new JButton("运行");
        JButton jb2 = new JButton("运行");
        JComboBox box1 = new JComboBox();
        box1.addItem("请选择要运行的样例程序");
        box1.addItem("NetworkExample1");
        box1.addItem("NetworkExample2");
        box1.addItem("NetworkExample3");
        jb1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = box1.getSelectedIndex()+1;
                JFrame jf = new JFrame("Result");
                File file = new File("D:\\1.txt");
                JScrollPane scrollPane = new JScrollPane();
                jf.setBounds(400,300,400,250);
                jf.setLayout(new FlowLayout(FlowLayout.LEFT));
                JTextArea area = new JTextArea(20,30);
                if(i==1){
                    area.setText("测试");
                }
                jf.add(area);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        jf.add(box);
        jf.add(jb1);
        jf.add(box1);
        jf.add(jb2);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
