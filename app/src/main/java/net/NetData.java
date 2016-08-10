package net;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import net.data.ArticleInfo;
import net.data.ArticleInfoList;
import net.data.Contest;
import net.data.ContestInfoList;
import net.data.ProblemInfo;
import net.data.ProblemInfoList;

/**
 * Created by lenovo on 2016/8/7.
 */
public class NetData {
    Activity activity;
    final static String severAddress = "http://acm.uestc.edu.cn",
            problemListUrl = severAddress + "/problem/search",
            contestListUrl = severAddress + "/contest/search";
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
                        viewHandler.showContestList(contestInfoList);
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
                        viewHandler.showProblemList(problemInfoList);
                    }
                });

            }
        }).start();
    }
    public void getAriticleList(final int page, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "false").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(problemListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                final ArticleInfoList articleInfoList = new ArticleInfoList(result);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.showArticleList(articleInfoList);
                    }
                });

            }
        }).start();
    }
}
