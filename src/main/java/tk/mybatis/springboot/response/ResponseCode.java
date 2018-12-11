package tk.mybatis.springboot.response;
/**
 * @author yjgithub
 * @create 2018/05/28 15:38
 * @desc 响应状态码及信息
 **/ 
public enum ResponseCode {
	
	OPERATION_SUCCESS(200, "操作成功"), 
	OPERATION_ERROR(400, "操作失败");
	
	ResponseCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private int code;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
