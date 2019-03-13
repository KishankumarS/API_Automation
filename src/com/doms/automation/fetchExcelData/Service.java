package com.doms.automation.fetchExcelData;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.utils.NikeDOMSConstants;

public class Service {

	List<String> envList = new ArrayList<>();

	public List<String> getLoadedTestCaseData(int i) {

		List<String> names = new ArrayList<String>();
		// testCaseDataVOList = new ArrayList<TestCaseDataVO>();
		FileInputStream fis = null;
		try {

			
    		System.setProperty("user.dir", NikeDOMSConstants.PATH);
			
			String mappingsheetPath = System.getProperty("user.dir") + "\\resources\\testCases\\"
					+ "Automation_Test_Suite.xlsx";

			fis = new FileInputStream(mappingsheetPath);

			/*
			 * XSSFWorkbook workbook = new XSSFWorkbook(fis); XSSFSheet
			 * dataSheet = workbook.getSheetAt(0);
			 */

			Workbook workbook = WorkbookFactory.create(fis);

			// Fetch template xmls from testcases sheet
			Sheet dataSheet = workbook.getSheetAt(i);

			/*
			 * Iterating each row by row
			 */
			for (int j = 1; j < dataSheet.getPhysicalNumberOfRows(); j++) {
				Row tstRow = dataSheet.getRow(j);
				String testCase = tstRow.getCell(0).getStringCellValue();
				TestCaseDataVO caseDataVO = new TestCaseDataVO();
				caseDataVO.setTestCaseName(testCase);
				// testCaseDataVOList.add(j, caseDataVO);
				names.add(testCase);

			}

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return names; // testCaseDataVOList;

	}

	public List<String> getEnvNames() {

		List<String> names = new ArrayList<String>();
		FileInputStream fis = null;
		try {
			System.setProperty("user.dir", NikeDOMSConstants.PATH);
			String mappingsheetPath = System.getProperty("user.dir") + "\\resources\\db\\" + "EnvConfig.xlsx";
			fis = new FileInputStream(mappingsheetPath);

			/*
			 * XSSFWorkbook workbook = new XSSFWorkbook(fis); XSSFSheet
			 * dataSheet = workbook.getSheetAt(0);
			 */

			Workbook workbook = WorkbookFactory.create(fis);

			// Fetch template xmls from testcases sheet
			Sheet dataSheet = workbook.getSheetAt(0);

			/*
			 * Iterating each row by row
			 */
			for (int j = 1; j < dataSheet.getPhysicalNumberOfRows(); j++) {
				Row tstRow = dataSheet.getRow(j);
				String testCase = tstRow.getCell(0).getStringCellValue();
				TestCaseDataVO caseDataVO = new TestCaseDataVO();
				caseDataVO.setTestCaseName(testCase);
				// testCaseDataVOList.add(j, caseDataVO);
				names.add(testCase);
			}

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return names; // testCaseDataVOList;

		// return envList;

	}

}