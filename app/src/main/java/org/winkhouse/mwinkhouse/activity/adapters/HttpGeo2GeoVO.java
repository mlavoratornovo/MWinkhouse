package org.winkhouse.mwinkhouse.activity.adapters;

import java.util.ArrayList;

import org.winkhouse.mwinkhouse.models.GeoVO;

public class HttpGeo2GeoVO {

	public HttpGeo2GeoVO() {

	}
	
	public ArrayList<GeoVO> parse(String httpgeo){
		
		ArrayList<GeoVO> result = new ArrayList<GeoVO>();
		
		String[] rows = httpgeo.split("\\|");
		
		for (int i = 0; i < rows.length; i++) {
			
			String[] elements = rows[i].split("/");
			
			GeoVO gVO = new GeoVO();
			
			gVO.setCodIstat(elements[0]);
			gVO.setCitta(elements[1]);
			gVO.setProvincia(elements[2]);
			gVO.setRegione(elements[3]);
			gVO.setCap(elements[4]);
			
			result.add(gVO);
			
		}
		
		return result;
		
	}

}
