package GUITEST;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class diaoduTest extends JFrame {
    SpringLayout springLayout=new SpringLayout();//定义布局
    JPanel centerpanel=new JPanel(springLayout);//定义Jpanel的容器，并确定其布局
    JPanel northpanel=new JPanel();
    JLabel label1=new JLabel("算法调度",JLabel.CENTER);//该标签展示文本
    JLabel label2=new JLabel("样例展示:");
    JLabel label3=new JLabel("调度算法:");
    JButton button1=new JButton("开始");
    JButton button2=new JButton("开始");
    String[] b1=new String[]{"CloudSimExample1","CloudSimExample2","CloudSimExample3","CloudSimExample4",
            "CloudSimExample5","CloudSimExample6","CloudSimExample7","CloudSimExample8"};
    String[] b2=new String[]{"贪心策略","顺序分配策略"};
    JComboBox<String> lib1=new JComboBox<>(b1);//下拉列表
    JComboBox<String> lib2=new JComboBox<>(b2);
    public diaoduTest(){
        super();
        Container container=getContentPane();
        //设置字体样式、大小
        label1.setFont(new Font("微软雅黑",Font.BOLD,60));
        label2.setFont(new Font("微软雅黑",Font.BOLD,30));
        label3.setFont(new Font("微软雅黑",Font.BOLD,30));
        button1.setFont(new Font("微软雅黑",Font.BOLD,30));
        button2.setFont(new Font("微软雅黑",Font.BOLD,30));
        lib1.setFont(new Font("微软雅黑",Font.BOLD,30));
        lib2.setFont(new Font("微软雅黑",Font.BOLD,30));
        //设置字体颜色
        label1.setForeground(Color.red);
        label2.setForeground(Color.red);
        label3.setForeground(Color.red);
        //加入面板
        northpanel.add(label1);
        centerpanel.add(label2);
        centerpanel.add(label3);
        centerpanel.add(lib1);
        centerpanel.add(lib2);
        centerpanel.add(button1);
        centerpanel.add(button2);
        //设置组件约束
        springLayout.putConstraint(SpringLayout.NORTH,label2,90,SpringLayout.NORTH,centerpanel);
        springLayout.putConstraint(SpringLayout.WEST,label2,60,SpringLayout.WEST,centerpanel);
        springLayout.putConstraint(SpringLayout.NORTH,lib1,0,SpringLayout.NORTH,label2);
        springLayout.putConstraint(SpringLayout.WEST,lib1,20,SpringLayout.EAST,label2);
        springLayout.putConstraint(SpringLayout.NORTH,button1,0,SpringLayout.NORTH,lib1);
        springLayout.putConstraint(SpringLayout.WEST,button1,20,SpringLayout.EAST,lib1);
        springLayout.putConstraint(SpringLayout.NORTH,label3,90,SpringLayout.SOUTH,label2);
        springLayout.putConstraint(SpringLayout.EAST,label3,0,SpringLayout.EAST,label2);
        springLayout.putConstraint(SpringLayout.NORTH,lib2,0,SpringLayout.NORTH,label3);
        springLayout.putConstraint(SpringLayout.WEST,lib2,20,SpringLayout.EAST,label3);
        springLayout.putConstraint(SpringLayout.NORTH,button2,0,SpringLayout.NORTH,lib2);
        springLayout.putConstraint(SpringLayout.WEST,button2,20,SpringLayout.EAST,lib2);
        container.add(northpanel,BorderLayout.NORTH);
        container.add(centerpanel,BorderLayout.CENTER);
        //对按钮添加监视器
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = lib1.getSelectedIndex();//获取用户所选择的下拉列表选项的下标
                File file = new File("D:\\1.txt");
                JFrame frame1 = new JFrame("例子");
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
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index1=lib2.getSelectedIndex();
                File file=new File("D:\\1.txt");
                JFrame frame2=new JFrame("调度算法");
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
                            CloudSimExample1.main(new String[]{});
                            break;
                        case 1:
                            CloudSimExample2.main(new String[]{});
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
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new diaoduTest();
    }
}

