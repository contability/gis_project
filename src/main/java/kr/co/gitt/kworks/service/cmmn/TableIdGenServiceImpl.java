package kr.co.gitt.kworks.service.cmmn;

import java.math.BigDecimal;
import java.util.Locale;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.impl.AbstractDataBlockIdGnrService;

/////////////////////////////////////////////
/// @class TableIdGenServiceImpl
/// kr.co.gitt.kworks.service.common \n
///   ㄴ TableIdGenServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 9. 오후 12:12:22 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 일련번호 생성 구현 클래스 입니다.
///   -# 테이블 명명규칙 변경으로 인한 수정
///   -# 카이로스에서 트랜잭션 격리수준 미지원으로 인한 변경
/////////////////////////////////////////////
public class TableIdGenServiceImpl extends AbstractDataBlockIdGnrService {

	Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * ID생성을 위한 테이블 정보 디폴트는 ids임.법정동 정렬
     */
	private String table = "ids";

    /**
     * 테이블 정보에 기록되는 대상 키정보 대개의 경우는 아이디로 생성되는 테이블명을 기재함
     */
    private String tableName = "id";

    /**
     * 테이블명(구분값)에 대한 테이블 필드명 지정
     */
    private String tableNameFieldName = "sn_nm";

    /**
     * Next Id 정보를 보관하는 필드명 지정
     */
    private String nextIdFieldName = "next_sn";

    /**
     * Jdbc template
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * TransactionTemplate
     */
    private TransactionTemplate transactionTemplate;

    /**
     * 생성자
     */
    public TableIdGenServiceImpl() {
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);

        this.jdbcTemplate = new JdbcTemplate(dataSource);

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
        this.transactionTemplate.setIsolationLevelName("ISOLATION_DEFAULT");
    }

    /**
     * tableName에 대한 초기 값이 없는 경우 초기 id 값 등록 (blockSize 처리)
     *
     * @param useBigDecimals
     * @param initId
     */
    private Object insertInitId(final boolean useBigDecimals, final int blockSize) {

		logger.debug(messageSource.getMessage("debug.idgnr.init.idblock", new Object[] { tableName }, Locale.getDefault()));

        Object initId = null;

    	String insertQuery = "INSERT INTO " + table + "(" + tableNameFieldName + ", " + nextIdFieldName + ") " + "values('" + tableName + "', ?)";

        logger.debug("Insert Query : {}", insertQuery);

    	if (useBigDecimals) {
    		initId = new BigDecimal(blockSize);

    	} else {
    		initId = new Long(blockSize);
    	}

    	jdbcTemplate.update(insertQuery, initId);

    	return initId;

    }

    /**
     * blockSize 대로 ID 지정
     *
     * @param blockSize 지정되는 blockSize
     * @param useBigDecimals BigDecimal 사용 여부
     * @return BigDecimal을 사용하면 BigDecimal 아니면 long 리턴
     * @throws FdlException ID생성을 위한 블럭 할당이 불가능할때
     */
	private Object allocateIdBlock(final int blockSize, final boolean useBigDecimals) throws FdlException {

		logger.debug(messageSource.getMessage("debug.idgnr.allocate.idblock", new Object[] { new Integer(blockSize), tableName }, Locale.getDefault()));
		
		try {
			return transactionTemplate.execute(new TransactionCallback<Object>() {
				@SuppressWarnings("deprecation")
				public Object doInTransaction(TransactionStatus status) {

					Object nextId;
					Object newNextId;

					try {

						//String selectQuery = "SELECT " + nextIdFieldName + " FROM " + table + " WHERE " + tableNameFieldName + " = ? For UPDATE";
						String selectQuery = "SELECT " + nextIdFieldName + " FROM " + table + " WHERE " + tableNameFieldName + " = ?";

						logger.debug("Select Query : {}", selectQuery);

						if (useBigDecimals) {
							try {
								nextId = jdbcTemplate.queryForObject(selectQuery, new Object[] { tableName }, BigDecimal.class);
							} catch (EmptyResultDataAccessException erdae) {
								logger.error(erdae.getMessage());
								nextId = null;
							}

							if (nextId == null) { // no row
								insertInitId(useBigDecimals, blockSize);

								return new BigDecimal(0);
							}
						} else {
							try {
								nextId = jdbcTemplate.queryForLong(selectQuery, tableName);
							} catch (EmptyResultDataAccessException erdae) {
								logger.error(erdae.getMessage());
								nextId = -1L;
							}

							if ((Long) nextId == -1L) { // no row
								insertInitId(useBigDecimals, blockSize);

								return new Long(0);
							}
						}
					} catch (DataAccessException dae) {
//						dae.printStackTrace();
						logger.error("{}", dae);
						status.setRollbackOnly();
						throw new RuntimeException(new FdlException(messageSource, "error.idgnr.select.idblock", new String[] { tableName }, null));
					}

					try {
						String updateQuery = "UPDATE " + table + " SET " + nextIdFieldName + " = ?" + " WHERE " + tableNameFieldName + " = ?";

						logger.debug("Update Query : {}", updateQuery);

						if (useBigDecimals) {
							newNextId = ((BigDecimal) nextId).add(new BigDecimal(blockSize));

						} else {
							newNextId = new Long(((Long) nextId).longValue() + blockSize);
						}

						jdbcTemplate.update(updateQuery, newNextId, tableName);

						return nextId;
					} catch (DataAccessException dae) {
						logger.error(dae.getMessage());
						status.setRollbackOnly();
						throw new RuntimeException(new FdlException(messageSource, "error.idgnr.update.idblock", new String[] { tableName }, null));
					}
				}
			});
		} catch (RuntimeException re) {
			logger.error(re.getMessage());
			
			if (re.getCause() instanceof FdlException) {
				throw (FdlException)re.getCause();
			} else {
				throw re;
			}
		}
    }

    /**
     * blockSize 대로 ID 지정(BigDecimal)
     *
     * @param blockSize 지정되는 blockSize
     * @return 할당된 블럭의 첫번째 아이디
     * @throws FdlException ID생성을 위한 블럭 할당이 불가능할때
     */
	protected BigDecimal allocateBigDecimalIdBlock(int blockSize) throws FdlException {
		return (BigDecimal) allocateIdBlock(blockSize, true);
	}

    /**
     * blockSize 대로 ID 지정(long)
     *
     * @param blockSize 지정되는 blockSize
     * @return 할당된 블럭의 첫번째 아이디
     * @throws FdlException ID생성을 위한 블럭 할당이 불가능할때
     */
    protected long allocateLongIdBlock(int blockSize) throws FdlException {
        Long id = (Long) allocateIdBlock(blockSize, false);

        return id.longValue();
    }

    /**
     * ID생성을 위한 테이블 정보 Injection
     *
     * @param table config로 지정되는 정보
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * ID 생성을 위한 테이블의 키정보 ( 대개의경우는 대상 테이블명을 기재함 )
     *
     * @param tableName config로 지정되는 정보
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     *  테이블명(구분값)에 대한 테이블 필드명 정보 지정
     *
     * @param tableNameFieldName
     */
    public void setTableNameFieldName(String tableNameFieldName) {
    	this.tableNameFieldName = tableNameFieldName;
    }

    /**
     * Next Id 정보를 보관하는 필드명 정보 지정
     *
     * @param nextIdFieldName
     */
    public void setNextIdFieldName(String nextIdFieldName) {
    	this.nextIdFieldName = nextIdFieldName;
    }

}
