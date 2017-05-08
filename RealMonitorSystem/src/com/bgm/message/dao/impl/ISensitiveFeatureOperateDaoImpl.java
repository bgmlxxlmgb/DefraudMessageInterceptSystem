package com.bgm.message.dao.impl;

import com.bgm.message.Util.DBConnectionUtil;
import com.bgm.message.dao.ISensitiveFeatureOperateDao;
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
public class ISensitiveFeatureOperateDaoImpl implements ISensitiveFeatureOperateDao {
    @Override
    public JSONObject queryFeature(int offset, int limit) {
        String sql = "select * from features order by create_time desc;";
        JSONObject result = new JSONObject();
        try {
            Connection connection = DBConnectionUtil.ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            JSONArray jsonArray = new JSONArray();
            int sum = 0;
            int index = 0;
            int start = 1;
            while (resultSet.next()) {
                index++;
                sum++;
                if (index > offset) {
                    JSONObject jj = new JSONObject();
                    jj.put("time", resultSet.getString(2));
                    jj.put("feature", resultSet.getString(1));
                    if (start <= limit) {
                        jsonArray.add(jj);
                    }
                    start++;
                }
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            result.put("total", sum);
            result.put("rows", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delFeature(String feature) {
        String sql = "delete from features where feature_word='" + feature + "';";
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
    public boolean addFeature(String feature) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        String sql = "insert into features values('" + feature + "','" + now + "');";
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
