package com.qf.dao;

/**
 * 获取代码的执行时间
 * @author TeanWang
 *
 */
public class TimeForCode {
	
	/**
	 * 获取代码的执行时间
	 * @param cb
	 * @return
	 */
	public long getTime(Callback cb, Object ... obj) {
		long start = System.currentTimeMillis();
		cb.code(obj);
		long end = System.currentTimeMillis();
		return end - start;
	}

}
