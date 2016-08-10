package net.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by qwe on 16-8-10.
 */
public class InfoList <T>{
    public ArrayList<T> list = new ArrayList<>(20);
    public boolean result = false;
    public PageInfo pageInfo;
    public InfoList(String json){
        if (json == null) {
            return;
        }
        try {
            JSONObject msg = new JSONObject(json);
            result = msg.getString("result").equals("success");
            pageInfo = new PageInfo(msg.getJSONObject("pageInfo"));
            JSONArray list0 = msg.getJSONArray("list");
            for (int i = 0; i < list0.length(); i++) {
//                list.add(T.toString());
                        //Object(list0.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public ArrayList getArticleInfoList() {
        return list;
    }
}
