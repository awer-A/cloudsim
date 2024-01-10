package GUI;

import java.util.*;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

public class Ant{
    public class position{
        public int vm;
        public int task;
        public position(int a, int b){
            vm = a;
            task = b;
        }
    }
    public double[][] delta;//每个结点增加的信息素
    public int Q = 100;//信息素强度
    public List<position> tour;//蚂蚁获得的路径（解：任务分配给虚拟机的分法）
    public double tourLength;//蚂蚁获得的路径长度（分配好后总的花费时间）
    public long[] TL_task;//每个虚拟机的任务总量
    public List<Integer> tabu;//禁忌表
    private int VMs;//城市的个数（相当于虚拟机的个数）
    private int tasks;//任务个数
    private List<? extends Cloudlet> cloudletList;	//任务列表
    private List<? extends Vm> vmList;		//虚拟机列表
    /*
    随机分配蚂蚁到某个节点上，同时完成蚂蚁包含字段的初始化工作
    list1：任务列表
    list2：虚拟机列表
     */
    public void RandomSelectVM(List<? extends Cloudlet> list1, List<? extends Vm> list2){
        cloudletList = list1;//传进来的任务列表
        vmList = list2;//传进来的虚拟机列表
        VMs = vmList.size();//虚拟机数量
        tasks = cloudletList.size();//任务数量
        delta = new double[VMs][tasks];//每个节点增加的信息素
        TL_task = new long[VMs];//每个虚拟机的任务总量
        for(int i=0; i<VMs; i++)TL_task[i] = 0;//初始化

        tabu = new ArrayList<Integer>();
        tour=new ArrayList<position>();
        //随机选择蚂蚁的位置
        int firstVM = (int)(VMs*Math.random());//随机选择第一个虚拟机
        int firstExecute = (int)(tasks*Math.random());//随机选择第一个任务
        tour.add(new position(firstVM, firstExecute));//任务分配给虚拟机的办法
        tabu.add(new Integer(firstExecute));//禁忌表增加第一个任务
        TL_task[firstVM] += cloudletList.get(firstExecute).getCloudletLength();//更新虚拟机上的任务总量
    }

    /*
    Dij为距离矩阵
    vm：虚拟机序号
    task：任务序号
     */
    public double Dij(int vm, int task){
        double d;
        d = TL_task[vm]/vmList.get(vm).getMips() + cloudletList.get(task).getCloudletLength()/vmList.get(vm).getBw();
        return d;
    }//前面是执行时间+传输时间
    /*
    step：计算选择概率

    基于信息素选择下一个虚拟机：计算蚂蚁转移概率部分
    pheromone：全局的信息素信息
    Pij表示选择概率：蚂蚁在搜索空间中选择下一个未曾走过的节点[搜索禁忌表之外的点]的概率：
    */
    public void SelectNextVM(double[][] pheromone){
        double[][] p;			//每个节点被选中的概率
        p = new double[VMs][tasks];
        double alpha = 1;//蚂蚁在移动过程中所积累的信息量在对蚂蚁搜索路径是的重要程度,
        //权重越大，则蚂蚁选择走过的路径概率越大，搜索的随机性减弱。
        double beta = 1;//启发函数因子反映了启发式信息在指导蚁群搜索过程中的相对重要程度.
        double sum = 0;//分母

        //计算公式中的分母部分
        for(int i=0; i<VMs; i++){
            for(int j=0; j<tasks; j++){
                if(tabu.contains(new Integer(j))) continue;
                sum += Math.pow(pheromone[i][j], alpha)*Math.pow(1/Dij(i,j),beta);
            }//  启发性矩阵：[1/Dij] is the heuritic used设置为距离的倒数
        }
        //计算每个节点被选中的概率
        for(int i=0; i<VMs; i++){
            for(int j=0; j<tasks; j++){
                p[i][j] = Math.pow(pheromone[i][j], alpha)*Math.pow(1/Dij(i,j),beta)/sum;
                if(tabu.contains(new Integer(j)))p[i][j] = 0;
            }
        }
        double selectp = Math.random();
        //轮盘赌选择一个VM
        double sumselect = 0;
        int selectVM = -1;
        int selectTask = -1;
        boolean flag=true;
        for(int i=0; i<VMs&&flag==true; i++){
            for(int j=0; j<tasks; j++){
                sumselect += p[i][j];
                if(sumselect>=selectp){
                    selectVM = i;
                    selectTask = j;
                    flag=false;
                    break;
                }
            }
        }
        if (selectVM==-1 | selectTask == -1)
            System.out.println("选择下一个虚拟机没有成功！");//选择下一个虚拟机没有成功
        tabu.add(new Integer(selectTask));
        tour.add(new position(selectVM, selectTask));
        TL_task[selectVM] += cloudletList.get(selectTask).getCloudletLength();
    }

    public void CalTourLength(){
        //System.out.println();
        double[] max;
        max = new double[VMs];
        for(int i=0; i<tour.size(); i++){
            max[tour.get(i).vm] += cloudletList.get(tour.get(i).task).getCloudletLength()/vmList.get(tour.get(i).vm).getMips();
        }
        tourLength = max[0];
        for(int i=0; i<VMs; i++){
            if(max[i]>tourLength)tourLength = max[i];
            //System.out.println(""+i+""+max[i]);//第i台虚拟机的执行时间max[i]
        }
        return;
    }
    /*
    计算信息素增量矩阵：
    在每一次迭代的所有蚂蚁走完搜索空间后，计算出所有蚂蚁的信息素增量
     */
    public void CalDelta(){
        for(int i=0; i<VMs; i++){
            for(int j=0; j<tasks; j++){
                if(i==tour.get(j).vm&&tour.get(j).task==j)
                    delta[i][j] = Q/tourLength;   //Q是信息素强度除以路径长度
                else delta[i][j] = 0;
            }
        }
    }
}
