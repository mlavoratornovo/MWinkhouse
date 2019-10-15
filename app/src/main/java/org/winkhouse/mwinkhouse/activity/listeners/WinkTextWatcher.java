package org.winkhouse.mwinkhouse.activity.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.winkhouse.mwinkhouse.util.EditedActivity;

import android.text.Editable;
import android.text.TextWatcher;

public class WinkTextWatcher implements TextWatcher {

	private Object object = null;
	private String setMethodName = null;
	private Class inputType = null;
	private EditedActivity ea = null;
	
	public WinkTextWatcher(Object object,String setMethodName, Class inputType) {
		this.object = object;
		this.setMethodName = setMethodName;
		this.inputType = inputType;
	}

	public WinkTextWatcher(Object object,String setMethodName, Class inputType,EditedActivity ea) {
		this.object = object;
		this.setMethodName = setMethodName;
		this.inputType = inputType;
		this.ea = ea;
	}

	
	@Override
	public void afterTextChanged(Editable s) {
		
		if (ea != null){
			ea.setEdited(true);
		}
		
		Class[] params = new Class[1];
		if (this.object != null){
			if (this.inputType.getName().equalsIgnoreCase(String.class.getName())){
				params[0] = String.class;
			}
			if (this.inputType.getName().equalsIgnoreCase(Integer.class.getName())){
				params[0] = Integer.class;
			}
			if (this.inputType.getName().equalsIgnoreCase(Double.class.getName())){
				params[0] = Double.class;
			}
			if (this.inputType.getName().equalsIgnoreCase(Float.class.getName())){
				params[0] = Float.class;
			}
			if (this.inputType.getName().equalsIgnoreCase(Date.class.getName())){
				params[0] = Date.class;
			}
			 
			try {
				Method method = this.object.getClass().getMethod(this.setMethodName, params[0]);
				Object[] oparam = new Object[1];
				if (this.inputType.getName().equalsIgnoreCase(String.class.getName())){
					oparam[0] = s.toString();
				}
				if (this.inputType.getName().equalsIgnoreCase(Integer.class.getName())){
					oparam[0] = Integer.valueOf((s.toString().trim().equalsIgnoreCase("")?"0":s.toString().trim()));
				}
				if (this.inputType.getName().equalsIgnoreCase(Double.class.getName())){
					oparam[0] = Double.valueOf((s.toString().trim().equalsIgnoreCase("")?"0.0":s.toString().trim()));
				}
				if (this.inputType.getName().equalsIgnoreCase(Float.class.getName())){
					oparam[0] = Float.valueOf((s.toString().trim().equalsIgnoreCase("")?"0.0":s.toString().trim()));
				}
				if (this.inputType.getName().equalsIgnoreCase(Date.class.getName())){
					oparam[0] = null;
				}
				method.invoke(this.object,oparam);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {}

}
