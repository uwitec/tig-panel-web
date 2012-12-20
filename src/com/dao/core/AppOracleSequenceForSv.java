/**
 * 
 */
package com.dao.core;

import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

/**
 * @author David
 *
 */
public class AppOracleSequenceForSv extends OracleSequenceMaxValueIncrementer {
	private int sequenceLength;
	public int getSequenceLength() {
		return sequenceLength;
	}


	public void setSequenceLength(int sequenceLength) {
		this.sequenceLength = sequenceLength;
	}


	public String nextStringValue(){
		String nextValue = super.nextStringValue();
		int length = sequenceLength - nextValue.length();
		if(length != 0){
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<length;i++){
				buf.append('0');
			}
			return buf.toString() + nextValue;
		}else{
			return nextValue;
		}
	}
}
