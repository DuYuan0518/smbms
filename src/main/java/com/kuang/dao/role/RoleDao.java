package com.kuang.dao.role;

import com.kuang.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
