package tk.mybatis.springboot.util;

import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.lang.reflect.Field;

import tk.mybatis.springboot.model.Pages;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 * https://blog.csdn.net/sosmmh/article/details/80047492?utm_source=blogxgwz5
 */
public abstract class BaseService<T> {

	@Autowired
	protected MyMapper<T> myMapper;

	private Class<T> clazz; // 当前泛型真实类型的Class
    
	
	
	private JSONObject jsonObject;

	@SuppressWarnings("unchecked")
	public BaseService() {
		// 获得具体model，通过反射来根据属性条件查找数据
	    clazz = (Class<T>) ReflectionUtils.getSuperClassGenericParameterizedType(getClass());
	    System.out.println("clazz:::" + ReflectionUtils.getSuperClassGenericParameterizedType(getClass()));
	 }

	/**
	 * 通过主键查找
	 * 
	 * @return 类
	 */
    public T getById(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }
    
	/**
	 * 通过类查找
	 * 
	 * @return List<T>
	 */
    public List<T> select(T model) {
        return myMapper.select(model);
    }
    
	/**
	 * 通用分页工具
	 * 
	 * @return 返回JSON字符串
	 */
    public JSONObject getAll(Pages pages) {
    	Field[] fields = clazz.getDeclaredFields();
    	String id = fields[0].getName(); // 取出主键名称
    	System.out.println("id:::" + id);    	
        if (pages.getPage() != null && pages.getRows() != null) {
            PageHelper.startPage(pages.getPage(), pages.getRows(), id);
        }   
        List<T> list = myMapper.selectAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageInfo", new PageInfo<T>(list));
        jsonObject.put("page", pages.getPage());
        jsonObject.put("rows", pages.getRows());
        return jsonObject;
    }
    
	/**
	 * 通用分页工具,带条件查询
	 * 
	 * @return 返回JSON字符串
	 */
    public JSONObject getBys(Pages pages,T model) {
    	Field[] fields = clazz.getDeclaredFields();
    	String id = fields[0].getName(); // 取出主键名称
    	System.out.println("id:::" + id);    	
        if (pages.getPage() != null && pages.getRows() != null) {
            PageHelper.startPage(pages.getPage(), pages.getRows(), id);
        }   
        List<T> list = myMapper.select(model);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageInfo", new PageInfo<T>(list));
        jsonObject.put("page", pages.getPage());
        jsonObject.put("rows", pages.getRows());
        return jsonObject;
    }
   
    
	/**
	 * 通过主键删除
	 * 
	 * @return int
	 */
    public int deleteById(Long id) {
    	return myMapper.deleteByPrimaryKey(id);
    }
    
    
	/**
	 * 通过主键批量删除，传入字符串如1,2,3
	 * 
	 * @return int
	 */
    public int deleteByIds(String ids) {
    	return myMapper.deleteByIds(ids);
    }
    
	/**
	 * 通过类删除
	 * 
	 * @return int
	 */
    public int delete(T model) {
    	return myMapper.delete(model);
    }
    
	/**
	 * 保存，传入 model
	 * 
	 * @return int
	 */
    public int save(T model) {
        return myMapper.insertSelective(model);
    }
    
	/**
	 * 批量保存，传入List<T>
	 * 
	 * @return int
	 */
    public int saves(List<T> models) {
        return myMapper.insertList(models);
    }
    
	/**
	 * 更新，传入 model
	 * 
	 * @return int
	 */
    public int updateById(T model) {
        return myMapper.updateByPrimaryKeySelective(model);
    }
    
	/**
	 * 按model查找
	 * 
	 * @return model
	 */
    public T selectOne(T model) {
    	return myMapper.selectOne(model);
    }
    
	/**
	 * 通过model条件查找，返回主键字符串如"1,2,3,4"
	 * 
	 * @return 字符串
	 */
    public String getIds(T model) {
    	Field[] fields = clazz.getDeclaredFields();
    	Field f = fields[0];
    	String ids = "";
    	f.setAccessible(true);
    	List<T> list = myMapper.select(model);
    	for( int i = 0 ; i < list.size() ; i++) {
    		try {				
				if (i == list.size() - 1) {
					ids = ids + f.get(list.get(i));
				} else {
					ids = ids + f.get(list.get(i)) + ",";
				}
	    		
			} catch (Exception e) {
				throw new ServiceException(e.getMessage(), e);
			}
    	}   		
    	return ids;
    }
    

	/**
	 * 通过model条件查找，返回特定字段值"1,2,3,4"
	 * 
	 * @return FieldValue
	 */
    public String getByValues(T model,String FieldName) {
    	try {
        	Field f= clazz.getDeclaredField(FieldName);
        	String ids = "";
        	f.setAccessible(true);
        	List<T> list = myMapper.select(model);		
        	for( int i = 0 ; i < list.size() ; i++) {			
				if (i == list.size() - 1) {
					ids = ids + f.get(list.get(i));
				} else {
					ids = ids + f.get(list.get(i)) + ",";
				}	    		
        	}   		
        	return ids;
		} catch (ReflectiveOperationException e) {
			throw new ServiceException(e.getMessage(), e);
		}

    }
    
	/**
	 * 通过ids条件查找，主键字符串如"1,2,3,4"
	 * 
	 * @return List<T>
	 */
    public List<T> selectByIds(String ids) {
    	return myMapper.selectByIds(ids);
    }
    
	/**
	 * 按主键是否存在，判断保存或更新
	 * 
	 * @return JSON
	 */
    public JSONObject saveAndUpdate (T model) { 
    	Field[] fields = clazz.getDeclaredFields();
    	Field f = fields[0];
    	String id = f.getName();
    	f.setAccessible(true);
    	try {
    		if(f.get(model) != null) {
    			myMapper.updateByPrimaryKeySelective(model);
    		} else {
    			myMapper.insertSelective(model);
    		}
    		jsonObject = new JSONObject();
    		jsonObject.put(id, f.get(model));
    		return jsonObject;   		   		
		} catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
		}  	
    }
    

}
