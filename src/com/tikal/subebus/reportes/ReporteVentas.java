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

import com.tikal.subebus.modelo.entity.Venta;

	


	public class ReporteVentas {
		private List<Venta> ventas;

//		public List<LuminariaVO> getVentas() {
//			return lumis;
//		}
	//
//		public void setVentas(List<LuminariaVO> lumis) {
//			this.lumis = lumis;
//		}
		
		public HSSFWorkbook getReporte(List<Venta> ventas) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, "Reporte de Ventas");

			
			System.out.println("listaaaaa:"+ventas);
			String[] headers = new String[] { "Fecha Venta","Membresia","Tipo","Duracion", "Caducidad","Nombre","Edad", "Sexo","Telefono","Mail","Precio"};
			Integer[] wd =                   {256*25,       256*15,      256*15,256*15,     256*25,    256*20 , 256*5, 256*10, 256*20, 256*30, 256*10};   
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
	        HSSFCell cell = headerRow.createCell(0);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("REPORTE DE VENTAS POR PERIODO");
            HSSFCell cellz = headerRow.createCell(1);
            cellz.setCellStyle(headerStyle);
            		
            HSSFRow headerRow1 = sheet.createRow(3);
	        for (int i = 0; i <11; ++i) {
	            String header = headers[i];
	            HSSFCell cell1 = headerRow1.createCell(i);
	            cell1.setCellStyle(headerStyle);
	            cell1.setCellValue(header);
	            
	            sheet.setColumnWidth(i,wd[i]);
	        }
	        double totalCaja=0;
	        for(int i=0; i<ventas.size();i++){
	        	HSSFRow dataRow = sheet.createRow(i + 4);
	        	Venta v= ventas.get(i);
//	        	List<Detalle> ds= v.getDetalles();
//	        	if(i==3){
//		        	for(Detalle d:ds){
//		        		v.llenarRenglon(dataRow);
//		        	}
//	        	}
//	         	
	        	v.llenarRenglon(dataRow);
//	        	if(v.getFacturado())!=null){
//	        		if(v.getFacturado()).compareToIgnoreCase("Efectivo")==0){
//	        			//totalCaja+=v.getImporte());
//	        		}
//	        	}
	        	totalCaja=totalCaja+v.getPrecio();
	        	
	        }
	        HSSFRow dataRow = sheet.createRow(ventas.size()+6);
	        HSSFCell cellT = dataRow.createCell(9);
	        cellT.setCellValue("Total en Caja");
	        cellT.setCellStyle(headerStyle);
	       // dataRow.createCell(1).setCellValue(totalCaja);
	        HSSFCell cellt= dataRow.createCell(10);
	        cellt.setCellValue(totalCaja);
	        cellt.setCellStyle(headerStyle);
			return workbook;
		}
		
	}
