package com.qf.dao;

/**
 * 接口回调的接口
 * @author TeanWang
 *
 */
public interface Callback {
	/**
	 * 要测耗时的代码
	 */
	void code(Object ... obj);

}
