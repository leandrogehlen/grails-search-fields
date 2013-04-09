package org.grails.plugins.search.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.reflect.ConstructorUtils;
import org.springframework.util.ReflectionUtils;


public class ReflectionUtil {
	private static final String PREFIX_SET = "set";
	private static final String PREFIX_GET = "get";

	public static final Long ZERO_LONG = new Long(0L);	
	public static final Integer ZERO_INTEGER = new Integer(0);	
	public static final Short ZERO_SHORT = new Short((short)0);	
	public static final Byte ZERO_BYTE = new Byte((byte)0);	
	public static final Float ZERO_FLOAT = new Float(0F);	
	public static final Double ZERO_DOUBLE = new Double(0D);	
	public static final BigDecimal ZERO_BIG_DECIMAL = new BigDecimal(0D);	
	public static final BigInteger ZERO_BIG_INTEGER = BigInteger.ZERO;	
	public static final Character NULL_CHARACTER = new Character('\u0000');
			
					
	public static boolean isPrimitiveType(Class<?> type) {
		return type.isPrimitive() || 
			   Character.class.isAssignableFrom(type) ||
			   Byte.class.isAssignableFrom(type) ||
			   Short.class.isAssignableFrom(type) ||
			   Integer.class.isAssignableFrom(type) ||
			   Long.class.isAssignableFrom(type) ||
			   Float.class.isAssignableFrom(type) ||
			   Double.class.isAssignableFrom(type) ||
			   String.class.isAssignableFrom(type);
	}
	
	public static Field findField(Class<?> clazz, String fieldPath) {
		while (true) {
			int i = fieldPath.indexOf('.');			
			if (i < 0)
				return ReflectionUtils.findField(clazz, fieldPath);
			
			Field field = ReflectionUtils.findField(clazz, fieldPath.substring(0, i));
			if (field == null)
				return null;
			
			clazz = field.getType();
			fieldPath = fieldPath.substring(i+1);
		}
	}
		
	public static Object coerce(Class<?> cls, Object val) throws ClassCastException {
		if (cls.isInstance(val))
			return val;

		if (String.class == cls) {
			return ObjectUtils.toString(val);
		} else if (BigDecimal.class == cls) {
			if (val == null) {
				return null;
			} else if (val instanceof Double) {
				return new BigDecimal(((Double)val).doubleValue());
			} else if (val instanceof Float) {
				return new BigDecimal(((Float)val).doubleValue());
			} else if (val instanceof BigInteger) {
				return new BigDecimal((BigInteger)val);
			} else if (val instanceof Number) {						
				return new BigDecimal(((Number)val).intValue());
			} else if (val instanceof String) {
				return new BigDecimal((String)val);
			} else if (val instanceof Date) {
				return new BigDecimal(((Date)val).getTime());
			}
		} else if (Integer.class == cls || int.class == cls) {
			if (val == null) {
				return Integer.class == cls ? null: ZERO_INTEGER;
			} else if (val instanceof Integer) { //int.class
				return val;
			} else if (val instanceof Number) {
				return new Integer(((Number)val).intValue());
			} else if (val instanceof String) {
				return new Integer((String)val);
			}
		} else if (Boolean.class == cls || boolean.class == cls) {
			if (val == null) {
				return Boolean.class == cls ? null: Boolean.FALSE;
			} else if (val instanceof Boolean) { //boolean.class
				return val;
			} else if (val instanceof String) {
				return Boolean.valueOf((String)val);
			} else if (val instanceof BigDecimal) {
				return Boolean.valueOf(((BigDecimal)val).signum() != 0);
			} else if (val instanceof BigInteger) {
				return Boolean.valueOf(((BigInteger)val).signum() != 0);
			} else if (val instanceof Number) {
				return Boolean.valueOf(((Number)val).intValue() != 0);
			} else {
				return Boolean.TRUE; //non-null is true
			}
		} else if (Short.class == cls || short.class == cls) {
			if (val == null) {
				return Short.class == cls ? null: ZERO_SHORT;
			} else if (val instanceof Short) { //short.class
				return val;
			} else if (val instanceof Number) {
				return new Short(((Number)val).shortValue());
			} else if (val instanceof String) {
				return new Short((String)val);
			}
		} else if (Long.class == cls || long.class == cls) {
			if (val == null) {
				return Long.class == cls ? null: ZERO_LONG;
			} else if (val instanceof Long) { //long.class
				return val;
			} else if (val instanceof Number) {
				return new Long(((Number)val).longValue());
			} else if (val instanceof String) {
				return NumberUtils.createLong((String)val);
			} else if (val instanceof Date) {
				return new Long(((Date)val).getTime());
			}
		} else if (Double.class == cls || double.class == cls) {
			if (val == null) {
				return Double.class == cls ? null: ZERO_DOUBLE;
			} else if (val instanceof Double) { //double.class
				return val;
			} else if (val instanceof Number) {
				return new Double(((Number)val).doubleValue());
			} else if (val instanceof String) {
				return new Double((String)val);
			} else if (val instanceof Date) {
				return new Double(((Date)val).getTime());
			}
		} else if (BigInteger.class == cls) {
			if (val == null) {
				return null;						
			} else if (val instanceof Number || val instanceof String) {
				return NumberUtils.createBigInteger(val.toString());					
			} else if (val instanceof Date) {						
				String str = String.valueOf(((Date)val).getTime());
				return NumberUtils.createBigInteger(str);
			}
		} else if (Float.class == cls || float.class == cls) {
			if (val == null) {
				return Float.class == cls ? null: ZERO_FLOAT;
			} else if (val instanceof Float) { //float.class
				return val;
			} else if (val instanceof Number) {
				return new Float(((Number)val).floatValue());
			} else if (val instanceof String) {
				return new Float((String)val);
			} else if (val instanceof Date) {
				return new Float(((Date)val).getTime());
			}
		} else if (Byte.class == cls || byte.class == cls) {
			if (val == null) {
				return Byte.class == cls ? null: ZERO_BYTE;
			} else if (val instanceof Byte) { //byte.class
				return val;
			} else if (val instanceof Number) {
				return new Byte(((Number)val).byteValue());
			} else if (val instanceof String) {
				return new Byte((String)val);
			}
		} else if (Character.class == cls || char.class == cls) {
			if (val == null) {
				return Character.class == cls ? null: NULL_CHARACTER;
			} else if (val instanceof Character) { //character.class
				return val;
			} else if (val instanceof Number) {
				return new Character((char)((Number)val).shortValue());
			} else if (val instanceof String) {
				final String s = (String)val;
				return s.length() > 0 ? new Character(s.charAt(0)): NULL_CHARACTER;
			}
		} else if (Date.class == cls) {
			if (val == null) {
				return null;
			} else if (val instanceof Number) {
				return new Date(((Number)val).longValue());
			}
		} else if (Number.class == cls) {
			if (val == null) {
				return null;
			} else if (val instanceof String) {
				return new BigDecimal((String)val);
			} else if (val instanceof Date) {
				return new BigDecimal(((Date)val).getTime());
			}
		} else {
			if (val == null) {
				return null;
			} else {
				try {									
					return ConstructorUtils.invokeConstructor(cls, new Object[] {val});
				} catch (Exception ex) {
					final ClassCastException t = new ClassCastException("Class not compatible");
					t.initCause(ex);
					throw t;
				}
			}
		}

		throw new ClassCastException("Cast not possible"+				
				val != null ? val+"("+val.getClass().getName()+")" : "null");
	}
	
	public static String toGetMethod(String field){
		return PREFIX_GET + StringUtils.capitalize(field);
	}
	
	public static String toSetMethod(String field){
		return PREFIX_SET + StringUtils.capitalize(field);
	}
}
