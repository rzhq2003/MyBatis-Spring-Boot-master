package tk.mybatis.springboot.request;





import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.springboot.model.HistoryItems;

@ApiModel(value = "HistoryAddDTO", description = "参数值对象")
public class HistoryAddDTO {
	
	@ApiModelProperty(name = "templateid", value = "不能为空", example = "1004", position = 1, required = true)
	private Long templateid;
	
	@ApiModelProperty(name = "hostid", value = "不能为空", example = "1006", position = 2, required = true)
	private Long hostid;
	
	@ApiModelProperty(position = 3, required = true)
    private List<HistoryItems> listItems; 
    
	public Long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Long templateid) {
		this.templateid = templateid;
	}

	public Long getHostid() {
		return hostid;
	}

	public void setHostid(Long hostid) {
		this.hostid = hostid;
	}

	public List<HistoryItems> getListItems() {
		return listItems;
	}

	public void setListItems(List<HistoryItems> listItems) {
		this.listItems = listItems;
	}


	
	
  
}
