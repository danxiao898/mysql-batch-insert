package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yky
 */
public class DataImport {
    public static void dispose(Connection conn, List<String> list, Integer startRows, Integer size, String sql) throws SQLException {
        conn.setAutoCommit(false);
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = startRows; i < list.size(); i++) {
            String[] split = list.get(i).split(",", -1);
            for (int j = 0; j < split.length; j++) {
                ps.setObject(j + 1, split[j]);
            }
            ps.addBatch();
            if (i % size == 0 && i != 0) {
                System.out.println("开始提交事务");
                ps.executeBatch();
                conn.commit();
                conn.setAutoCommit(false);
                ps = conn.prepareStatement(sql);
                System.out.println("提交事务");
            }
        }

        ps.executeBatch();
        conn.commit();
        ps.close();
        conn.close();
    }
}
