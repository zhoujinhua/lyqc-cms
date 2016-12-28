package com.liyun.car.param.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;


public class DictType implements UserType, ParameterizedType {
	//SQL类型映射
	private static final Map<String,Integer> SQLTYPE_MAP = Collections.synchronizedMap(new HashMap<String,Integer>());
	
	static{
		SQLTYPE_MAP.put("VARCHAR", Types.VARCHAR);
		SQLTYPE_MAP.put("CHAR", Types.CHAR);
		SQLTYPE_MAP.put("INTEGER", Types.INTEGER);
		SQLTYPE_MAP.put("INT", Types.INTEGER);
		SQLTYPE_MAP.put("NUMERIC", Types.NUMERIC);
		SQLTYPE_MAP.put("BIGINT", Types.BIGINT);
		SQLTYPE_MAP.put("SMALLINT", Types.SMALLINT);
		SQLTYPE_MAP.put("TINYINT", Types.TINYINT);
		SQLTYPE_MAP.put("DECIMAL", Types.DECIMAL);
		SQLTYPE_MAP.put("TIMESTAMP", Types.TIMESTAMP);
		SQLTYPE_MAP.put("DATE", Types.DATE);
		SQLTYPE_MAP.put("DOUBLE", Types.DOUBLE);
		SQLTYPE_MAP.put("FLOAT", Types.FLOAT);
		SQLTYPE_MAP.put("BLOB", Types.BLOB);
		SQLTYPE_MAP.put("CLOB", Types.CLOB);
		SQLTYPE_MAP.put("NULL", Types.NULL);
	}
	
	@SuppressWarnings("rawtypes")
	private Class enumClass;

	private int[] sqlTypes;
	
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClass");
		String sqlType = parameters.getProperty("sqlType");
		Integer type = SQLTYPE_MAP.get(sqlType!=null?sqlType.toUpperCase():null);
		
		sqlTypes = new int[]{type==null?Types.VARCHAR:type};
		try {
			enumClass = Class.forName(enumClassName);
		} catch (ClassNotFoundException exception) {
			throw new HibernateException("dict enum class not found", exception);
		}
	}

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return enumClass;
	}

	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor arg2, Object arg3)
			throws HibernateException, SQLException {
		return DictEnum.valueOf(enumClass, rs.getObject(names[0]));
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session)
			throws HibernateException, SQLException {
		Object val = null;
		if(value!=null){
			val = ((DictEnum)value).getCode();
			if(val!=null && !"".equals(val)){
				st.setObject(index, val, sqlTypes[0]);
			} else {
				st.setNull(index, sqlTypes[0]);
			}
		}else{
			st.setNull(index, sqlTypes[0]);
		}
	}

	public int[] sqlTypes() {
		return sqlTypes;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

}

