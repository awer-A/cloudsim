package GUI;

import java.util.*;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

public class ACO {
    public class position{
        int vm;
        int task;
        public position(int a, int b){
            vm = a;
            task = b;
        }
    }
    private List<Ant> ants;//定义蚂蚁群
    private int antcount;//蚂蚁的数量   蚂蚁的个数N，即在搜索空间中找出N组解
    private int Q = 100;//信息素强度
    private double[][] pheromone;//信息素矩阵
    private double[][] Delta;//总的信息素增量
    private int VMs;//虚拟机数量
    private int tasks;//任务个数
    public position[] bestTour;//最佳解
    private double bestLength;//最优解的长度（时间的大小）
    private List<? extends Cloudlet> cloudletList;
    private List<? extends Vm> vmList;
    /*
    初始化矩阵
    antnum为系统要用到的蚂蚁数量
    */
    public void init(int antNum, List<? extends Cloudlet> list1, List<? extends Vm> list2){
        //cloudletList = new ArrayList<? extends Cloudlet>;
        cloudletList = list1;
        vmList = list2;
        antcount = antNum;
        ants = new ArrayList<Ant>();
        VMs = vmList.size();//取长度
        tasks = cloudletList.size();//取长度
        pheromone = new double[VMs][tasks];//信息素矩阵
        Delta = new double[VMs][tasks];//总的信息素增量
        bestLength = 1000000;

        //初始化信息素矩阵：全部赋值为常量0.1
        for(int i=0; i<VMs; i++){
            for(int j=0; j<tasks; j++){
                pheromone[i][j] = 0.1; // 信息素矩阵：任务分配到虚拟机上的概率相同
            }
        }
        bestTour = new position[tasks];
        for(int i=0; i<tasks; i++){
            bestTour[i] = new position(-1, -1);
        }
        //随机放置蚂蚁：将蚂蚁随机放在虚拟机节点
        for(int i=0; i<antcount; i++){
            ants.add(new Ant());
            ants.get(i).RandomSelectVM(cloudletList, vmList);
        }
    }
    /*
    ACO运行过程：
     */
    public void run(int maxgen){//迭代次数为约束条件
        for(int runTime=0; runTime<maxgen; runTime++){
            ///////	System.out.println("Run-Time"+runTime+"ms");
            //每只蚂蚁按照规则选择下一个虚拟机结点
            for(int i=0; i<antcount; i++){
                for(int j=1; j<tasks; j++){
                    ants.get(i).SelectNextVM(pheromone);
                }
            }
            for(int i=0; i<antcount; i++){
                //第i只蚂蚁
                ///////	System.out.println("ant-count"+i+"");
                ants.get(i).CalTourLength();
                //第i只蚂蚁的路程
                ///////		System.out.println(""+i+"Tour-Length"+ants.get(i).tourLength);
                ants.get(i).CalDelta();
                //遍历所有结点
                if(ants.get(i).tourLength<bestLength){
                    //保留最佳路径
                    bestLength = ants.get(i).tourLength;
                    //第runtime代的第i只蚂蚁发现新的解bestLength
                    //     System.out.println(""+runTime+""+i+"Best-Length"+bestLength);
                    for(int j=0;j<tasks;j++) {
                        bestTour[j].vm = ants.get(i).tour.get(j).vm;
                        bestTour[j].task = ants.get(i).tour.get(j).task;
                    }
                    //对发现最优解的路径更新信息素
                    for(int k=0; k<VMs; k++){
                        for(int j=0; j<tasks; j++){
                            pheromone[k][j] = pheromone[k][j] + Q/bestLength;
                        }
                    }
                }
            }

            UpdatePheromone();//更新每条路径上的信息素

            //重新随机设置蚂蚁
            for(int i=0;i<antcount;i++){
                ants.get(i).RandomSelectVM(cloudletList, vmList);
            }
        }
    }

    /*
    更新信息素矩阵：
    在每代中的所有蚂蚁走完搜索空间后，计算出所有蚂蚁的信息残留值，结合之前的信息素挥发，进行更新
     */
    public void UpdatePheromone(){
        double rou=0.5;
        //信息素挥发因子：
        //表示每一代蚂蚁走完之后留下的信息素的挥发程度，其值越小，则挥发程度越小，会导致算法收敛速度过快，全局搜索能力较弱
        for(int k=0; k<antcount; k++){
            for(int i=0; i<VMs; i++){
                for(int j=0; j<tasks; j++){
                    Delta[i][j] += ants.get(k).delta[i][j];
                }
            }
        }
        for(int i=0; i<VMs; i++){
            for(int j=0; j<tasks; j++){
                pheromone[i][j] = (1-rou)*pheromone[i][j] + Delta[i][j];
            }
        }
    }
    /*
    输出程序运行结果
     */
    public void ReportResult(){
        //System.out.println("Best-Length"+bestLength);//最优路径长度
        // for(int j=0; j<tasks; j++)
        //{
        //System.out.println(bestTour[j].task+"Best-Tour"+bestTour[j].vm);//将哪一个任务分配给哪一台虚拟机
        //}
    }
}
