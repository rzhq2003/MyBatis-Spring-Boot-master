package tk.mybatis.springboot.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyUtils {

	/**
	 * 判断字符串不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(String str) {
		// StringUtils.isNotEmpty(str);
		return str != null && !"".equals(str);
	}

	/**
	 * 判断字符串不为空 jdk StringUtils工具类实现如下所示
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 集合判断是否为空
	 * 
	 * @param collection
	 *            使用泛型
	 * @return
	 */
	public static <T> boolean notEmpty(Collection<T> collection) {
		if (collection != null) {
			Iterator<T> iterator = collection.iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					Object next = iterator.next();
					if (next != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * map集合不为空的判断
	 * 
	 * @param map
	 *            使用泛型，可以传递不同的类型参数
	 * @return
	 */
	public static <T> boolean notEmpty(Map<T, T> map) {
		return map != null && !map.isEmpty();
	}

	/**
	 * byte类型数组判断不为空
	 * 
	 * @param t
	 * @return
	 */
	public static boolean notEmpty(byte[] t) {
		return t != null && t.length > 0;
	}

	/**
	 * short类型数组不为空判断
	 * 
	 * @param t
	 * @return
	 */
	public static boolean notEmpty(short[] t) {
		return t != null && t.length > 0;
	}

	/**
	 * 数组判断不为空,没有泛型数组,所以还是分开写吧
	 * 
	 * @param t
	 *            可以是int,short,byte,String,Object,long
	 * @return
	 */
	public static boolean notEmpty(int[] t) {
		return t != null && t.length > 0;
	}

	/**
	 * long类型数组不为空
	 * 
	 * @param t
	 * @return
	 */
	public static boolean notEmpty(long[] t) {
		return t != null && t.length > 0;
	}

	/**
	 * String类型的数组不为空
	 * 
	 * @param t
	 * @return
	 */
	public static boolean notEmpty(String[] t) {
		return t != null && t.length > 0;
	}

	/**
	 * Object类型数组不为空
	 * 
	 * @param t
	 * @return
	 */
	public static boolean notEmpty(Object[] t) {
		return t != null && t.length > 0;
	}

	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean notEmpty(Object o) {
		return o != null && !"".equals(o) && !"null".equals(o);
	}
	

	// 并集（set唯一性）
	public static Long[] union(Long[] a1, Long[] a2) {
		Set<Long> hs = new HashSet<Long>();
		for (Long str : a1) {
			hs.add(str);
		}
		for (Long str : a2) {
			hs.add(str);
		}
		Long[] result = {};
		return hs.toArray(result);
	}

	// 交集(注意结果集中若使用LinkedList添加，则需要判断是否包含该元素，否则其中会包含重复的元素)
	public static Long[] intersect(Long[] a1, Long[] a2) {
		List<Long> l = new LinkedList<Long>();
		Set<Long> common = new HashSet<Long>();
		for (Long str : a1) {
			if (!l.contains(str)) {
				l.add(str);
			}
		}
		for (Long str : a2) {
			if (l.contains(str)) {
				common.add(str);
			}
		}
		Long[] result = {};
		return common.toArray(result);
	}

	// 求两个数组的差集
	public static Long[] substract(Long[] a1, Long[] a2) {
		LinkedList<Long> list = new LinkedList<Long>();
		for (Long str : a1) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (Long str : a2) {
			if (list.contains(str)) {
				list.remove(str);
			}
		}
		Long[] result = {};
		return list.toArray(result);
	}

	// 并集（set唯一性）
	public static String[] union(String[] a1, String[] a2) {
		Set<String> hs = new HashSet<String>();
		for (String str : a1) {
			hs.add(str);
		}
		for (String str : a2) {
			hs.add(str);
		}
		String[] result = {};
		return hs.toArray(result);
	}

	// 交集(注意结果集中若使用LinkedList添加，则需要判断是否包含该元素，否则其中会包含重复的元素)
	public static String[] intersect(String[] a1, String[] a2) {
		List<String> l = new LinkedList<String>();
		Set<String> common = new HashSet<String>();
		for (String str : a1) {
			if (!l.contains(str)) {
				l.add(str);
			}
		}
		for (String str : a2) {
			if (l.contains(str)) {
				common.add(str);
			}
		}
		String[] result = {};
		return common.toArray(result);
	}

	// 求两个数组的差集
	public static String[] substract(String[] a1, String[] a2) {
		LinkedList<String> list = new LinkedList<String>();
		for (String str : a1) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : a2) {
			if (list.contains(str)) {
				list.remove(str);
			}
		}
		String[] result = {};
		return list.toArray(result);
	}
	
	/*
	 * 
	 * 通过UUID生成16位唯一订单号
	 * 
	*/
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return machineId + String.format("%017d", hashCodeV);
    }
	
}
