package com.bgm.message.dao.impl;

import com.bgm.message.Util.DBConnectionUtil;
import com.bgm.message.dao.IBlackListOperateDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/7.
 */
public class IBlackListOperateDaoImpl implements IBlackListOperateDao {
    @Override
    public JSONObject queryBlacklist(int num, int offset, int limit) {
        String sql = "select phone,min(create_time) as time from black_list group by phone order by time desc limit " + num + ";";
        JSONObject result = new JSONObject();
        try {
            Connection connection = DBConnectionUtil.ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            JSONArray jsonArray = new JSONArray();
            int index = 0;
            int start = 1;
            while (resultSet.next()) {
                index++;
                if (index > offset) {
                    JSONObject jj = new JSONObject();
                    jj.put("phone", resultSet.getString(1));
                    jj.put("time", resultSet.getString(2));
                    if (start <= limit) {
                        jsonArray.add(jj);
                    }
                    start++;
                }
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            result.put("total", num);
            result.put("rows", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delBlacklist(String blacklist) {
        String sql = "delete from black_list where phone='" + blacklist + "';";
        boolean result = false;
        try {
            Connection connection = DBConnectionUtil.ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int delResult = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            if (delResult == 1) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addBlacklist(String blacklist) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        String sql = "insert into black_list values('" + blacklist + "','" + now + "');";
        boolean result = false;
        try {
            Connection connection = DBConnectionUtil.ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int delResult = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            if (delResult == 1) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
