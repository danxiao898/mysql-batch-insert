import dao.BaseDao;
import util.DataImport;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * desc: TODO
 *
 * @author : yky
 * @version : 1.0
 * @date : Created in 2022/4/22 11:08 下午
 */
public class Application {

    static final int INSERT_NUMBER = 1000000;
    static final int BATCH_SIZE = 200000;

    public static void main(String[] args) {
        //   通过JDBCUtil工具类获取数据库连接对象
        Connection conn = BaseDao.getConn("localhost", "23306", "test", "root", "123");

        List<String> list = new ArrayList<>();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i <= INSERT_NUMBER; i++) {
            String builder = UUID.randomUUID() + "," +
                    UUID.randomUUID() + "," +
                    dateFormat.format(new Date());
            list.add(builder);
        }
        System.out.println("生成数据成功");
        String sql = "insert into batch_insert(uuid, uuid1, date_time) values(?,?,?)";
        long start = System.currentTimeMillis();
        try {
            DataImport.dispose(conn, list, 0, BATCH_SIZE, sql);
            long end = System.currentTimeMillis();
            System.out.println("成功导入" + list.size() + "条数据！！时长：" + (end - start) / 1000 + "秒");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
