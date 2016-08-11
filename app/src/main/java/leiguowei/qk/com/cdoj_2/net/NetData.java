package leiguowei.qk.com.cdoj_2.net;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import leiguowei.qk.com.cdoj_2.net.data.ArticleInfoList;
import leiguowei.qk.com.cdoj_2.net.data.ContestInfoList;
import leiguowei.qk.com.cdoj_2.net.data.ProblemInfoList;

/**
 * Created by lenovo on 2016/8/7.
 */
public class NetData {
    Activity activity;
    final static String severAddress = "http://acm.uestc.edu.cn",
            problemListUrl = severAddress + "/problem/search",
            contestListUrl = severAddress + "/contest/search",
            articleListUrl = severAddress + "/article/search",
            articleDetailUrl= severAddress + "/article/data/",
            problemDetailUrl = severAddress + "/problem/data/",
            contestDetailUrl = severAddress + "/contest/data/";
    public NetData(Activity activity){
        this.activity = activity;
    }
    public void getContestList(final int page, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "true").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(contestListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                final ContestInfoList contestInfoList = new ContestInfoList(result);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.showContestList(contestInfoList, null, null);
                    }
                });

            }
        }).start();
    }
    public void getProblemList(final int page, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "true").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(problemListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                final ProblemInfoList problemInfoList = new ProblemInfoList(result);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.showProblemList(problemInfoList, null);
                    }
                });

            }
        }).start();
    }
    public void getArticleList(final int page, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "false").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(articleListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                final ArticleInfoList articleInfoList = new ArticleInfoList(result);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.showArticleList(articleInfoList, null);
                    }
                });

            }
        }).start();
    }
    public void getArticleDetail(final int id, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetWorkTool.get(articleDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                try {
                    r = new JSONObject(result).getJSONObject("article").toString();
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                final String article = r;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.showArticleList(null, article);
                    }
                });

            }
        }).start();
    }
    public void getProblemDetail(final int id, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetWorkTool.get(problemDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                final ArrayList<String> list = new ArrayList<>(20);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    r = jsonObject.getJSONObject("contest").toString();
                    JSONArray plist = jsonObject.getJSONArray("problemList");
                    for (int i = 0; i < plist.length(); i++) {
                        list.add(plist.getJSONObject(i).toString());
                    }
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                final String contest = r;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.showContestList(null, contest, list);
                    }
                });

            }
        }).start();
    }
    public void getContestDetail(final int id, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetWorkTool.get(contestDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                try {
                    r = new JSONObject(result).getJSONObject("problem").toString();
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                final String problem = r;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.showProblemList(null, problem);
                    }
                });

            }
        }).start();
    }
}
