package com.bgm.message.dao;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017/5/7.
 */
public interface IFraudMessageOperateDao {
    public JSONObject queryFraudMessage(int num, int offset, int limit);

    public boolean deleteOneFraudMessage(String f_number, String content);
}
