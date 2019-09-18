package com.tikal.subebus.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.googlecode.objectify.annotation.Entity;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

 
/** 
 * Utility class, where we will create methods for training read and write excel files,
 * with <a href="https://poi.apache.org/">Apache POI</a>, we use 
 * <a href="https://poi.apache.org/spreadsheet/">POI-HSSF and POI-XSSF - Java API To Access Microsoft</a>
 * HSSF is the POI Project's pure Java implementation of the Excel '97(-2007) file.
 * 
 * Clase de utilidades, donde crearemos métodos
 * para el aprendizaje de la lectura y escritura de ficheros excel con 
 * <a href="https://poi.apache.org/">Apache POI</a>, usaremos
 * <a href="https://poi.apache.org/spreadsheet/">POI-HSSF and POI-XSSF - Java API To Access Microsoft</a>
 * HSSF es el proyecto POI de implementación tota333l en Java para ficheros Excel '97(-2007).
 *
 * @author Xules You can follow me on my website http://www.codigoxules.org/en
 * Puedes seguirme en mi <span id="IL_AD7" class="IL_AD">web</span> http://www.codigoxules.org).
 */


public class ReadXls {

	 
	    /**
	     * Explanation of the method by which we read the excel file we pass as
	     * parameter if exists, and where we copy its content in a new excel spreadsheet 
	     * is also passed as a parameter.
	     * Método con el que leemos el fichero excel que pasamos como
	     * parámetro si existe y donde copiamos su contenido en una nueva hoja excel que
	     * también se pasa como parámetro.
	     * @param excelFile <code>String</code> 
	     *      excel File we are going to read. 
	     *      Fichero excel que vamos a leer. 
	     * @param excelNewFile <code>String</code> 
	     *      excel File we are going to write. 
	     *      Fichero excel en el que vamos a escribir. 
	     */
	    public void readWriteExcelFile(File excelNewFile){
	        InputStream excelStream = null;
	        OutputStream excelNewOutputStream = null;
	        File excelFile = new File("C:/Escritorio/CPdescarga.xls");	
	        try {            
	            excelStream = new FileInputStream(excelFile);
	            excelNewOutputStream = new FileOutputStream(excelNewFile);
	            // High level representation of a workbook.
	            // Representación del más alto nivel de la hoja excel.
	            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelStream);
	            HSSFWorkbook hssfWorkbookNew = new HSSFWorkbook();
	            // We chose the sheet is passed as parameter. 
	            // Elegimos la hoja que se pasa por parámetro.
	            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
	            // We create the new sheet we are going to use.
	            // Creamos la hoja nueva que vamos a utilizar.
	            HSSFSheet hssfSheetNew = hssfWorkbookNew.createSheet("Copy-Copia");
	            // An object that allows us to read a row of the excel sheet, and extract from it the cell contents.
	            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
	            HSSFRow hssfRow;
	            HSSFRow hssfRowNew;
	            // Initialize the object to read the value of the cell 
	            // Inicializo el objeto que leerá el valor de la celda
	            HSSFCell cellNew;            
	            // I get the number of rows occupied on the sheet
	            // Obtengo el número de filas ocupadas en la hoja
	            int rows = hssfSheet.getLastRowNum();  
	            String cellValue; 
	            
	            // For this example we'll loop through the rows getting all the cells to copy them in the new sheet.
	            // Para este ejemplo vamos a recorrer todas las filas para obtener todas las celdas y copiarlas en la nueva hoja.
	            for (int r = 0; r < rows; r++) { hssfRow = hssfSheet.getRow(r);
	            	if (hssfRow == null){ break; }
	            	else{ System.out.print("Row: " + r + " -> ");
	                    // Creamos la columna en la nueva excel
	                    hssfRowNew = hssfSheetNew.createRow(r + 10);   
	                    for (int c = 0; c < hssfRow.getLastCellNum(); c++) {
	                        /* 
	                            We have those cell types (tenemos estos tipos de celda): 
	                                CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
	                        */
	                        cellValue = hssfRow.getCell(c) == null?"":
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_STRING)?hssfRow.getCell(c).getStringCellValue():
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_NUMERIC)?"" + hssfRow.getCell(c).getNumericCellValue():
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BOOLEAN)?"" + hssfRow.getCell(c).getBooleanCellValue():
	                                //(hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BLANK)?"BLANK":
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_FORMULA)?"FORMULA":
	                                (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_ERROR)?"ERROR":"";                                
	                        System.out.print("[Column " + c + ": " + cellValue + "] ");                        
	                        cellNew = hssfRowNew.createCell(c);
	                        cellNew.setCellType(HSSFCell.CELL_TYPE_STRING);
	                        cellNew.setCellValue(cellValue);        
	                    }
	                    System.out.println();
	                }
	            }                   
	            hssfWorkbookNew.write(excelNewOutputStream);
	            excelNewOutputStream.close();
	            System.out.println("Your excel file has been generated!(¡Se ha generado tu hoja excel!");
	        } catch (FileNotFoundException fileNotFoundException) {
	            System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
	        } catch (IOException ex) {
	            System.out.println("Error in file procesing (Error al procesar el fichero): " + ex);
	        } finally {
	            try {
	                excelStream.close();
	            } catch (IOException ex) {
	                System.out.println("Error in file processing after close it (Error al procesar el fichero después de cerrarlo): " + ex);
	            }
	        }
	    }    
	     
	    
	  
	    	public void FileCopy(String sourceFile, String destinationFile) {
	    		System.out.println("Desde: " + sourceFile);
	    		System.out.println("Hacia: " + destinationFile);

	    		try {
	    			File inFile = new File(sourceFile);
	    			File outFile = new File(destinationFile);

	    			FileInputStream in = new FileInputStream(inFile);
	    			FileOutputStream out = new FileOutputStream(outFile);
	    	//		PdfWriter.getInstance(document, file);


	    			int c;
	    			while( (c = in.read() ) != -1)
	    				out.write(c);

	    			in.close();
	    			out.close();
	    		} catch(IOException e) {
	    			System.err.println("Hubo un error de entrada/salida!!!");
	    		}
	    	}
	    
	    	
	    	public void SendInfo( String destinationFile) {
	    	
	    	}
	    	
	    	// @SuppressWarnings("deprecation")
//			public static void WriteXls(OrdenXlsVo ox) throws IOException{
//				int renglon=0;
//			  	int columna=0;
//				System.out.println("ya esta escribiendo..........:");
//	    		 	FileInputStream file = new FileInputStream(new File(ox.getNombreArchivo()));
//	    		 	System.out.println("1111111");
//	    		 	HSSFWorkbook workbook = new HSSFWorkbook(file);
//	    		 	System.out.println("222222");
//	    		  	HSSFSheet sheet = workbook.getSheetAt(0);
//	    		  	System.out.println("nombre de hoja:"+ 	sheet.getSheetName());
//	    		  	Iterator<Row> rowIterator = sheet.iterator();
//	    		 	Row row;
//	    		  	while (rowIterator.hasNext()){
//	    		  		row = rowIterator.next();	
//	    		  		 Iterator<Cell> cellIterator = row.cellIterator();
//  	    		 	     renglon = renglon +1;
//
//	    		  	    while (cellIterator.hasNext()){
//	    		  	    	columna=columna+1;
//	    		  	    	Cell celda;
//	    		  	    	celda = cellIterator.next();
////	    		 	 		// Dependiendo del formato de la celda el valor se debe mostrar como String, Fecha, boolean, entero...
//	    		 	 		switch(celda.getCellType()) {
//		    		 	 		
//		    		 	 		case Cell.CELL_TYPE_STRING:
//		    		 	 		    System.out.println(celda.getStringCellValue());
////		    		 	 		    if (celda.getStringCellValue().equals("fecha")){
////		    		 	 		    	celda.setCellType(Cell.CELL_TYPE_STRING);
////		    		 	 		    	celda.setCellValue(ox.getFechaOrden());
////		    		 	 		    }
////		    		 	 		if (celda.getStringCellValue().equals("empresa")) celda.setCellValue(ox.getNombreEmpresa());
////		    		 	 		if (celda.getStringCellValue().equals("numero de orden")) celda.setCellValue(ox.getFolioOrden());
////		    		 	 		if (celda.getStringCellValue().equals("marca")) celda.setCellValue(ox.getMarcaAeronave());
////		    		 	 		if (celda.getStringCellValue().equals("modelo")) celda.setCellValue(ox.getModeloAeronave());
////		    		 	 		if (celda.getStringCellValue().equals("numero de serie")) celda.setCellValue(ox.getNumeroSerie());
////		    		 	 		if (celda.getStringCellValue().equals("matricula")) celda.setCellValue(ox.getMatricula());
////		    		 	 		if (celda.getStringCellValue().equals("planeador")) celda.setCellValue(ox.getPlaneador());
////		    		 	 		if (celda.getStringCellValue().equals("motor1")) celda.setCellValue(ox.getMotor1());
////		    		 	 		if (celda.getStringCellValue().equals("motor2")) celda.setCellValue(ox.getMotor2());
////		    		 	 		if (celda.getStringCellValue().equals("marca y modelo motores")) celda.setCellValue(ox.getMarcas());
//				    		 	 		String opc=celda.getStringCellValue();
//				    		 	 		switch( opc)  {
//						    		 	 		case "fecha":celda.setCellValue(ox.getFechaOrden());break;				    		 	 		  
//						    		 	 		case "empresa":celda.setCellValue(ox.getNombreEmpresa());break;
//						    		 	 		case "numero de orden":celda.setCellValue(ox.getFolioOrden());break;
//						    		 	 		case "marca":celda.setCellValue(ox.getMarcaAeronave());break;
//						    		 	 		case "modelo":celda.setCellValue(ox.getModeloAeronave());break;
//						    		 	 		case "numero de serie":celda.setCellValue(ox.getNumeroSerie());break;
//						    		 	 		case "matricula":celda.setCellValue(ox.getMatricula());break;
//						    		 	 		case "planeador":celda.setCellValue(ox.getPlaneador());break;
//						    		 	 		case "motor1":celda.setCellValue(ox.getMotor1());break;
//						    		 	 		case "motor2":celda.setCellValue(ox.getMotor2());break;
//						    		 	 		case "marca y modelo motores":celda.setCellValue(ox.getMarcas());break;
//						    		 	 		
//						    		 	 		case "D1":System.out.println("ox:"+ox.getAccionesDiscrepancia().get(0)+"----");
//						    		 	 			celda.setCellValue(ox.getAccionesDiscrepancia().get(0));break;
//						    		 	 		case "D2":celda.setCellValue(ox.getAccionesDiscrepancia().get(1));break;
//						    		 	 		case "D3":celda.setCellValue(ox.getAccionesDiscrepancia().get(2));break;
//						    		 	 		case "D4":celda.setCellValue(ox.getAccionesDiscrepancia().get(3));break;
//						    		 	 		case "D5":celda.setCellValue(ox.getAccionesDiscrepancia().get(4));break;
//						    		 	 		case "D6":celda.setCellValue(ox.getAccionesDiscrepancia().get(5));break;
//						    		 	 		case "D7":celda.setCellValue(ox.getAccionesDiscrepancia().get(6));break;
//						    		 	 		case "D8":celda.setCellValue(ox.getAccionesDiscrepancia().get(7));break;
//						    		 	 		case "D9":celda.setCellValue(ox.getAccionesDiscrepancia().get(8));break;
//						    		 	 		case "D10":celda.setCellValue(ox.getAccionesDiscrepancia().get(9));break;
//						    		 	 		case "D11":celda.setCellValue(ox.getAccionesDiscrepancia().get(10));break;
//						    		 	 		case "D12":celda.setCellValue(ox.getAccionesDiscrepancia().get(11));break;
//						    		 	 		case "D13":celda.setCellValue(ox.getAccionesDiscrepancia().get(12));break;
//						    		 	 		case "D14":celda.setCellValue(ox.getAccionesDiscrepancia().get(13));break;
//						    		 	 		case "D15":celda.setCellValue(ox.getAccionesDiscrepancia().get(14));break;
//						    		 	 		case "D16":celda.setCellValue(ox.getAccionesDiscrepancia().get(15));break;
//						    		 	 		case "D17":celda.setCellValue(ox.getAccionesDiscrepancia().get(16));break;
//						    		 	 		case "D18":celda.setCellValue(ox.getAccionesDiscrepancia().get(17));break;
//						    		 	 		case "D19":celda.setCellValue(ox.getAccionesDiscrepancia().get(18));break;
//						    		 	 		case "D20":celda.setCellValue(ox.getAccionesDiscrepancia().get(19));break;
//						    		 	 		case "D21":celda.setCellValue(ox.getAccionesDiscrepancia().get(20));break;
//						    		 	 		case "D22":celda.setCellValue(ox.getAccionesDiscrepancia().get(21));break;
//						    		 	 		case "D23":celda.setCellValue(ox.getAccionesDiscrepancia().get(22));break;
//						    		 	 		case "D24":celda.setCellValue(ox.getAccionesDiscrepancia().get(23));break;
//						    		 	 		case "D25":celda.setCellValue(ox.getAccionesDiscrepancia().get(24));break;
//						    		 	 		case "D26":celda.setCellValue(ox.getAccionesDiscrepancia().get(25));break;
//						    		 	 		case "D27":celda.setCellValue(ox.getAccionesDiscrepancia().get(26));break;
//						    		 	 		case "D28":celda.setCellValue(ox.getAccionesDiscrepancia().get(27));break;
//						    		 	 		case "D29":celda.setCellValue(ox.getAccionesDiscrepancia().get(28));break;
//						    		 	 		case "D30":celda.setCellValue(ox.getAccionesDiscrepancia().get(29));break;
//						    		 	 		case "D31":celda.setCellValue(ox.getAccionesDiscrepancia().get(30));break;
//						    		 	 		case "D32":celda.setCellValue(ox.getAccionesDiscrepancia().get(31));break;
//						    		 	 }
//		    		 	 	
//		    		 	 		
//		    		 	 		
//		    		 	 		////////ghacevr eto con todas las ds
//		    		 	 		//ox.getAccionesDiscrepancia().get(1);
////		    		 	 		System.out.println("tamaño lista:"+ox.getAccionesDiscrepancia().size());
////		    		 	 			for(int i=0; i<32; i++) {	    	 
////		    		 	 				System.out.println("nombre columna: D"+(i+1));
////		    		 	 				//System.out.println("tamaño de la lista: ");
////		    		 	 			//	System.out.println("accion:"+ox.getAccionesDiscrepancia().get(i));
////		    		 	 				if (celda.getStringCellValue().equals("D"+(i+1))) {
////		    		 	 					System.out.println("i="+i);
////		    		 	 					if (ox.getAccionesDiscrepancia().size() <=i) 
////		    		 	 						celda.setCellValue(ox.getAccionesDiscrepancia().get(i));
////		    		 	 					else
////		    		 	 						celda.setCellValue("");
////		    		 	 				}
////		    		 	 			}	
//		    		 	 			    
//		    		 	 		    
//		    		 	 		    
//		    		 	 		    break;
//		    		 	 		
//	    		 	 		}
//	    		 	    }
//	    		 	}
//	    		 		// cerramos el libro excel
//	    		  	
//	    		 	file.close();
//	    		 		
//	    	            FileOutputStream fileOut = new FileOutputStream(ox.getNombreArchivo()); //Doy la ruta y el nombre del archivo nuevo que se generará
//	    	            workbook.write(fileOut); //Escribo el nuevo archivo
//	    	            fileOut.close(); //Cierro el archivo
//	    		}
//			
//			public static void GeneratePDF(File archivoXls) {
//
//				
//					try {
//						OutputStream file = new FileOutputStream(new File("C:/Users/Lenovo/Desktop/OTs/nueva_orden.pdf"));
//						InputStream xls = new FileInputStream("C:/Users/Lenovo/Desktop/OTs/nueva_orden.xls");
//						PdfReader pdfReader = new PdfReader(xls);
//						
//						Document document = new Document();
//						PdfWriter writer = PdfWriter.getInstance(document, file);
//						//FileOutputStream fileOut= new FileOutputStream(archivoXls);
//						PdfWriter.getInstance(document, file);
//
//						document.open();
//						document.newPage();
//					//	page = writer.getImportedPage(pdfReader,
//						
//						document.close();
//						file.close();
//
//					} catch (Exception e) {
//
//						e.printStackTrace();
//					}
//				
//			}
	}
