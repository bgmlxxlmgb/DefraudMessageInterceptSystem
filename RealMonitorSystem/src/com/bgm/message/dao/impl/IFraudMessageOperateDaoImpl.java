package com.bgm.message.dao.impl;

import com.bgm.message.Util.DBConnectionUtil;
import com.bgm.message.dao.IFraudMessageOperateDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/5/7.
 */
public class IFraudMessageOperateDaoImpl implements IFraudMessageOperateDao {
    public JSONObject queryFraudMessage(int num, int offset, int limit) {
        String sql = "select f_number,min(t_number),min(m_time),content from fraudmessage group by f_number,content limit " + num + ";";
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
                    jj.put("caller", resultSet.getString(1));
                    jj.put("callee", resultSet.getString(2));
                    jj.put("time", resultSet.getString(3));
                    jj.put("content", resultSet.getString(4));
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
    public boolean deleteOneFraudMessage(String f_number, String content) {
        String sql = "delete from fraudmessage where f_number='" + f_number + "' AND content='" + content + "'";
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
