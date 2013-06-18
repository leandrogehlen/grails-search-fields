package org.grails.plugins.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.grails.plugins.search.util.ParserUtil;
import org.grails.plugins.search.util.ReflectionUtil;

public class Search {

	private String entityAlias;
	private String entityName;
	private String order;
	private Set<Join> joins;
	private List<String> conditions;
	private List<String> filters;
	private List<Object> params;
	private HashMap<String, String> fields;
	private String sortFieldName;
	private String sortDirection;
	private Configuration config;

	protected Search(Configuration config) {
		super();
		this.config = config;
		this.entityName = config.getEntityName();
		this.entityAlias = config.getEntityName().toLowerCase();
		this.fields = new HashMap<String, String>();
		this.joins = new LinkedHashSet<Join>();
		this.filters = new ArrayList<String>();
		this.conditions = new ArrayList<String>();
		this.params = new ArrayList<Object>();
	}

	private String concatEntityAlias(String property) {
		return (!StringUtils.contains(property, '.')) ? entityAlias + '.' + property : property;
	}

	private String createWhere() {
		List<String> where = new ArrayList<String>(conditions);

		if (!filters.isEmpty())
			where.add("(" + StringUtils.join(filters, " or ") + ")");

		return (!where.isEmpty()) ? "where" + StringUtils.join(where, " and ") : "";
	}

	private String createQuery() {
		StringBuilder query = new StringBuilder(" from ")
			.append(entityName)
			.append(" ")
			.append(entityAlias)
			.append(" ");

		if (!joins.isEmpty())
			query.append(StringUtils.join(joins , " ")) .append(" ");

		query.append(createWhere());
		return StringUtils.trim(query.toString());
	}


	public String getQuery() {
		String sort = (StringUtils.isNotEmpty(order)) ? " order by " + order : "" ;
		return StringUtils.trim(createQuery() + sort);
	}

	public String getCount() {
		return StringUtils.trim("select count("+ entityAlias +") "+ StringUtils.remove(createQuery(), "fetch "));
	}

	public List<Object> getParams() {
		return params;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public Search addAlias(String name, String alias) {
		joins.add( new Join(name, alias) );
		return this;
	}

	public Search addAlias(String name, String alias, int joinType) {
		joins.add( new Join(name, alias, joinType) );
		return this;
	}

	public Search addJoin(Join join) {
		joins.add( join );
		return this;
	}

	public Search addCondition(String filter, Object... params) {
		this.conditions.add(filter);
		for (Object obj : params)
			this.params.add(obj);
		return this;
	}

	public Search addCondition(String filter, Object param) {
		return addCondition(filter, new Object[]{param});
	}

	public Search addCondition(String filter) {
		return addCondition(filter, new Object[]{});
	}

	public Search addField(String field, String alias) {
		fields.put(field, alias);
		return this;
	}

	public void setSort(String property, boolean asc) {
		sortFieldName = ParserUtil.normalize(property);
		sortDirection = (asc)? "asc" : "desc";
		order = concatEntityAlias(sortFieldName) + " " + sortDirection;
	}

	public void setOrder(String order) {
		order = ParserUtil.normalize(order);

		List<String> orders = new ArrayList<String>();
		for	(String tk : StringUtils.split(order , ',')) {
			List<String> tokens = ParserUtil.getJavaTokens(tk);
			if (!tokens.isEmpty()) {
				String field = tokens.get(0);
				String direction = (tokens.size() == 2) ? tokens.get(1) : "";
				orders.add( concatEntityAlias(field) + " " + direction);
			}
		}
		this.order = StringUtils.join(orders, ", ");
	}

	public Search execute(Field field, String operator, String value) {
		if (field == null)
			throw new IllegalArgumentException("argument [field] is required; it must not be null");

		filters.clear();
		if (StringUtils.isNotBlank(value))	{
			if (field.getType() != null) {
				String alias = concatEntityAlias( fields.get(field.getName()) );

				if (String.class.isAssignableFrom(field.getType()))
					filters.add("lower(" + alias + ") "+ operator +" '"+ value.toLowerCase()+ "%'");
				else
					filters.add(alias +" "+ operator +" "+ ReflectionUtil.coerce(field.getType(), value));
			}
		}
		return this;
	}

	public Search execute(Field field, String value) {
		String op = (field != null && String.class.isAssignableFrom(field.getType())) ? "like" : "=";
		return execute(field, op, value);
	}

	public Search execute(String fieldName, String operator, String value) {
		return execute(config.findField(fieldName), operator, value);
	}

	public Search execute(String fieldName, String value) {
		return execute(config.findField(fieldName), value);
	}
	
	public Search execute(String value) {
		return execute(config.getDefaulField(), value);
	}

	public void clear(){
		conditions.clear();
		params.clear();
	}
}
