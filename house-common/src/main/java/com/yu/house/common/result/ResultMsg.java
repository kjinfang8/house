package com.yu.house.common.result;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

/**
 * 页面数据返回封装类
 * 
 * @author jinfang yu
 */
public class ResultMsg {

	public static final String errorMsgKey = "errorMsg";
	public static final String successMsgKey = "successMsg";
	private String errorMsg;// 异常
	private String successMsg;// 状态信息

	// 验证成功
	public boolean isCuccess() {
		errorMsg=null;
		return true;
	}

	// 发送异常
	public static ResultMsg errorMsg(String msg) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setErrorMsg(msg);
		return resultMsg;
	}

	// 信息状态
	public static ResultMsg successMsg(String successMsgs) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setSuccessMsg(successMsgs);
		return resultMsg;
	}

	public Map<String, String> asMap() {
		Map<String, String> map = Maps.newHashMap();
		map.put(successMsgKey, successMsg);
		map.put(errorMsgKey, errorMsg);
		return map;
	}

	public String getUrlParams(){
		Map<String,String> map = asMap();
		Map<String,String> newMap = Maps.newHashMap();
		map.forEach(
				(k,v)->{
					if(v!=null) { //
						try {
							newMap.put(k, URLEncoder.encode(k, "utf-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				});
		return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
	}

	// getter、setter
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

}
