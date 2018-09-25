package main.controller;

import java.util.Random;
import java.lang.*;

public class digit {

	int range;
	
	digit(){
		
	}
	
	digit(int range){
		this.range=range;
	}
	
	//产生整数
	String produceinteger(int range) {
		String integer = "";
	    Random seed=new Random();
	    int result=seed.nextInt(range)+1;  //控制随机数范围为1-range
	    integer=new Integer(result).toString();
		return integer;
	}
	
	//产生分数
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
	
	//产生数
	String producedigit() {
		String digital="";
		Random seed=new Random();
		int random=seed.nextInt(2);
		if(random==0)digital=produceinteger(this.range);
		else digital=producefraction(this.range);
		return digital;
	}
	
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
	
}
