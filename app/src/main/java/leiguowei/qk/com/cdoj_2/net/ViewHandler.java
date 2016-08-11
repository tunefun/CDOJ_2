package leiguowei.qk.com.cdoj_2.net;

import java.util.ArrayList;

import leiguowei.qk.com.cdoj_2.net.data.ArticleInfoList;
import leiguowei.qk.com.cdoj_2.net.data.ContestInfoList;
import leiguowei.qk.com.cdoj_2.net.data.ProblemInfoList;

/**
 * Created by lenovo on 2016/8/7.
 */
public interface ViewHandler {
    void showProblemList(ProblemInfoList problemInfoList, String problem);
    void showContestList(ContestInfoList contestInfoList, String contest, ArrayList prob_list);
    void showArticleList(ArticleInfoList articleInfoList, String article);
}
