package com.application.webserver;

import java.util.Random;

public class StringUtil {

	public static String key50[] = { "HU399646BISN5J1W", "B5169I06964RAT91",
			"899CWT8Y23RS0V54", "CT3U20AJ49895529", "O93M19Q72089W21Y",
			"S6SB2VMA7SAOBV0J", "9XV71L3CSZ073MRV", "PUL58P4HLE0U48VR",
			"VI248JK53LWTS28H", "W4T5X4U5YF5Z374S", "4W7WY8L053E321VW",
			"H8X044P515OLARS4", "0CEL3DJ2LHTU2JB5", "EO81IR3MNRT1F3LQ",
			"IB3MFVP2811KH0Y0", "H137851759370M5K", "O063H7A38NKZ5BK1",
			"9LUYO1T56DGI6QEC", "3W5RY11U9T0OZ13B", "A3SEA9C4S2Q0YJ15",
			"496LI6REUJ8R9WBT", "Z552QZ90M527C20S", "BPHK13V9X7CE6ZFI",
			"7SBQ2WVDRC81OO9Q", "EJ14WFL9Q9R56373", "7923CBS3821003J7",
			"C31DCXY12Q9LLXHI", "6ZEQVG1L84WR403J", "J69PTI56S7641K95",
			"GQY87EHB6HOKRL6P", "24I3U1W07HTJ6K33", "GIQ76E9I238KE1M7",
			"61K5UTNKWW645U9G", "7V7JV9LEO00KIC94", "RS08HAJXFJ5GD0KC",
			"19H6848V509R4100", "9G1PSX8T5HB16923", "46Q94N7Y7UF3Z42J",
			"31OV20MV0KZUUDXJ", "F4M70Y395V441SI4", "YHZ8Z559T382CQ87",
			"5S483TO6H60U5A07", "702GKX16Z39FQ4XI", "M2YJW1PU08GZG3LJ",
			"4G5R79B7001MW186", "3SF0RY67R5206M7R", "54E11OWKJLE0RPSZ",
			"G0VH8B55TU2NF167", "Z2EZ0E5509469BK3", "SR10MFQRME78AJO8" };

	public static String leftString(String s, String comm, Integer size) {// 左补东西，自己定义
		String returns = s;
		int s_len = 0;
		if (s == null) {
		} else {
			s_len = s.length();
		}
		for (int i = 0; i < size - s_len; i++) {
			returns = comm + returns;
		}
		return returns;
	}

	public static String rightString(String s, String comm, Integer size) {// 右补东西，自己定义
		String returns = s;
		int s_len = 0;
		if (s == null) {
		} else {
			s_len = s.length();
		}
		for (int i = 0; i < size - s_len; i++) {
			returns = returns + comm;
		}
		return returns;
	}

	public static String GetCheckCode(String s) {
		String nonCheckCodeCardId = s;
		if (nonCheckCodeCardId == null
				|| nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			throw new IllegalArgumentException("卡号不能为空!");
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		char code = (luhmSum % 10 == 0) ? '0'
				: (char) ((10 - luhmSum % 10) + '0');
		return String.valueOf(code);
	}

	public static String toThrowsException(String exceptionCode, String msgType) {
		String returnValue = "";
		if (exceptionCode.equals(Constant.MsgLengthError)) {
			returnValue = "报文长度错!";
		} else if (exceptionCode.equals(Constant.MsgHeadError)) {
			returnValue = "报文头错!";
		} else if (exceptionCode.equals(Constant.ServerSqlError)) {
			returnValue = "Socket服务端操作数据库错误!";
		}

		if (msgType.equals("3001")) {
			if (exceptionCode.equals("00003")) {
				returnValue = "卡号校验码错误!";
			} else if (exceptionCode.equals("00004")) {
				returnValue = "卡账户错误,不存在或找到多条记录";
			} else if (exceptionCode.equals("00005")) {
				returnValue = "此卡不为月票卡,不能登记为卡套损坏";
			}
		} else if (msgType.equals("3002")) {
			if (exceptionCode.equals("00003")) {
				returnValue = "卡号校验码错误!";
			}
		} else if (msgType.equals("3003")) {
			if (exceptionCode.equals("00003")) {
				returnValue = "卡号校验码错误!";
			}
		}

		return returnValue;
	}

	/**
	 * Luhn算法 根据卡号获取校验位
	 * 
	 * @param cardNumber
	 * @return
	 */
	public static int getCheckNumber(String cardNumber) {
		int totalNumber = 0;
		for (int i = cardNumber.length() - 1; i >= 0; i -= 2) {
			int tmpNumber = calculate(Integer.parseInt(String
					.valueOf(cardNumber.charAt(i))) * 2);
			if (i == 0) {
				totalNumber += tmpNumber;
			} else {
				totalNumber += tmpNumber
						+ Integer.parseInt(String.valueOf(cardNumber
								.charAt(i - 1)));
			}

		}
		if (totalNumber >= 0 && totalNumber < 9) {
			return (10 - totalNumber);
		} else {
			String str = String.valueOf(totalNumber);
			if (Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) == 0) {
				return 0;
			} else {
				return (10 - Integer.parseInt(String.valueOf(str.charAt(str
						.length() - 1))));
			}
		}

	}

	/**
	 * 计算数字各位和
	 * 
	 * @param number
	 * @return
	 */
	public static int calculate(int number) {
		String str = String.valueOf(number);
		int total = 0;
		for (int i = 0; i < str.length(); i++) {
			total += Integer.valueOf(Integer.parseInt(String.valueOf(str
					.charAt(i))));
		}
		return total;
	}

	/**
	 * 将0123456789ABCDEF字符转化为byte
	 * 
	 * @param c ,
	 *            flag: 标志：0-大写,1-小写
	 * @return
	 */
	private static byte toByte(char c, int flag) {
		byte b = 0;
		if (flag == 0)
			b = (byte) "0123456789ABCDEF".indexOf(c);
		else
			b = (byte) "0123456789abcdef".indexOf(c);
		return b;
	}

	/**
	 * 将16进制字符串转化为byte数组
	 * 
	 * @param hex ,
	 *            flag: 标志：0-大写,1-小写
	 * @return
	 */
	public static byte[] hexStringToByte(String hex, int flag) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos], flag) << 4 | toByte(
					achar[pos + 1], flag));
		}
		return result;
	}

	/**
	 * 将字节数组转16进制字符串
	 * 
	 * @param bArray,flag:
	 *            标志：0-大写,1-小写
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray, int flag) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			if (flag == 1) {
				sb.append(sTemp); // 小写
			} else {
				sb.append(sTemp.toUpperCase());// 大写
			}
		}
		return sb.toString();
	}

	/**
	 * 转化十六进制编码为字符串
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
						i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "UTF-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * int 转化为16进制字符
	 * 
	 * @param dec
	 *            输入的int
	 * @param hex
	 *            输出的字符数组
	 * @param length
	 *            字符数组的长度
	 * @return
	 */
	public static int DectoHex(int dec, char[] hex, int length) {
		int i;
		for (i = length - 1; i >= 0; i--) {
			hex[i] = (char) ((dec % 256) & 0xff);
			dec /= 256;
		}
		return 0;
	}

	/**
	 * 填充24个字节
	 * 
	 * @param byteKey
	 * @return
	 */
	public static byte[] changeTo24bytes(byte[] byteKey) {
		byte[] keys = null;
		if (byteKey.length == 16) {
			keys = new byte[24];
			System.arraycopy(byteKey, 0, keys, 0, 16);
			System.arraycopy(byteKey, 0, keys, 16, 8);
		} else {
			keys = byteKey;
		}
		return keys;
	}

	/**
	 * 生成随机字符和数字
	 * 
	 * @param length
	 * @return
	 */
	public static String getCharAndNumr(int length) {
		String val = "";

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = 65; // 取得大写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}

	/**
	 * 功能：调试用main方法
	 */
	public static void main(String[] args) throws Exception {

	}

}