package GUI;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.text.DecimalFormat;
import java.util.*;
/*
遗传调度算法GA
 */
public class fx_extended3 {
    public static int vmNum=25;//虚拟机总数
    public static int cloudletNum = 200;//任务总数
    private static List<Cloudlet> cloudletList; //任务列表
    private static List<Vm> vmList; //虚拟机列表
    static double value2=0;
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

            //第六步：绑定任务到虚拟机
            //调度遗传算法分配策略
            boolean isGAscheduleApplied=true;
            if(isGAscheduleApplied)
            {
                runSimulation_GA(broker);
            }
            else
            {
                runSimulation_RR(broker);
            }
            Log.printLine("GA遗传算法策略调度完成！");
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
    public static void runSimulation_RR(DatacenterBroker broker)
    {
        CloudSim.startSimulation();
        // Final step: Print results when simulation is over
        List<Cloudlet> newList = broker.getCloudletReceivedList();

        CloudSim.stopSimulation();

        for(Vm vm:vmList)
        {
            Log.printLine(String.format("vm id= %s ,mips = %s ",vm.getId(),vm.getMips()));
        }
        String finishTm=printCloudletList_1(newList);
        System.out.println("This schedule plan takes "+finishTm+" ms to finish execution.");
    }
    public static void runSimulation_GA(DatacenterBroker broker)
    {
        //GA调度算法的参数
        int popsize=20;
        int gmax=50;
        double crossoverProb=0.8;
        double mutationRate=0.01;
        //执行GA调度方案
        applyGAscheduling(popsize,gmax,crossoverProb,mutationRate);

        CloudSim.startSimulation();

        // Final step: Print results when simulation is over
        List<Cloudlet> newList = broker.getCloudletReceivedList();

        CloudSim.stopSimulation();

        for(Vm vm:vmList)
        {
            Log.printLine(String.format("vm id= %s ,mips = %s ",vm.getId(),vm.getMips()));
        }
        String finishTm=printCloudletList_1(newList);
        System.out.println("This schedule plan takes "+finishTm+" ms to finish execution.");

        //由于每次执行GA调度算法的调度结果都不同(由于GA过程中加入了随机性,甚至可能比RR还差),以下3行代码是取n次调度方案来计算GA结果的平均执行时间.
        int n=10;
        double avgRuntime=getAvgRuntimeByGAscheduling(n,popsize,gmax,crossoverProb,mutationRate);
        System.out.println(String.format("==============Printing the average runningtime GA schedule plans ===================\nAvg runtime of (n=%d) GA schedule plans:%.2f ms.",n,avgRuntime));

    }
    public static double getAvgRuntimeByGAscheduling(int times,int popSize,int gmax,double crossoverProb,double mutationRate)
    {
        double sum=0;
        for(int i=0;i<times;i++)
        {
            int[] schedule=getScheduleByGA( popSize, gmax, crossoverProb, mutationRate);
            sum+=getFitness(schedule);
        }
        return sum/times;
    }
    public static void applyGAscheduling(int popSize,int gmax,double crossoverProb,double mutationRate)
    {
        int []schedule=getScheduleByGA(popSize,gmax,crossoverProb,mutationRate);
        assignResourcesWithSchedule(schedule);
    }
    public static void assignResourcesWithSchedule(int []schedule)
    {
        for(int i=0;i<schedule.length;i++)
        {
            getCloudletById(i).setVmId(schedule[i]);
        }
    }

    private static int[] findBestSchedule(ArrayList<int[]> pop)
    {
        double bestFitness=1000000000;
        int bestIndex=0;
        for(int i=0;i<pop.size();i++)
        {
            int []schedule=pop.get(i);
            double fitness=getFitness(schedule);
            if(bestFitness>fitness)
            {
                bestFitness=fitness;
                bestIndex=i;
            }
        }
        return pop.get(bestIndex);
    }

    private static int[] getScheduleByGA(int popSize,int gmax,double crossoverProb,double mutationRate)
    {
        ArrayList<int[]> pop=initPopsRandomly(cloudletList.size(),vmList.size(),popSize);
        pop=GA(pop,gmax,crossoverProb,mutationRate);
        return findBestSchedule(pop);
    }

    private static ArrayList<int[]> initPopsRandomly(int taskNum,int vmNum,int popsize)
    {
        ArrayList<int[]> schedules=new ArrayList<int[]>();
        for(int i=0;i<popsize;i++)
        {
            //保存计划的数据结构：数组，数组的索引为cloudlet id，数组的内容为vm id
            int[] schedule=new int[taskNum];
            for(int j=0;j<taskNum;j++)
            {
                schedule[j]=new Random().nextInt(vmNum);
            }
            schedules.add(schedule);
        }
        return schedules;
    }

    private static double getFitness(int[] schedule)
    {
        double fitness=0;

        HashMap<Integer,ArrayList<Integer>> vmTasks=new HashMap<Integer,ArrayList<Integer>>();
        int size=cloudletList.size();

        for(int i=0;i<size;i++)
        {
            if(!vmTasks.keySet().contains(schedule[i]))
            {
                ArrayList<Integer> taskList=new ArrayList<Integer>();
                taskList.add(i);
                vmTasks.put(schedule[i],taskList);
            }
            else
            {
                vmTasks.get(schedule[i]).add(i);
            }
        }

        for(Map.Entry<Integer, ArrayList<Integer>> vmtask:vmTasks.entrySet())
        {
            int length=0;
            for(Integer taskid:vmtask.getValue())
            {
                length+=getCloudletById(taskid).getCloudletLength();
            }

            double runtime=length/getVmById(vmtask.getKey()).getMips();
            if (fitness<runtime)
            {
                fitness=runtime;
            }
        }

        return fitness;
    }

    private static ArrayList<int[]> GA(ArrayList<int[]> pop,int gmax,double crossoverProb,double mutationRate)
    {
        HashMap<Integer,double[]> segmentForEach=calcSelectionProbs(pop);
        ArrayList<int[]> children=new ArrayList<int[]>();
        ArrayList<int[]> tempParents=new ArrayList<int[]>();
        while(children.size()<pop.size())
        {
            //选择阶段：每次选择两个家长
            for(int i=0;i<2;i++)
            {
                double prob = new Random().nextDouble();
                for (int j = 0; j < pop.size(); j++)
                {
                    if (isBetween(prob, segmentForEach.get(j)))
                    {
                        tempParents.add(pop.get(j));
                        break;
                    }
                }
            }
            //交叉运算阶段
            int[] p1,p2,p1temp,p2temp;
            p1= tempParents.get(tempParents.size() - 2).clone();
            p1temp= tempParents.get(tempParents.size() - 2).clone();
            p2 = tempParents.get(tempParents.size() -1).clone();
            p2temp = tempParents.get(tempParents.size() -1).clone();
            if(new Random().nextDouble()<crossoverProb)
            {
                int crossPosition = new Random().nextInt(cloudletList.size() - 1);
                //cross-over operation
                for (int i = crossPosition + 1; i < cloudletList.size(); i++)
                {
                    int temp = p1temp[i];
                    p1temp[i] = p2temp[i];
                    p2temp[i] = temp;
                }
            }
            //选择更好的孩子，否则在下一次迭代中保留父母。
            children.add(getFitness(p1temp) < getFitness(p1) ? p1temp : p1);
            children.add(getFitness(p2temp) < getFitness(p2) ? p2temp : p2);
            // 突变阶段
            if (new Random().nextDouble() < mutationRate)
            {
                //以下是突变操作
                int maxIndex = children.size() - 1;

                for (int i = maxIndex - 1; i <= maxIndex; i++)
                {
                    operateMutation(children.get(i));
                }
            }
        }

        gmax--;
        return gmax > 0 ? GA(children, gmax, crossoverProb, mutationRate): children;
    }

    public static void operateMutation(int []child)
    {
        int mutationIndex = new Random().nextInt(cloudletList.size());
        int newVmId = new Random().nextInt(vmList.size());
        while (child[mutationIndex] == newVmId)
        {
            newVmId = new Random().nextInt(vmList.size());
        }

        child[mutationIndex] = newVmId;
    }

    private static boolean isBetween(double prob,double[]segment)
    {
        if(segment[0]<=prob&&prob<=segment[1])
            return true;
        return false;
    }

    private static HashMap<Integer,double[]> calcSelectionProbs(ArrayList<int[]> parents)
    {
        int size=parents.size();
        double totalFitness=0;
        ArrayList<Double> fits=new ArrayList<Double>();
        HashMap<Integer,Double> probs=new HashMap<Integer,Double>();

        for(int i=0;i<size;i++)
        {
            double fitness=getFitness(parents.get(i));
            fits.add(fitness);
            totalFitness+=fitness;
        }
        for(int i=0;i<size;i++)
        {
            probs.put(i,fits.get(i)/totalFitness );
        }

        return getSegments(probs);
    }

    private static HashMap<Integer,double[]> getSegments(HashMap<Integer,Double> probs)
    {
        HashMap<Integer,double[]> probSegments=new HashMap<Integer,double[]>();
        //probSegments保存每个个体的选择概率的起点、终点，以便选择作为交配元素。
        int size=probs.size();
        double start=0;
        double end=0;
        for(int i=0;i<size;i++)
        {
            end=start+probs.get(i);
            double[]segment=new double[2];
            segment[0]=start;
            segment[1]=end;
            probSegments.put(i, segment);
            start=end;
        }

        return probSegments;
    }
    public static Cloudlet getCloudletById(int id)
    {
        for(Cloudlet c:cloudletList)
        {
            if(c.getCloudletId()==id)
                return c;
        }
        return null;
    }

}

