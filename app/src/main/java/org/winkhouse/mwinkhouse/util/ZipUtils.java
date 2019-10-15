package org.winkhouse.mwinkhouse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils{
	
	private List<String> fileList;
	private String sourceFolder;
	private String outputFile;
	
	public ZipUtils(String sourceFolder, String outputFile){
		
	   fileList = new ArrayList<String>();
	   this.sourceFolder = sourceFolder;
	   this.outputFile = outputFile;
	   
	}
	
	public void zipDirectory(){
	   
	   generateFileList(new File(sourceFolder));
	   zipIt(outputFile);
	   
	}
	
	public void zipIt(String zipFile){
		
	   byte[] buffer = new byte[1024];
	   String source = "";
	   FileOutputStream fos = null;
	   ZipOutputStream zos = null;
	   FileInputStream in = null;
	   try
	   {
	     fos = new FileOutputStream(zipFile);
	     zos = new ZipOutputStream(fos);
	
	     System.out.println("Output to Zip : " + zipFile);	     
	
	     for (String file : this.fileList)
	     {
	         ZipEntry ze = new ZipEntry((file.startsWith(File.separator)?file.substring(1):file));
	         zos.putNextEntry(ze);
	        try
	        {
				if (sourceFolder.lastIndexOf(File.separator) == sourceFolder.length()-1) {
					in = new FileInputStream(sourceFolder + file);
				}else {
					in = new FileInputStream(sourceFolder + File.separator + file);
				}

	           	int len;
	           	while ((len = in.read(buffer)) > 0)
	           	{
	            	zos.write(buffer, 0, len);
	           	}
	        }catch (Exception e){
				e.printStackTrace();
			}
	        finally
	        {
	           in.close();
	        }
	     }
	
	     zos.closeEntry();
	
	  }
	  catch (IOException ex)
	  {
	     ex.printStackTrace();
	  }
	  finally
	  {
	     try
	     {
	        zos.close();
	     }
	     catch (Exception e)
	     {
	        e.printStackTrace();
	     }
	     try
	     {
	        fos.close();
	     }
	     catch (Exception e)
	     {
	        e.printStackTrace();
	     }
	     try
	     {
	        in.close();
	     }
	     catch (Exception e)
	     {
	        e.printStackTrace();
	     }
	     
	     
	  }
	}
	
	public void generateFileList(File node)
	{
	
	  // add file only
	  if (node.isFile())
	  {
	     fileList.add(generateZipEntry(node.toString()));
	
	  }
	
	  if (node.isDirectory())
	  {
	     String[] subNote = node.list();
	     for (String filename : subNote)
	     {
	        generateFileList(new File(node, filename));
	     }
	  }
	}
	
	private String generateZipEntry(String file){
	   return file.substring(sourceFolder.length(), file.length());
	}
	
}    
