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
