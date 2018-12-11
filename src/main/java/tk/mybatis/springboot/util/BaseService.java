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
	    // ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
	    clazz = (Class<T>) ReflectionUtils.getSuperClassGenericParameterizedType(getClass());
	    System.out.println("clazz:::" + ReflectionUtils.getSuperClassGenericParameterizedType(getClass()));
	 }

	// 通过主键查找，返回model
    public T getById(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }
    
    // 获取全部，并分页
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
   
    
    // 通过id删除
    public int deleteById(Long id) {
    	return myMapper.deleteByPrimaryKey(id);
    }
    
    // 批量ids删除
    public int deleteByIds(String ids) {
    	return myMapper.deleteByIds(ids);
    }
    
    // 返回0表示失败
    public int save(T model) {
        return myMapper.insertSelective(model);
    }
    
    // 返回0表示失败
    public int saves(List<T> models) {
        return myMapper.insertList(models);
    }
    
    
    public int updateById(T model) {
        return myMapper.updateByPrimaryKeySelective(model);
    }
    
    // 返回一个值，多个异常
    public T selectOne(T model) {
    	return myMapper.selectOne(model);
    }
    
    // 返回ids
    public String select(T model) {
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
