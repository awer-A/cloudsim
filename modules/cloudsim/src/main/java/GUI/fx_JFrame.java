package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class fx_JFrame extends JFrame {
    SpringLayout springLayout1=new SpringLayout();//定义布局
    JPanel cenPanel = new JPanel(springLayout1);//定义JPanel的容器，并确定其布局
    JPanel norPanel = new JPanel();
    JLabel l1 = new JLabel("CloudSim Examples",JLabel.CENTER);//该标签展示文本
    JLabel l2 = new JLabel("CloudSim Examples:");
    JLabel l3 = new JLabel("NetWork  Examples:");
    JLabel l4 = new JLabel("by XJU 网安19-5班白凤瑶 designed");
    String[] s1 = new String[]{"CloudSimExample1","CloudSimExample2","CloudSimExample3","CloudSimExample4",
            "CloudSimExample5","CloudSimExample6","CloudSimExample7","CloudSimExample8"};
    String[] s2 = new String[]{"NetWorkExample1","NetWorkExample2","NetWorkExample3","NetWorkExample4"};
    JComboBox<String> lib1=new JComboBox<>(s1);//下拉列表
    JComboBox<String> lib2=new JComboBox<>(s2);
    JButton b1=new JButton("运行");
    JButton b2=new JButton("运行");
    public fx_JFrame(){
        super();
        Container container1=getContentPane();
        //设置字体样式、大小
        l1.setFont(new Font("微软雅黑",Font.BOLD,60));
        l2.setFont(new Font("微软雅黑",Font.BOLD,30));
        l3.setFont(new Font("微软雅黑",Font.BOLD,30));
        b1.setFont(new Font("微软雅黑",Font.BOLD,30));
        b2.setFont(new Font("微软雅黑",Font.BOLD,30));
        lib1.setFont(new Font("微软雅黑",Font.BOLD,30));
        lib2.setFont(new Font("微软雅黑",Font.BOLD,30));
        //设置字体颜色
        l1.setForeground(Color.BLACK);
        l2.setForeground(Color.BLACK);
        l3.setForeground(Color.BLACK);
        //加入面板
        norPanel.add(l1);
        cenPanel.add(l2);
        cenPanel.add(l3);
        cenPanel.add(lib1);
        cenPanel.add(lib2);
        cenPanel.add(b1);
        cenPanel.add(b2);
        cenPanel.add(l4);
        //设置组件约束
        springLayout1.putConstraint(SpringLayout.NORTH,l2,90,SpringLayout.NORTH,cenPanel);
        springLayout1.putConstraint(SpringLayout.WEST,l2,60,SpringLayout.WEST,cenPanel);
        springLayout1.putConstraint(SpringLayout.NORTH,lib1,0,SpringLayout.NORTH,l2);
        springLayout1.putConstraint(SpringLayout.WEST,lib1,100,SpringLayout.EAST,l2);
        springLayout1.putConstraint(SpringLayout.NORTH,b1,0,SpringLayout.NORTH,lib1);
        springLayout1.putConstraint(SpringLayout.WEST,b1,100,SpringLayout.EAST,lib1);
        springLayout1.putConstraint(SpringLayout.NORTH,l3,90,SpringLayout.SOUTH,l2);
        springLayout1.putConstraint(SpringLayout.EAST,l3,0,SpringLayout.EAST,l2);
        springLayout1.putConstraint(SpringLayout.NORTH,lib2,0,SpringLayout.NORTH,l3);
        springLayout1.putConstraint(SpringLayout.WEST,lib2,100,SpringLayout.EAST,l3);
        springLayout1.putConstraint(SpringLayout.NORTH,b2,0,SpringLayout.NORTH,lib2);
        springLayout1.putConstraint(SpringLayout.WEST,b2,100,SpringLayout.EAST,lib2);
        springLayout1.putConstraint(SpringLayout.NORTH,l4,90,SpringLayout.SOUTH,l3);
        springLayout1.putConstraint(SpringLayout.EAST,l4,300,SpringLayout.EAST,l3);
        container1.add(norPanel,BorderLayout.NORTH);
        container1.add(cenPanel,BorderLayout.CENTER);
        //对按钮添加监视器
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = lib1.getSelectedIndex();//获取用户所选择的下拉列表选项的下标
                File file = new File("D:\\1.txt");
                JFrame frame1 = new JFrame("样例程序运行结果");
                JTextArea JT = new JTextArea();
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setBounds(50, 200, 500, 300);
                scrollPane.setViewportView(JT);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                //例子逻辑设置
                try {
                    PrintStream ps = new PrintStream(file);
                    System.setOut(ps);//把创建的打印输出流赋给系统，即系统下次向Ps输出
                    switch (index){
                        case 0:
                            CloudSimExample1.main(new String[]{});
                            break;
                        case 1:
                            CloudSimExample2.main(new String[]{});
                            break;
                        case 2:
                            CloudSimExample3.main(new String[]{});
                            break;
                        case 3:
                            CloudSimExample4.main(new String[]{});
                            break;
                        case 4:
                            CloudSimExample5.main(new String[]{});
                            break;
                        case 5:
                            CloudSimExample6.main(new String[]{});
                            break;
                        case 6:
                            CloudSimExample7.main(new String[]{});
                            break;
                        case 7:
                            CloudSimExample8.main(new String[]{});
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
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index1=lib2.getSelectedIndex();
                File file=new File("D:\\1.txt");
                JFrame frame2=new JFrame("样例程序运行结果");
                JTextArea JT=new JTextArea();
                JScrollPane scrollPane1=new JScrollPane();
                scrollPane1.setBounds(23, 217, 650, 266);
                scrollPane1.setViewportView(JT);
                scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                try{
                    PrintStream ps = new PrintStream(file);
                    System.setOut(ps);//把创建的打印输出流赋给系统，即系统下次向Ps输出
                    switch (index1){
                        case 0:
                            NetworkExample1.main(new String[]{});
                            break;
                        case 1:
                            NetworkExample2.main(new String[]{});
                        case 2:
                            NetworkExample3.main(new String[]{});
                            break;
                        case 3:
                            NetworkExample4.main(new String[]{});
                    }
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String s;
                    while ((s = br.readLine()) != null) {
                        JT.setText(JT.getText() + "\n" + s);
                    }
                    br.close();
                }catch (IOException exception){
                    exception.printStackTrace();
                }
                JT.setFont(new Font("微软雅黑",Font.BOLD,20));
                JT.setEditable(false);
                frame2.add(scrollPane1);
                frame2.setBounds(400,80,900,700);
                frame2.setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        setSize(1200,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }}
