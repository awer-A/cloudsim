package GUI;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.text.DecimalFormat;
import java.util.*;

/*
贪心算法
 */
public class fx_extended2 {
    public static int vmNum=25;//虚拟机总数
    public static int cloudletNum = 200;//任务总数
    private static List<Cloudlet> cloudletList; //任务列表
    private static List<Vm> vmList; //虚拟机列表
    public static void main(String[] args) {
        Log.printLine("开始仿真......");
        try {
            int num_user = 1;
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;
            //第一步：初始化CloudSim包
            CloudSim.init(num_user,calendar,trace_flag);
            //第二步：创建数据中心
            Datacenter datacenter0 = createDatacenter("Datacenter_0");
            Datacenter datacenter1 = createDatacenter("Datacenter_1");
            Datacenter datacenter2 = createDatacenter("Datacenter_2");
            //第三步：创建数据中心代理
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();
            //设置虚拟机参数
            int vmid = 0;
            int[] mipss = new int[]{1000,1500,2000,2500,3000,1000,1500,2000,2500,3000,1000,1500,2000,2500,3000,1000,1500,2000,2500,3000,1000,1500,2000,2500,3000};//,1000,1500,2000,2500,3000
            long size = 10000;
            int ram = 512;
            long bw = 1000;
            int pesNumber = 1;
            String vmm = "Xen";
            //第四步：创建虚拟机
            vmList = new ArrayList<Vm>();
            for(int i = 0;i<vmNum;i++){
                vmList.add(new Vm(vmid,brokerId,mipss[i],pesNumber,ram,bw,size,vmm,
                        new CloudletSchedulerSpaceShared()));
                vmid++;
            }
            //提交虚拟机列表
            broker.submitVmList(vmList);
            //任务参数
            int id = 0;
            long[] lengths = new long[cloudletNum];
            long fileSize = 1000;
            long outputSize = 1000;
            UtilizationModel utilizationModel = new UtilizationModelFull();
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
            //第五步：创建云任务
            cloudletList = new ArrayList<Cloudlet>();
            for(int i = 0;i<cloudletNum;i++){
                Cloudlet cloudlet = new Cloudlet(id,lengths[i],pesNumber,fileSize,outputSize,
                        utilizationModel,utilizationModel,utilizationModel);
                cloudlet.setUserId(brokerId);
                cloudletList.add(cloudlet);
                id++;
            }
            //提交任务列表
            broker.submitCloudletList(cloudletList);
            //第六步：绑定任务到虚拟机
            //自定义调度算法：调度贪婪分配策略
            broker.fx_CloudletsToVmTimeAwared();
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

//            第七步:启动仿真
            CloudSim.startSimulation();
            //第八步：统计结果并输出结果
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            CloudSim.stopSimulation();
            printCloudletList_1(newList);
            Log.printLine("贪心算法策略调度完成！");
        }catch(Exception e){
            e.printStackTrace();
            Log.printLine("出错了。。。");
        }
    }
    //创建数据中心的步骤
    private static Datacenter createDatacenter(String name){
        //1.创建主机列表
        List<Host> hostList = new ArrayList<Host>();
        //PE及主机参数
        int MIPS = 1000;
        int hostId = 0;
        int ram = 4096;
        long storage = 10000000;
        int bw = 10000;
        for(int i=0;i<vmNum;i++){
            //2.创建PE列表
            List<Pe> peList = new ArrayList<Pe>();
            //3.创建PE并加入列表
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

            //4.创建主机并加入列表
            hostList.add(new Host(hostId,
                    new RamProvisionerSimple(ram),
                    new BwProvisionerSimple(bw),
                    storage,
                    peList,
                    new VmSchedulerTimeShared(peList)
            ));
            hostId++;
        }
        //数据中心特征参数
        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";
        double time_zone = 5.30;
        double cost = 3.0;
        double costPerMem = 0.05;
        double costPerStorage = 0.001;
        double costPerBw = 0.001;
        LinkedList<Storage> storageList = new LinkedList<Storage>();
        //5.创建数据中心特征对象
        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch,os,vmm,hostList,time_zone,cost,costPerMem,costPerStorage,costPerBw
        );
        //6.创建数据中心对象
        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name,characteristics,new VmAllocationPolicySimple(hostList),
                    storageList,0);
        }catch(Exception e){
            e.printStackTrace();
        }
        return datacenter;
    }
    //创建数据中心代理
    private static DatacenterBroker createBroker(){
        DatacenterBroker broker = null;
        try{
            broker = new DatacenterBroker("Broker");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return broker;
    }
    private static String printCloudletList_1(List<Cloudlet> list)
    {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        Log.printLine();
        Log.printLine("================ Execution Result ==================");
        Log.printLine("No."+indent +"Cloudlet ID" + indent + "STATUS" + indent
                + "Data center ID" + indent + "VM ID" + indent+"VM mips"+ indent +"CloudletLength"+indent+ "Time"
                + indent + "Start Time" + indent + "Finish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++)
        {
            cloudlet = list.get(i);
            Log.print(i+1+indent+indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getStatus()== Cloudlet.SUCCESS)
            {
                Log.print("SUCCESS");

                Log.printLine(indent +indent + indent + cloudlet.getResourceId()
                        + indent + indent + indent + cloudlet.getVmId()
                        + indent + indent + getVmById(cloudlet.getVmId()).getMips()
                        + indent + indent + cloudlet.getCloudletLength()
                        + indent + indent+ indent + indent
                        + dft.format(cloudlet.getActualCPUTime()) + indent
                        + indent + dft.format(cloudlet.getExecStartTime())
                        + indent + indent
                        + dft.format(cloudlet.getFinishTime()));
            }
        }
        Log.printLine("================ Execution Result Ends here ==================");
        //最后完成的任务的完成时刻就是调度方案的总执行时间
        return dft.format(list.get(size-1).getFinishTime());
    }
    public static Vm getVmById(int vmId)
    {
        for(Vm v:vmList)
        {
            if(v.getId()==vmId)
                return v;
        }
        return null;
    }

}

