package leiguowei.qk.com.cdoj_2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import leiguowei.qk.com.cdoj_2.net.NetData;
import leiguowei.qk.com.cdoj_2.net.ViewHandler;
import leiguowei.qk.com.cdoj_2.net.data.ArticleInfo;
import leiguowei.qk.com.cdoj_2.net.data.ArticleInfoList;
import leiguowei.qk.com.cdoj_2.net.data.ContestInfo;
import leiguowei.qk.com.cdoj_2.net.data.ContestInfoList;
import leiguowei.qk.com.cdoj_2.net.data.ProblemInfo;
import leiguowei.qk.com.cdoj_2.net.data.ProblemInfoList;

import java.util.ArrayList;

import leiguowei.qk.com.cdoj_2.layout.MyContentFragment;
import leiguowei.qk.com.cdoj_2.layout.MyListFragment;

public class MainActivity extends AppCompatActivity implements ViewHandler {

    private LinearLayout list_main;
    private ViewPager content_main;
    private TabLayout tab_bottom;
    private MyListFragment articleList_fragment;
    private MyListFragment problemList_fragment;
    private MyListFragment contestList_fragment;
    private MyContentFragment[] content_fragment = new MyContentFragment[3];
    private NetData netData;
    private FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        netData = new NetData(this);
        fragmentManager = getFragmentManager();

        if (savedInstanceState == null){
            for (int i = 0; i != 3; ++i) {
                content_fragment[i] = new MyContentFragment();
            }
            initViewsFirst();
        }else {
            for (int i = 0; i != 3; ++i) {
                content_fragment[i] = (MyContentFragment)fragmentManager.findFragmentByTag(savedInstanceState.getString("content_fragment"+i));
            }
            initViews();
        }
    }

    private void initViews() {
        articleList_fragment = (MyListFragment) fragmentManager.findFragmentByTag("articleList");
        problemList_fragment = (MyListFragment) fragmentManager.findFragmentByTag("problemList");
        contestList_fragment = (MyListFragment) fragmentManager.findFragmentByTag("contestList");
        initViewsFirst();
    }

    private void initViewsFirst(){
        content_main = (ViewPager)findViewById(R.id.content_main);
        content_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: setSelectionList(0);break;
                    case 1: setSelectionList(1);break;
                    case 2: setSelectionList(2);break;
//                    default: setSelection(3);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        content_main.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return content_fragment[0];
                    case 1:
                        return content_fragment[1];
                    case 2:
                        return content_fragment[2];
                    default:
                        return null;
                }
            }
            @Override
            public int getCount() {
                return 3;
            }
        });

        setDefaultFragment();
        tab_bottom = (TabLayout)findViewById(R.id.tablayout_bottom);
        tab_bottom.setupWithViewPager(content_main);
        tab_bottom.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tab_bottom.getTabAt(1).setIcon(R.drawable.ic_action_tiles_large);
        tab_bottom.getTabAt(2).setIcon(R.drawable.ic_action_achievement);
//        tab_bottom.getTabAt(3).setIcon(R.drawable.ic_action_user);
    }

    private void setDefaultFragment() {
        if (articleList_fragment == null) {
            articleList_fragment = (new MyListFragment()).createAdapter(this);
            netData.getAriticleList(1,this);
        }
        if (!articleList_fragment.isAdded()){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.list_main,articleList_fragment,"articleList");
            transaction.commit();
        }
    }

    private void setSelectionList(final int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideMyListFragment();
        switch (position){
            case 0:
                transaction.show(articleList_fragment);
                break;
            case 1:
                if (problemList_fragment == null) {
                    problemList_fragment = (new MyListFragment()).createAdapter(this);
                    netData.getProblemList(1,this);
                }else {
                    if (!problemList_fragment.isAdded()){
                        transaction.add(R.id.list_main,problemList_fragment,"problemList");
                    }
                    transaction.show(problemList_fragment);
                }
                break;
            case 2:
                if (contestList_fragment == null) {
                    contestList_fragment = (new MyListFragment()).createAdapter(this);
                    netData.getContestList(1,this);
                }else {
                    if (!contestList_fragment.isAdded()) {
                        transaction.add(R.id.list_main,contestList_fragment,"contestList");
                    }
                    transaction.show(contestList_fragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideMyListFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (articleList_fragment != null) {
            transaction.hide(articleList_fragment);
        }
        if (problemList_fragment != null) {
            transaction.hide(problemList_fragment);
        }
        if (contestList_fragment != null) {
            transaction.hide(contestList_fragment);
        }
        transaction.commit();
    }

    @Override
    public void showArticleList(ArticleInfoList articleInfoList, String article) {
        ArrayList<ArticleInfo> infoList = articleInfoList.getArticleInfoList();
        for (ArticleInfo tem : infoList) {
            articleList_fragment.addToList(tem.title);
        }
        articleList_fragment.notifydataSetChanged();
    }

    @Override
    public void showProblemList(ProblemInfoList problemInfoList, String problem) {
        ArrayList<ProblemInfo> infoList = problemInfoList.getProblemInfo();
        for (ProblemInfo tem : infoList) {
            problemList_fragment.addToList(tem.title);
        }
        problemList_fragment.notifydataSetChanged();
    }

    @Override
    public void showContestList(ContestInfoList contestInfoList, String contest, ArrayList prob_list) {
        ArrayList<ContestInfo> infoList = contestInfoList.getContestInfo();
        for (ContestInfo tem : infoList) {
            contestList_fragment.addToList(tem.title);
        }
        contestList_fragment.notifydataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        for (int i = 0; i != 3; ++i) {
            outState.putString("content_fragment"+i,content_fragment[i].getTag());
        }
    }
}
