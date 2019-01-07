package tk.mybatis.springboot.util;

import java.text.NumberFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelImportUtils {

	// @描述：是否是2003的excel，返回true是2003
	public static boolean isExcel2003(String fileName) {
		return fileName.matches("^.+\\.(?i)(xls)$");
	}

	// @描述：是否是2007的excel，返回true是2007
	public static boolean isExcel2007(String fileName) {
		return fileName.matches("^.+\\.(?i)(xlsx)$");
	}

	/**
	 * 验证EXCEL文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean validateExcel(String fileName) {
		if (fileName == null || !(isExcel2003(fileName) || isExcel2007(fileName))) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * 表格字段类型转换
	 * @param cell
	 * @return String
	 */
	public static String cellTypeString(Cell cell) {
		NumberFormat ds = NumberFormat.getInstance();
        String values = "";
        switch (cell.getCellType()) {	                    	
            case HSSFCell.CELL_TYPE_STRING:
            	values = cell.getRichStringCellValue().getString().trim();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:	                        	
            	values = ds.format(cell.getNumericCellValue()).replace(",", "");
                break;
            default:
            	values = cell.getRichStringCellValue().getString().trim();
              }  
        return values;
	}

}
