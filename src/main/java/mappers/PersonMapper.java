package mappers;

import domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lukaszgodlewski on 07.04.2017.
 */

public class PersonMapper {

    public static Person map(ResultSet rs) throws SQLException {
        Person p = new Person();
        p.setId(rs.getInt("id"));
        p.setUsername(rs.getString("username"));
        p.setPassword(rs.getString("password"));
        p.setPremium(rs.getBoolean("is_premium"));
        p.setEmail(rs.getString("email"));
        p.setAdmin(rs.getBoolean("is_admin"));
        return p;
    }
}
