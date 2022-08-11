package kr.co.gitt.kworks.cmmn.typeHandler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(Integer[].class)
public class IntegerArrayTH extends BaseTypeHandler<Integer[]> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            Integer[] parameter, JdbcType jdbcType) throws SQLException {
        Connection c = ps.getConnection();
        Array inArray = c.createArrayOf("int", parameter);
        ps.setArray(i, inArray);
    }

    /// 수정자 : 이승재, 2020.12.07
    @Override
    public Integer[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        Array outputArray = rs.getArray(columnName);
        if (outputArray == null) {
            return null;
        }
        
        // Long(outputArray.getArray())이 Integer로 casting에 에러가 있어 수정
        //return (Integer[])outputArray.getArray();
        
        Long[] outputResult = (Long[]) outputArray.getArray();
        Integer[] returnValues = new Integer[outputResult.length];
        for (int i = 0; i < outputResult.length; i++) {
        	returnValues[i] = (int)(long)outputResult[i];
        }
        return returnValues;
        
    }

    @Override
    public Integer[] getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        Array outputArray = rs.getArray(columnIndex);
        if (outputArray == null) {
            return null;
        }
        return (Integer[])outputArray.getArray();
    }

    @Override
    public Integer[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        Array outputArray = cs.getArray(columnIndex);
        if (outputArray == null) {
            return null;
        }
        return (Integer[])outputArray.getArray();
    }
}