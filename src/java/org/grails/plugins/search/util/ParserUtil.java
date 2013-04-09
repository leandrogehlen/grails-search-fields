package org.grails.plugins.search.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ParserUtil {
	
	public static final Character TK_COMMA = ',';
	public static final Character TK_DOT = '.';
	public static final Character TK_DBDOT = ':';
	public static final Character TK_SPACE = 32;
	public static final Character TK_RETURN = 13;
	public static final Character TK_QUOTE = 39;
	public static final Character TK_DBQUOTE = '"';
	public static final Character TK_LGROUP = '(';
	public static final Character TK_RGROUP = ')';
	public static final Character TK_LPARENT = '(';
	public static final Character TK_RPARENT = ')';
	public static final Character TK_MINOR = '<';
	public static final Character TK_MAJOR = '>';
	public static final Character TK_EQUAL = '=';
	public static final Character TK_CONCAT = '|';
	public static final Character TK_PLUS = '+';
	public static final Character TK_MINUS = '-';
	public static final Character TK_UNDERSCORE = '_';

	public static final String TK_BETWEEN = "between";
	public static final String TK_CASE = "case";
	public static final String TK_DELETE = "delete";
	public static final String TK_NEW = "new";
	public static final String TK_END = "end";
	public static final String TK_OBJECT = "object";
	public static final String TK_INSERT = "insert";
	public static final String TK_DISTINCT = "distinct";
	public static final String TK_WHERE = "where";
	public static final String TK_TRAINLING = "trailing";
	public static final String TK_THEN = "then";
	public static final String TK_SELECT = "select";
	public static final String TK_AND = "and";
	public static final String TK_OUTHER = "outer";
	public static final String TK_NOT = "not";
	public static final String TK_FETCH = "fetch";
	public static final String TK_FROM = "from";
	public static final String TK_NULL = "null";
	public static final String TK_COUNT = "count";
	public static final String TK_LIKE = "like";
	public static final String TK_WHEN = "when";
	public static final String TK_CLASS = "class";
	public static final String TK_INNER = "inner";
	public static final String TK_LEADING = "leading";
	public static final String TK_WITH = "with";
	public static final String TK_SET = "set";
	public static final String TK_ESCAPE = "escape";
	public static final String TK_JOIN = "join";
	public static final String TK_ELEMENTS = "elements";
	public static final String TK_OF = "of";
	public static final String TK_IS = "is";
	public static final String TK_MEMBER = "member";
	public static final String TK_OR = "or";
	public static final String TK_ANY = "any";
	public static final String TK_FULL = "full";
	public static final String TK_MIM = "min";
	public static final String TK_AS = "as";
	public static final String TK_BY = "by";
	public static final String TK_ALL = "all";
	public static final String TK_UNION = "union";
	public static final String TK_ORDER = "order";
	public static final String TK_BOTH = "both";
	public static final String TK_SOME = "some";
	public static final String TK_PROPERTIES = "properties";
	public static final String TK_ASCENDING = "ascending";
	public static final String TK_DESCENDING = "descending";
	public static final String TK_FALSE = "false";
	public static final String TK_EXISTS = "exists";
	public static final String TK_ASC = "asc";
	public static final String TK_LEFT = "left";
	public static final String TK_DESC = "desc";
	public static final String TK_MAX = "max";
	public static final String TK_EMPTY = "empty";
	public static final String TK_SUM = "sum";
	public static final String TK_ON = "on";
	public static final String TK_INTO = "into";
	public static final String TK_ELSE = "else";
	public static final String TK_RIGHT = "right";
	public static final String TK_VERSIONED = "versioned";
	public static final String TK_IN = "in";
	public static final String TK_AVG = "avg";
	public static final String TK_UPDATE = "update";
	public static final String TK_TRUE = "true";
	public static final String TK_GROUP = "group";
	public static final String TK_HAVING = "having";
	public static final String TK_INDICEES = "indices";
	public static final String TK_LOWER = "lower";
	
	private static final Set<String> keyWords = new HashSet<String>();
	
	static {
		keyWords.add(TK_BETWEEN);
		keyWords.add(TK_CASE);
		keyWords.add(TK_DELETE);
		keyWords.add(TK_NEW);
		keyWords.add(TK_END);
		keyWords.add(TK_OBJECT);
		keyWords.add(TK_INSERT);
		keyWords.add(TK_DISTINCT);
		keyWords.add(TK_WHERE);
		keyWords.add(TK_TRAINLING);
		keyWords.add(TK_THEN);
		keyWords.add(TK_SELECT);
		keyWords.add(TK_AND);
		keyWords.add(TK_OUTHER);
		keyWords.add(TK_NOT);
		keyWords.add(TK_FETCH);
		keyWords.add(TK_FROM);
		keyWords.add(TK_NULL);
		keyWords.add(TK_COUNT);
		keyWords.add(TK_LIKE);
		keyWords.add(TK_WHEN);
		keyWords.add(TK_CLASS);
		keyWords.add(TK_INNER);
		keyWords.add(TK_LEADING);
		keyWords.add(TK_WITH);
		keyWords.add(TK_SET);
		keyWords.add(TK_ESCAPE);
		keyWords.add(TK_JOIN);
		keyWords.add(TK_ELEMENTS);
		keyWords.add(TK_OF);
		keyWords.add(TK_IS);
		keyWords.add(TK_MEMBER);
		keyWords.add(TK_OR);
		keyWords.add(TK_ANY);
		keyWords.add(TK_FULL);
		keyWords.add(TK_MIM);
		keyWords.add(TK_AS);
		keyWords.add(TK_BY);
		keyWords.add(TK_ALL);
		keyWords.add(TK_UNION);
		keyWords.add(TK_ORDER);
		keyWords.add(TK_BOTH);
		keyWords.add(TK_SOME);
		keyWords.add(TK_PROPERTIES);
		keyWords.add(TK_ASCENDING);
		keyWords.add(TK_DESCENDING);
		keyWords.add(TK_FALSE);
		keyWords.add(TK_EXISTS);
		keyWords.add(TK_ASC);
		keyWords.add(TK_LEFT);
		keyWords.add(TK_DESC);
		keyWords.add(TK_MAX);
		keyWords.add(TK_EMPTY);
		keyWords.add(TK_SUM);
		keyWords.add(TK_ON);
		keyWords.add(TK_INTO);
		keyWords.add(TK_ELSE);
		keyWords.add(TK_RIGHT);
		keyWords.add(TK_VERSIONED);
		keyWords.add(TK_IN);
		keyWords.add(TK_AVG);
		keyWords.add(TK_UPDATE);
		keyWords.add(TK_TRUE);
		keyWords.add(TK_GROUP);
		keyWords.add(TK_HAVING);
		keyWords.add(TK_INDICEES);	
		keyWords.add(TK_LOWER);
	}
	
	public static boolean isKeyWord(String token) {
		for (String key : keyWords) {
			if (key.equals(token))
				return true;
		}
		return false;
	}
		
	public static boolean isWord(String token) {		
		return (StringUtils.isNotEmpty(token) && token.matches("\\w+"));
	}
	
	public static boolean isSymbol(String token) {		
		return (StringUtils.isNotEmpty(token) && (token.length() == 1) 
				&& !Character.isLetterOrDigit(token.charAt(0)) );
	}
	
	public static List<String> getTokens(String str){								
		ArrayList<String> result = new ArrayList<String>();		
		StringBuilder token = new StringBuilder();
		
		int i = 0;
		while (i < str.length()) {
			Character c = str.charAt(i);
			if (TK_QUOTE.equals(c) || TK_DBQUOTE.equals(c)) {
				String tk = str.substring(i+1);
				int j = tk.indexOf(c);
				if (j < 0)
					result.add(c.toString());
				else  {
					tk = tk.substring(0, j);
					result.add(c + tk + c);
					i = i + j;
				}
			}
			else if (Character.isLetterOrDigit(c) || TK_UNDERSCORE.equals(c)) {
				token.append(c);
			}
			else  {
				if (StringUtils.isNotEmpty(token.toString())) {
					result.add(token.toString());
					token = new StringBuilder();
				}
				if ( !TK_UNDERSCORE.equals(c) &&  !TK_SPACE.equals(c))
					result.add(Character.toString(c));
			}			
			i++;
		}
		
		if (!StringUtils.isEmpty(token.toString()))
			result.add(token.toString());
		
		return result;
	}	
	
	public static List<String> getJavaTokens(String str){
		List<String> result = new ArrayList<String>();

		List<String> tokens = getTokens(str);
		String path = "";		

		for (int i = 0; i < tokens.size(); i++ ){
			String current = tokens.get(i);			
			String next = (i+1 < tokens.size()) ? tokens.get(i+1) : "";			

			if (isWord(current) && next.equals(TK_DOT.toString())){ 
				path = path.concat(current).concat(next);
				i++;
			}
			else if (isWord(current) && path.endsWith(TK_DOT.toString()))
				path = path.concat(current);
			else {
				if (StringUtils.isNotEmpty(path)) {
					result.add(path);
					path = "";
				}
				result.add(current);				
			}			
		}		
		return result;
	}
	
	public static String extractLastField(String property){
		String[] tokens = StringUtils.split(property, TK_DOT);
		if (tokens.length > 0)					
			return tokens[tokens.length -1];
		else
			return property;
	}	
	
	public static String normalize(String property) {
		return StringUtils.replace(property, "_", ".");		
	}
}
