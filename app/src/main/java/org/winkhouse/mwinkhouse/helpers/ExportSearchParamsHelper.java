package org.winkhouse.mwinkhouse.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.winkhouse.mwinkhouse.util.ZipUtils;

import javolution.xml.XMLBinding;
import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;

public class ExportSearchParamsHelper extends ExportDataHelper {

	private String cloudSearchDirectory = null;
	
	public ExportSearchParamsHelper() {
		String state = Environment.getExternalStorageState();
		cloudSearchDirectory = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/searchC";
	}

	public boolean exportSearchParamToXML(ArrayList<SearchParam> params,String fileName){		
		return exportSelection(params, fileName);				
	}

	@Override
	protected boolean exportSelection(Object obj, String fileName) {
		boolean returnValue = true;
		
		FileOutputStream fos = null;
		
		File f_dir = new File(cloudSearchDirectory);
		
		if (f_dir.exists() == false){
			f_dir.mkdirs();
		}
		
		File f = new File(cloudSearchDirectory + File.separator + fileName);
		
		try {
			if (f.createNewFile()) {
                fos = new FileOutputStream(f);

                XMLObjectWriter xmlow = XMLObjectWriter.newInstance(fos);
                xmlow.setBinding(getBindingsObj());

                if (obj instanceof ArrayList) {
                    Iterator it = ((ArrayList) obj).iterator();
                    while (it.hasNext()) {
                        xmlow.write(it.next());
                    }
                } else {
                    xmlow.write(obj);
                }


                xmlow.close();
            }else{
                returnValue = false;
            }

		} catch (FileNotFoundException e1) {
			returnValue = false;
		} catch (IOException e2) {
			returnValue = false;
		} catch (XMLStreamException e1) {
			returnValue = false;
		}
						
		return returnValue;
		
	}

	@Override
	protected XMLBinding getBindingsObj() {
		
		XMLBinding xmlbind = super.getBindingsObj();
		xmlbind.setAlias(SearchParam.class, "ricerca");
		
		return xmlbind;
	}
	
	
	public boolean zipArchivio(String dirToZip,String filePathName){
		
		boolean returnValue = true;
		
		ZipUtils zu = new ZipUtils(dirToZip,filePathName + ".zip");
		zu.zipDirectory();
		
		File f = new File(filePathName + ".zip");
		
		returnValue = f.exists();
		
		return returnValue;
		
	}

	public String getCloudSearchDirectory() {
		return cloudSearchDirectory;
	}

}
