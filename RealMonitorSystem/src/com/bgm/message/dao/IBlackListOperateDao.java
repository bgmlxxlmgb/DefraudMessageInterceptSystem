package com.bgm.message.dao;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017/5/7.
 */
public interface IBlackListOperateDao {
    public JSONObject queryBlacklist(int num, int offset, int limit);

    public boolean delBlacklist(String blacklist);

    public boolean addBlacklist(String blacklist);
}
