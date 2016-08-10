package net;

import net.data.ArticleInfoList;
import net.data.ContestInfoList;
import net.data.ProblemInfo;
import net.data.ProblemInfoList;

/**
 * Created by lenovo on 2016/8/7.
 */
public interface ViewHandler {
    void showProblemList(ProblemInfoList problemInfoList);
    void showContestList(ContestInfoList contestInfoList);
    void showArticleList(ArticleInfoList aritcleInfoList);
}
