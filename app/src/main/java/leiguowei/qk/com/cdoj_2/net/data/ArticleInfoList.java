package leiguowei.qk.com.cdoj_2.net.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by qwe on 16-8-10.
 */
public class ArticleInfoList {
    public ArrayList<ArticleInfo> list = new ArrayList<>(20);
    public boolean result = false;
    public PageInfo pageInfo;
    public ArticleInfoList(String json){
        Log.d("TAG", "ArticleInfoList: " + json);
        if (json == null) {
            return;
        }
        try {
            JSONObject msg = new JSONObject(json);
            result = msg.getString("result").equals("success");
            pageInfo = new PageInfo(msg.getJSONObject("pageInfo"));
            JSONArray list0 = msg.getJSONArray("list");
            for (int i = 0; i < list0.length(); i++) {
                list.add(new ArticleInfo(list0.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<ArticleInfo> getArticleInfoList() {
        return list;
    }
}
