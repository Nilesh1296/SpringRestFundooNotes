package com.bridgeit.fundoonotes.label;

import javax.persistence.Column;

public class Label
{
	@Column
	private int labelid;
	
	@Column
	private String labeldata;

	public int getLabelid() {
		return labelid;
	}

	public void setLabelid(int labelid) {
		this.labelid = labelid;
	}

	public String getLabeldata() {
		return labeldata;
	}

	public void setLabeldata(String labeldata) {
		this.labeldata = labeldata;
	}
	
}
