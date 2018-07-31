package com.bankwel.phjf_admin.component.c13webtag.bootstrap;

public class DynamicSelectData implements IDynamicSelectData {

	private String id = "";
	private String name = "";
	private String displayLable = "";
	private String isSelected = "";
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayLable() {
		return displayLable;
	}

	public void setDisplayLable(String displayLable) {
		this.displayLable = displayLable;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	
}
