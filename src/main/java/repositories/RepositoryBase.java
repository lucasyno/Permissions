package repositories;

import java.sql.*;

/**
 * Created by lukaszgodlewski on 07.04.2017.
 */

public abstract class RepositoryBase {

    protected Connection connection;
    protected Statement createTable;
    protected PreparedStatement insert;
    protected PreparedStatement delete;
    protected PreparedStatement update;
    protected PreparedStatement get;
    protected PreparedStatement list;
    protected PreparedStatement drop;

    public RepositoryBase(Connection connection) {

        try {
            this.connection = connection;
            createTable = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null,
                    null);
            boolean tableExists = false;
            while (rs.next()) {
                if (tableName().equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            if (!tableExists)
                createTable.executeUpdate(createTableSql());

            insert = connection.prepareStatement(insertSql());
            delete = connection.prepareStatement(deleteSql());
            get = connection.prepareStatement(getSql());
            list = connection.prepareStatement(listSql());
            drop = connection.prepareStatement(dropSql());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop(){
        try {
            drop.execute();
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected String deleteSql() {
        return "DELETE FROM " + tableName() + " WHERE id=?;";
    }

    protected String getSql() {
        return "SELECT * FROM " + tableName() + " WHERE id=?;";
    }

    protected String listSql() {
        return "SELECT * FROM " + tableName();
    }

    protected String dropSql() {
        return "DROP TABLE " + tableName();
    }

    protected abstract String tableName();

    protected abstract String createTableSql();

    protected abstract String insertSql();

}
