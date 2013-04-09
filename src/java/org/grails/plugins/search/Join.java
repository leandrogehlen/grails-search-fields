package org.grails.plugins.search;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.sql.JoinFragment;

public class Join {
	
	private String name;
	private String alias;
	private String root;
	private int joinType = JoinFragment.INNER_JOIN;

	public Join(String name, String alias) {
		super();
		this.name = name;
		this.alias = alias;			
	}
	
	public Join(String name, String alias, int joinType) {
		super();
		this.name = name;
		this.alias = alias;
		this.joinType = joinType;
	}				
					
	public Join(String name, String alias, String root) {
		super();
		this.name = name;
		this.alias = alias;
		this.root = root;		
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public int getJoinType() {
		return joinType;
	}

	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}
			
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String toString() {                                                 
		String left = (joinType == JoinFragment.INNER_JOIN)? "": "left";       	                                                                          
		return new StringBuilder(left)         
			.append("join fetch ")
			.append((root != null) ? root + "." : "")			
			.append(name)                      
    		.append(" ")                                                       
			.append(alias)                                                      
			.toString();					                                   
    }
		
	public int hashCode() {			
		return new HashCodeBuilder().append(name).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Join other = (Join) obj;					
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}		

}
