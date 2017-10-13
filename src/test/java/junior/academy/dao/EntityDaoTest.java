package junior.academy.dao;


import junior.academy.config.HibernateTestConfig;
import junior.academy.config.HsqlDataTypeFactory;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import javax.sql.DataSource;

@ContextConfiguration(classes = {HibernateTestConfig.class})
public abstract class EntityDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    DataSource dataSource;

    @BeforeMethod
    public void setUp() throws Exception{
        IDatabaseConnection dbConn=new DatabaseDataSourceConnection(dataSource);
        DatabaseConfig config=dbConn.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new HsqlDataTypeFactory());
        DatabaseOperation.CLEAN_INSERT.execute(dbConn,getDataSet());
    }

    protected abstract IDataSet getDataSet() throws Exception;
}
