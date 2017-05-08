package com.bgm.message.dao;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017/5/7.
 */
public interface ISensitiveFeatureOperateDao {
    public JSONObject queryFeature(int offset, int limit);

    public boolean delFeature(String feature);

    public boolean addFeature(String feature);
}
