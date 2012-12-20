package com.web.action.report.htreport.util;

/**
 * 字节码转换；
 * @author zhuxp
 *
 */
public class Coding {
  /*交易应答码定义*/
	public static String convetErr(String errcode){
		return null;
	}
	public static String HighLowConvert(String str){
		return HighLowConvert(str,str.length());
	}
	/**/
	public static String HighLowConvert(String str,int nBytes)
	{
		char[] temp=new char[5];
		StringBuffer tempstr=new StringBuffer();
		int i;
	    if ( nBytes % 2!=0)
	    {
	        return null;
	    }
	    for (i =(nBytes / 2);i>0; i-=1 )
	    {
	    	 str.getChars(i*2-2, i*2, temp, 0);
	    	 tempstr=tempstr.append(temp, 0, 2);
	    }
		return tempstr.toString();
	}

 public static int I3WORDhigh(byte[] b,int inno){
  byte[] a=new byte[4];
  I2DWORDhigh(a,inno);
  for(int i=0;i<4;i++){
   if(i!=0){
     b[i-1]=a[i];
   }
  }
  return  0;
 }

	/*拼串长度*/
	public static int byteappend(byte[] des,int start,int value,int srclen)
	{
		int i;
		if(des.length<start+srclen)
		{
			return -1;
		}
		for(i=0;i<srclen;i++)
		{
			des[i+start]=(byte) value;
		}
		return 0;
	}
	/*start是从第补起; srclen长度*/
	public static int byteappend(byte[] des,int start,byte src[],int srclen)
	{
		int i;
		if(des.length<start+srclen)
		{
			return -1;
		}
		for(i=0;i<srclen;i++)
		{
			des[i+start]=src[i];
		}
		return 0;
	}

public static int byteappend1(byte[] des,int start,byte src[],int srclen)
	{

 for(int i=start;i<start+srclen;i++){
   des[i-start]=src[i];
 }
  return 0;
 }
 /**/


	public static int I2BYTE(byte[] des,int data)
	{
		String temp1;
		String temp2;
		char[] temp3=new char[]{'F','F','F','F','F','F','F','F','F','F','F','F'};
		char[] temp4=new char[]{'0','0','0','0','0','0','0','0','0','0','0','0'};
		byte datatemp[];
		String temp5;
		if((data>255)||(data<-128))
		{
			return -1;
		}
		temp1=Integer.toHexString(data);
		if(temp1.length()==1)
		{
			datatemp=temp1.getBytes();
			if(datatemp[0]<0)
			{
				temp2=String.copyValueOf(temp3, 0,1);
			}else
			{
				temp2=String.copyValueOf(temp4, 0,1);
			}
			temp5=temp2+temp1;
		}else if(temp1.length()>2)
		{
			temp5=temp1.substring(temp1.length()-2, temp1.length());
		}
		else
		{
			temp5=temp1;
		}

		Asc2Hex(des,temp5);
		return 0;
	}

	public static int I2WORDhigh(byte[] des,int data)
	{
		String temp1;
		String temp2;
		char[] temp3=new char[]{'F','F','F','F','F','F','F','F','F','F','F','F'};
		char[] temp4=new char[]{'0','0','0','0','0','0','0','0','0','0','0','0'};
		byte datatemp[];
		String temp5;
		if((data>65535)||(data<-32768))
		{
			return -1;
		}
		temp1=Integer.toHexString(data);
		if(temp1.length()<4)
		{
			datatemp=temp1.getBytes();
			if(datatemp[0]<0)
			{
				temp2=String.copyValueOf(temp3, 0, 4-temp1.length());
			}else
			{
				temp2=String.copyValueOf(temp4, 0, 4-temp1.length());
			}
			temp5=temp2+temp1;
		}else if(temp1.length()>4)
		{
			temp4=temp1.toCharArray();
			temp5=temp1.copyValueOf(temp4, temp1.length()-4, 4);
		}else
		{
			temp5=temp1;
		}

		temp5=HighLowConvert(temp5);
		Asc2Hex(des,temp5);
		return 0;
	}
  public static int I2WORDlow(byte[] des,int data)
	{
		String temp1;
		String temp2;
		char[] temp3=new char[]{'F','F','F','F','F','F','F','F','F','F','F','F'};
		char[] temp4=new char[]{'0','0','0','0','0','0','0','0','0','0','0','0'};
		byte datatemp[];
		String temp5;
		if((data>65535)||(data<-32768))
		{
			return -1;
		}
		temp1=Integer.toHexString(data);
		if(temp1.length()<4)
		{
			datatemp=temp1.getBytes();
			if(datatemp[0]<0)
			{
				temp2=String.copyValueOf(temp3, 0, 4-temp1.length());
			}else
			{
				temp2=String.copyValueOf(temp4, 0, 4-temp1.length());
			}
			temp5=temp2+temp1;
		}else if(temp1.length()>4)
		{
			temp4=temp1.toCharArray();
			temp5=temp1.copyValueOf(temp4, temp1.length()-4, 4);
		}else
		{
			temp5=temp1;
		}

		Asc2Hex(des,temp5);
		return 0;
	}

  /**/
	public static int I2DWORDhigh(byte[] des,int data)
	{
		String temp1;
		String temp2;
		char[] temp3=new char[]{'F','F','F','F','F','F','F','F','F','F','F','F'};
		char[] temp4=new char[]{'0','0','0','0','0','0','0','0','0','0','0','0'};
		byte datatemp[];
		String temp5;
		temp1=Integer.toHexString(data);
		if(temp1.length()<8)
		{
			datatemp=temp1.getBytes();
			if(datatemp[0]<0)
			{
				temp2=String.copyValueOf(temp3, 0, 8-temp1.length());
			}else
			{
				temp2=String.copyValueOf(temp4, 0, 8-temp1.length());
			}
			temp5=temp2+temp1;
		}else
		{
			temp5=temp1;
		}
		temp5=HighLowConvert(temp5);
		Asc2Hex(des,temp5);
		return 0;
	}
  public static int I2DWORDlow(byte[] des,int data)
	{
		String temp1;
		String temp2;
		char[] temp3=new char[]{'F','F','F','F','F','F','F','F','F','F','F','F'};
		char[] temp4=new char[]{'0','0','0','0','0','0','0','0','0','0','0','0'};
		byte datatemp[];
		String temp5;
		temp1=Integer.toHexString(data);
		if(temp1.length()<8)
		{
			datatemp=temp1.getBytes();
			if(datatemp[0]<0)
			{
				temp2=String.copyValueOf(temp3, 0, 8-temp1.length());
			}else
			{
				temp2=String.copyValueOf(temp4, 0, 8-temp1.length());
			}
			temp5=temp2+temp1;
		}else
		{
			temp5=temp1;
		}
		Asc2Hex(des,temp5);
		return 0;
	}
  /*数字转换*/
  //使用说明：用于将1个字节的Hex(BIN)转换成整型
  public static int Byte2Int(byte[] src){
		/*偏移值 0表示 offset*/
		return Byte2Int(src,0);
	}
	public static int Byte2Int(byte[] src,int offset){

		return 0x000000FF&src[offset];
	}

	public static int DWord2IntHigh(byte[] src,int offset){
		String str;
		int temp;
		str=Byte2Asc(src,offset,4);
	 str=HighLowConvert(str);    /////////////
		/*int <equalsIgnoreCase >最小的负数*/
		if(str.equalsIgnoreCase("10000000"))
		{
			return Integer.MIN_VALUE;
		}else
		{
   temp=(int) Long.parseLong(str, 16);
  }
		return temp;
	}

 public static int DWord2Intlow(byte[] src,int offset){
		String str;
		int temp;
		str=Byte2Asc(src,offset,4);
		/*int <equalsIgnoreCase >最小的负数*/
		if(str.equalsIgnoreCase("10000000"))
		{
			return Integer.MIN_VALUE;
		}else
		{
			temp=(int) Long.parseLong(str, 16);
		}
		return temp;
	}
  //使用说明：用于将2个字节的Hex(BIN)转换成整型(低字节低地址),
  // 如果采用"低字节高地址(高字节低地址)"需要重新写函数，可将原程序中调用HighLowConvert这行函数去掉即可。
	public static int Word2Inthigh(byte[] src,int offset){
		String str;
		str=Byte2Asc(src,offset,2);
		str=HighLowConvert(str);
		return Integer.parseInt(str, 16);
	}
  public static int Word2Intlow(byte[] src,int offset){
		String str;
		str=Byte2Asc(src,offset,2);
		return Integer.parseInt(str, 16);
	}
  /*发送,初始化tempbyte的长度;变量为ontentsize=65*/
    public static int Asc2lenght(byte[] sDest,String str){
	 	   int i=0;
    	   sDest[i]=0;
	 	return 0;
     }
    /*初始化数组长度不足追加为零*/
    public static int InitByte(byte[]sDest,int initvalue){
    	int i;
    	for(i=0;i<sDest.length;i++)
    	{
    		sDest[i]=(byte)initvalue;
    	}
    	return 0;
    }
	/* BCD*/
    public static String Byte2Asc(byte[] b,int len) {

    	return Byte2Asc(b,0,len);
    }
    /**/
    public static String Byte2Asc(byte[] b,int offset,int len) {
        // 转成16进制字符串
        String hs = "";
        String tmp = "";
        String tmp2="";

        int sint;
        for (int n = 0; n < len; n++) {
            //整数转成十六进制表示
        	/*去掉无符号数*/
        	sint=0x000000ff&b[n+offset];
            tmp = Integer.toHexString(sint);
            if (tmp.length() == 1) {
                hs = hs + "0" + tmp;
            } else if(tmp.length()>2) {
            	tmp2=String.copyValueOf(tmp.toCharArray(),tmp.length()-2, 2);
            	hs=hs+tmp2;
            }else
            {
            	 hs = hs + tmp;
            }
        }


        return hs.toUpperCase(); //转成大写
    }
    
    
    
    /**
     * 字符串转java字节码
     * @param sSource
     * @return  int
     */

     public static int Asc2Hex(byte[] sDest,String sSource){

    	 byte sou[]=sSource.getBytes();
    	 return Asc2Hex(sDest,sou,sSource.length());
       }
   /**
    *  boolean java 虚拟机用整数0 表示false   用任意1非整数表示 true
    *  && 短路与 ||短路或
    * @param a
    * @return int
    */
     public static int isxdigit(byte a)
     {
    	 if((a>='0' && a<='9')||(a>='A')&&(a<='F')||(a>='a' && a<='f'))
    	 {
    		 return 0;
    	 }else
    	 {
    		 return -1;
    	 }
     }
     /*Asc2Hex*/
     public  static int Asc2Hex(byte[] sDest,byte[] sSource,int nBytes){
	 	int i;
	    byte cTemp;
	    int a;
	    sDest[0] = 0x00;
	    for ( i = 0; i < nBytes; i++ )
	    {
	    	if(isxdigit(sSource[i])!=0)
	    	{
	    		return -1;
	    	}
	        if( sSource[i] < 'A' )
	            cTemp = (byte) (sSource[i] - '0');
	        else if( sSource[i] < 'a' )
	            cTemp = (byte) (sSource[i] - 'A' + 10);
	        else
	            cTemp = (byte) (sSource[i] - 'a' + 10);

	        if( nBytes % 2 !=0)
	        {
	            if( i % 2!=0)
	                sDest[(i + 1)/2] = (byte) (cTemp << 4);
	            else
	                sDest[(i + 1)/2] |= cTemp;

	        }
	        else
	        {
	            if( i % 2!=0 )
	                sDest[i/2] |= cTemp;
	            else
	                sDest[i/2] = (byte) (cTemp << 4);

	            a=sDest[i/2];
	        }
	    }
	    return 0;
      }

     public static int cal_crc22(byte[] ptr, long len,int offset){
   	  int crc;
   	  byte da;
   	  int temp;
   	  int i=0;
   	  i=offset;
   	  int[] crc_ta= {/* CRC余式表 */
           0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7,
   	    0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef,
   	    0x1231, 0x0210, 0x3273, 0x2252, 0x52b5, 0x4294, 0x72f7, 0x62d6,
   	    0x9339, 0x8318, 0xb37b, 0xa35a, 0xd3bd, 0xc39c, 0xf3ff, 0xe3de,
   	    0x2462, 0x3443, 0x0420, 0x1401, 0x64e6, 0x74c7, 0x44a4, 0x5485,
   	    0xa56a, 0xb54b, 0x8528, 0x9509, 0xe5ee, 0xf5cf, 0xc5ac, 0xd58d,
   	    0x3653, 0x2672, 0x1611, 0x0630, 0x76d7, 0x66f6, 0x5695, 0x46b4,
   	    0xb75b, 0xa77a, 0x9719, 0x8738, 0xf7df, 0xe7fe, 0xd79d, 0xc7bc,
   	    0x48c4, 0x58e5, 0x6886, 0x78a7, 0x0840, 0x1861, 0x2802, 0x3823,
   	    0xc9cc, 0xd9ed, 0xe98e, 0xf9af, 0x8948, 0x9969, 0xa90a, 0xb92b,
   	    0x5af5, 0x4ad4, 0x7ab7, 0x6a96, 0x1a71, 0x0a50, 0x3a33, 0x2a12,
   	    0xdbfd, 0xcbdc, 0xfbbf, 0xeb9e, 0x9b79, 0x8b58, 0xbb3b, 0xab1a,
   	    0x6ca6, 0x7c87, 0x4ce4, 0x5cc5, 0x2c22, 0x3c03, 0x0c60, 0x1c41,
   	    0xedae, 0xfd8f, 0xcdec, 0xddcd, 0xad2a, 0xbd0b, 0x8d68, 0x9d49,
   	    0x7e97, 0x6eb6, 0x5ed5, 0x4ef4, 0x3e13, 0x2e32, 0x1e51, 0x0e70,
   	    0xff9f, 0xefbe, 0xdfdd, 0xcffc, 0xbf1b, 0xaf3a, 0x9f59, 0x8f78,
   	    0x9188, 0x81a9, 0xb1ca, 0xa1eb, 0xd10c, 0xc12d, 0xf14e, 0xe16f,
   	    0x1080, 0x00a1, 0x30c2, 0x20e3, 0x5004, 0x4025, 0x7046, 0x6067,
   	    0x83b9, 0x9398, 0xa3fb, 0xb3da, 0xc33d, 0xd31c, 0xe37f, 0xf35e,
   	    0x02b1, 0x1290, 0x22f3, 0x32d2, 0x4235, 0x5214, 0x6277, 0x7256,
   	    0xb5ea, 0xa5cb, 0x95a8, 0x8589, 0xf56e, 0xe54f, 0xd52c, 0xc50d,
   	    0x34e2, 0x24c3, 0x14a0, 0x0481, 0x7466, 0x6447, 0x5424, 0x4405,
   	    0xa7db, 0xb7fa, 0x8799, 0x97b8, 0xe75f, 0xf77e, 0xc71d, 0xd73c,
   	    0x26d3, 0x36f2, 0x0691, 0x16b0, 0x6657, 0x7676, 0x4615, 0x5634,
   	    0xd94c, 0xc96d, 0xf90e, 0xe92f, 0x99c8, 0x89e9, 0xb98a, 0xa9ab,
   	    0x5844, 0x4865, 0x7806, 0x6827, 0x18c0, 0x08e1, 0x3882, 0x28a3,
   	    0xcb7d, 0xdb5c, 0xeb3f, 0xfb1e, 0x8bf9, 0x9bd8, 0xabbb, 0xbb9a,
   	    0x4a75, 0x5a54, 0x6a37, 0x7a16, 0x0af1, 0x1ad0, 0x2ab3, 0x3a92,
   	    0xfd2e, 0xed0f, 0xdd6c, 0xcd4d, 0xbdaa, 0xad8b, 0x9de8, 0x8dc9,
   	    0x7c26, 0x6c07, 0x5c64, 0x4c45, 0x3ca2, 0x2c83, 0x1ce0, 0x0cc1,
   	    0xef1f, 0xff3e, 0xcf5d, 0xdf7c, 0xaf9b, 0xbfba, 0x8fd9, 0x9ff8,
   	    0x6e17, 0x7e36, 0x4e55, 0x5e74, 0x2e93, 0x3eb2, 0x0ed1, 0x1ef0
   	    };
   	    crc=0;
   	    while(len--!=0)
   	    {
   		    da=(byte)(crc/256);      /* 以8位二进制数的形式暂存CRC的高8位 */
   	        crc=(crc<<8)&0xFFFF;         /* 左移8位，相当于CRC的低8位乘以 */
   	        temp=0x000000FF &(da^ptr[i]);
   	        crc^=crc_ta[temp];   /* 高8位和当前字节相加后再查表求CRC ，再加上以前的CRC */
   	        i=i+1;
   	    }
   	    return(crc);
    }

       public static int cal_crc22(byte[] ptr, long len){

    	   return cal_crc22(ptr,len,0);
     }

     public static int verify_crc2(byte[] Block,int offset, long BlockLen,String CRCVal)
     {
         String str;
         str=generate_crc2(Block,BlockLen,offset);
         if(str.equals(CRCVal)==true)
         {
        	 return  0;
         }else
         {
        	 System.out.println("CRCVal效验失败="+CRCVal);
        	 System.out.println("str="+str);
        	 return -1;
         }

         }
     public static String generate_crc2(byte[]Block,long BlockLen,int offset)
     {
         int nCRC;
         byte[] sCRC;
         long  nLen;
         String stmp;
         nLen = BlockLen;
         nCRC= cal_crc22(Block,nLen,offset);
         if(nCRC>0)
         {

         	stmp=String.format("%1$04X",nCRC);
         }else
         {
         	stmp=String.format("%1$F4X",nCRC);
         }
          stmp=stmp+"FFFF";

         return stmp;
     }

    public static String generate_crc2(byte[]Block,long BlockLen)
    {
        return generate_crc2(Block,BlockLen,0);
    }

    /**
     * 字符串转换成十六进制值
     * @param bin String 我们看到的要转换成十六进制的字符串
     * @return  String
     */
    public static String bin2hex(String bin) {
        char[] digital = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer("");
        byte[] bs = bin.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(digital[bit]);
            bit = bs[i] & 0x0f;
            sb.append(digital[bit]);
        }
        return sb.toString();
    }


    /**
     * 十六进制转换字符串
     * @param hex String 十六进制
     * @return String 转换后的字符串
     */
    public static String hex2bin(String hex) {
        String digital = "0123456789ABCDEF";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte) (temp & 0xff);
        }
        return new String(bytes);
    }


    //ajax 转换成中文
    private final static String[] hex = { "00", "01", "02", "03", "04", "05",
        "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
        "1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
        "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
        "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
        "3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
        "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
        "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
        "5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
        "69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
        "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
        "7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
        "8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
        "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
        "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
        "AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
        "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
        "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
        "CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
        "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
        "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
        "ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
        "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };

        private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
        0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
        0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

        /**
         * 编码
         *
         * @param s
         * @return String
         */
        public static String escape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
        int ch = s.charAt(i);
        if ('A' <= ch && ch <= 'Z') {
        sbuf.append((char) ch);
        } else if ('a' <= ch && ch <= 'z') {
        sbuf.append((char) ch);
        } else if ('0' <= ch && ch<= '9') {
        sbuf.append((char) ch);
        } else if (ch == '-' || ch == '_'
        || ch == '.' || ch == '!' || ch == '~' || ch == '*'
        || ch == '\'' || ch == '(' || ch == ')') {
        sbuf.append((char) ch);
        } else if (ch <= 0x007F) {
        sbuf.append('%');
        sbuf.append(hex[ch]);
        } else {
        sbuf.append('%');
        sbuf.append('u');
        sbuf.append(hex[(ch >>> 8)]);
        sbuf.append(hex[(0x00FF & ch)]);
        }
        }
        return sbuf.toString();
        }

        /**
         * 解码 说明：本方法保证 不论参数s是否经过escape()编码，均能得到正确的“解码”结果
         *
         * @param s
         * @return String
         */
        public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
        int ch = s.charAt(i);
        if ('A' <= ch && ch <= 'Z') {
        sbuf.append((char) ch);
        } else if ('a' <= ch && ch <= 'z') {
        sbuf.append((char) ch);
        } else if ('0' <= ch && ch<= '9') {
    sbuf.append((char) ch);
        } else if (ch == '-' || ch == '_'|| ch == '.' || ch == '!' || ch == '~' || ch == '*'|| ch == '\'' || ch == '(' || ch == ')') {
        sbuf.append((char) ch);
        } else if (ch == '%') {
        int cint = 0;
        if ('u' != s.charAt(i + 1)) {
        cint = (cint << 4) | val[s.charAt(i + 1)];
        cint = (cint << 4) | val[s.charAt(i + 2)];
        i += 2;
        } else {
        cint = (cint << 4) | val[s.charAt(i + 2)];
        cint = (cint << 4) | val[s.charAt(i + 3)];
        cint = (cint << 4) | val[s.charAt(i + 4)];
        cint = (cint << 4) | val[s.charAt(i + 5)];
        i += 5;
        }
        sbuf.append((char) cint);
        } else {
        sbuf.append((char) ch);
        }
        i++;
        }
        return sbuf.toString();
        }

        
  public static String ByteToString(byte[] b,int start,int length){
	  byte[] data=new byte[length];
	  for(int i=0;i<(+length);i++){
		  data[i]=b[start+i];
	  }
	  return new String(data);
  }      
        
        
  public static String TimetoString(byte[] b){
  Long str=new Long(0);
  for(int i=0;i<b.length;i++){
  Long nowvalue=Long.valueOf(TimeToString(b[i]));
  System.out.println(nowvalue);
  str=str+nowvalue;
  }
  return String.valueOf(str);
  }

  public static String TimeToString(int dwIn)
  {
  String year,szMonth,szDay,szHour,szMinute,szSecond;
		int  iTemp;
		iTemp=(dwIn&0xfc000000)/67108864;
		year=Integer.toString(iTemp>=0?iTemp:-iTemp);
		if(iTemp<10)
		{
			year="20"+"0"+year;
		}else
		{
			year="20"+year;
		}
		iTemp=(dwIn&0x03c00000)/4194304;
		szMonth=Integer.toString(iTemp);
		if(iTemp<10)
		{
			szMonth="0"+szMonth;
		}

		iTemp=(dwIn&0x003e0000)/131072;
		szDay=Integer.toString(iTemp);
		if(iTemp<10)
		{
			szDay="0"+szDay;
		}

		iTemp=(dwIn&0x0001f000)/4096;
		szHour=Integer.toString(iTemp);
		if(iTemp<10)
		{
			szHour="0"+szHour;
		}

		iTemp=(dwIn&0x00000fc0)/64;
		szMinute=Integer.toString(iTemp);
		if(iTemp<10)
		{
			szMinute="0"+szMinute;
		}

		iTemp=dwIn&0x0000003f;
		szSecond=Integer.toString(iTemp);
		if(iTemp<10)
		{
			szSecond="0"+szSecond;
		}
		return year+szMonth+szDay+szHour+szMinute+szSecond;
	}

	/*
	 * CmcTime :14位完整时间,如20300712225225
	 * 返回值:String 型,ASCII字符串79D96D19
	 * 该函数在年份大于2063年时,返回的字符串长度会超出8.使用时请注意
	 */
	public static int StringToTime(byte[] des,String  CmcTime)
	{
		long year,month,day,hour,minuits,second;
		/*请增加时间合法性校验的容错处理*/
		/*
		if(checkcmctime(Cmctime))
		{
			return null;
		}
		*/
		year=Integer.parseInt(CmcTime.substring(2, 4));
		month=Integer.parseInt(CmcTime.substring(4, 6));
		day=Integer.parseInt(CmcTime.substring(6, 8));
		hour=Integer.parseInt(CmcTime.substring(8, 10));
		minuits=Integer.parseInt(CmcTime.substring(10, 12));
		second=Integer.parseInt(CmcTime.substring(12, 14));
		year=year*67108864;
		month=month*4194304;
		day=day*131072;
		hour=hour*4096;
		minuits=minuits*64;
		year=year|month|day|hour|minuits|second;
    return Asc2Hex(des,Long.toHexString(year).toUpperCase());
	}
	

    public static void main(String[] args){
    	
        System.out.print(Coding.bin2hex("16"));
    }
	
	
	
}
