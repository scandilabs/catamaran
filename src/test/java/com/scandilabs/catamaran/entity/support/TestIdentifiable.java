package com.scandilabs.catamaran.entity.support;

public class TestIdentifiable extends IdentifiableBase {

	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public Long getId() {
		return id;
	}

}
