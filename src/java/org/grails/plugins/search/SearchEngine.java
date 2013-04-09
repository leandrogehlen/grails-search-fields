package org.grails.plugins.search;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.grails.plugins.search.util.ParserUtil;

public class SearchEngine {
				
	private HashMap<String, String> fields;
	private Set<Join> joins;				
	private Configuration config;
			
	public SearchEngine(Configuration model) {	
		this.config = model;
		this.fields = new LinkedHashMap<String, String>();
		this.joins = new LinkedHashSet<Join>();		
				
		proccessFields();
		proccessJoins();
	}

	private void proccessFields(){				
		 												
		for (Field field : config.getFields()) {
			String[] tokens = StringUtils.split(ParserUtil.normalize(field.getName()), '.');		

			if (tokens.length > 1) {
				String alias = tokens[0];							
				joins.add(new Join(tokens[0], alias, config.getEntityName().toLowerCase()));							

				for (int i = 1;i < tokens.length-1; i++){
					if (StringUtils.isNotBlank(field.getAlias()) && i == tokens.length-2)
						alias = field.getAlias();																	
					else
						alias = tokens[i];

					joins.add(new Join(tokens[i-1] +"."+ tokens[i], alias));
				}

				if (field.isFilterable())
					fields.put(field.getName(), alias+"."+tokens[tokens.length-1]);
			}					
			else if( field.isFilterable() )
				fields.put(field.getName(), field.getName());					
		}							
	}			
	
	private void proccessJoins(){		
		for (Join join : config.getJoins()){				
			String alias = ParserUtil.extractLastField(join.getName());
			joins.add( new Join(join.getName(), alias, join.getJoinType()) );										
		}
				
	}	
																			
	public Search createSearch() {			
		Search query = new Search(config); 
						
		for (String key : fields.keySet())
			query.addField(key, fields.get(key));
		
		for (Join join : joins)				
			query.addJoin(join);		
				
		if (StringUtils.isNotBlank(config.getOrder()))
			query.setOrder(config.getOrder());
		
		return query;	
	}

	public Configuration getConfiguration() {
		return config;
	}		
}
