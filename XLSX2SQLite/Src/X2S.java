package sourcecode;

import java.util.List;

public class X2S {
	private static List<String[]> content;
	public static void main(String[] args) {
		if(args.length < 2) {
			try {
				throw new Exception("参数输入过少");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		String db_url = args[0];
		String excelFile = args[1];
		String sheetName = "";
		String tableName = "";
		if(args.length == 2) {
			tableName = excelFile.substring(0, excelFile.indexOf("."));
		}else if(args.length == 3) {
			sheetName = args[2];
			tableName = args[2];
		}else if(args.length == 4) {
			sheetName = args[2];
			tableName = args[3];
		}
		content = Operation.readExcelFile(excelFile, sheetName);
		try {
			System.out.println("TableName:	" + tableName);
			Operation.copeTable(content, tableName, db_url);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
