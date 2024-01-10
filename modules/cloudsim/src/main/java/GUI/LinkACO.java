package GUI;

/*
模拟环境：创建VM，创建数据中心，提交虚拟机等

 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.lists.CloudletList;
import org.cloudbus.cloudsim.lists.VmList;

//定义代理函数

public class LinkACO extends SimEntity {

    /** The vm list. */
    protected List<? extends Vm> vmList;

    /** The vms created list. */
    protected List<? extends Vm> vmsCreatedList;

    /** The cloudlet list. */
    protected List<? extends Cloudlet> cloudletList;

    /** The cloudlet submitted list. */
    protected List<? extends Cloudlet> cloudletSubmittedList;

    /** The cloudlet received list. */
    protected List<? extends Cloudlet> cloudletReceivedList;

    /** The cloudlets submitted. */
    protected int cloudletsSubmitted;

    /** The vms requested. */
    protected int vmsRequested;

    /** The vms acks. */
    protected int vmsAcks;

    /** The vms destroyed. */
    protected int vmsDestroyed;

    /** The datacenter ids list. */
    protected List<Integer> datacenterIdsList;

    /** The datacenter requested ids list. */
    protected List<Integer> datacenterRequestedIdsList;

    /** The vms to datacenters map. */
    protected Map<Integer, Integer> vmsToDatacentersMap;

    /** The datacenter characteristics list. */
    protected Map<Integer, DatacenterCharacteristics> datacenterCharacteristicsList;

    public LinkACO(String name) throws Exception {
        super(name);

        setVmList(new ArrayList<Vm>());
        setVmsCreatedList(new ArrayList<Vm>());
        setCloudletList(new ArrayList<Cloudlet>());
        setCloudletSubmittedList(new ArrayList<Cloudlet>());
        setCloudletReceivedList(new ArrayList<Cloudlet>());

        cloudletsSubmitted = 0;
        setVmsRequested(0);
        setVmsAcks(0);
        setVmsDestroyed(0);

        setDatacenterIdsList(new LinkedList<Integer>());
        setDatacenterRequestedIdsList(new ArrayList<Integer>());
        setVmsToDatacentersMap(new HashMap<Integer, Integer>());
        setDatacenterCharacteristicsList(new HashMap<Integer, DatacenterCharacteristics>());
    }

    public void submitVmList(List<? extends Vm> list) {
        getVmList().addAll(list);
    }

    public void submitCloudletList(List<? extends Cloudlet> list) {
        getCloudletList().addAll(list);
    }

    public void bindCloudletToVm(int cloudletId, int vmId) {
        CloudletList.getById(getCloudletList(), cloudletId).setVmId(vmId);
    }

//	/**
//	 *基于贪心算法的cloudlet调度
//	 */
//	public void bindCloudletsToVmsTimeAwared(){
//		int cloudletNum=cloudletList.size();//任务的数量
//		int vmNum=vmList.size();//虚拟机数量
//		double[][] time=new double[cloudletNum][vmNum];
//		//time[i][j]表示任务i在虚拟机j上的执行时间
//
//		Collections.sort(cloudletList,new CloudletComparator());//对任务排序按照任务长度降序 任务长度由长到短
//		Collections.sort(vmList,new VmComparator());//对虚拟机按照mips升序排列 按照mips由低到高
//
//		for(int i=0;i<cloudletNum;i++){
//			for(int j=0;j<vmNum;j++){
//				time[i][j]=					(double)cloudletList.get(i).getCloudletLength()/vmList.get(j).getMips();
//				//System.out.print(time[i][j]+" ");   //For test任务执行时间
//			}
//			//System.out.println();   //For test
//		}//计算出任务执行时间
//		double[] vmLoad=new double[vmNum];//在某个虚拟机上任务的总执行时间
//		int[] vmTasks=new int[vmNum]; //在特定vm上运行的任务数
//		double minLoad=0;//记录当前任务分配方式的最优值
//		int idx=0;		//记录当前任务最优分配方式对应的虚拟机列号
//		//将最长的任务分配给最快的虚拟机
//		vmLoad[vmNum-1]=time[0][vmNum-1];//vmload表示某个虚拟机上任务的总执行时间
//		vmTasks[vmNum-1]=1;//设定最快的虚拟机上的任务数为1
//		cloudletList.get(0).setVmId(vmList.get(vmNum-1).getId());
//		//外层循环用来分配任务
//		for(int i=1;i<cloudletNum;i++){
//			minLoad=vmLoad[vmNum-1]+time[i][vmNum-1];//minload记录当前任务分配方式的最优解 i表示任务编号
//			idx=vmNum-1;//idx记录当前最优任务分配对应的虚拟机列号     先将其与第一台虚拟机联系
//			//内层循环给任务配置合适的虚拟机
//			for(int j=vmNum-2;j>=0;j--)
//			//如果当前虚拟机未分配任务，则比较完当前任务分配给虚拟机是否最优
//			{
//				if(vmLoad[j]==0){//虚拟机排序由快到慢，正好可以使云任务分配更加合理化
//					if(minLoad>=time[i][j])idx=j;//注意 整个程序的目的是让时间最小化 minload表示的是当前程序所需要的最小时间
//					break;
//				}
//				if(minLoad>vmLoad[j]+time[i][j]){
//					minLoad=vmLoad[j]+time[i][j];//使整个程序最大合理化 重复比较
//					idx=j;
//				}
//				//简单的负载均衡
//				else if(minLoad==vmLoad[j]+time[i][j]&&vmTasks[j]<vmTasks[idx])
//					idx=j;
//				//使所有虚拟机上分配的任务尽可能均衡
//			}
//			vmLoad[idx]+=time[i][idx];//vmload表示某个虚拟机上任务的总执行时间
//			vmTasks[idx]++;//设定虚拟机上的任务数加一
//			cloudletList.get(i).setVmId(vmList.get(idx).getId());
//		}
//	}
//	private class CloudletComparator implements Comparator<Cloudlet>{
//		public int compare(Cloudlet c11,Cloudlet c12){
//			return (int)(c12.getCloudletLength()-c11.getCloudletLength());
//		}
//	}	//后一个任务减去前一个任务的长度
//		private class VmComparator implements Comparator<Vm>{
//			public int compare(Vm vm1,Vm vm2){
//				return (int)(vm1.getMips()-vm2.getMips());
//			}
//		}

    /**
     * 基于蚁群算法将任务绑定到VM
     */
    //public void init(int antNum, List<? extends Cloudlet> cloudletList, List<? extends Vm> vmList)
    //public void run(int maxgen)
    //调用蚁群任务调度算法输入蚂蚁数量和迭代次数
    public void bind(int antcount, int maxgen){
        ACO aco;//也就是解的个数代表任务的数量
        aco = new ACO();
        aco.init(antcount, cloudletList, vmList);
        aco.run(maxgen);
        aco.ReportResult();
        for (int i = 0; i < cloudletList.size(); i++) {
            cloudletList.get(aco.bestTour[i].task).setVmId(aco.bestTour[i].vm);
        }
    }
    @Override

    public void processEvent(SimEvent ev) {
        switch (ev.getTag()) {
            // 资源特征请求
            case CloudSimTags.RESOURCE_CHARACTERISTICS_REQUEST:
                processResourceCharacteristicsRequest(ev);
                break;
            // 资源特征回复
            case CloudSimTags.RESOURCE_CHARACTERISTICS:
                processResourceCharacteristics(ev);
                break;
            // VM创建回复
            case CloudSimTags.VM_CREATE_ACK:
                processVmCreate(ev);
                break;
            // 完成的任务返回
            case CloudSimTags.CLOUDLET_RETURN:
                processCloudletReturn(ev);
                break;
            // 如果模拟完成
            case CloudSimTags.END_OF_SIMULATION:
                shutdownEntity();
                break;
            // 其他未知标记通过此方法进行处理
            default:
                processOtherEvent(ev);
                break;
        }
    }

    //资源特征回复
    protected void processResourceCharacteristics(SimEvent ev) {
        DatacenterCharacteristics characteristics = (DatacenterCharacteristics) ev.getData();
        getDatacenterCharacteristicsList().put(characteristics.getId(), characteristics);

        if (getDatacenterCharacteristicsList().size() == getDatacenterIdsList().size()) {
            setDatacenterRequestedIdsList(new ArrayList<Integer>());
            createVmsInDatacenter(getDatacenterIdsList().get(0));
        }
    }

    //资源特征请求
    protected void processResourceCharacteristicsRequest(SimEvent ev) {
        setDatacenterIdsList(CloudSim.getCloudResourceList());
        setDatacenterCharacteristicsList(new HashMap<Integer, DatacenterCharacteristics>());

        //Log.printLine(CloudSim.clock() + ": " + getName() + ": Cloud Resource List received with "
        //	+ getDatacenterIdsList().size() + " resource(s)");

        for (Integer datacenterId : getDatacenterIdsList()) {
            sendNow(datacenterId, CloudSimTags.RESOURCE_CHARACTERISTICS, getId());
        }
    }

    //虚拟机创建
    protected void processVmCreate(SimEvent ev) {
        int[] data = (int[]) ev.getData();
        int datacenterId = data[0];
        int vmId = data[1];
        int result = data[2];

        if (result == CloudSimTags.TRUE) {
            getVmsToDatacentersMap().put(vmId, datacenterId);
            getVmsCreatedList().add(VmList.getById(getVmList(), vmId));
            Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
                    + " has been created in Datacenter #" + datacenterId + ", Host #"
                    + VmList.getById(getVmsCreatedList(), vmId).getHost().getId());
        } else {
            Log.printLine(CloudSim.clock() + ": " + getName() + ": Creation of VM #" + vmId
                    + " failed in Datacenter #" + datacenterId);
        }

        incrementVmsAcks();

        // 所有请求的虚拟机被创建
        if (getVmsCreatedList().size() == getVmList().size() - getVmsDestroyed()) {
            submitCloudlets();
        } else {
            //接到请求但是虚拟机没有被创建
            if (getVmsRequested() == getVmsAcks()) {
                // 尝试下一个数据中心
                for (int nextDatacenterId : getDatacenterIdsList()) {
                    if (!getDatacenterRequestedIdsList().contains(nextDatacenterId)) {
                        createVmsInDatacenter(nextDatacenterId);
                        return;
                    }
                }
                // 所有数据中心被请求
                if (getVmsCreatedList().size() > 0) { // if some vm were created
                    submitCloudlets();
                } else { // no vms created. abort
                    Log.printLine(CloudSim.clock() + ": " + getName()
                            + ": none of the required VMs could be created. Aborting");
                    finishExecution();
                }
            }
        }
    }

    //任务完成返回
    protected void processCloudletReturn(SimEvent ev) {
        Cloudlet cloudlet = (Cloudlet) ev.getData();
        getCloudletReceivedList().add(cloudlet);
        Log.printLine(CloudSim.clock() + ": " + getName() + ": Cloudlet " + cloudlet.getCloudletId()
                + " received");//This shows the cloudlets recieved.
        cloudletsSubmitted--;
        if (getCloudletList().size() == 0 && cloudletsSubmitted == 0) { // all cloudlets executed
            Log.printLine(CloudSim.clock() + ": " + getName() + ": All Cloudlets executed. Finishing...");
            clearDatacenters();
            finishExecution();
        } else { // some cloudlets haven't finished yet
            if (getCloudletList().size() > 0 && cloudletsSubmitted == 0) {
                // all the cloudlets sent finished. It means that some bount
                // cloudlet is waiting its VM be created
                clearDatacenters();
                createVmsInDatacenter(0);
            }

        }
    }

    //其他情况
    protected void processOtherEvent(SimEvent ev) {
        if (ev == null) {
            Log.printLine(getName() + ".processOtherEvent(): " + "Error - an event is null.");
            return;
        }

        Log.printLine(getName() + ".processOtherEvent(): "
                + "Error - event unknown by this DatacenterBroker.");
    }

    /**
     * Create the virtual machines in a datacenter.
     *在数据中心中创建虚拟机。
     */
    protected void createVmsInDatacenter(int datacenterId) {
        //在尝试下一个数据中心之前，请为此数据中心发送尽可能多的虚拟机
        int requestedVms = 0;
        String datacenterName = CloudSim.getEntityName(datacenterId);
        for (Vm vm : getVmList()) {
            if (!getVmsToDatacentersMap().containsKey(vm.getId())) {//该部分在数据中心创建虚拟机 this part creates VMs in the data center
                Log.printLine(CloudSim.clock() + ": " + getName() + ": Trying to Create VM #" + vm.getId()
                        + " in " + datacenterName);
                sendNow(datacenterId, CloudSimTags.VM_CREATE_ACK, vm);
                requestedVms++;
            }
        }

        getDatacenterRequestedIdsList().add(datacenterId);

        setVmsRequested(requestedVms);
        setVmsAcks(0);
    }

    /**
     * Submit cloudlets to the created VMs.
     * 向创建的虚拟机提交cloudlets。
     */
    protected void submitCloudlets() {
        int vmIndex = 0;
        for (Cloudlet cloudlet : getCloudletList()) {
            Vm vm;
            // 如果用户没有绑定这个cloudlet并且它还没有被执行 if user didn't bind this cloudlet and it has not been executed yet
            if (cloudlet.getVmId() == -1) {
                vm = getVmsCreatedList().get(vmIndex);
            } else { // 提交给特定的vm submit to the specific vm
                vm = VmList.getById(getVmsCreatedList(), cloudlet.getVmId());
                if (vm == null) { // 虚拟机没有创建 vm was not created
                    Log.printLine(CloudSim.clock() + ": " + getName() + ": Postponing execution of cloudlet "
                            + cloudlet.getCloudletId() + ": bount VM not available");
                    continue;
                }
            }

            Log.printLine(CloudSim.clock() + ": " + getName() + ": Sending cloudlet "
                    + cloudlet.getCloudletId() + " to VM #" + vm.getId());//shows which cloudlet is sent to which VM.
            cloudlet.setVmId(vm.getId());
            sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
            cloudletsSubmitted++;
            vmIndex = (vmIndex + 1) % getVmsCreatedList().size();
            getCloudletSubmittedList().add(cloudlet);
        }

        // 从等待列表中删除已提交的cloudlets remove submitted cloudlets from waiting list
        for (Cloudlet cloudlet : getCloudletSubmittedList()) {
            getCloudletList().remove(cloudlet);
        }
    }

    /**
     * 销毁在数据中心中运行的虚拟机。 Destroy the virtual machines running in datacenters.
     *
     */
    protected void clearDatacenters() {
        for (Vm vm : getVmsCreatedList()) {
            Log.printLine(CloudSim.clock() + ": " + getName() + ": Destroying VM #" + vm.getId());
            sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.VM_DESTROY, vm);
        }
        //销毁所有创建的vm destroying all the vms created
        getVmsCreatedList().clear();
    }

    protected void finishExecution() {
        sendNow(getId(), CloudSimTags.END_OF_SIMULATION);
    }

    @Override
    public void shutdownEntity() {
        Log.printLine(getName() + " is shutting down...");	//datacenter shutting down.
    }

    @Override
    public void startEntity() {
        Log.printLine(getName() + " is starting...");
        schedule(getId(), 0, CloudSimTags.RESOURCE_CHARACTERISTICS_REQUEST);
    }

    /**
     * Gets the vm list.
     * 获取vm列表。
     */
    @SuppressWarnings("unchecked")
    public <T extends Vm> List<T> getVmList() {
        return (List<T>) vmList;
    }

    /**
     * Sets the vm list.
     * 设置vm列表。
     * @param <T> the generic type
     * @param vmList the new vm list
     */
    protected <T extends Vm> void setVmList(List<T> vmList) {
        this.vmList = vmList;
    }

    /**
     * Gets the cloudlet list.
     * 获取任务列表
     */
    @SuppressWarnings("unchecked")
    public <T extends Cloudlet> List<T> getCloudletList() {
        return (List<T>) cloudletList;
    }

    /**
     * Sets the cloudlet list.
     * 设置任务列表
     */
    protected <T extends Cloudlet> void setCloudletList(List<T> cloudletList) {
        this.cloudletList = cloudletList;
    }

    /**
     * Gets the cloudlet submitted list.
     * 获取cloudlet提交的列表。
     */
    @SuppressWarnings("unchecked")
    public <T extends Cloudlet> List<T> getCloudletSubmittedList() {
        return (List<T>) cloudletSubmittedList;
    }

    /**
     * Sets the cloudlet submitted list.
     * 设置cloudlet提交的列表。
     */
    protected <T extends Cloudlet> void setCloudletSubmittedList(List<T> cloudletSubmittedList) {
        this.cloudletSubmittedList = cloudletSubmittedList;
    }

    /**
     * Gets the cloudlet received list.
     * 获取cloudlet接收列表。
     */
    @SuppressWarnings("unchecked")
    public <T extends Cloudlet> List<T> getCloudletReceivedList() {
        return (List<T>) cloudletReceivedList;
    }

    /**
     * Sets the cloudlet received list.
     * 设置cloudlet接收列表。
     */
    protected <T extends Cloudlet> void setCloudletReceivedList(List<T> cloudletReceivedList) {
        this.cloudletReceivedList = cloudletReceivedList;
    }

    @SuppressWarnings("unchecked")
    public <T extends Vm> List<T> getVmsCreatedList() {
        return (List<T>) vmsCreatedList;
    }

    protected <T extends Vm> void setVmsCreatedList(List<T> vmsCreatedList) {
        this.vmsCreatedList = vmsCreatedList;
    }

    /**
     * Gets the vms requested.
     * 获取请求的vm。
     */
    protected int getVmsRequested() {
        return vmsRequested;
    }

    /**
     * Sets the vms requested.
     * 设置请求的vm
     */
    protected void setVmsRequested(int vmsRequested) {
        this.vmsRequested = vmsRequested;
    }

    /**
     * Gets the vms acks.
     * 获取vms确认。
     */
    protected int getVmsAcks() {
        return vmsAcks;
    }

    /**
     * Sets the vms acks.
     * 设置vms确认。
     */
    protected void setVmsAcks(int vmsAcks) {
        this.vmsAcks = vmsAcks;
    }

    /**
     * Increment vms acks.
     * 增加vms acks
     */
    protected void incrementVmsAcks() {
        vmsAcks++;
    }

    /**
     * Gets the vms destroyed.
     * 获取已销毁的vm。
     */
    protected int getVmsDestroyed() {
        return vmsDestroyed;
    }

    protected void setVmsDestroyed(int vmsDestroyed) {
        this.vmsDestroyed = vmsDestroyed;
    }

    protected List<Integer> getDatacenterIdsList() {
        return datacenterIdsList;
    }

    protected void setDatacenterIdsList(List<Integer> datacenterIdsList) {
        this.datacenterIdsList = datacenterIdsList;
    }

    protected Map<Integer, Integer> getVmsToDatacentersMap() {
        return vmsToDatacentersMap;
    }

    protected void setVmsToDatacentersMap(Map<Integer, Integer> vmsToDatacentersMap) {
        this.vmsToDatacentersMap = vmsToDatacentersMap;
    }

    protected Map<Integer, DatacenterCharacteristics> getDatacenterCharacteristicsList() {
        return datacenterCharacteristicsList;
    }

    protected void setDatacenterCharacteristicsList(
            Map<Integer, DatacenterCharacteristics> datacenterCharacteristicsList) {
        this.datacenterCharacteristicsList = datacenterCharacteristicsList;
    }


    protected List<Integer> getDatacenterRequestedIdsList() {
        return datacenterRequestedIdsList;
    }

    protected void setDatacenterRequestedIdsList(List<Integer> datacenterRequestedIdsList) {
        this.datacenterRequestedIdsList = datacenterRequestedIdsList;
    }



}
