package tk.mybatis.springboot.response;

/**
 * @author yjgithub
 * @create 2018/5/28 11:48
 * @Description: 业务响应实体
 */
public class ResObject {

	// 响应业务状态码
	private Integer code;

	// 响应信息
	private String msg;

	// 响应数据
	private Object data;

	public ResObject(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResObject(Integer code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}
	
	public ResObject(Integer code, Object data, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ResObject build(Integer code, String msg) {
		return new ResObject(code, msg);
	}
	
	public static ResObject build(Integer code, Object data) {
		return new ResObject(code,data);
	}
	
	public static ResObject build(Integer code, Object data, String msg) {
		return new ResObject(code,data, msg);
	}
	
	

	public static ResObject ok(Object data) {
		return new ResObject(data);
	}

	public static ResObject ok() {
		return new ResObject(null);
	}

	public ResObject() {

	}

	public ResObject(Object data) {
		this.code = ResponseCode.OPERATION_SUCCESS.getCode();
		this.msg = ResponseCode.OPERATION_SUCCESS.getMsg();
		this.data = data;
	}


	@Override
	public String toString() {
		return "{" + 
				"code=" + code + 
				", data='" + data + '\'' + 
				", msg=" + msg + '}';
	}
}
