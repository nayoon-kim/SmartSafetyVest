package com.hanium_ict.smartvest.model.dto;

public class FieldWorkDto {
	
	private int field_id;
	private String harbor;
	private String dock;
	private String company;
	
	public FieldWorkDto() {
		
	}
	
	public FieldWorkDto(String harbor, String dock, String company) {
		setHarbor(harbor);
		setDock(dock);
		setCompany(company);
	}

	public int getField_id() {
		return field_id;
	}

	public void setField_id(int field_id) {
		this.field_id = field_id;
	}

	public String getHarbor() {
		return harbor;
	}

	public void setHarbor(String harbor) {
		this.harbor = harbor;
	}

	public String getDock() {
		return dock;
	}

	public void setDock(String dock) {
		this.dock = dock;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public String toString() {
		return "FieldWork value fieldwork<" + getField_id() + "> <" + getHarbor() + "> <" + getDock() +"> <" + getCompany()+">";
	}
	
}
