# 项目要求 #
## 功能列表 ##
>  [完成] 使用 -n 参数控制生成题目的个数  
>  [完成] 使用 -r 参数控制题目中数值的范围, 。该参数可以设置为1或其他自然数。  
>  [完成] 生成的题目中计算过程不能产生负数  
>  [完成] 生成的题目中如果存在形如e1 ÷ e2的子表达式，那么其结果应是真分数。    
>  [完成] 程序一次运行生成的题目不能重复,生成的题目存入执行程序的当前目录下的Exercises.txt文件  
>  [完成] 每道题目中出现的运算符个数不超过3个  
>  [完成] 在生成题目的同时，计算出所有题目的答案，并存入执行程序的当前目录下的Answers.txt文件  
>  [完成] 程序应能支持一万道题目的生成。  
>  [完成] 程序支持对给定的题目文件和答案文件，判定答案中的对错并进行数量统计  
 
# 设计   #
## 表达式： ##

我们平时运用和看见的表达式一般都是中缀表达式，也就是**1 + 1 + 2**，这次我们为了实现表达式判重和运算，我们在表达式操作中统一把中缀表达式转为逆波兰表达式，也就是我们通常所说的后缀表达式 **1 1 + 2 +**，后缀表达式不像中缀表示法，还有各种优先级啊，还有小括号之类的，逻辑特别麻烦，而逆波兰表示法比较简洁，完全不用考虑优先级，也没用小括号，中括号还有大括号搅局。

## 逆波兰表达式： ##

## 负数： ##

在用逆波兰表达式计算时，我们在manager类中会对每次的计算过程都会调用symbol类的自定义运算方法，每一步运算出来负数就会返回-1给manager类，把每一步的计算结果和最终结果用字符串数组存起来，遍历筛选。

## 分数，整数的表示​​​​​​​： ##

思路是把全部的数，包括整数也当作分数计算，然后在输出的时候做处理。

## 判断是否重复​​​​​​​： ##

思路是把成功输出的式子用一个hashMap存起来，key为表达式，value为字符串数组，字符串数组是在逆波兰表达式计算时记录的最终答案和每一步的运算结果。

当hashMap有重复的key时候，直接返回false，当遍历寻找hashMap中有相同的字符串数组，用list记录，当list的长度大于零，对key用sort排序对比，相同就重复，不同就true。例如 3+（2+1）+4  ，1 + 2 + 4 + 3 ，这两个不重复，但是数字符号答案相同，但第二次运算结果不同，所以不重复。


# 用户使用说明 #
举例
> 
> -n   [数值]     使用 -n 参数控制生成题目的个数。
> 
> -r   [数值]     使用 -r 参数控制题目中数值（自然数、真分数和真分数分母）的范围。
> 
> -e   <exercisefile>.txt -a <answerfile>.txt  对给定的题目文件和答案文件，判定答案中的对错并进行数量统计 。                                                                 

 
主函数：（启动程序）main.java

	public static void main(String[] args) throws IOException{        
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
        default:
            System.out.println("输入的命令有误!");main(args);break;
    }
    
	}
 

##  生成表达式： ##

产生整数

	String produceinteger(int range) {
    String integer = "";
    Random seed=new Random();
    int result=seed.nextInt(range)+1;  //控制随机数范围为1-range
    integer=new Integer(result).toString();
    return integer;
	}
 产生分数

	String producefraction(int range) {
    String fraction="";
    Random seed=new Random();
    int numerator=seed.nextInt(range)+1;//分子
    int denominator=seed.nextInt(range)+1;//分母
    if(numerator==denominator) {
        fraction="1";
        return fraction;
    }
    while((range*denominator)<numerator) {
        numerator=seed.nextInt(range)+1;
        denominator=seed.nextInt(range)+1;
    }
    
    int mixernumber=0;//带分数的整数部分
    while(numerator>denominator) {
        numerator-=denominator;
        mixernumber++;
    }
    
    fraction=(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
    
    if(mixernumber>0) {
        fraction=(new Integer(mixernumber).toString())+"`"+(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
        if(numerator==denominator) {
            fraction=new Integer(mixernumber).toString();
        }
    }
    return fraction;
}　
 产生数

	String producedigit() {
    String digital="";
    Random seed=new Random();
    int random=seed.nextInt(2);
    if(random==0)digital=produceinteger(this.range);
    else digital=producefraction(this.range);
    return digital;
	}
 通分和约分

	//约分
	String reductfrac(String fraction){
    String result="";
    String []fracs;
    fracs=fraction.split("/");
    int numerator=new Integer(fracs[0]).intValue();
    if(numerator==0) {
        result="0";
        return result;
    }
    int denominator=new Integer(fracs[1]).intValue();
    int temp1,temp2;        
    if(denominator>numerator) {
        temp1=denominator;
        temp2=numerator;
    }
    else {
        temp1=numerator;
        temp2=denominator;
    }
    int res=temp1%temp2;
    while(res!=0) {
        temp1=temp2;
        temp2=res;
        res=temp1%temp2;
    }
    numerator/=temp2;
    denominator/=temp2;
    result=(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
    return result;
}

	//通分
	String sharing(String para1,String para2) {
    String result="";
    String []para1s;
    String []para2s;
    para1s=para1.split("/");
    para2s=para2.split("/");
    int numerator=new Integer(para1s[0]).intValue();
    int denominator=new Integer(para1s[1]).intValue();
    int denominator2=new Integer(para2s[1]).intValue();
    int temp1=new Integer(para1s[1]).intValue();
    int temp2=new Integer(para2s[1]).intValue();
    int res;
    if(temp1<temp2) {
        res=temp1;
        temp1=temp2;
        temp2=res;
    }
    res=temp1%temp2;
    while(res!=0) {
        temp1=temp2;
        temp2=res;
        res=temp1%temp2;
    }
    denominator=denominator*denominator2/temp2;
    numerator=numerator*denominator2/temp2;
    result=(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
    return result;
	}
 数值互转

	//整数，带分数转分数
	String changetofraction(String para1) {
    String result="";
    if(para1.indexOf("/")<0) {
        if(para1=="0") {
            result="0";
        }
        else {
        result=para1+"/"+"1";
        }
    }
    else if(para1.indexOf("`")<0) {
        result=para1;
    }
    else {
        String []temp1=para1.split("`");
        String times=temp1[0];
        String []temp2=temp1[1].split("/");
        int numerator=(new Integer(temp2[1]).intValue())*(new Integer(times).intValue())+(new Integer(temp2[0]).intValue());
        int denominator=new Integer(temp2[1]).intValue();
        result=(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
    }
    return result;
	}

	//分数转整数
	String changetointeger(String para1) {
    String result="";
    result=para1.split("/")[0];
    return result;
	}

	//分数转带分数
	String changrtomixfrac(String para1) {
    String result="";
    String []para1s;
    para1s=para1.split("/");
    int numerator=new Integer(para1s[0]).intValue();
    int denominator=new Integer(para1s[1]).intValue();
    int mixernumber=0;//带分数的整数部分
    while(numerator>denominator) {
        numerator-=denominator;
        mixernumber++;
    }
    result=(new Integer(mixernumber).toString())+"`"+(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
    return result;
	}
 

自定义的计算函数：

	package main.controller;
	import java.io.*;
	import java.lang.*;
	import java.util.Random;
	
	public class symbol {
    String  operators;
       
    symbol(){
        this.operators="+-*/";
    }
    
    String produceoperator() {
        String operator="";
        Random seed=new Random();
        int random=seed.nextInt(4);
        operator=new Character(this.operators.charAt(random)).toString();
        return operator;
    }

    String add(String para1,String para2) {
        String result="";
        String []para1s;
        String []para2s;
        para1s=para1.split("/");
        para2s=para2.split("/");
        int numerator=(new Integer(para1s[0]).intValue())+(new Integer(para2s[0]).intValue());
        int denominator=(new Integer(para1s[1]).intValue());
        int mixernumber=0;//带分数的整数部分
           while(numerator>denominator) {
            numerator-=denominator;
            mixernumber++;
           }
           result=(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
           result=new digit().reductfrac(result);
           if(mixernumber>0) {
            result=(new Integer(mixernumber).toString())+"`"+(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
           }

        return result;
    }
    
    String subtract(String para1,String para2) {
        String result="";
        String []para1s;
        String []para2s;
        para1s=para1.split("/");
        para2s=para2.split("/");
        int numerator=(new Integer(para1s[0]).intValue())-(new Integer(para2s[0]).intValue());
        if(numerator<0) {
            result="-1";
            return result;
        }
       if(numerator==0) {
           result="0";
           return result;
       }
        int denominator=(new Integer(para1s[1]).intValue());
        int mixernumber=0;//带分数的整数部分
           while(numerator>denominator) {
            numerator-=denominator;
            mixernumber++;
           }
           result=(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
           result=new digit().reductfrac(result);
           if(mixernumber>0) {
            result=(new Integer(mixernumber).toString())+"`"+(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
           }
        return result;
    }
    
    String multiply(String para1,String para2) {
        String result="";
        String []para1s;
        String []para2s;
        para1s=para1.split("/");
        para2s=para2.split("/");
        int numerator=(new Integer(para1s[0]).intValue())*(new Integer(para2s[0]).intValue());
        int denominator=(new Integer(para1s[1]).intValue())*(new Integer(para2s[1]).intValue());
        int mixernumber=0;//带分数的整数部分
           while(numerator>denominator) {
            numerator-=denominator;
            mixernumber++;
           }
           
           result=(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
           result=new digit().reductfrac(result);
           if(mixernumber>0) {
            result=(new Integer(mixernumber).toString())+"`"+(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
           }
        return result;
    }
    
    String divide(String para1,String para2) {
        String result="";
        String []para1s;
        String []para2s;
        para1s=para1.split("/");
        para2s=para2.split("/");
        String temp="";
        if(para2=="0") {
            result="-2";
            return result;
        }
        int numerator,denominator;
        numerator=(new Integer(para1s[0]).intValue())*(new Integer(para2s[1]).intValue());
        denominator=(new Integer(para1s[1]).intValue())*(new Integer(para2s[0]).intValue());
       result=(new Integer(numerator).toString())+"/"+(new Integer(denominator).toString());
       result=new digit().reductfrac(result);
        return result;
    }

	}
 

逆波兰表达式生成：

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
 

逆波兰表达式计算：

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
 

表达式判断重复：

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
 

输出表达式到Exercises.txt和输出答案到Answers.txt：

	File exercises = new File("Exercises.txt");
	File answers = new File("Answers.txt");
	                            
	FileOutputStream exercisesOutput = new FileOutputStream(exercises);
	PrintStream exercisesPrintStream = new PrintStream(exercisesOutput);
	
	FileOutputStream answersOutput = new FileOutputStream(answers);
	PrintStream answersPrintStream = new PrintStream(answersOutput);
	
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
 

 对给定的题目文件和答案文件，判定答案中的对错并进行数量统计：

	public class answerJudge {
    public static void Judge(String exercisePath, String answersPath) throws IOException {
        List<String> exerciseAnswers = exerciseFileRecording(exercisePath);
        List<String> answers = answerRecording(answersPath);
        
        List<String> correct = new ArrayList<>();
        List<String> wrong = new ArrayList<>();
        int SerialNumber = 1;
        int MIN = Math.min(exerciseAnswers.size(), answers.size());
        
        for (int i = 0; i < MIN; i++){
            if (exerciseAnswers.get(i).equals(answers.get(i))){
                correct.add(String.valueOf(SerialNumber++));
            }else {
                wrong.add(String.valueOf(SerialNumber++));
            }
        }
        
        File grade = new File("Grade.txt");
        if (grade.exists()){
            grade.delete();
        }
        if (grade.createNewFile()){
            FileOutputStream gradeOutput = new FileOutputStream(grade);
            PrintStream gradePrintStream = new PrintStream(gradeOutput);
            String corrects = String.join(",", correct);
            gradePrintStream.println("Correct：" + correct.size() +
                    " (" + corrects + ")");
            String wrongs = String.join(",", wrong);
            gradePrintStream.println("Wrong：" + wrong.size() +
                    " (" + wrongs + ")");
        }
        System.out.println("判定完成!记录在Grade.txt。");
    }
    
    public static List<String> exerciseFileRecording(String path) throws IOException {
        String exerciseAnswer = "";
        BufferedReader exerciseRecording = new BufferedReader(new FileReader(path));
        List<String> exerciseAnswers = new ArrayList<>();
        while ((exerciseAnswer = exerciseRecording.readLine()) != null){
            String[] split = exerciseAnswer.split("=");
            if (split.length >= 2){
                exerciseAnswers.add(split[1]);
            }else {
                exerciseAnswers.add(" ");
            }
        }
        return exerciseAnswers;
    }
    
    public static List<String> answerRecording(String path) throws IOException {
        String answer = "";
        BufferedReader answerRecording = new BufferedReader(new FileReader(path));
        List<String> answers = new ArrayList<>();
        while ((answer = answerRecording.readLine()) != null){
            String[] split = answer.split(" ");
            answers.add(split[1]);
        }
        return answers;
    }
	}
 

# 测试运行 #

 
# 总结 #
> 与黎扬乐同学一起实现该次题目，他主要负责代码框架的构思和表达式的计算和生成，我主要负责判重，判负以及文件输出和结果判定。总体来说，在这个作业里面，黎扬乐同学主要负责的是逻辑计算，我主要负责逻辑判断和博客。结对编程有个好处就是能让我们能讨论代码的思路和一起debug。在完成这次作业之前，我们两个可以在一起先把整个作业的流程和代码框架设计出来，把整体的代码知识点全部统计出来，两个人的想法可以在一起碰撞，这样可以在代码实现的过程中大大减少做弯路的时间，包括判重和采用逆波兰表达式等等。这一次，我们两个在合作的过程也出现了一些不足之处，最主要的问题是我们两个这次为了方便没有采用gitlab的协同方式，导致两个的代码在实现的过程产生了很多不必要的错误和bug。相对而言，黎扬乐同学的在逻辑代码算法的能力比较扎实，也多得他把后缀表达式的转化这一块完成，这次作业才可以在短时间完成，这次合作中，也让我认识到自己在数据结构这一块还是很薄弱，以前那种前端开发是不太需要数据结构的想法是错误的，今后自己一定会加强数据结构的学习。
