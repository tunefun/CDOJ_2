package leiguowei.qk.com.cdoj_2;

import android.app.FragmentTransaction;
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
import net.data.ContestInfo;
import net.data.ContestInfoList;
import net.data.ProblemInfo;
import net.data.ProblemInfoList;

import java.util.ArrayList;

import layout.MyContentFragment;
import layout.MyListFragment;

public class MainActivity extends AppCompatActivity implements ViewHandler {

    private LinearLayout list_main;
    private ViewPager content_main;
    private TabLayout tab_bottom;
    private MyListFragment noticeList;
    private MyContentFragment noticeContent;
    private MyListFragment problemList;
    private MyContentFragment problemContent;
    private MyListFragment contestList;
    private MyContentFragment contestContent;
    private FragmentTransaction transaction;
    private NetData netData;
    private ProblemInfoList problemInfoList;
    private ContestInfoList contestInfoList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        netData = new NetData(this);
        netData.getProblemList(1,this);
        netData.getContestList(1,this);
        initViews();
        transaction = getFragmentManager().beginTransaction();

    }

    private void initViews(){
        list_main = (LinearLayout)findViewById(R.id.list_main);
        content_main = (ViewPager)findViewById(R.id.content_main);
        content_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: setSelection(0);break;
                    case 1: setSelection(1);break;
                    case 2: setSelection(2);break;
//                    default: setSelection(3);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        content_main.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        if (noticeContent == null) {
                            noticeContent = new MyContentFragment();
                        }
                        return noticeContent;
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

    private void setSelection(int position) {
        hideListFragment();
        switch (position){
            case 0:
                if (noticeList == null) {
                    noticeList = new MyListFragment();
                    transaction.add(R.id.list_main,noticeList);
                }else {
                    transaction.show(noticeList);
                }
                break;
            case 1:
                if (problemList == null) {
                    problemList = new MyListFragment();
                    problemList.setListAdapter(getArrayAdapter(position));
                    Log.d("321","process");
                    transaction.add(R.id.list_main,problemList);
                }else {
                    transaction.show(problemList);
                }
                break;
            case 2:
                if (contestList == null) {
                    contestList = new MyListFragment();
                    contestList.setListAdapter(getArrayAdapter(position));
                    transaction.add(R.id.list_main,contestList);
                }else {
                    transaction.show(contestList);
                }
                break;
        }
    }

    private void hideListFragment(){
        if (noticeList != null) {
            transaction.hide(noticeList);
        }
        if (problemList != null) {
            transaction.hide(problemList);
        }
        if (contestList != null) {
            transaction.hide(contestList);
        }
    }

    @Override
    public void showProblemList(ProblemInfoList problemInfoList) {
        this.problemInfoList = problemInfoList;
    }

    @Override
    public void showContestList(ContestInfoList contestInfoList) {
        this.contestInfoList = contestInfoList;
    }

    private ArrayAdapter<String> getArrayAdapter(int position) {
        switch (position) {
            case 0:break;
            case 1:
                ArrayList<ProblemInfo> problemList = problemInfoList.getProblemInfo();
                ArrayList<String> arraylist = new ArrayList<>(problemList.size());
                for (ProblemInfo tem: problemList) {
                    arraylist.add(tem.title);
                    Log.d("123",tem.title);
                }
                return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraylist);
            case 2:
                ArrayList<ContestInfo> contestList = contestInfoList.getContestInfo();
                ArrayList<String> arraylist1 = new ArrayList<>(contestList.size());
                for (ContestInfo tem : contestList) {
                    arraylist1.add(tem.title);
                }
                return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraylist1);
            default:return null;
        }
        return null;
    }
}
