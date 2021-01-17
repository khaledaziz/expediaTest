package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {
	public String[][] retrieveMyData(String filePath , String sheetName) throws IOException{
		File excel = new File(filePath);
		FileInputStream fileSt = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fileSt);
		XSSFSheet gotSheet = wb.getSheet(sheetName);
		int rowNum = gotSheet.getLastRowNum() ;
		int colNum = gotSheet.getRow(0).getLastCellNum();
		String data [][]= new String[rowNum ][colNum];
		
		for(int i = 0 ; i<rowNum ; i++){
			Row row = gotSheet.getRow(i + 1);
			for(int j = 0 ; j<colNum ; j++){
				Cell cell = row.getCell(j);
				DataFormatter dataFormatter = new DataFormatter();
				String value = dataFormatter.formatCellValue(cell);
				data [i][j] = value;
				fileSt.close();
			}
		}
		return data;
		
	}

}
