package org.grails.plugins.search.util;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.codehaus.groovy.grails.commons.GrailsClassUtils;
import org.grails.plugins.search.Configuration;
import org.grails.plugins.search.Field;
import org.grails.plugins.search.Join;
import org.springframework.util.Assert;

public class SearchUtil {
	
	private static final String PROP_SEARCH_FIELDS = "searchFields";
	private static final String PROP_FIELDS = "fields";
	private static final String PROP_JOINS = "joins";
	private static final String PROP_DEFAULT_SORT = "defaultSort";
	private static final String PROP_DEFAULT_FIELD = "defaultField";
		
	private static void assertProperty(Object prop, String propName) {		
		Assert.isInstanceOf(Collection.class,  prop);
		Collection<?> collection = (Collection<?>) prop;
		
		if (!collection.isEmpty()) {
			Object element = collection.iterator().next();
			
			if (element instanceof Map<?,?>) {				
				Map<?,?> map = (Map<?,?>) element;
				
				if (map.isEmpty() || map.keySet().iterator().next() instanceof String)
					return;				
			}
											
			throw new IllegalArgumentException("Element of collection [" + propName +"] must be an " +
					"instance of java.util.Map<String, ?>");			
		}
	}
			
	private static Set<Field> extractFields(Map<?,?> config, Class<?> domain) {
		Set<Field> result = new LinkedHashSet<Field>();				
		Object fields =  MapUtils.getObject(config,  PROP_FIELDS);        			

		for (Object e : (Collection<?>)fields) {			
			if (!(e instanceof String)) {
				throw new IllegalArgumentException("Element of collection ["+ PROP_FIELDS +"] must be an " +
						"instance of java.lang.String");
			}						
			String name = ParserUtil.normalize((String) e);						
			java.lang.reflect.Field property = ReflectionUtil.findField(domain, name);        			        			        			        			
			result.add( new Field(name, property.getType()) );        			
		}        		
        	        		
        return result;		
	}
	
	@SuppressWarnings("unchecked")
	private static Set<Join> extractJoins(Map<?,?> config) {		
		Set<Join> result = new LinkedHashSet<Join>();								       
		Object joins = MapUtils.getObject(config, PROP_JOINS);
		if (joins != null) {
			assertProperty(joins, PROP_JOINS);

			for (Object e : (Collection<?>)joins) {
				Map<String, String> map = (Map<String, String>) e;

				String name = MapUtils.getString(map, "name");
				int joinType = MapUtils.getIntValue(map, "joinType");					

				result.add( new Join(name, null, joinType) );
			}        			
		}		
		return result;		
	}
	
	public static Configuration extractConfig(Class<?> domain) {
		Object search = GrailsClassUtils.getStaticPropertyValue(domain, PROP_SEARCH_FIELDS);
		Assert.isInstanceOf(Map.class,  search);						
			
		Map<?,?> config = (Map<?,?>) search;		
		Set<Field> fields = extractFields(config, domain);
		Set<Join> joins = extractJoins(config);				
		String order =  MapUtils.getString(config, PROP_DEFAULT_SORT);
		String defaulField = MapUtils.getString(config, PROP_DEFAULT_FIELD);
		
		return new Configuration(domain.getSimpleName(), fields, joins, order, defaulField);
	}

}
