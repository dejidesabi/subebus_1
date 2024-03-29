package com.tikal.subebus.formatos.pdf;


	import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
	import com.itextpdf.text.DocumentException;
	import com.itextpdf.text.Element;
	import com.itextpdf.text.Font;
	//import com.itextpdf.text.FontFactory;
	import com.itextpdf.text.Image;
	import com.itextpdf.text.PageSize;
	//import com.itextpdf.text.PageSize;
	import com.itextpdf.text.Paragraph;
	import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
	import com.itextpdf.text.pdf.PdfPTable;
	import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;
import com.tikal.subebus.util.Util;

import java.io.*;
	import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
	import java.util.List;

	import javax.print.Doc;
	import javax.print.DocFlavor;
	import javax.print.DocPrintJob;
	import javax.print.PrintException;
	import javax.print.PrintService;
	import javax.print.PrintServiceLookup;
	import javax.print.SimpleDoc;
	import javax.servlet.ServletOutputStream;
	//import java.util.Date;
	//import java.util.List; 
	
	public class GeneraMembresias {
		private Document document;
		public GeneraMembresias(Lote l, List<Membresia> mems, OutputStream ops) throws MalformedURLException, IOException {
	    	 
	    	  
	    	try {
	    		int largo= 200;
	    		Rectangle envelope = new Rectangle(300, largo*mems.size());
	    	  //  Document document = new Document(envelope, 230f, 10f, 100f, 0f);
	    		Document document = new Document(envelope,0,0,0,0);  
	    	
//	    	 
//	    	 this.document = new Document();
//	 		this.document.setPageSize(PageSize.LETTER);
//	 		this.document.setMargins(30, 30, 25, 30);
	    	 
		        PdfWriter.getInstance(document,ops);
		        document.open();
		        String g="";
	    	    
	    	    Font f0 = new Font();
	      	  //  f1.setStyle(1);
	      	    f0.setSize(4);
	      	    f0.setColor(BaseColor.BLACK);
	    	    
	    	    
	    	    Font f1 = new Font();
	    	    f1.setStyle(1);
	    	    f1.setSize(18);
	    	    f1.setColor(BaseColor.BLACK);
	    	    
	    	    Font f2 = new Font();
	    	   // f2.setStyle(2);
	    	    f2.setSize(12);
	    	    f2.setColor(BaseColor.BLACK);
	    	    f2.setStyle(1);
	    	    
	    	    Font f3 = new Font();
	    	    f3.setStyle(1);
	    	    f3.setSize(8);
	    	    f3.setColor(BaseColor.BLACK);
	    	   
	    	    Font f4 = new Font();
	        	  //  f1.setStyle(1);
	        	    f4.setSize(6);
	        	    f4.setColor(BaseColor.BLACK);
	        	   
	        	    document.add(new Paragraph("\n"));
		            document.add(new Paragraph("\n"));
	            
	        	  
	            
	            
	         
	      System.out.println("cant:"+l.getCantidad());
	      
	     for (Membresia m: mems){
	      //  	 System.out.println("i:"+i);
	    	 
	    	  PdfPTable table = new PdfPTable(6);
	    
	    	//  document.add(new Paragraph("\n")); 
//	    	  Paragraph pe = new Paragraph("\n",f1);
//	    	  PdfPCell ce = new PdfPCell(pe);
//	          //  pe.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            ce.setColspan(3);
//	            ce.setRowspan(1);
//	            //c8.setBorder(Rectangle.NO_BORDER);
//	            table.addCell(ce);	
	    	  
	    		Image imgQRCode=null;
		        //	try{
		        	URL url=new URL(new String(Util.generate(new String(m.getCodogoQR()))));
		        	System.out.println("ruta:"+url);
						imgQRCode = Image.getInstance(new URL(new String(Util.generate(new String(m.getCodogoQR())))));
				//	}catch(IOException e){
				//		BarcodeQRCode barcodeQRCode = new BarcodeQRCode(new String(m.getCodogoQR()), 3000, 3000, null);
				//		imgQRCode= barcodeQRCode.getImage();
				//	}
						
		      //  	imgQRCode.scalePercent(150f);
		        	imgQRCode.scaleAbsolute(100,100);
		        //	imgQRCode.setAbsolutePosition(0f, 100f);
		        //	Chunk chunkQRCode = null;
		        	
		        	PdfPCell celdaQRCode = new PdfPCell(imgQRCode);
		    		
		    	//	chunkQRCode = new Chunk(imgQRCode, 7.0F, -75F);
		        	
		        
		    		celdaQRCode.setHorizontalAlignment(Element.ALIGN_CENTER);
		    		//celdaQRCode.
		    		celdaQRCode.setVerticalAlignment(80);
		    		celdaQRCode.setColspan(3);
		    		celdaQRCode.setRowspan(6);	
		    		celdaQRCode.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
		          
		    		
		    		
	        	   Image imagen = Image.getInstance("img/subebus.png");
		            imagen.scaleAbsolute(200, 35);      
		            PdfPCell c1 = new PdfPCell(imagen);
		            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		            c1.setVerticalAlignment(Element.ALIGN_CENTER);
		            c1.setColspan(6);
//		            c1.setRowspan(1);
		            c1.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		          //  c1.setBorder(Rectangle.TOP);
		            //c1.setBorder(Rectangle.LEFT);
		         //   c1.setBorder(Rectangle.RIGHT);
		            table.addCell(c1);
		            
		            
		            String leyenda="--";
		            switch(l.getDuracion()){
		            case "Dia":leyenda="PASE CONVENIENTE";
		            	break;
		            case "Semanal":leyenda="MEMBRESIA SEMANAL";
		            	break;
		            case "Mensual":leyenda="MEMBRESIA MENSUAL";
		            	break;
		            case "Semestral":leyenda="PASE DE CORTESIA";
		            	break;
		            
		            }
		            
		            Paragraph pa = new Paragraph(leyenda,f1);
		            PdfPCell ca = new PdfPCell(pa);
		            ca.setHorizontalAlignment(Element.ALIGN_CENTER);
		            ca.setColspan(6);
		            ca.setRowspan(1);
		           
		            ca.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
		            table.addCell(ca);	
		          //  table.addCell(celdaQRCode);
		            
		            Paragraph va = new Paragraph("\n",f1);
		            PdfPCell v = new PdfPCell(va);
		            v.setHorizontalAlignment(Element.ALIGN_CENTER);
		            v.setColspan(6);
		            v.setRowspan(1);
		            v.setBorder(Rectangle.LEFT);
		            v.setBorder(Rectangle.RIGHT);
		          //  table.addCell(v);	
		            
		            table.addCell(celdaQRCode);
	        	
	            Paragraph p8 = new Paragraph("Folio:",f3);
	            PdfPCell c8 = new PdfPCell(p8);
	            c8.setHorizontalAlignment(Element.ALIGN_CENTER);
	            c8.setColspan(3);
	            c8.setRowspan(1);
	            c8.setBorder(Rectangle.RIGHT);
	            table.addCell(c8);	
	            
	            
	            Paragraph pf = new Paragraph(m.getId().toString(),f1);
	            PdfPCell cf = new PdfPCell(pf);
	            cf.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cf.setColspan(3);
	            cf.setRowspan(1);
	            cf.setBorder(Rectangle.RIGHT);
	            table.addCell(cf);
	           // table.addCell(c6);
	            table.addCell(v);
	            
	            Paragraph pb = new Paragraph("+52 456 101 8313",f3);
	            PdfPCell cb = new PdfPCell(pb);
	            cb.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cb.setColspan(3);
	            cb.setRowspan(1);
	            cb.setBorder(Rectangle.RIGHT);
	            table.addCell(cb);	
	           // table.addCell(c6);
	            
	            Paragraph pc = new Paragraph("VALLE DE SANTIAGO, GTO.",f3);
	            PdfPCell cc = new PdfPCell(pc);
	            cc.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cc.setColspan(3);
	            cc.setRowspan(1);
	            cc.setBorder(Rectangle.RIGHT);
	            table.addCell(cc);	
	           
	            
	            Paragraph v1 = new Paragraph("\n",f1);
	            PdfPCell v11 = new PdfPCell(v1);
	            v11.setHorizontalAlignment(Element.ALIGN_CENTER);
	            v11.setColspan(6);
	            v11.setRowspan(1);
	            v11.setBorder( Rectangle.RIGHT | Rectangle.BOTTOM);
	            table.addCell(v11);	
	//
	          
	            document.add(table);
	           // document.add(new Paragraph("\n"));
	            //document.add(new Paragraph("\n"));
	            
	       
	            document.add(new Paragraph("\n")); 
	            document.add(new Paragraph("\n"));
	            //document.add(new Paragraph("otra mem"));       
	    }   
	                 
	                
	 
	                  
	     


	          	            
	         //   document.add(table2);
	            document.close();
	    	    System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");
	    	} catch (DocumentException documentException) {
	    	    System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
	    	}
	    }

	      
//	      public void GeneraComDisPdf(DetalleDiscrepanciaVo det, Document document) throws DocumentException {
//	          // Aquí introduciremos el código para crear el PDF.      	  
//	      
//	    	  	PdfPTable table2 = new PdfPTable(12);      
//	    	    Font fuente = new Font();
//		  	    fuente.setStyle(1);
//		  	    fuente.setSize(10);
//		  	    fuente.setStyle(Font.BOLD);
//		  	    Font f1 = new Font();
//		  	    f1.setStyle(2);
//		  	    f1.setSize(9);
//		  	    Font f2 = new Font();
//		  	    f2.setStyle(3);
//		  	    f2.setSize(8);
//	            Paragraph d = new Paragraph("Logo");
//	            PdfPCell c0 = new PdfPCell(d);
//	            c0.setHorizontalAlignment(Element.ALIGN_LEFT);
//	            c0.setColspan(12);
//	            table2.addCell(c0);
//	            Paragraph a = new Paragraph("CROSS AIR SERVICES, S.A. DE C.V.",f1);
//	            PdfPCell celdaFinal = new PdfPCell(a);
//	            celdaFinal.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            celdaFinal.setColspan(12);
//	            table2.addCell(celdaFinal);
//	            Paragraph b =new Paragraph("DISCREPANCY REPORT / REPORTE DE DISCREPANCIAS: \n",fuente);
//	            PdfPCell c2 =new PdfPCell(b);
//	            c2.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c2.setColspan(12);
//	            table2.addCell(c2);
//	          
//	            Paragraph p2=new Paragraph("CUSTOMER / CLIENTE:"+det.getNombreEmpresa() ,f1);
//	            PdfPCell c3 =new PdfPCell(p2);
//	            c3.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c3.setColspan(12);
//	            table2.addCell(c3);
//	            
//	            Paragraph p3=new Paragraph("MARK/MODEL - MARCA/MODELO:\n\n"+det.getModelo() ,f1);
//	            PdfPCell c4 =new PdfPCell(p3);
//	            c4.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c4.setColspan(3);
//	            table2.addCell(c4);
//	            
//	            Paragraph p4=new Paragraph("N/S:\n\n"+det.getNoSerie() ,f1);
//	            PdfPCell c5 =new PdfPCell(p4);
//	            c5.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c5.setColspan(3);
//	            table2.addCell(c5);
//	            
//	            Paragraph p5=new Paragraph("REG. / MATRICULA:\n\n"+det.getMatricula() ,f1);
//	            PdfPCell c6 =new PdfPCell(p5);
//	            c6.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c6.setColspan(3);
//	            table2.addCell(c6);
//	            
//	            Paragraph p6=new Paragraph("O.T. #:\n\n"+det.getMatricula() ,f1);
//	            PdfPCell c7 =new PdfPCell(p6);
//	            c7.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c7.setColspan(3);
//	            table2.addCell(c7);
//	            
//	            Paragraph p7=new Paragraph("DISCREPANCIA No:"+d.lastIndexOf(det) ,f1);
//	            PdfPCell c8 =new PdfPCell(p7);
//	            c8.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c8.setColspan(6);
//	            table2.addCell(c8);
//	            
//	            Paragraph p8=new Paragraph("DATE / FECHA:"+det.getFechaOrden(),f1);
//	            PdfPCell c9 =new PdfPCell(p8);
//	            c9.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c9.setColspan(6);
//	            table2.addCell(c9);
//	            
//	            Paragraph p9=new Paragraph("DISCREPANCY / DISCREPANCIA:",f1);
//	            PdfPCell c10 =new PdfPCell(p9);
//	            c10.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c10.setColspan(6);
//	            table2.addCell(c10);
//	            
//	            Paragraph p10=new Paragraph("CORRECT ACCTION / ACCION CORRECTIVA:",f1);
//	            PdfPCell c11 =new PdfPCell(p10);
//	            c11.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c11.setColspan(6);
//	            table2.addCell(c11);
//	            
//	            Paragraph p11=new Paragraph("\n"+det.getDescripcion()+"\n",fuente);
//	            PdfPCell c12 =new PdfPCell(p11);
//	            c12.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c12.setColspan(6);
//	            table2.addCell(c12);
//	            
//	            Paragraph p12=new Paragraph("\n"+det.getAccion()+"\n",fuente);
//	            PdfPCell c13 =new PdfPCell(p12);
//	            c13.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c13.setColspan(6);
//	            table2.addCell(c13);
//	            
//	            Paragraph p13=new Paragraph("\n",f1); ////celda invisible
//	            PdfPCell c14 =new PdfPCell(p13);
//	            c14.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c14.setColspan(6);
//	            table2.addCell(c14);
//	            
//	            Paragraph p14=new Paragraph("MAN TIME HOURS / TIEMPO HORAS HOMBRE:",f1); 
//	            PdfPCell c15 =new PdfPCell(p14);
//	            c15.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c15.setColspan(6);
//	            table2.addCell(c15);
//	            
//	            Paragraph p15=new Paragraph("DESCRIPTION / DESCRIPCION:",f1); 
//	            PdfPCell c16 =new PdfPCell(p15);
//	            c16.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c16.setColspan(2);
//	            table2.addCell(c16);
//	            
//	            Paragraph p16=new Paragraph("PART NUMBER / NUMERO DE PARTE:",f1); 
//	            PdfPCell c17=new PdfPCell(p16);
//	            c17.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c17.setColspan(2);
//	            table2.addCell(c17);
//	            
//	            Paragraph p17=new Paragraph("QUANTITY / CANTIDAD:",f1); 
//	            PdfPCell c18=new PdfPCell(p17);
//	            c18.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c18.setColspan(2);
//	            table2.addCell(c18);
//	            
//	            Paragraph p18=new Paragraph("N/S REMOVABLE / N/S REMOVIDA:",f1); 
//	            PdfPCell c19=new PdfPCell(p18);
//	            c19.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c19.setColspan(2);
//	            table2.addCell(c19);
//	            
//	            Paragraph p19=new Paragraph("N/S INSTALL / N/S INSTALADA:",f1); 
//	            PdfPCell c20=new PdfPCell(p19);
//	            c20.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c20.setColspan(2);
//	            table2.addCell(c20);
//	            
//	            Paragraph p20=new Paragraph("OSERVATION/OBSERVATIONS:",f1); 
//	            PdfPCell c21=new PdfPCell(p20);
//	            c21.setHorizontalAlignment(Element.ALIGN_CENTER);             
//	            c21.setColspan(2);
//	            table2.addCell(c21);
//	            
//	            List<ComDisVo> cds = det.getComponentes();
//	            System.out.println("cds:"+cds);
//	            if (cds.isEmpty()){
//	            	
//		            Paragraph pp=new Paragraph("\n",f1); 
//		            PdfPCell cp=new PdfPCell(pp);
//		            cp.setHorizontalAlignment(Element.ALIGN_CENTER);             
//		            cp.setColspan(2);
//		            table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);
//		            table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);
//		            table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);
//		            table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);table2.addCell(cp);
//	            }
//	            for (ComDisVo cd : cds){
//	            	
//	            	System.out.println("cds. nombre:"+cd.getNombre_componente());
//		            Paragraph p21=new Paragraph(cd.getNombre_componente(),f1); 
//		            PdfPCell c22=new PdfPCell(p21);
//		            c22.setHorizontalAlignment(Element.ALIGN_CENTER);             
//		            c22.setColspan(2);
//		            table2.addCell(c22);
//		            
//		            System.out.println("cds. parte:"+cd.getNoParte());
//		            Paragraph p22=new Paragraph(cd.getNoParte(),f1); 
//		            PdfPCell c23=new PdfPCell(p22);
//		            c23.setHorizontalAlignment(Element.ALIGN_CENTER);             
//		            c23.setColspan(2);
//		            table2.addCell(c23);
//		            
//		            Paragraph p23=new Paragraph(cd.getCantidad().toString(),f1); 
//		            PdfPCell c24=new PdfPCell(p23);
//		            c24.setHorizontalAlignment(Element.ALIGN_CENTER);             
//		            c24.setColspan(2);
//		            table2.addCell(c24);
//		            
//		            Paragraph p24=new Paragraph("N/A",f1); 
//		            PdfPCell c25=new PdfPCell(p24);
//		            c25.setHorizontalAlignment(Element.ALIGN_CENTER);             
//		            c25.setColspan(2);
//		            table2.addCell(c25);
//		            table2.addCell(c25);
//		            
//		            Paragraph p25=new Paragraph("las observaciones de horas hombre",f1); 
//		            PdfPCell c26=new PdfPCell(p25);
//		            c26.setHorizontalAlignment(Element.ALIGN_CENTER);             
//		            c26.setColspan(2);
//		            table2.addCell(c26);
//		            
//		            
//	            
//	            }
//	            /////////////////////////validar cuantos comps vienen para poner los demas celdas vacias
//	           // for (){
//	            	
//	          //  }
//	            Paragraph p26=new Paragraph("REMOVIBLE BY/ REMOVIDO POR:\n",f1); 
//	            PdfPCell c27=new PdfPCell(p26);
//	            c27.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c27.setColspan(6);
//	            table2.addCell(c27);
//	            
//	            Paragraph p27=new Paragraph("INSTALL BY/ INSTALADA POR:\n",f1); 
//	            PdfPCell c28=new PdfPCell(p27);
//	            c28.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c28.setColspan(6);
//	            table2.addCell(c28);
//	            
//	            Paragraph p28=new Paragraph("SIGN / FIRMA:\n\n",f1); 
//	            PdfPCell c29=new PdfPCell(p28);
//	            c29.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c29.setColspan(6);
//	            table2.addCell(c29);
//	            table2.addCell(c29);
//	            
//	            Paragraph p29=new Paragraph("PERMISSION / LICENCIA:\n",f1); 
//	            PdfPCell c30=new PdfPCell(p29);
//	            c30.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c30.setColspan(6);
//	            table2.addCell(c30);
//	            table2.addCell(c30);
//	            
//	            Paragraph p30=new Paragraph("DATE / FECHA:\n",f1); 
//	            PdfPCell c31=new PdfPCell(p30);
//	            c31.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c31.setColspan(6);
//	            table2.addCell(c31);
//	            table2.addCell(c31);
//	            
//	            Paragraph p31=new Paragraph("CUSTOMER AUTORIZATION / AUTORIZADO POR EL CLIENTE:\n\n NAME / NOMBRE:\n\n SIGN / FIRMA:\n\n",f1); 
//	            PdfPCell c32=new PdfPCell(p31);
//	            c32.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c32.setColspan(12);
//	            table2.addCell(c32);
//	            
//	            Paragraph p32=new Paragraph("                                  FORM AUTORIZATION / MEDIO DE AUTORIZACION:\n PERSONAL:\n\n BY TELEFON / VIA TELEFONICA:\n\n "
//	            		+ "E-MAIL / CORREO ELECTRONICO:\n\n  HOUR / DATE / HORA Y FECHA \n\n",f1); 
//	            PdfPCell c33=new PdfPCell(p32);
//	            c33.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c33.setColspan(12);
//	            table2.addCell(c33);
//	            
//	            Paragraph p33=new Paragraph("\n",f1); 
//	            PdfPCell c34=new PdfPCell(p33);
//	            c34.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c34.setColspan(6);
//	            table2.addCell(c34);
//	            
//	            Paragraph p34=new Paragraph("Vo Bo INSPECTOR\n\n",f1); 
//	            PdfPCell c35=new PdfPCell(p34);
//	            c35.setHorizontalAlignment(Element.ALIGN_LEFT);             
//	            c35.setColspan(6);
//	            table2.addCell(c35);
//	            
//	            
//	            document.add(table2);  
//	            document.add(new Paragraph("\n\n\n"));
//	            
//	      }

	      
	     


		public String letras(Double number){
	    	  Integer entero = number.intValue();//.longValue();
	    	  double decimales = number - entero;
	    	  
	    	  
	    	  StringBuffer resultado = new StringBuffer(); 
	    	  String strEntero = letras(entero.doubleValue()); 
	    	
	    	  String strDecimales = letras(decimales); 
	    	  resultado.append(strEntero); 
	    	  resultado.append(" con "); 
	    	  resultado.append(strDecimales); 
	    	  return resultado.toString(); 
	    	  
	    	  //return "si";
	      }
		
		
	}


