package org.winkhouse.mwinkhouse.helpers;

import javolution.xml.XMLFormat;
import javolution.xml.XMLFormat.InputElement;
import javolution.xml.XMLFormat.OutputElement;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.models.ImmobiliPropietariVO;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchParam implements Parcelable{

    public final static String IMMOBILI = "immobili";
    public final static String ANAGRAFICHE = "anagrafiche";

	private String column_name = null;
	private String value_da = null; 
	private String value_a = null;
	private String logic_operatore = null;
	private String matchType = null;
	private String searchType = null;

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { 
															public SearchParam createFromParcel(Parcel in) { 
																return new SearchParam(in); 
															}   
															public SearchParam[] newArray(int size) { 
																return new SearchParam[size]; 
															} 
														 };
	
	public SearchParam(Parcel in){
		
		column_name = in.readString();
		value_da = in.readString();
		value_a = in.readString();
		logic_operatore = in.readString();
		matchType = in.readString();
        searchType = in.readString();

	}

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public SearchParam(String column_name, String value_da, String value_a, String logic_operatore, String matchType, String searchType){
		this.column_name = column_name;
		this.value_da = value_da;
		this.value_a = value_a;
		this.logic_operatore = logic_operatore;

		this.matchType = matchType;
        this.searchType = searchType;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getValue_da() {
		return value_da;
	}

	public void setValue_da(String value_da) {
		this.value_da = value_da;
	}

	public String getValue_a() {
		return value_a;
	}

	public void setValue_a(String value_a) {
		this.value_a = value_a;
	}

	@Override
	public String toString() {

		String returnValue = null;

		if (column_name != null && (value_da != null || value_a != null)){

			returnValue = column_name ;

			if (value_da != null){
				returnValue += " da : " + value_da;
			}

			if (value_a != null){
				returnValue += " a : " + value_a;
			}

			if (logic_operatore != null){
				returnValue += " " + logic_operatore;
			}
		}
		return returnValue;
	}

	public String getLogic_operatore() {
		return logic_operatore;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public void setLogic_operatore(String logic_operatore) {
		this.logic_operatore = logic_operatore;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeString(column_name);
		arg0.writeString(value_da);
		arg0.writeString(value_a);
		arg0.writeString(logic_operatore);
		arg0.writeString(matchType);
        arg0.writeString(searchType);
		
	}
	
	protected static final XMLFormat<SearchParam> SEARCHPARAM_XML = new XMLFormat<SearchParam>(SearchParam.class){
		
        public void write(SearchParam sp, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("column_name", sp.getColumn_name());
        	xml.setAttribute("logic_operatore", sp.getLogic_operatore());
        	xml.setAttribute("matchType", sp.getMatchType());
        	xml.setAttribute("value_a", sp.getValue_a());
        	xml.setAttribute("value_da", sp.getValue_da());
            xml.setAttribute("searchType", sp.getSearchType());
        }

		@Override
		public void read(InputElement arg0, SearchParam arg1) throws XMLStreamException {
 
			
		}
        
        
                
        
   };
	
	
}
