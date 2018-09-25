package main.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;


public class manager {
          int subjectnumbers;
          int subjectrange;
          BufferedReader bin;
          BufferedWriter bout;
          HashMap<String, String[]> problems=new HashMap<>();
          ArrayList<String> problemList = new ArrayList<String>();
          String answerPrint = null;
          
          manager() throws IOException{
        	  this.subjectnumbers=0;
        	  this.subjectrange=0;
        	  this.bin=new BufferedReader(new FileReader(new File("Answers.txt")));
        	  this.bout=new BufferedWriter(new FileWriter(new File("Exercises.txt")));
          }
          
          public manager(int numbers,int range) throws IOException{
        	  this.subjectnumbers=numbers;
        	  this.subjectrange=range;
        	  this.bin=new BufferedReader(new FileReader(new File("Answers.txt")));
        	  this.bout=new BufferedWriter(new FileWriter(new File("Exercises.txt")));
          }
          
          String createproblem() {
        	  digit Digit=new digit(this.subjectrange);
        	  symbol Symbol;
        	  Random seed= new Random();
              int random;
              int flag=seed.nextInt(5);
        	  String problem="";
        	  int direction=0;
        		  
        	  //随机确定题目长度，4，6，8，10
         	  while(true) {
	              random=seed.nextInt(11);
	              if(random==4||random==6||random==8||random==10)break;
              }
         	  //产生题目
        	  for(int j=0;j<random-1;j++) {
        	    	  if(j%2==0)problem+=(Digit.producedigit())+" ";
        	    	  else problem+=(new symbol().produceoperator())+" ";
        	    	  if((random==8&&flag==0)||random==10) {
        	    		  if(j==1)problem+="( ";
        	    		  if(j==4)problem+=") ";
        	    		  direction=1;
        	      }
        	    	  if(direction==1&&j==random-4)break;
        	  }  
        	  problem+="="+" ";  
              return problem;
          }
          
	      Boolean judgeproblem(String problem) {
	    	  if(problems.containsKey(problem)) {
	    		  return false;
	    	  }
	    	  String postfix=changetopostfix(problem);       	 
	    	  String []result=calculator(postfix);
	    	  String firstresult = result[1];
	    	  String secondresult="";
	    	  if(result.length>3) { secondresult=result[2];}
	    	  String finalresult = result[0];
	    	  
	    	    	  
	    	  for (int i = 0; i < result.length; i++) {
	    		  if(result[i].equals("-1")||result[i].equals("-2"))  return false;
	    	  }
	    	  
	    	  List<String> keyList = new ArrayList();
	    	  char[] postfixChar = postfix.toCharArray();
	    	  Arrays.sort(postfixChar);
	    	  String sortPostfixChar = String.valueOf(postfixChar);
	    	  for(String getKey: problems.keySet()){
	    		  if(result.length>3) {
	    			  if(problems.get(getKey)[0].equals(finalresult)&&problems.get(getKey)[1].equals(firstresult)&&problems.get(getKey)[2].equals(secondresult)){
	        			  keyList.add(getKey);
	        		  }
	    		  }
	    		  else {
	    		  if(problems.get(getKey)[0].equals(finalresult)&&problems.get(getKey)[1].equals(firstresult)){
	    			  keyList.add(getKey);
	    		  }
	    		  }
	    	  }
	    	  
	    	  if (keyList.size()>0) {
	    		  for(int j = 0; j < keyList.size();j++) {       			  
	    			  char[] strChar = keyList.get(j).toCharArray();
	    			  Arrays.sort(strChar);
	    			  String sortstrChar = String.valueOf(strChar);
	    			  if (sortstrChar.equals(sortPostfixChar)) {
						return false;
					}else {
						continue;
					}
	        	 }
	    		  problems.put(postfix, result);             
	    		  return true;
	    	  }
	    	problems.put(postfix, result);
	    	return true;       	 		
	      }

          String changetopostfix(String problem) {
        	  String result="";
        	  Stack<String> digit=new Stack();
        	  Stack<String> symbol=new Stack();
        	  String[] items=problem.split(" ");
        	  int stackpri=0;
        	  int enterpri=0;
        	  int kuopri=0;
        	  int first=0;
        	  String temp="";
        	  String temp2="";
        	  
        	  for(int i=0;i<items.length;i++) {
        		   if((items[i].compareTo("(")==0)||(items[i].compareTo(")")==0)||(items[i].compareTo("+")==0)||(items[i].compareTo("-")==0)||(items[i].compareTo("*")==0)||(items[i].compareTo("/")==0)||(items[i].compareTo("=")==0)) {
        			   switch(items[i].charAt(0)) {
    				   case '+':enterpri=1;break;
    				   case '-':enterpri=1;break;
    				   case '*':enterpri=2;break;
    				   case '/':enterpri=2;break;
    				   case '(':enterpri=3;break;
    				   case ')':enterpri=4;break;
    				   case '=':enterpri=0;break;
    				   }
        			   if(symbol.isEmpty()==true) {
        				   symbol.add(items[i]);
        				   stackpri=enterpri;
        			   }
        			   else {
        				   if(enterpri>stackpri) {
        					  if(enterpri==4) {
        						  temp=digit.pop();
       					          temp2+=digit.pop()+" "+temp+" "+symbol.pop();
       					          digit.add(temp2);
       					          temp2="";
        						  if(first==0)first=1;
        						  symbol.pop();
        						  stackpri=kuopri;
        					  }
        					  else if(enterpri==3) {
        						  symbol.add(items[i]);
        						  kuopri=stackpri;
        						  stackpri=0;
        					  }
        					  else {
        						  symbol.add(items[i]);
        						  stackpri=enterpri;
        					  }
        				   }
        				   else {
        					   if(first==0) {
        						   temp=digit.pop();
        					       temp2+=digit.pop()+" "+temp+" "+symbol.pop();
        					       digit.add(temp2);
        					       first=1;
        					       temp2="";
        					   }
        					   else {
        						   temp=digit.pop();
        						   temp2+=digit.pop()+" "+temp+" "+symbol.pop();
        						   digit.add(temp2);
        						   temp2="";
        					   }
        					   symbol.add(items[i]);
        				   }
        			   }
        		   }
        		   else {
        			   digit.add(items[i]);
        		   }
        	  }
        	  if(symbol.isEmpty()==false) {
        		  symbol.pop();
        		  while(symbol.isEmpty()==false) {
        		  temp=digit.pop();
        		  result+=digit.pop()+" "+temp+" "+symbol.pop()+" ";
        		  temp="";
        		  }
        		  if(digit.isEmpty()==false) {
        			  result+=digit.pop();
        		  }
        	  }
        	  return result;
          }

          String []calculator(String result) {
        	  String [] arr = {"", "", "", ""};
        	  String [] s = result.split(" ");
        	  int num = 1;
        	  List<String> problemResult = new ArrayList<>();
        	  
        	  for (int i = 0; i < s.length; i++) {
	             int size = problemResult.size();
	             switch (s[i]) {
	             	case "+": String a=calculatorChildren(problemResult.remove(size-2), problemResult.remove(size-2), "+");
	             				problemResult.add(a);arr[num++]=a;break;
	                case "-": String b=calculatorChildren(problemResult.remove(size-2), problemResult.remove(size-2), "-");
	                			problemResult.add(b);arr[num++]=b;break;
	                case "*": String c=calculatorChildren(problemResult.remove(size-2), problemResult.remove(size-2), "*");
	                			problemResult.add(c);arr[num++]=c;break;
	                case "/": String d=calculatorChildren(problemResult.remove(size-2), problemResult.remove(size-2), "/");
	                			problemResult.add(d);arr[num++]=d;break;
	                default: problemResult.add(s[i]);break;
	             }
	           }
        	  arr[0] = problemResult.get(0);
        	  answerPrint = arr[0];
        	  return arr;
          } 
          
          String  calculatorChildren(String para1,String para2,String oper) {
        	  String answer="";
        	  String temp1="";
        	  String temp2="";
        	  String answerstr="";
        	  temp1=new digit().changetofraction(para1);
        	  temp2=new digit().changetofraction(para2);
        	  temp1=new digit().sharing(temp1, temp2);
        	  temp2=new digit().sharing(temp2, temp1);
        	  switch(oper.charAt(0)) {
        	   case '+':answerstr=new symbol().add(temp1, temp2);break;
			   case '-':answerstr=new symbol().subtract(temp1, temp2);break;
			   case '*':answerstr=new symbol().multiply(temp1, temp2);break;
			   case '/':answerstr=new symbol().divide(temp1, temp2);break;
        	  }
        	  if(answerstr.equals("-1")) {
        		  answer="-1";
        		  return answer;
        	  }
        	  answerstr=new digit().changetofraction(answerstr);
        	  answerstr=new digit().reductfrac(answerstr);
        	  
        	  String temp3=answerstr;
        	  String[]temp4=temp3.split("/");
        	  if(temp4.length==2) {
        	  if(new Integer(temp4[0]).intValue()<new Integer(temp4[1]).intValue()) {
        		  answer=answerstr;
        	  }
        	  else if((new Integer(temp4[1]).intValue())==1) {
        		  answer=new digit().changetointeger(answerstr);
        	  }
        	  else {
        		  answer=new digit().changrtomixfrac(answerstr);
        	  }
        	  }
        	  else answer="1";
        	  return answer;
          }
          
 		 void save(String problem, String answer, int count, PrintStream exercisesPrintStream, PrintStream answersPrintStream) throws FileNotFoundException, IOException {     		  	             
             String[] problems = new String[2];
             problems[0] = problem;
             problems[1] = answer;
             outputFile(count, problems, exercisesPrintStream, answersPrintStream);
 		  }

          public void outputFile(int i, String problems[], PrintStream... var){
              try {
            	  var[0].println(i + ". " + problems[0]);
                  var[1].println(i + ". " + problems[1]);
              }catch (ArrayIndexOutOfBoundsException e){
                  System.out.println("程序内部出错了");
              }
          }
         
          public void produceproblems(int range) throws IOException {
        	  File exercises = new File("Exercises.txt");
        	  File answers = new File("Answers.txt");
        	  
        	  
        	  FileOutputStream exercisesOutput = new FileOutputStream(exercises);
              PrintStream exercisesPrintStream = new PrintStream(exercisesOutput);

              FileOutputStream answersOutput = new FileOutputStream(answers);
              PrintStream answersPrintStream = new PrintStream(answersOutput); 
        	         	 
        	  for(int i=1;i<range+1;) {
        		  String problem=createproblem();
        		  if(judgeproblem(problem)) {
        			  save(problem, answerPrint, i, exercisesPrintStream, answersPrintStream);       			  
        			  i++;
        		  }
        		  else {
					continue;
				}
        	  }
        	  exercisesOutput.close();
              answersOutput.close();
              exercisesPrintStream.close();
              answersPrintStream.close();
        	  System.out.println("文件创建成功！");
          }

}


