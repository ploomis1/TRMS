package com.revature.data;

import java.util.List;

import com.revature.beans.TRF;

/*
 * Update form
 * get form
 * add form
 * get forms
 * 
 */
public interface TRFDAO {
	
	public void addForm(TRF form) throws Exception;
	public void updateForm(TRF form) throws Exception;
	public TRF getForm(int id) throws Exception;
	public List<TRF> getForms() throws Exception;
	public void createTRFTable() throws Exception;
	

}
