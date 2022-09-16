package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public String filePath;
	public FileInputStream fis = null;
	public FileOutputStream fos = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;

	public ExcelReader(String filePath) {
		this.filePath = filePath;
		try {
			fis = new FileInputStream(this.filePath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			return sheet.getLastRowNum() + 1;
		}

	}

	public int getColumnCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (row == null) {
				return 0;
			}
			return row.getLastCellNum();
		}

	}

	public ArrayList<Hashtable<String, String>> getDataHashTableFormat(String sheetName, int rowCount) {
		ArrayList<Hashtable<String, String>> excelData = new ArrayList<Hashtable<String, String>>();
		ArrayList<String> keyList = new ArrayList<String>();
		sheet = workbook.getSheet(sheetName);
		XSSFRow firstRow = sheet.getRow(0);

		for (int i = 0; i < rowCount; i++) {
			if (i == 0) {
				for (Cell key : firstRow) {
					keyList.add(key.toString());
				}

			} else {
				row = sheet.getRow(i);
				Hashtable<String, String> data = new Hashtable<String, String>();
				for (int j = 0; j < keyList.size(); j++) {
					data.put(keyList.get(j), row.getCell(j).toString());
				}
				excelData.add(data);
			}

		}
		return excelData;

	}

	public static void main(String[] args) throws IOException {

	}

}
