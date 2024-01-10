package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Enumeration;

public class fx_JFrame1 extends JFrame {
    SpringLayout springLayout1=new SpringLayout();//定义布局
    JPanel cenPanel = new JPanel(springLayout1);//定义JPanel的容器，并确定其布局
    JPanel norPanel = new JPanel();
    JLabel l1 = new JLabel("自定义模拟仿真调度",JLabel.CENTER);//该标签展示文本
    JLabel l2 = new JLabel("Cloudlets Number:");
    JLabel l3 = new JLabel("Virtual Machine Number:");
    JLabel l4 = new JLabel("by XJU 网安19-5班白凤瑶 designed");
    JRadioButton j1 = new JRadioButton("顺序分配调度");
    JRadioButton j2 = new JRadioButton("贪心算法调度");
    JRadioButton j3 = new JRadioButton("GA遗传算法调度");
//    JRadioButton j4 = new JRadioButton("ACO遗传算法调度");


    ButtonGroup group = new ButtonGroup();
    JTextField text1 = new JTextField(22);
    JTextField text2 = new JTextField(22);
    JButton b1=new JButton("运行");
    public fx_JFrame1(){
        super();
        Container container1=getContentPane();
        group.add(j1);
        group.add(j2);
        group.add(j3);
//        group.add(j4);

        //设置字体样式、大小
        l1.setFont(new Font("微软雅黑",Font.BOLD,60));
        l2.setFont(new Font("微软雅黑",Font.BOLD,15));
        l3.setFont(new Font("微软雅黑",Font.BOLD,15));
        b1.setFont(new Font("微软雅黑",Font.BOLD,30));
        //设置字体颜色
        l1.setForeground(Color.BLACK);
        l2.setForeground(Color.BLACK);
        l3.setForeground(Color.BLACK);
        //加入面板
        norPanel.add(l1);
        cenPanel.add(l2);
        cenPanel.add(l3);
        cenPanel.add(j1);
        cenPanel.add(text1);
        cenPanel.add(j2);
        cenPanel.add(j3);
//        cenPanel.add(j4);
        cenPanel.add(l4);
        cenPanel.add(b1);
        cenPanel.add(text2);
        //设置组件约束
        springLayout1.putConstraint(SpringLayout.NORTH,l2,90,SpringLayout.NORTH,cenPanel);
        springLayout1.putConstraint(SpringLayout.WEST,l2,100,SpringLayout.WEST,cenPanel);
        springLayout1.putConstraint(SpringLayout.NORTH,text1,0,SpringLayout.NORTH,l2);
        springLayout1.putConstraint(SpringLayout.WEST,text1,50,SpringLayout.EAST,l2);
        springLayout1.putConstraint(SpringLayout.NORTH,l3,50,SpringLayout.SOUTH,l2);
        springLayout1.putConstraint(SpringLayout.EAST,l3,0,SpringLayout.EAST,l2);
        springLayout1.putConstraint(SpringLayout.NORTH,text2,0,SpringLayout.NORTH,l3);
        springLayout1.putConstraint(SpringLayout.WEST,text2,50,SpringLayout.EAST,l3);
        springLayout1.putConstraint(SpringLayout.NORTH,j1,50,SpringLayout.NORTH,l3);
        springLayout1.putConstraint(SpringLayout.WEST,j1,50,SpringLayout.EAST,l3);
        springLayout1.putConstraint(SpringLayout.NORTH,j2,100,SpringLayout.NORTH,l3);
        springLayout1.putConstraint(SpringLayout.WEST,j2,50,SpringLayout.EAST,l3);
        springLayout1.putConstraint(SpringLayout.NORTH,j3,150,SpringLayout.NORTH,l3);
        springLayout1.putConstraint(SpringLayout.WEST,j3,50,SpringLayout.EAST,l3);
//        springLayout1.putConstraint(SpringLayout.NORTH,j4,150,SpringLayout.NORTH,l3);
//       springLayout1.putConstraint(SpringLayout.WEST,j4,50,SpringLayout.EAST,l3);
        springLayout1.putConstraint(SpringLayout.NORTH,b1,50,SpringLayout.SOUTH,j3);
        springLayout1.putConstraint(SpringLayout.EAST,b1,0,SpringLayout.EAST,j3);
        springLayout1.putConstraint(SpringLayout.NORTH,l4,20,SpringLayout.SOUTH,b1);
        springLayout1.putConstraint(SpringLayout.EAST,l4,50,SpringLayout.EAST,b1);
        container1.add(norPanel,BorderLayout.NORTH);
        container1.add(cenPanel,BorderLayout.CENTER);
        //对按钮添加监视器
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("D:\\1.txt");
                JFrame frame1 = new JFrame("模拟调度结果");
                JTextArea JT = new JTextArea();
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setBounds(50, 200, 500, 300);
                scrollPane.setViewportView(JT);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                String s1 = text1.getText();//获取用户输入的参数
                String s2 = text2.getText();
                int cloudNum = Integer.parseInt(s1);//将字符串转化为int
                int vmNumer = Integer.parseInt(s2);
                int index = 0; //单选框序列值
                Enumeration<AbstractButton> radioBtns = group.getElements();
                while (radioBtns.hasMoreElements()) {
                    AbstractButton btn = radioBtns.nextElement();
                    if(btn.isSelected()){
                        if(btn.getText()=="顺序分配调度"){
                            index = 0;
                        }
                        else if(btn.getText()=="贪心算法调度"){
                            index = 1;
                        }
                        else if(btn.getText()=="GA遗传算法调度"){
                            index = 2;
                        }
//                        else if(btn.getText()=="ACO算法调度"){
//                            index = 3;
//                        }
                        break;
                    }
                }
                //程序主要逻辑
                try {
                    fx_extended1.cloudletNum = cloudNum;
                    fx_extended1.vmNum = vmNumer;

                    fx_extended2.cloudletNum = cloudNum;
                    fx_extended2.vmNum = vmNumer;

                    fx_extended3.cloudletNum = cloudNum;
                    fx_extended3.vmNum = vmNumer;

//                    MyAllocationTest.cloudletNum = cloudNum;
//                    MyAllocationTest.vmNum = vmNumer;

                    PrintStream ps = new PrintStream(file);
                    System.setOut(ps);//把创建的打印输出流赋给系统，即系统下次向Ps输出
                    switch (index){
                        case 0:
                            fx_extended1.main(new String[]{});
                            break;
                        case 1:
                            fx_extended2.main(new String[]{});
                            break;
                        case 2:
                            fx_extended3.main(new String[]{});
//                        case 3:
//                            MyAllocationTest.main(new String[]{});
                            break;
                    }
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String s;
                    while ((s = br.readLine()) != null) {
                        JT.setText(JT.getText() + "\n" + s);
                    }
                    br.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                JT.setFont(new Font("微软雅黑",Font.BOLD,20));
                JT.setEditable(false);
                frame1.add(scrollPane);
                frame1.setBounds(400,80,900,700);
                frame1.setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        setSize(800,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
