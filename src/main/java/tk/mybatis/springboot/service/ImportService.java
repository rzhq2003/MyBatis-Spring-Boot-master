package tk.mybatis.springboot.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import tk.mybatis.springboot.util.ExcelImportUtils;

@Service
public class ImportService {

	public  List<Map<String, Object>> getBankListByExcel(InputStream is, String fileName) throws IOException {

		// 根据版本选择创建Workbook的方式
		Workbook workbook = null;
		Sheet sheet = null;
        Row row = null;

		// 根据文件名判断文件是2003版本还是2007版本
		if (ExcelImportUtils.isExcel2007(fileName)) {
			workbook = new XSSFWorkbook(is);
		} else {
			workbook = new HSSFWorkbook(is);
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// workbook.getNumberOfSheets(); 表示几个表格,目前只取sheet1
		for (int i = 0; i < 1; i++) {
            sheet = workbook.getSheetAt(i);
            if (sheet == null) { continue; }            
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) { continue; }
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                	String keys = ExcelImportUtils.cellTypeString(sheet.getRow(0).getCell(y));                  
                    String values = ExcelImportUtils.cellTypeString(row.getCell(y));                       
                    map.put(keys, values);	            
                }
                list.add(map);
            }            
		} 
		workbook.close();					
		return list;		
	}



}
