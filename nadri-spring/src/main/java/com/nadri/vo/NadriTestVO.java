package com.nadri.vo;

public class NadriTestVO {
	 private int id;
	private String name;
	
	public NadriTestVO() {
		super();
	}

	public NadriTestVO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "NadriTestVO [id=" + id + ", name=" + name + "]";
	}

}
