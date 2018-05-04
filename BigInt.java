import java.math.*;
import java.util.ArrayList;
import java.time.*;
public class BigInt {
	String content;
	public BigInt(String s) {
		  if(s.charAt(0)=='-') {
			  int flag=0;
			  for(int i=1;i<s.length();i++)
				  if(s.charAt(i)!='0') {
					  flag=i;
					  break;
				  }
			  content="-"+s.substring(flag,s.length());
		  }
		  else {
			  int flag=0;
			  for(int i=0;i<s.length();i++)
				  if(s.charAt(i)!='0') {
					  flag=i;
					  break;
				  }
			  content=s.substring(flag,s.length());
		  }
		  
	}//constructor
	public BigInt() {
		   content="0";
	}//constructor
	public String toString() {
	       return this.content;
	}//todo
	public int compare(BigInt a) {
		   if(this.content.charAt(0)=='-') {
			   if(a.content.charAt(0)=='-') {
				   BigInt c=new BigInt(this.content.substring(1));
				   BigInt d=new BigInt(a.content.substring(1));
				   return -1*c.compare(d);
			   }
			   else return -1;
		   }
		   else {
			   if(a.content.charAt(0)=='-')
				   return 1;
			   else {
				   int x1=this.content.length();
				   int x2=a.content.length();
				   if(x1!=x2)
					   return x1>x2?1:-1;
					   else {
						   for(int i=0;i<x2;i++) {
							   if(this.content.charAt(i)!=a.content.charAt(i))
								   return this.content.charAt(i)>a.content.charAt(i)?1:-1;
						   }
						   return 0;
					   }
			   }
		   }
	}
	public boolean equals(BigInt a) {
		if(this.compare(a)==0)
			return true;
		else return false;
	}
	public BigInt add(BigInt a) {
		   if((this.content.charAt(0)=='-')&&(a.content.charAt(0)=='-')){
				   BigInt c=new BigInt(this.content.substring(1));
				   BigInt d=new BigInt(a.content.substring(1));
				   BigInt result=new BigInt("-"+c.add(d).content);
				   return result;
			   }
		   else if(this.content.charAt(0)=='-'){
			   BigInt c=new BigInt(this.content.substring(1));
			   if(c.compare(a)==1) {
				   String x1=c.content;
				   String x2=a.content;
				   ArrayList<Integer>result=new ArrayList<Integer>();
				   int last=0;
				   for(int i=c.content.length()-1;i>=0;i--) {
					   char d=(char)(x1.charAt(i)+last);
					   char e='0';
					   if(i-x1.length()+x2.length()>=0)
						   e=x2.charAt(i-x1.length()+x2.length());
					   if(d-e<0) {
						   last=-1;
						   result.add(d-e+10);
					   }
					   else {
						   last=0;
						   result.add(d-e); 
					   }
				   }
				   char[] b=new char[result.size()+1];
				   b[0]='-';
				   for(int i=0;i<result.size();i++)
					   b[i+1]=(char)(result.get(result.size()-i-1).intValue()+'0');
				   return new BigInt(String.copyValueOf(b));
			   }
			   else if(c.compare(a)==0)
				   return new BigInt("0");
			   else {
				   BigInt d=new BigInt("-"+a.content);
				   BigInt result=d.add(c);
				   return new BigInt(result.content.substring(1));
			   }
		   }
		   else if(a.content.charAt(0)=='-') 
			      return a.add(this);
		   else {
		   String x1=this.content;
		   String x2=a.content;
		   int lenth1=x1.length();
		   int lenth2=x2.length();
		   ArrayList<Integer> result=new ArrayList<Integer>();
		   if(lenth1>=lenth2){
			   int last=0;
			   for(int i=lenth1-1;i>=0;i--) {
				   char c=(char) (x1.charAt(i)+last);
				   char d='0';
				   if(i-lenth1+lenth2>=0)
					   d=x2.charAt(i-lenth1+lenth2);
				   if(c+d>=10+2*'0')
		             last=1;
				   else last=0;
				   result.add((c+d-2*'0')%10);
			   }
			   if(last==1)
				   result.add(1);
		   }
		   else {
			  return a.add(this);
		   }
		   char[] b=new char[result.size()];
		   for(int i=0;i<result.size();i++)
			   b[i]=(char)(result.get(result.size()-i-1).intValue()+'0');
		   return new BigInt(String.copyValueOf(b));
		  }
	}
    public BigInt subtract(BigInt a) {
    	BigInt c;
    	if(a.content.charAt(0)=='-')
    	c=new BigInt(a.content.substring(1));
    	else c=new BigInt("-"+a.content);
    	return this.add(c);
    }
    public BigInt multiply(BigInt a) {
    	if(this.content.charAt(0)=='-'&&a.content.charAt(0)=='-') {
    		BigInt c= new BigInt(this.content.substring(1));
    		BigInt d= new BigInt(a.content.substring(1));
    		return c.multiply(d);
    	}
    	else if(this.content.charAt(0)=='-'||a.content.charAt(0)=='-') {
    		if(this.content.charAt(0)=='-') {
    			BigInt c= new BigInt(this.content.substring(1));
    			return new BigInt("-"+c.multiply(a).content);
    		}
    		else return a.multiply(this);
    	}
    	else {
    		int []product=new int [a.content.length()+this.content.length()-1];
    		int carry=0;
     		for(int i=a.content.length()-1;i>=0;i--) {
     			carry=0;
    			for(int j=this.content.length()-1;j>=0;j--) {
    				product[a.content.length()+this.content.length()-2-i-j]+=
    						(carry+(a.content.charAt(i)-'0')*(this.content.charAt(j)-'0'));
    				carry=product[a.content.length()+this.content.length()-2-i-j]/10;
    				product[a.content.length()+this.content.length()-2-i-j]=
    						product[a.content.length()+this.content.length()-2-i-j]%10;
    			}
    			if(i!=0)
    			product[a.content.length()+this.content.length()-2-i+1]+=carry;
    		}
    		char[] result=new char[this.content.length()+a.content.length()-1];
    		for(int i=0;i<result.length;i++)
    			result[i]=(char)(product[result.length-i-1]+'0');
    		if(carry==0)
    		return new BigInt(String.copyValueOf(result));
    		else {
    			char last=(char)(carry+'0');
    			return new BigInt(last+String.copyValueOf(result));
    		}
    	}
    }
    public BigInt divide(BigInt a) {
    	if(this.content.charAt(0)=='-'&&a.content.charAt(0)=='-') {
    		BigInt c=new BigInt (this.content.substring(1));
    		BigInt d=new BigInt (a.content.substring(1));
    		return  c.divide(d);
    	}
    	else if(this.content.charAt(0)!='-'&&a.content.charAt(0)!='-') {
             if(a.compare(this)==1)
            	 return new BigInt("0");
             else if(a.compare(this)==0)
            	 return new BigInt("1");
             else if(a.compare(new BigInt("1"))==0)
            	 return this;
             else if(a.compare(new BigInt("2"))==0)
            	 return this.divide2();
             else {
            	 BigInt left=new BigInt("1");
            	 BigInt right=this;
            	 BigInt med=left.add(right).divide2();
            	 while((med.multiply(a).compare(this)==1)||(med.multiply(a).compare(this.subtract(a))==-1||
            			 med.multiply(a).compare(this.subtract(a))==0)) {                                    
            		 if(med.multiply(a).compare(this)==1) {
            			 right=med;
            			 med= right.add(left).divide2();
            		 }
            		 else {
            			 left=med;
            			 med=right.add(left).divide2();
            		 }
            	 }
            	 return med;
             } 
    	}
    	else {
    		if(this.content.charAt(0)=='-') {
    			BigInt c=new BigInt(this.content.substring(1));
    			return new BigInt("-"+c.divide(a).content);
    		}
    		else {
    			BigInt c=new BigInt(a.content.substring(1));
    			return new BigInt("-"+this.divide(c).content);
    		}
    	}
    }
    public BigInt divide2() {
    	  if(this.content.charAt(0)=='-') {
    		  BigInt a=new BigInt(this.content.substring(1));
    		  BigInt b=a.divide2();
    		  return new BigInt ("-"+b.content);
    	  }
    	  else {
    		   ArrayList<Integer> result=new ArrayList<Integer>();
    		   int carry=0;
    		   for(int i=0;i<this.content.length();i++) {
    			   result.add((this.content.charAt(i)-'0'+carry*10)/2);
    			   carry=(this.content.charAt(i)-'0'+carry*10)%2;
    		   }
    		   char []re=new char[result.size()];
    		   for(int i=0;i<result.size();i++)
    			   re[i]=(char)(result.get(i)+'0');
    		   return new BigInt(String.copyValueOf(re));
    	  }
    }
    public BigInt mod(BigInt a) {
    	return this.subtract(this.divide(a).multiply(a));
    }
}  
