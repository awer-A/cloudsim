package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class fx_main extends JFrame {
    SpringLayout springLayout=new SpringLayout();//定义布局
    JPanel centerPanel = new JPanel(springLayout);//定义JPanel的容器，并确定其布局
    JPanel northPanel = new JPanel();
    JLabel label1 = new JLabel("欢迎来到CloudSim!",JLabel.CENTER);//该标签展示文本
    //JLabel label2=new JLabel("CloudSim 样例:");
    JLabel label3=new JLabel("CloudSim 仿真:");
    //JLabel label4=new JLabel("by XJU 网安19-5班白凤瑶 designed");
    //JButton button1=new JButton("进入");
    JButton button2=new JButton("进入");

    public fx_main(){
        super();
        Container container=getContentPane();
        //设置字体样式、大小
        label1.setFont(new Font("微软雅黑",Font.BOLD,60));
       // label2.setFont(new Font("微软雅黑",Font.BOLD,30));
        label3.setFont(new Font("微软雅黑",Font.BOLD,30));
       // button1.setFont(new Font("微软雅黑",Font.BOLD,30));
        button2.setFont(new Font("微软雅黑",Font.BOLD,30));
        //设置字体颜色
        label1.setForeground(Color.RED);
        //label2.setForeground(Color.BLUE);
        label3.setForeground(Color.BLUE);
        //加入面板
        northPanel.add(label1);
       // centerPanel.add(label2);
        centerPanel.add(label3);
       // centerPanel.add(button1);
        centerPanel.add(button2);
        //centerPanel.add(label4);
        //设置组件约束
       // springLayout.putConstraint(SpringLayout.NORTH,label2,90,SpringLayout.NORTH,centerPanel);
        //springLayout.putConstraint(SpringLayout.WEST,label2,200,SpringLayout.WEST,centerPanel);
//        springLayout.putConstraint(SpringLayout.NORTH,button1,0,SpringLayout.NORTH,label2);
//        springLayout.putConstraint(SpringLayout.WEST,button1,20,SpringLayout.EAST,label2);
//        springLayout.putConstraint(SpringLayout.NORTH,label3,90,SpringLayout.SOUTH,label2);
//        springLayout.putConstraint(SpringLayout.EAST,label3,0,SpringLayout.EAST,label2);
        springLayout.putConstraint(SpringLayout.NORTH,button2,0,SpringLayout.NORTH,label3);
        springLayout.putConstraint(SpringLayout.WEST,button2,20,SpringLayout.EAST,label3);
//        springLayout.putConstraint(SpringLayout.NORTH,label4,90,SpringLayout.SOUTH,label3);
//        springLayout.putConstraint(SpringLayout.EAST,label4,30,SpringLayout.EAST,label3);
        container.add(northPanel,BorderLayout.NORTH);
        container.add(centerPanel,BorderLayout.CENTER);
        //对按钮添加监视器
//       button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new fx_JFrame();
//            }
//
//        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new fx_JFrame1();
            }
        });
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new fx_main();
    }
}
