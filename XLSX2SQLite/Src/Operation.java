package sourcecode;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Operation {
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	private static final String Class_Name = "org.sqlite.JDBC";
	private static final int bitchSize = 3000;
	
	public static List<String[]> readExcelFile(String fileName, String tableName)
	 {
		 List<String[]> content= new ArrayList<String[]>();
		 try {
			 File excelFile = new File(fileName);
			 FileInputStream in = new FileInputStream(excelFile);
			 checkExcelVaild(excelFile);
			 Workbook workbook = getWorkbook(in, excelFile);
			 
			 Sheet sheet = null;
			 if(tableName.length() != 0) {
				 sheet = workbook.getSheet(tableName);
				 if(sheet == null) {
					 throw new Exception("该Excel文件中无此页");
				 }
			 }else {
				 sheet = workbook.getSheetAt(0);
			 }
			 if(sheet.getLastRowNum() < 2) {
				 throw new Exception("Excel文件不符合标准");
			 }else {
				 int firstRowNum = sheet.getFirstRowNum();
				 int lastRowNum = sheet.getLastRowNum();
				 Row attrTag = sheet.getRow(0);
				 Row typetag = sheet.getRow(1);
				 int firstCellNum = attrTag.getFirstCellNum();
				 int lastCellNum = attrTag.getLastCellNum();
				 String [] attr = new String[attrTag.getPhysicalNumberOfCells()];
				 String [] type = new String[attrTag.getPhysicalNumberOfCells()];
				 for(int i = firstCellNum; i < lastCellNum; i++) {
					  Cell attrCell = attrTag.getCell(i);
					  attr[i] = getCellValue(attrCell);
					  Cell typeCell = typetag.getCell(i);
					  switch(typeCell.getCellType()) {
					  case NUMERIC:
						  type[i] = "NUMERIC";
						  break;
					  default:
						  type[i] = "CHAR";
					  }
				 }
				 content.add(attr);
				 content.add(type);
				 for(int i = firstRowNum + 1; i <= lastRowNum; i++) {
					 Row row = sheet.getRow(i);
					 String [] record = new String[attrTag.getPhysicalNumberOfCells()];
					 for(int k =  firstCellNum; k < lastCellNum; k++) {
						 Cell cell = row.getCell(k);
						 record[k] = getCellValue(cell);
					 }
					 content.add(record);
				 }
			 }
		 }catch(Exception e) {
			 System.out.println(e.getMessage());
			 e.printStackTrace();
		 }
		 return content;
	 }
	 
	public static void copeTable(List<String []> attr, String tableName, String db_url) throws Exception {
		String [] type;
		if(attr.size() < 3) {
			throw new Exception("Excel数据读取错误");
		}
		else {
			int column = attr.get(0).length;
			type = new String[column];
			for(int i = 0; i < column; i++) {
				if(attr.get(1)[i].equals("NUMERIC")) {
					if(attr.get(2)[i].contains(".")) {
						type[i] = "REAL";
					}else {
						type[i] = "INT";
					}
				}
				else if(attr.get(1)[i].equals("CHAR")) {
					int length = 0;
					for(int k = 2; k < attr.size(); k++) {
						if(attr.get(k)[i].length() > length) {
							length = attr.get(k)[i].length();
						}
					}
					String tmp = "CHAR(" + length + ")";
					type[i] = tmp;
				}
			}
		}
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		try {
			String db = "jdbc:sqlite:" + db_url;
			conn = createConnection(db);
			stmt = conn.createStatement();
			StringBuffer preTable = new StringBuffer();
			StringBuffer preInsert = new StringBuffer();
			String [] tableAttr = attr.get(0);
			for(int i = 0; i < tableAttr.length - 1; i++) {
				preTable.append(tableAttr[i] + "	" + type[i] + ", ");
				preInsert.append(tableAttr[i] + ", ");
			}
			preTable.append(tableAttr[tableAttr.length - 1] + "	" + type[tableAttr.length - 1]);
			preInsert.append(tableAttr[tableAttr.length - 1] + ") " + "values (");
			for(int j = 0; j < tableAttr.length; j++) {
				preInsert.append("?, ");
			}
			preInsert.append("?");
			String table = preTable.toString();
			String insert = preInsert.toString();
			String deletesql = "DROP TABLE IF EXISTS " + tableName + ";";
			String createsql = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
						 "(ID INT PRIMARY KEY	NOT NULL," +
						 table +");";
			stmt.executeUpdate(deletesql);
			stmt.executeUpdate(createsql);
			
			conn.setAutoCommit(false);
			String insertsql = "INSERT INTO " + tableName + "(ID, " + insert + ")";
			ps = conn.prepareStatement(insertsql);
			int cnt = 1000;
			for(int i = 2; i < attr.size(); i++) {
				ps.setInt(1, i - 1);
				for(int j = 0; j < attr.get(i).length; j++) {
					if(type[j].equals("INT")) {
						ps.setInt(j + 2, Integer.parseInt(attr.get(i)[j]));	
					}else if(type[j].equals("REAL")) {
						ps.setFloat(j + 2, Float.parseFloat(attr.get(i)[j]));
					}else {
						ps.setString(j + 2, attr.get(i)[j]);
					}
				}
				ps.addBatch();
				if(++cnt % bitchSize == 0) {
					ps.executeBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
			ps.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Insert Successfully");
		//System.out.println("Table Structure");
		for(int i = 0; i < attr.get(0).length; i++) {
			System.out.printf("%-20s %-20s\n", attr.get(0)[i], type[i]);
		}
		System.out.printf("%-20s %-20s\n","The number of Line is:", (attr.size() - 2));
	}

	private static Workbook getWorkbook(InputStream in, File file) throws IOException{
		 Workbook wb = null;
		 if(file.getName().endsWith(EXCEL_XLS)) {
			 wb = new HSSFWorkbook(in);
		 }
		 else if(file.getName().endsWith(EXCEL_XLSX)) {
			 wb = new XSSFWorkbook(in);
		 }
		 return wb;
	 }
	 
	private static void checkExcelVaild(File file) throws Exception{
		 if(!file.exists()) {
			 throw new Exception("文件不存在");
		 }
		 if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
			 throw new Exception("文件不是Excel");
		 }
	 }
	 
	private static String getCellValue(Cell cell) {
		 String cellValue = "";
		 if(cell == null) {
			 return cellValue;
		 }
		 if(cell.getCellType() == CellType.NUMERIC) {
			 cell.setCellType(CellType.STRING);
		 }
		 switch (cell.getCellType()) {
		 case STRING:
			 cellValue = String.valueOf(cell.getStringCellValue());
			 break;
		 case NUMERIC:
			 cellValue = String.valueOf(cell.getNumericCellValue());
			 break;
		 case BOOLEAN:
			 cellValue = String.valueOf(cell.getBooleanCellValue());
			 break;
		 case FORMULA:
			 cellValue = String.valueOf(cell.getCellFormula());
			 break;
		 case BLANK:
			 cellValue = "";
			 break;
		 case ERROR:
			 cellValue = "Illegal";
			 break;
		default:
			cellValue = "Unknown";
			break;
			 
		 }
		 return cellValue;
	 }
	 
	public static Connection createConnection(String db) throws SQLException, ClassNotFoundException{
		 Class.forName(Class_Name);
		 return DriverManager.getConnection(db);
	 }
}
