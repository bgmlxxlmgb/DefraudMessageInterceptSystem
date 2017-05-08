package com.bgm.message.dao.impl;

import com.bgm.message.Util.DBConnectionUtil;
import com.bgm.message.dao.IUserLoginOperateDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/5/7.
 */

public class IUserLoginOperateDaoImpl implements IUserLoginOperateDao {
    @Override
    public boolean loginCheck(String account, String password) {
        boolean legaluser = false;
        String sql = "select * from userinfo where account='" + account + "' and password='" + password + "'";
        try {
            Connection connection = DBConnectionUtil.ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                legaluser = true;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return legaluser;
    }
}
