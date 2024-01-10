package GUI;

import java.util.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.border.TitledBorder;
import java.awt.event.*;

/*
主函数
 */
public class MyAllocationTest {
    private static List<Cloudlet> cloudletList;//任务列表
    public static int cloudletNum;//任务数量之前是private
    static JFrame frame;
    static JTextField textField;
    private static List<Vm> vmList= new ArrayList<Vm>();//虚拟机列表
    public static int vmNum=5;//虚拟机数量之前是private
    static String st;
    static double value1=0;

    MyAllocationTest(){
        //ImageIcon icon = new ImageIcon(getClass().getResource("aco1.png"));
        //JLabel commentlabel = new JLabel(icon);
        //commentlabel.setBounds(520,25,250,200);
        //frame.add(commentlabel);

    }


    public static void main(String args[]){

        frame = new JFrame();
        frame.setBounds(100, 100, 521, 332);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        new MyAllocationTest();

        final JLabel lblTaskSchedulingIn = new JLabel("resource Scheduling");
        lblTaskSchedulingIn.setBounds(20, 0, 550, 80);
        lblTaskSchedulingIn.setFont(new Font("FreeSerif", Font.BOLD, 25));
        frame.getContentPane().add(lblTaskSchedulingIn);
        frame.setBackground(Color.GRAY);
        frame.setTitle("TA-Ant Colony Optimization by SYSU  白凤瑶");

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyAllocationTest window = new MyAllocationTest();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel lblNewLabel = new JLabel("Enter the Number of job");
        lblNewLabel.setBounds(32, 72, 236, 20);
        lblNewLabel.setFont(new Font("FreeSerif", Font.BOLD, 16));
        frame.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(341, 72, 114, 32);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Start Simulation");
        btnNewButton.setBounds(341, 116, 157, 25);
        frame.getContentPane().add(btnNewButton);

        frame.setSize(800,600);
        frame.setVisible(true);


        Log.printLine("Starting TA-ACO Simulaion...");
        int num_user=1;//16
        Calendar calendar=Calendar.getInstance();
        boolean trace_flag=false;
        System.out.println("Enter the number of Cloudlets to be allocated- ");


        JLabel lblNewLabel1 = new JLabel();
        lblNewLabel1.setBounds(32, 190, 236, 32);
        lblNewLabel1.setFont(new Font("FreeSerif", Font.BOLD, 16));
        frame.getContentPane().add(lblNewLabel1);

        JLabel lbltime = new JLabel();
        lbltime.setBounds(32, 430, 630, 32);
        lbltime.setFont(new Font("FreeSerif", Font.BOLD, 16));
        frame.getContentPane().add(lbltime);

        btnNewButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //tf.setText("Welcome to Javatpoint.");
                st= textField.getText();


                cloudletNum=Integer.parseInt(st);

                //第一步：初始化CloudSim库
                CloudSim.init(num_user, calendar, trace_flag);

                @SuppressWarnings("unused")
                //第二步：创建数据中心
                Datacenter datacenter0=createDatacenter("Datacenter_0");
                Datacenter datacenter1=createDatacenter("Datacenter_1");
                Datacenter datacenter2=createDatacenter("Datacenter_2");

                //第三步：创建数据中心代理
                LinkACO broker=createBroker();
                int brokerId=broker.getId();

                //第四步：创建虚拟机
                //虚拟机一致的描述
                long size=10000;
                int ram=512;//512
                long bw=1000;//1000
                int pesNumber=1;
                String vmm="xen";

                //创建五个虚拟机
                Vm vm1 = new Vm(0, brokerId, 1000, pesNumber, ram, bw, size,
                        vmm, new CloudletSchedulerSpaceShared());
                Vm vm2 = new Vm(1, brokerId, 1500, pesNumber, ram, bw, size,
                        vmm,new CloudletSchedulerTimeShared());
                Vm vm3 = new Vm(2, brokerId, 2000, pesNumber, ram, bw, size,
                        vmm,new CloudletSchedulerTimeShared());
                Vm vm4 = new Vm(3, brokerId, 2500, pesNumber, ram, bw, size,
                        vmm, new CloudletSchedulerSpaceShared());
                Vm vm5 = new Vm(4, brokerId, 3000, pesNumber, ram, bw, size,
                        vmm, new CloudletSchedulerSpaceShared());
				Vm vm6 = new Vm(6, brokerId, 1000, pesNumber, ram, bw, size,
						vmm, new CloudletSchedulerSpaceShared());
				Vm vm7 = new Vm(7, brokerId, 1500, pesNumber, ram, bw, size,
						vmm,new CloudletSchedulerTimeShared());
				Vm vm8 = new Vm(8, brokerId, 2000, pesNumber, ram, bw, size,
						vmm,new CloudletSchedulerTimeShared());
				Vm vm9 = new Vm(9, brokerId, 2500, pesNumber, ram, bw, size,
						vmm, new CloudletSchedulerSpaceShared());
				Vm vm10 = new Vm(10, brokerId, 3000, pesNumber, ram, bw, size,
						vmm, new CloudletSchedulerSpaceShared());

                //五个虚拟机添加到虚拟机列表
                vmList.add(vm1);
                vmList.add(vm2);
                vmList.add(vm3);
                vmList.add(vm4);
                vmList.add(vm5);
							vmList.add(vm6);
							vmList.add(vm7);
							vmList.add(vm8);
							vmList.add(vm9);
							vmList.add(vm10);

                //将虚拟机提交给broker，broker还没创建主机列表里面的主机
                broker.submitVmList(vmList);

                //第五步：创建任务
                //任务信息
                int id=0;
                long[] lengths=new long[cloudletNum];
                long fileSize=1000;//1000
                long outputSize=1000;//1000

                UtilizationModel model=new UtilizationModelFull();
                Random rand = new Random();//创建一些随机数
                for(int i=0;i<cloudletNum;i++)
                {
                    long leftLimit = 16000;//11000
                    long rightLimit = 50000;//12000
                    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
                    //创建长度不一样的任务
                    //调用这个Math.Random()函数能够返回带正号的double值，该值大于等于0.0且小于1.0，
                    // 即取值范围是[0.0,1.0)的左闭右开区间，返回值是一个伪随机选择的数，在该范围内（近似）均匀分布
                    lengths[i]=generatedLong;
                }

                cloudletList=new ArrayList<Cloudlet>();

                for (int i = 0; i < cloudletNum; i++) {
                    Cloudlet cloudlet=new Cloudlet(id, lengths[i], pesNumber, fileSize, outputSize, model, model, model);
                    cloudlet.setUserId(brokerId);
                    cloudletList.add(cloudlet);//提交任务到列表
                    id++;
                }
                //向代理提交任务列表
                broker.submitCloudletList(cloudletList);
                //自定义任务调度算法：基于蚁群算法将任务绑定到虚拟机
                broker.bind(5,50);//bind    蚂蚁数量和迭代次数
                //broker.bindCloudletsToVmsTimeAwared();

                NetworkTopology.buildNetworkTopology("D:\\桌面\\cloudsim-master1\\cloudsim-master\\modules\\cloudsim-examples\\src\\main\\java\\org\\cloudbus\\cloudsim\\examples\\network\\topology.brite");
                //NetworkTopology.buildNetworkTopology("D:\\桌面\\cloudsim-3.0.3\\examples\\org\\cloudbus\\cloudsim\\examples\\network\\topology.brite");
                //D:\桌面\cloudsim-master1\cloudsim-master\modules\cloudsim-examples\src\main\java\org\cloudbus\cloudsim\examples\network\topology.brite
                //将CloudSim实体映射到BRITE实体 maps CloudSim entities to BRITE entities

                //数据中心0将对应BRITE节点0
                int briteNode=0;
                NetworkTopology.mapNode(datacenter0.getId(),briteNode);


//				briteNode=1;
//				NetworkTopology.mapNode(vm1.getId(),briteNode);
                //数据中心1将对应BRITE节点2

                briteNode=2;
                NetworkTopology.mapNode(datacenter1.getId(),briteNode);

                //代理将对应BRITE节点3
                briteNode=3;
                NetworkTopology.mapNode(broker.getId(),briteNode);

                //数据中心2将对应BRITE节点4
                briteNode=4;
                NetworkTopology.mapNode(datacenter2.getId(), briteNode);

                //第六步：开始模拟
                CloudSim.startSimulation();

                //最后一步：模拟结束后打印结果
                List<Cloudlet> newList=broker.getCloudletReceivedList();

                CloudSim.stopSimulation();

                Log.printLine("TA-ACO simulation is finished!");

                lblNewLabel1.setText("TA-ACO Simulation finished!");

                String data[][]=printCloudletList(newList);

                String column[]={"job ID" ,  "Datacenter ID" ,  "VM ID", "Time", "Start Time", "Finish Time", "Status"};
                JTable jt=new JTable(data,column);
                frame.getContentPane().add(jt);
                //jt.setBounds(32,185,540,192);
                JScrollPane scrollPane = new JScrollPane(jt);
                scrollPane.setBounds(32, 230, 700, 180);
                frame.getContentPane().add(scrollPane);

                lbltime.setText("This  schedule plan takes "+ value1/10 +" ms  to finish execution.");
            }
        });

        //Log.printLine("ACO simulation is finished!");*/
    }


    //创建数据中心是一个单独的函数，定义步骤如下：
    public static Datacenter createDatacenter(String name){
        //第一步：定义主机列表
        List<Host> hostList=new ArrayList<Host>();

        //第二步：定义pe列表，代表物理主机的一个cpu，主机包含一个或多个pe
        List<Pe> peList = new ArrayList<Pe>();

        //第三步：创建pe并加入到pelist中，每个pe的构造函数需要传入他的id和计算能力
        int mips=5000;//mips衡量了cpu的计算能力
        peList.add(new Pe(0, new PeProvisionerSimple(mips)));

        mips = 2500;
        peList.add(new Pe(1, new PeProvisionerSimple(mips)));

        mips = 2500;
        peList.add(new Pe(2, new PeProvisionerSimple(mips)));

        mips = 1500;
        peList.add(new Pe(3, new PeProvisionerSimple(mips)));

        mips = 1000;
        peList.add(new Pe(4, new PeProvisionerSimple(mips)));

        //第四步：创建主机，指定id，内存大小，磁盘大小，带宽大小

        int hostId=0;
        int ram=4096;
        long storage=10000000;
        int bw=10000;
        //虚拟机分配策略
        hostList.add(
                new Host(
                        hostId,
                        new RamProvisionerSimple(ram),//内存提供者，为虚拟机提供内存
                        new BwProvisionerSimple(bw),//带宽提供者
                        storage,
                        peList,
                        new VmSchedulerSpaceShared(peList)//空间共享的虚拟机调度策略 之前是时间共享
                )
        );
        //第五步：创建数据中心特征：
        String arch="x86";
        String os="Linux";
        String vmm="Xen";
        double time_zone=5.30;
        double cost=3.0;
        double costPerMcm=0.05;
        double costPerStorage=0.001;
        double costPerBw=0.001;
        LinkedList<Storage> storageList=new LinkedList<Storage>();

        DatacenterCharacteristics characteristics=new DatacenterCharacteristics(arch, os, vmm, hostList, time_zone, cost, costPerMcm, costPerStorage, costPerBw);

        //第六步：完成数据中心的配置并返回构建好的对象，我们需要创建一个PowerDatacenter对象
        Datacenter datacenter=null;
        try {
            datacenter=new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return datacenter;
    }
//至此，createDatacenter函数结束

    //创建代理函数：
    private static LinkACO createBroker(){
        LinkACO broker=null;
        try {
            broker=new LinkACO("Broker");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return broker;
    }

    //打印Cloudlet对象
    protected static String[][] printCloudletList(List<Cloudlet> list){
        int size=list.size();
        Cloudlet cloudlet;
        //double value1=0;
        String indent="    ";
        Log.printLine();
        Log.printLine("========Execution Result ========");
        Log.printLine("job ID"+indent+"STATUS"+indent+"Datacenter ID"+indent+"VM ID"+indent+"Time"+indent+"Start Time"+indent+"Finish Time");
        DecimalFormat dft=new DecimalFormat("###.##");
        String arr[][]=new String[size][7];
        for(int i=0;i<size;i++){
            cloudlet=list.get(i);
            Log.print(indent+cloudlet.getCloudletId()+indent+indent);
            if (cloudlet.getStatus()==Cloudlet.SUCCESS) {
                Log.print("SUCESSS");
                arr[i][0]=String.valueOf(cloudlet.getCloudletId());
                arr[i][1]=String.valueOf(cloudlet.getResourceId());
                arr[i][2]=String.valueOf(cloudlet.getVmId());
                arr[i][3]=String.valueOf(cloudlet.getActualCPUTime());
                arr[i][4]=String.valueOf(cloudlet.getExecStartTime());
                arr[i][5]=String.valueOf(cloudlet.getFinishTime());
                arr[i][6]="Success";
                Log.printLine(indent+indent+cloudlet.getResourceId()+indent+indent+indent
                        +cloudlet.getVmId()+indent+indent+dft.format(cloudlet.getActualCPUTime())+
                        indent+indent+dft.format(cloudlet.getExecStartTime())+
                        indent+indent+dft.format(cloudlet.getFinishTime()));


            }
            value1= value1+Double.parseDouble(dft.format(cloudlet.getActualCPUTime()));

        }

        Log.printLine("================ Execution Result Ends here ==================");
        System.out.println("This  schedule plan takes "+value1/10+" ms  to finish execution.");

        return arr;
    }
}
