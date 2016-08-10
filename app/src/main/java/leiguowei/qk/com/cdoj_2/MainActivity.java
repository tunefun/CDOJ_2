package leiguowei.qk.com.cdoj_2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import net.NetData;
import net.ViewHandler;
import net.data.ArticleInfo;
import net.data.ArticleInfoList;
import net.data.ContestInfo;
import net.data.ContestInfoList;
import net.data.ProblemInfo;
import net.data.ProblemInfoList;

import java.util.ArrayList;

import layout.MyContentFragment;

public class MainActivity extends AppCompatActivity implements ViewHandler {

    private LinearLayout list_main;
    private ViewPager content_main;
    private TabLayout tab_bottom;
    private ListFragment articleList_fragment;
    private MyContentFragment articleContent;
    private ListFragment problemList_fragment;
    private MyContentFragment problemContent;
    private ListFragment contestList_fragment;
    private MyContentFragment contestContent;
    private NetData netData;
    private ArrayList<String> articleList_String;
    private ArrayList<String> problemList_string;
    private ArrayList<String> contestList_string;
    private ArrayAdapter<String> articleAdapter;
    private ArrayAdapter<String> problemAdapter;
    private ArrayAdapter<String> contestAdapter;
    private FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        articleList_String = new ArrayList<>(20);
        problemList_string = new ArrayList<>(20);
        contestList_string = new ArrayList<>(20);
        articleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articleList_String);
        problemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problemList_string);
        contestAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contestList_string);
        netData = new NetData(this);
        netData.getAriticleList(1,this);
        netData.getProblemList(1,this);
        netData.getContestList(1,this);
        fragmentManager = getFragmentManager();
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews(){
        setDefaultFragment();
        list_main = (LinearLayout)findViewById(R.id.list_main);
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
                        if (articleContent == null) {
                            articleContent = new MyContentFragment();
                        }
                        return articleContent;
                    case 1:
                        if (problemContent == null) {
                            problemContent = new MyContentFragment();
                        }
                        return problemContent;
                    case 2:
                        if (contestContent == null) {
                            contestContent = new MyContentFragment();
                        }
                        return contestContent;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        tab_bottom = (TabLayout)findViewById(R.id.tablayout_bottom);
        tab_bottom.setupWithViewPager(content_main);
        tab_bottom.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tab_bottom.getTabAt(1).setIcon(R.drawable.ic_action_tiles_large);
        tab_bottom.getTabAt(2).setIcon(R.drawable.ic_action_achievement);
//        tab_bottom.getTabAt(3).setIcon(R.drawable.ic_action_user);
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        articleList_fragment = new ListFragment();
        articleList_fragment.setListAdapter(articleAdapter);
        transaction.add(R.id.list_main,articleList_fragment);
        transaction.commit();
    }

    private void setSelectionList(final int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideListFragment();
        switch (position){
            case 0:
                transaction.show(articleList_fragment);
                break;
            case 1:
                if (problemList_fragment == null) {
                    problemList_fragment = new ListFragment();
                    problemList_fragment.setListAdapter(problemAdapter);
                    transaction.add(R.id.list_main,problemList_fragment);
                }else {
                    transaction.show(problemList_fragment);
                }
                break;
            case 2:
                if (contestList_fragment == null) {
                    contestList_fragment = new ListFragment();
                    contestList_fragment.setListAdapter(contestAdapter);
                    transaction.add(R.id.list_main,contestList_fragment);
                }else {
                    transaction.show(contestList_fragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideListFragment(){
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
    public void showProblemList(ProblemInfoList problemInfoList) {
        ArrayList<ProblemInfo> infoList = problemInfoList.getProblemInfo();
        for (ProblemInfo tem : infoList) {
            problemList_string.add(tem.title);
        }
        problemAdapter.notifyDataSetChanged();

    }

    @Override
    public void showContestList(ContestInfoList contestInfoList) {
        ArrayList<ContestInfo> infoList = contestInfoList.getContestInfo();
        for (ContestInfo tem : infoList) {
            contestList_string.add(tem.title);
        }
        contestAdapter.notifyDataSetChanged();
    }

    @Override
    public void showArticleList(ArticleInfoList articleInfoList) {
        ArrayList<ArticleInfo> infoList = articleInfoList.getArticleInfoList();
        for (ArticleInfo tem : infoList) {
            articleList_String.add(tem.title);
        }
        articleAdapter.notifyDataSetChanged();
    }
}
