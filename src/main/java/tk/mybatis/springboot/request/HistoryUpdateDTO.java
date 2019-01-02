package tk.mybatis.springboot.request;





import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.springboot.model.HistoryItems;

@ApiModel(value = "HistoryUpdateDTO", description = "参数值对象")
public class HistoryUpdateDTO {
	
	@ApiModelProperty(name = "sheetid", value = "不能为空", example = "123456789123456789", position = 1, required = true)
	private String sheetid;

	
	@ApiModelProperty(position = 2, required = true)
    private List<HistoryItems> listItems; 
    
	public String getSheetid() {
		return sheetid;
	}

	public void setSheetid(String sheetid) {
		this.sheetid = sheetid;
	}

	public List<HistoryItems> getListItems() {
		return listItems;
	}

	public void setListItems(List<HistoryItems> listItems) {
		this.listItems = listItems;
	}


	
	
  
}
