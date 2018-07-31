package com.bankwel.phjf_admin.component.c11assistant;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * C11_1 - Trans Helper Component
 * 数据转换助手
 * @author sln
 *
 */
public class TransHelper {
	
	public static final String SPLIT_DEFAULT = "#,#";

	/**
	 * 用于将数据库返回的Map结果集转换为标准Object结果集
	 * @param dbList 将数据库返回结果集
	 * @param targetClass 标准ObjectClass类型
	 * @return
	 */
	public static <T> List transDbList2TList(List<Map<String, Object>> dbList,Class<T> targetClass){
		List<T> objList = new ArrayList<T>();
		for(Map<String, Object> row : dbList){
			try {
				T t = targetClass.newInstance();
				BeanUtils.populate(t, row);
				objList.add(t);
			} catch (Exception e) {e.printStackTrace();}
		}
		return objList;
	}
	
	/** 
	   * 将对象转为map
	   * map的key为对象的属性名称
	   * @param list 
	   *            查询结果集
	   * @return 
	   */  
	  public static List<Map<String, Object>> list2MapList(List list) {  
			List<Map<String, Object>> listmap = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < list.size(); i++) {
				try {
					listmap.add(BeanUtils.describe(list.get(i)));
				} catch (Exception e) {
				}
			}		
			return listmap;
	  }

    /**
     * 将对象转为map，对象内部属性不做转化
     * map的key为对象的属性名称
     * @param list 查询结果集
     * @return
     */
    public static List<Map<String, Object>> list2ObjectMapList(List list) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < list.size(); i++) {
            try {
                listmap.add(PropertyUtils.describe(list.get(i)));
            } catch (Exception e) {
            }
        }
        return listmap;
    }
	  
	  /**
	   * 将对象转为map
	   * map的key为对象的属性名称
	   * @param obj
	   * @return
	   */
	  public static Map<String, Object> obj2map(Object obj) {  
			try {
				return BeanUtils.describe(obj);
			} catch (Exception e) {
			}
			return null;
	  } 
	
	/**
	 * 字符串数组按照分隔符进行拼接
	 * @param strs
	 * @param split
	 * @return
	 */
	public static String transStrBySplit(String[] strs,String split){
		StringBuffer sbf = new StringBuffer();
		String toStr = "";
		for(int i =0;i<strs.length;i++){
			if(i==0){
				toStr=strs[i];
			}else{
				toStr=toStr+split+strs[i];
			}
		}
		sbf.append(toStr);
		return sbf.toString();
	}
	
	/**
	 * 用于连接StringBuffer字符串
	 * @param strs
	 * @return
	 */
	public static StringBuffer sbfAppender(String... strs){
		StringBuffer sbf = new StringBuffer();
		for(String s : strs){stringBufferAppender(sbf,s);}
		return sbf;
	}
	
	/**
	 * 用于连接StringBuffer字符串
	 * @param sbf
	 * @param strs
	 */
	public static void stringBufferAppender(StringBuffer sbf,String... strs){
		for(String s : strs){sbf.append(s);}
	}
	
}
