package cc.crss.mod.api;

import cc.crss.mod.util.JsonUtil;

public class Model {
    public String toJson() {
        return JsonUtil.gson.toJson(this);
    }

    public static Object fromJson(String json, Class<? extends Model> clazz) {
        return JsonUtil.gson.fromJson(json, clazz);
    }
}
