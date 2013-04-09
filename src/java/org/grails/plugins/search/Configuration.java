package org.grails.plugins.search;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class Configuration {
	
	private Set<Field> fields;
	private Set<Join> joins;
	private String entityName;	
	private String defaultField;
	private String order;
			
	public Configuration(String entityName, Set<Field> fields, Set<Join> joins, 
			String order, String defaultField) {
		super();
		this.entityName = entityName;
		this.fields = fields;
		this.joins = joins;		
		this.order = order;
		this.defaultField = defaultField;
	}
		
	public String getEntityName() {
		return entityName;
	}

	public Set<Field> getFields() {
		return fields;
	}
	
	public Set<Join> getJoins() {
		return joins;
	}
	
	public String getOrder() {
		return order;
	}

	public Field getDefaulField() {
		if (StringUtils.isBlank(defaultField)) {
			if (!fields.isEmpty()) 
				return fields.iterator().next();			
		}
		else { 		
			for (Field field : fields) {
				if (defaultField.equals(field.getName()))
					return field;
			}				
		}
		return null;
	}
	
	public Field findField(String fieldName) {
		for (Field field : getFields()) {
			if (field.getName().equals(StringUtils.replace(fieldName, "_", ".")) )
				return field; 
		}
		return null;
	}
}
