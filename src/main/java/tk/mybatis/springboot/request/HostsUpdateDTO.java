package tk.mybatis.springboot.request;




import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "HostsUpdateDTO", description = "主机对象")
public class HostsUpdateDTO {
	
    @ApiModelProperty(name = "hostid", value = "主机id", example = "1000", position = 1)
    private Long hostid;
    
    @ApiModelProperty(name = "host", value = "主机号", example = "XM0005", position = 2)
    private String host;
	
    @ApiModelProperty(name = "name", value = "主机名", example = "海天店", position = 3)
    private String name;

    @ApiModelProperty(name = "description", example = "无", position = 4)
    private String description;
    
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    private Integer status = 0;
    
    @ApiModelProperty(position = 5)
    private Integer enable;
    
    @ApiModelProperty(name = "groupids", position = 6, value = "主机组ids")
    private Long[] groupids;
    

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long[] getGroupids() {
		return groupids;
	}

	public void setGroupids(Long[] groupids) {
		this.groupids = groupids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public Long getHostid() {
		return hostid;
	}

	public void setHostid(Long hostid) {
		this.hostid = hostid;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}	
    
    


}
