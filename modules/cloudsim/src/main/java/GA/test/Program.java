package GA.test;

import java.util.Random;

public class Program
{
	public static void main(String[] args)
	{
		String testData="E:\\cloudsim\\cloudsim-cloudsim-4.0\\modules\\cloudsim\\src\\main\\data\\cloudlets.txt";
		//createTestData(testData);
		int taskNum=20;
		fx_GA.Runtest(testData,taskNum);
	}

	public static void createTestData(String filePath)
	{
		//create 50,000 data as cloudlet length for subsequent testing.
		int taskNum=50000;
		int[]taskLength=new int[taskNum];
		for(int i=0;i<taskNum;i++)
		{
			taskLength[i]=(new Random().nextInt(200)+1)*50+new Random().nextInt(500);
		}
		
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<taskNum;i++)
		{
			sb.append(taskLength[i]).append("\t");
			if(i%20==19)//20 data each line.
			{
				fx_GA.writeTxtAppend(filePath, sb.toString());
				sb=null;
				sb=new StringBuilder();
			}
		}
	}

}
