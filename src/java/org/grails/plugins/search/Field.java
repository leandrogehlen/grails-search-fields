package org.grails.plugins.search;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Field {	
	
	private String name;				
	private String alias;		
	private Class<?> type;
	private boolean visible = true; 	
	private boolean filterable = true;
		
	public Field(String name, Class<?> type) {
		super();
		this.name = name;
		this.type = type;
	}
					
	public Field(String name, Class<?> type, String alias) {
		this(name, type);
		this.alias = alias;		
	}

	public String getName() {
		return name;
	}	
			
	public Class<?> getType() {
		return type;
	}
		
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}	
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
		
	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}		
}
