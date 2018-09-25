package main;

import java.io.IOException;
import java.util.Scanner;
import main.JudgeAnswer.answerJudge;
import main.controller.manager;

public class Main {
	public static void main(String[] args) throws IOException{		
		boolean exit = true;
		while (exit) {
		System.out.println("**** -n [数值]     使用 -n 参数控制生成题目的个数");
        System.out.println("**** -e <exercisefile>.txt -a <answerfile>.txt  对给定的题目文件和答案文件，判定答案中的对错并进行数量统计");
        System.out.print("请输入命令：");
		Scanner s = new Scanner(System.in);
        String m =s.nextLine();
        String arr[]=m.split("\\s");
        switch (arr[0]){
	        case "-n":
	        	System.out.println("**** -r [数值]     使用 -r 参数控制题目中数值（自然数、真分数和真分数分母）的范围");
	        	System.out.print("请输入命令：");
	            Scanner scope = new Scanner(System.in);
	            String numScope = scope.nextLine();
	            String Scope[]=numScope.split("\\s");
	            manager Manager=new manager(new Integer(arr[1]).intValue(), new Integer(Scope[1]).intValue());
	            Manager.produceproblems(new Integer(arr[1]).intValue());
	            break;
	        case "-e":
	        	answerJudge.Judge(arr[1], arr[3]);
	        	break;
	        case "-q":
	        	answerJudge.Judge(arr[1], arr[3]);
	        	exit = false;
	        default:
	        	System.out.println("输入的命令有误!");main(args);break;
	    }
        
	}
	}
}
