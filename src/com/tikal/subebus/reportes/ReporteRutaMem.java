package com.tikal.subebus.reportes;


	import java.text.SimpleDateFormat;
	import java.util.List;

	import org.apache.poi.hssf.usermodel.HSSFCell;
	import org.apache.poi.hssf.usermodel.HSSFCellStyle;
	import org.apache.poi.hssf.usermodel.HSSFRow;
	import org.apache.poi.hssf.usermodel.HSSFSheet;
	import org.apache.poi.hssf.usermodel.HSSFWorkbook;
	import org.apache.poi.hssf.util.HSSFColor;
	import org.apache.poi.ss.usermodel.CellStyle;
	import org.apache.poi.ss.usermodel.Font;
	import org.apache.poi.ss.usermodel.Row;

import com.tikal.subebus.modelo.entity.RutaMem;



	public class ReporteRutaMem {
		private List<RutaMem> rms;

//		public List<LuminariaVO> getVentas() {
//			return lumis;
//		}
	//
//		public void setVentas(List<LuminariaVO> lumis) {
//			this.lumis = lumis;
//		}
		
		public HSSFWorkbook getReporte(List<RutaMem> rms) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, "Reporte de uso de membresias");

			
			System.out.println("listaaaaa:"+rms);
			String[] headers = new String[] { "Ruta", "Chofer","Membresia","Duración","Nombre del Cliente", "Fecha de Uso"};
			Integer[] wd =                   {256*20,   256*20 , 256*12,     256*20, 256*30, 256*25};   
			CellStyle headerStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerStyle.setFont(font);
//			 short bgColorIndex = gotDrawing
//	                 ?HSSFColor.LIGHT_GREEN.index //42
//	                 :HSSFColor.RED.index; //10
			 headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			 headerStyle.setFillForegroundColor(HSSFColor.AQUA.index);
			// headerStyle.setAlignment();

	        HSSFRow headerRow = sheet.createRow(0);
	        for (int i = 0; i <6; ++i) {
	            String header = headers[i];
	            HSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	            
	            sheet.setColumnWidth(i,wd[i]);
	        }
	        double totalCaja=0;
	        for(int i=0; i<rms.size();i++){
	        	HSSFRow dataRow = sheet.createRow(i + 1);
	        	RutaMem rm= rms.get(i);
//	        	List<Detalle> ds= v.getDetalles();
//	        	if(i==3){
//		        	for(Detalle d:ds){
//		        		v.llenarRenglon(dataRow);
//		        	}
//	        	}
//	         	
	        	rm.llenarRenglon(dataRow);
//	        	if(v.getFacturado())!=null){
//	        		if(v.getFacturado()).compareToIgnoreCase("Efectivo")==0){
//	        			//totalCaja+=v.getImporte());
//	        		}
//	        	}
	        //	totalCaja=totalCaja+v.getImporte();
	        	
	        }
//	        HSSFRow dataRow = sheet.createRow(lumis.size()+3);
//	        HSSFCell cellT = dataRow.createCell(6);
//	        cellT.setCellValue("Total en Caja");
//	        cellT.setCellStyle(headerStyle);
//	       // dataRow.createCell(1).setCellValue(totalCaja);
//	        HSSFCell cell = dataRow.createCell(7);
//	        cell.setCellValue(totalCaja);
//	        cell.setCellStyle(headerStyle);
			return workbook;
		}
		
	}
