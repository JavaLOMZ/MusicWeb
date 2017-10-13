package junior.academy.config;

import org.apache.commons.logging.*;
import org.dbunit.dataset.datatype.*;

import java.sql.*;

public class HsqlDataTypeFactory
        extends DefaultDataTypeFactory
{
    private static final Log log = LogFactory.getLog(HsqlDataTypeFactory.class);

    public DataType createDataType(int sqlType, String sqlTypeName)
            throws DataTypeException
    {
        if (sqlType == Types.BOOLEAN)
        {
            return DataType.BOOLEAN;
        }

        return super.createDataType(sqlType, sqlTypeName);
    }
}