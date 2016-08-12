package leiguowei.qk.com.cdoj_2.net;

import java.util.ArrayList;

import leiguowei.qk.com.cdoj_2.net.data.ArticleInfoList;
import leiguowei.qk.com.cdoj_2.net.data.ContestInfoList;
import leiguowei.qk.com.cdoj_2.net.data.ProblemInfoList;


/**
 * Created by lenovo on 2016/8/7.
 */
public interface ViewHandler {
    static final int PROBLEM_lIST = 0,
            CONTEST_LIST = 1,
            ARTICLE_LIST = 2,
            PROBLEM_DETAIL = 3,
            CONTEST_DETAIL = 4,
            ARTICLE_DETAIL = 5;
    //    void showProblemList(ProblemInfoList problemInfoList);
//    void showContestList(ContestInfoList contestInfoList);
//    void showArticleList(ArticleInfoList aritcleInfoList);
    void show(int which, Object data);
}
