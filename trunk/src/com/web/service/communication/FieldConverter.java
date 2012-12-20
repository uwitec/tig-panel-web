/**
 * 
 */
package com.web.service.communication;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author David
 *
 */
public class FieldConverter {
	public static List<Byte> convert(DatagramField df, String value, String ascii_encoding) throws Exception{
		if(value == null)
			value = "";

		List<Byte> byte_list = null;
		int temp_length = 0;
		byte[] tmp_bytes;
		JudgeInfo judgeInfo = null;
		JudgeInfo judgeInfo1 = null;
		DatagramField.Enum_type fieldtype = df.getType();
		if(fieldtype.equals(DatagramField.Enum_type.A)
			||fieldtype.equals(DatagramField.Enum_type.AN)
			||fieldtype.equals(DatagramField.Enum_type.ANS)
			||fieldtype.equals(DatagramField.Enum_type.B)
			||fieldtype.equals(DatagramField.Enum_type.A)
			||fieldtype.equals(DatagramField.Enum_type.HHMMSS)
			||fieldtype.equals(DatagramField.Enum_type.MMDD)
			||fieldtype.equals(DatagramField.Enum_type.N)
			||fieldtype.equals(DatagramField.Enum_type.S)
			||fieldtype.equals(DatagramField.Enum_type.YYYYMMDD)
			||fieldtype.equals(DatagramField.Enum_type.YYYYMMDDHHMMSS)
			||fieldtype.equals(DatagramField.Enum_type.CHINESE)){
			judgeInfo = judgeInfo(df);
			byte_list = encodingField(df.getFormat(), value, judgeInfo, ascii_encoding);
		}else if(fieldtype.equals(DatagramField.Enum_type.ALLLVAR)
				|| fieldtype.equals(DatagramField.Enum_type.ALLVAR)){
			judgeInfo = judgeInfo(df);
			temp_length = value.length();
			byte_list = encodingField(DatagramField.Enum_format.BCD, String.valueOf(temp_length), judgeInfo, ascii_encoding);
			df.setType(DatagramField.Enum_type.A);
			df.setLength(String.valueOf(temp_length));
			judgeInfo1 = judgeInfo(df);
			byte_list.addAll(encodingField(df.getFormat(), value, judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.ANLLLVAR)
				|| fieldtype.equals(DatagramField.Enum_type.ANLLVAR)){
			judgeInfo = judgeInfo(df);
			temp_length = value.length();
			byte_list = encodingField(DatagramField.Enum_format.BCD, String.valueOf(temp_length), judgeInfo, ascii_encoding);
			df.setType(DatagramField.Enum_type.AN);
			df.setLength(String.valueOf(temp_length));
			judgeInfo1 = judgeInfo(df);
			byte_list.addAll(encodingField(df.getFormat(), value, judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.ANSLLLVAR)
				||fieldtype.equals(DatagramField.Enum_type.ANSLLVAR)){
			judgeInfo = judgeInfo(df);
			temp_length = value.length();
			byte_list = encodingField(DatagramField.Enum_format.BCD, String.valueOf(temp_length), judgeInfo, ascii_encoding);
			df.setType(DatagramField.Enum_type.ANS);
			df.setLength(String.valueOf(temp_length));
			judgeInfo1 = judgeInfo(df);
			byte_list.addAll(encodingField(df.getFormat(), value, judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.BLLLVAR)
				||fieldtype.equals(DatagramField.Enum_type.BLLVAR)){
			judgeInfo = judgeInfo(df);
			temp_length = value.length();
			byte_list = encodingField(DatagramField.Enum_format.BCD, String.valueOf(temp_length), judgeInfo, ascii_encoding);
			df.setType(DatagramField.Enum_type.B);
			df.setLength(String.valueOf(temp_length));
			judgeInfo1 = judgeInfo(df);
			byte_list.addAll(encodingField(df.getFormat(), value, judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.NLLLVAR)
				||fieldtype.equals(DatagramField.Enum_type.NLLVAR)){
			judgeInfo = judgeInfo(df);
			temp_length = value.length();
			byte_list = encodingField(DatagramField.Enum_format.BCD, String.valueOf(temp_length), judgeInfo, ascii_encoding);
			df.setType(DatagramField.Enum_type.N);
			df.setLength(String.valueOf(temp_length));
			judgeInfo1 = judgeInfo(df);
			byte_list.addAll(encodingField(df.getFormat(), value, judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.CHINESELLLVAR)
				||fieldtype.equals(DatagramField.Enum_type.CHINESELLVAR)){
			tmp_bytes = value.getBytes(Charset.forName(ascii_encoding));
			judgeInfo = judgeInfo(df);
			temp_length = tmp_bytes.length;
			byte_list = encodingField(DatagramField.Enum_format.BCD, String.valueOf(temp_length), judgeInfo, ascii_encoding);
			for(byte b : tmp_bytes){
				byte_list.add(b);
			}
		}

		return byte_list;
	}
	
		
	public static ParseResult convertFromBytes(
			DatagramField df, byte[] retDatagram, int next_position, String ascii_encoding) throws Exception{
		DatagramField tmp_df = null;
		StringBuffer str_buf = new StringBuffer();
		ParseResult result = new ParseResult();
		
		JudgeInfo judgeInfo = judgeInfo(df);
		JudgeInfo judgeInfo1 = null;
		byte[] field_bytes = null;
		field_bytes = new byte[judgeInfo.length];
		int end_byte_position = next_position + judgeInfo.length;
		for(int i=next_position; i<end_byte_position; i++){
			field_bytes[i - next_position] = retDatagram[i];
		}
		next_position += judgeInfo.length;
			
		DatagramField.Enum_type fieldtype = df.getType();
		if(fieldtype.equals(DatagramField.Enum_type.A)
			||fieldtype.equals(DatagramField.Enum_type.AN)
			||fieldtype.equals(DatagramField.Enum_type.ANS)
			||fieldtype.equals(DatagramField.Enum_type.B)
			||fieldtype.equals(DatagramField.Enum_type.A)
			||fieldtype.equals(DatagramField.Enum_type.HHMMSS)
			||fieldtype.equals(DatagramField.Enum_type.MMDD)
			||fieldtype.equals(DatagramField.Enum_type.N)
			||fieldtype.equals(DatagramField.Enum_type.S)
			||fieldtype.equals(DatagramField.Enum_type.YYYYMMDD)
			||fieldtype.equals(DatagramField.Enum_type.YYYYMMDDHHMMSS)
			||fieldtype.equals(DatagramField.Enum_type.CHINESE)){
			str_buf.append(decodingField(df.getFormat(), field_bytes, judgeInfo, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.ALLLVAR)
				|| fieldtype.equals(DatagramField.Enum_type.ALLVAR)){
			int tmp_length = Integer.parseInt(decodingField(DatagramField.Enum_format.BCD, field_bytes, judgeInfo, ascii_encoding));
			tmp_df = new DatagramField();
			tmp_df.setType(DatagramField.Enum_type.A);
			tmp_df.setPendingdir(df.getPendingdir());
			tmp_df.setPendingchar(df.getPendingchar());
			tmp_df.setFormat(df.getFormat());
			tmp_df.setLength(String.valueOf(tmp_length));
			judgeInfo1 = judgeInfo(tmp_df);

			byte[] field_bytes1 = null;
			field_bytes1 = new byte[judgeInfo1.length];
			end_byte_position = next_position + judgeInfo1.length;
			for(int i=next_position; i<end_byte_position; i++){
				field_bytes1[i - next_position] = retDatagram[i];
			}
			next_position += judgeInfo1.length;
			str_buf.append(decodingField(tmp_df.getFormat(), field_bytes1 , judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.ANLLLVAR)
				|| fieldtype.equals(DatagramField.Enum_type.ANLLVAR)){
			int tmp_length = Integer.parseInt(decodingField(DatagramField.Enum_format.BCD, field_bytes, judgeInfo, ascii_encoding));
			tmp_df = new DatagramField();
			tmp_df.setType(DatagramField.Enum_type.AN);
			tmp_df.setPendingdir(df.getPendingdir());
			tmp_df.setPendingchar(df.getPendingchar());
			tmp_df.setFormat(df.getFormat());
			tmp_df.setLength(String.valueOf(tmp_length));
			judgeInfo1 = judgeInfo(tmp_df);

			byte[] field_bytes1 = null;
			field_bytes1 = new byte[judgeInfo1.length];
			end_byte_position = next_position + judgeInfo1.length;
			for(int i=next_position; i<end_byte_position; i++){
				field_bytes1[i - next_position] = retDatagram[i];
			}
			next_position += judgeInfo1.length;
			str_buf.append(decodingField(tmp_df.getFormat(), field_bytes1 , judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.ANSLLLVAR)
				||fieldtype.equals(DatagramField.Enum_type.ANSLLVAR)){
			int tmp_length = Integer.parseInt(decodingField(DatagramField.Enum_format.BCD, field_bytes, judgeInfo, ascii_encoding));
			tmp_df = new DatagramField();
			tmp_df.setType(DatagramField.Enum_type.ANS);
			tmp_df.setPendingdir(df.getPendingdir());
			tmp_df.setPendingchar(df.getPendingchar());
			tmp_df.setFormat(df.getFormat());
			tmp_df.setLength(String.valueOf(tmp_length));
			judgeInfo1 = judgeInfo(tmp_df);
			
			byte[] field_bytes1 = null;
			field_bytes1 = new byte[judgeInfo1.length];
			end_byte_position = next_position + judgeInfo1.length;
			for(int i=next_position; i<end_byte_position; i++){
				field_bytes1[i - next_position] = retDatagram[i];
			}
			next_position += judgeInfo1.length;
			str_buf.append(decodingField(tmp_df.getFormat(), field_bytes1 , judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.BLLLVAR)
				||fieldtype.equals(DatagramField.Enum_type.BLLVAR)){
			int tmp_length = Integer.parseInt(decodingField(DatagramField.Enum_format.BCD, field_bytes, judgeInfo, ascii_encoding));
			tmp_df = new DatagramField();
			tmp_df.setType(DatagramField.Enum_type.B);
			tmp_df.setPendingdir(df.getPendingdir());
			tmp_df.setPendingchar(df.getPendingchar());
			tmp_df.setFormat(df.getFormat());
			tmp_df.setLength(String.valueOf(tmp_length*8));
			judgeInfo1 = judgeInfo(tmp_df);
			//judgeInfo1.length = tmp_length;
			byte[] field_bytes1 = null;
			field_bytes1 = new byte[judgeInfo1.length];
			end_byte_position = next_position + judgeInfo1.length;
			for(int i=next_position; i<end_byte_position; i++){
				field_bytes1[i - next_position] = retDatagram[i];
			}
			next_position += judgeInfo1.length;
			str_buf.append(decodingField(tmp_df.getFormat(), field_bytes1 , judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.NLLLVAR)
				||fieldtype.equals(DatagramField.Enum_type.NLLVAR)){
			int tmp_length = Integer.parseInt(decodingField(DatagramField.Enum_format.BCD, field_bytes, judgeInfo, ascii_encoding));
			tmp_df = new DatagramField();
			tmp_df.setType(DatagramField.Enum_type.N);
			tmp_df.setPendingdir(df.getPendingdir());
			tmp_df.setPendingchar(df.getPendingchar());
			tmp_df.setFormat(df.getFormat());
			tmp_df.setLength(String.valueOf(tmp_length));
			judgeInfo1 = judgeInfo(tmp_df);
			//judgeInfo1.length = tmp_length;
			byte[] field_bytes1 = null;
			field_bytes1 = new byte[judgeInfo1.length];
			end_byte_position = next_position + judgeInfo1.length;
			for(int i=next_position; i<end_byte_position; i++){
				field_bytes1[i - next_position] = retDatagram[i];
			}
			next_position += judgeInfo1.length;
			str_buf.append(decodingField(tmp_df.getFormat(), field_bytes1 , judgeInfo1, ascii_encoding));
		}else if(fieldtype.equals(DatagramField.Enum_type.CHINESELLLVAR)
				||fieldtype.equals(DatagramField.Enum_type.CHINESELLVAR)){
			int tmp_length = Integer.parseInt(decodingField(DatagramField.Enum_format.BCD, field_bytes, judgeInfo, ascii_encoding));
			tmp_df = new DatagramField();
			tmp_df.setType(DatagramField.Enum_type.CHINESE);
			tmp_df.setPendingdir(df.getPendingdir());
			tmp_df.setPendingchar(df.getPendingchar());
			tmp_df.setFormat(df.getFormat());
			tmp_df.setLength(String.valueOf(tmp_length));
			judgeInfo1 = judgeInfo(tmp_df);
			//judgeInfo1.length = tmp_length;
			byte[] field_bytes1 = null;
			field_bytes1 = new byte[judgeInfo1.length];
			end_byte_position = next_position + judgeInfo1.length;
			for(int i=next_position; i<end_byte_position; i++){
				field_bytes1[i - next_position] = retDatagram[i];
			}
			next_position += judgeInfo1.length;
			str_buf.append(decodingField(tmp_df.getFormat(), field_bytes1 , judgeInfo1, ascii_encoding));
		}
		result.result = str_buf.toString();
		result.next_position = next_position;
		return result;
	}
	

	public static JudgeInfo judgeInfo(DatagramField field) throws Exception{
		DatagramField.Enum_type type = field.getType();
		DatagramField.Enum_format format = field.getFormat();
		
		JudgeInfo judgeInfo = new JudgeInfo();
		int temp_length = Integer.parseInt(field.getLength());
		if(type.equals(DatagramField.Enum_type.N)
				||type.equals(DatagramField.Enum_type.HHMMSS)
				||type.equals(DatagramField.Enum_type.MMDD)
				||type.equals(DatagramField.Enum_type.YYYYMMDD)
				||type.equals(DatagramField.Enum_type.YYYYMMDDHHMMSS)){
			judgeInfo.islvar = false;
			if(format.equals(DatagramField.Enum_format.ASCII)){
				judgeInfo.length = temp_length;
				if(field.getPendingdir().equals(DatagramField.Enum_pending_dir.LEADING)){
					judgeInfo.ending = JudgeInfo.Ending.LEADING;
				}else{
					judgeInfo.ending = JudgeInfo.Ending.TRAILING;
				}
				judgeInfo.pendingchar = field.getPendingchar().charValue();
			}else if(format.equals(DatagramField.Enum_format.BCD)
				||format.equals(DatagramField.Enum_format.HEX)){
				judgeInfo.length = (temp_length + 1) / 2;
				if(field.getPendingdir().equals(DatagramField.Enum_pending_dir.LEADING)){
					judgeInfo.ending = JudgeInfo.Ending.LEADING;
				}else{
					judgeInfo.ending = JudgeInfo.Ending.TRAILING;
				}
				judgeInfo.pendingchar = field.getPendingchar().charValue();
			}else{
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}
		}else if(type.equals(DatagramField.Enum_type.A)
				||type.equals(DatagramField.Enum_type.AN)
				||type.equals(DatagramField.Enum_type.ANS)
				||type.equals(DatagramField.Enum_type.S)){
			judgeInfo.islvar = false;
			if(format.equals(DatagramField.Enum_format.ASCII)){
				judgeInfo.length = temp_length;
				if(field.getPendingdir().equals(DatagramField.Enum_pending_dir.LEADING)){
					judgeInfo.ending = JudgeInfo.Ending.LEADING;
				}else{
					judgeInfo.ending = JudgeInfo.Ending.TRAILING;
				}
				judgeInfo.pendingchar = field.getPendingchar().charValue();
			}else if(format.equals(DatagramField.Enum_format.BCD)
				||format.equals(DatagramField.Enum_format.HEX)){
				judgeInfo.length = (temp_length + 1) / 2;
				if(field.getPendingdir().equals(DatagramField.Enum_pending_dir.LEADING)){
					judgeInfo.ending = JudgeInfo.Ending.LEADING;
				}else{
					judgeInfo.ending = JudgeInfo.Ending.TRAILING;
				}
				judgeInfo.pendingchar = field.getPendingchar().charValue();
			}else{
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}
		}else if(type.equals(DatagramField.Enum_type.ALLVAR)
				||type.equals(DatagramField.Enum_type.ANLLVAR)
				||type.equals(DatagramField.Enum_type.ANSLLVAR)){
			judgeInfo.islvar = true;
			judgeInfo.length = 1;
			if(format.equals(DatagramField.Enum_format.BINARY)){
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}else{
				judgeInfo.ending = JudgeInfo.Ending.LEADING;
				judgeInfo.pendingchar = '0';
			}
		}else if(type.equals(DatagramField.Enum_type.ALLLVAR)
				||type.equals(DatagramField.Enum_type.ANLLLVAR)
				||type.equals(DatagramField.Enum_type.ANSLLLVAR)){
			judgeInfo.islvar = true;
			judgeInfo.length = 2;
			if(format.equals(DatagramField.Enum_format.BINARY)){
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}else{
				judgeInfo.ending = JudgeInfo.Ending.LEADING;
				judgeInfo.pendingchar = '0';
			}
		}else if(type.equals(DatagramField.Enum_type.CHINESE)){
			judgeInfo.islvar = false;
			switch(format){
				case ASCII:
					judgeInfo.length = temp_length;
					if(field.getPendingdir().equals(DatagramField.Enum_pending_dir.LEADING)){
						judgeInfo.ending = JudgeInfo.Ending.LEADING;
					}else{
						judgeInfo.ending = JudgeInfo.Ending.TRAILING;
					}
					judgeInfo.pendingchar = field.getPendingchar().charValue();
				break;
				default:
					throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}
		}else if(type.equals(DatagramField.Enum_type.CHINESELLVAR)){
			judgeInfo.islvar = true;
			judgeInfo.length = 1;
			switch(format){
				case ASCII:
					judgeInfo.ending = JudgeInfo.Ending.LEADING;
					judgeInfo.pendingchar = '0';
				break;
				default:
					throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}
		}else if(type.equals(DatagramField.Enum_type.CHINESELLLVAR)){
			judgeInfo.islvar = true;
			judgeInfo.length = 2;
			switch(format){
				case ASCII:
					judgeInfo.ending = JudgeInfo.Ending.LEADING;
					judgeInfo.pendingchar = '0';
				break;
				default:
					throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}
		}else if(type.equals(DatagramField.Enum_type.B)){
			judgeInfo.islvar = false;
			switch(format){
				case BINARY:
					if(temp_length % 8 != 0){
						judgeInfo.length = temp_length / 8 + 1;
					}else{
						judgeInfo.length = temp_length / 8;
					}
					if(field.getPendingdir().equals(DatagramField.Enum_pending_dir.LEADING)){
						judgeInfo.ending = JudgeInfo.Ending.LEADING;
					}else{
						judgeInfo.ending = JudgeInfo.Ending.TRAILING;
					}
					judgeInfo.pendingchar = field.getPendingchar().charValue();
				break;
				default:
					throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}
		}else if(type.equals(DatagramField.Enum_type.BLLVAR)){
			judgeInfo.islvar = true;
			judgeInfo.length = 1;
			switch(format){
				case BINARY:
					judgeInfo.ending = JudgeInfo.Ending.LEADING;
					judgeInfo.pendingchar = '0';
				break;
				default:
					throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}
		}else if(type.equals(DatagramField.Enum_type.BLLLVAR)){
			judgeInfo.islvar = true;
			judgeInfo.length = 2;
			switch(format){
				case BINARY:
					judgeInfo.ending = JudgeInfo.Ending.LEADING;
					judgeInfo.pendingchar = '0';
				break;
				default:
					throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}
		}else if(type.equals(DatagramField.Enum_type.NLLVAR)){
			judgeInfo.islvar = true;
			judgeInfo.length = 1;
			if(format.equals(DatagramField.Enum_format.BINARY)){
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}else{
				judgeInfo.ending = JudgeInfo.Ending.LEADING;
				judgeInfo.pendingchar = '0';
			}
		}else if(type.equals(DatagramField.Enum_type.NLLLVAR)){
			judgeInfo.islvar = true;
			judgeInfo.length = 2;
			if(format.equals(DatagramField.Enum_format.BINARY)){
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMFIELDTYPEFORMATNOTMATCH);
			}else{
				judgeInfo.ending = JudgeInfo.Ending.LEADING;
				judgeInfo.pendingchar = '0';
			}
		}
		
		return judgeInfo;		
	}
	
	
	private static List<Byte> encodingField(DatagramField.Enum_format fieldFormat, String value, JudgeInfo judgeInfo, String ascii_encoding) throws Exception{
		int ending = EncodingConverter.LEADING;;
		if(judgeInfo.ending.equals(JudgeInfo.Ending.LEADING)){
			ending = EncodingConverter.LEADING;
		}else if(judgeInfo.ending.equals(JudgeInfo.Ending.TRAILING)){
			ending = EncodingConverter.TRAILING;
		}
		List<Byte> byte_list = null;
		switch(fieldFormat){
			case ASCII:
				byte_list = EncodingConverter.byteListForLiteralString(value, judgeInfo.length, ending, judgeInfo.pendingchar, ascii_encoding);
			break;
			case BCD:
				byte_list = EncodingConverter.byteListForHexBCD(value, judgeInfo.length, ending, judgeInfo.pendingchar);
			break;
			case BINARY:
				byte_list = EncodingConverter.byteListForBinaryString(value, judgeInfo.length, ending, judgeInfo.pendingchar);
			break;
			case HEX:
				byte_list = EncodingConverter.byteListForHexBCD(value, judgeInfo.length, ending, judgeInfo.pendingchar);
			break;
		}
		return byte_list;
	}
	
	private static String decodingField(DatagramField.Enum_format fieldFormat, byte[] retDatagram, JudgeInfo judgeInfo, String ascii_encoding) throws Exception{
		/*String regex = null;
		if(judgeInfo.ending.equals(JudgeInfo.Ending.LEADING)){
			if(judgeInfo.pendingchar == ' '){
				regex = "^\\s*";
			}else if(judgeInfo.pendingchar == '0'){
				regex = "^0*";
			}
		}else if(judgeInfo.ending.equals(JudgeInfo.Ending.TRAILING)){
			if(judgeInfo.pendingchar == ' '){
				regex = "\\s*$";
			}else if(judgeInfo.pendingchar == '0'){
				regex = "0*$";
			}
		}*/

		String ret_str = null;
		StringBuffer str_buf = null;
		switch(fieldFormat){
			case ASCII:
				ret_str = new String(retDatagram, Charset.forName(ascii_encoding))/*.replaceFirst(regex, "")*/;
			break;
			case BCD:
				ret_str = EncodingConverter.hexStringFromBytes(retDatagram)/*.replaceFirst(regex, "")*/;
			break;
			case BINARY:
				str_buf = new StringBuffer();
				for(int i=0;i<retDatagram.length;i++){
					ret_str = Integer.toBinaryString(Integer.decode("0x" + EncodingConverter.hexStringFromByte(retDatagram[i])));
					for(int j = 0;j<8 - ret_str.length();j++){
						str_buf.append('0');
					}
					str_buf.append(ret_str);
				}
				ret_str = str_buf.toString();
			break;
			case HEX:
				ret_str = EncodingConverter.hexStringFromBytes(retDatagram)/*.replaceFirst(regex, "")*/;
			break;
		}
		return ret_str;
	}
}
