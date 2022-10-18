package com.github.anthonywww.spiderman.reporters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.anthonywww.spiderman.IReport;
import com.github.anthonywww.spiderman.Report;
import com.github.anthonywww.spiderman.ReportType;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class DefaultReporter extends Report implements IReport {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultReporter.class);
	
	public DefaultReporter() {
		
	}
	
	public void saveAsFile(ReportType type, String path) {
		
		if (type != ReportType.EXCEL) {
			super.saveAsFile(type, path);
			return;
		}
		
		File file = new File(path);
		
		if (file.exists()) {
			logger.warn("Report {} already exists. The file will be over-written.", path);
			file.delete();
		}
		
		try {
			generateExcel(file);
			logger.debug("Report written!");
		} catch (IOException e) {
			logger.error("Error while writing report: {}", e.getMessage());
		}
		
	}
	
	private void generateExcel(File file) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		
		Sheet overviewSheet = workbook.createSheet("Crawler Overview");
		overviewSheet.setColumnWidth(0, 6000);
		overviewSheet.setColumnWidth(1, 4000);

		Row header = overviewSheet.createRow(0);
		
		CellStyle defaultStyle = workbook.createCellStyle();
		defaultStyle.setWrapText(true);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		headerStyle.setFillPattern(FillPatternType.FINE_DOTS);
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		font.setColor(IndexedColors.BLACK.getIndex());
		headerStyle.setFont(font);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Name");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("Age");
		headerCell.setCellStyle(headerStyle);
		
		//
		
		Row row = overviewSheet.createRow(2);
		Cell cell = row.createCell(0);
		cell.setCellValue("John Smith");
		cell.setCellStyle(defaultStyle);

		cell = row.createCell(1);
		cell.setCellValue(20);
		cell.setCellStyle(defaultStyle);
		
		//
		
		Sheet crawledPagesSheet = workbook.createSheet("Crawled Pages");
		
		crawledPagesSheet.setColumnWidth(0, 6000);
		crawledPagesSheet.setColumnWidth(1, 4000);

		Row cps = crawledPagesSheet.createRow(0);
		
		Cell idCPS = cps.createCell(0);
		idCPS.setCellValue("Id");
		idCPS.setCellStyle(headerStyle);
		
		Cell urlCPS = cps.createCell(1);
		urlCPS.setCellValue("URL");
		urlCPS.setCellStyle(headerStyle);
		
		
		FileOutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);
		workbook.close();
	}
	
}
