package magio.ohmypet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import magio.ohmypet.Fragment.AdoptListFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    AdoptListFragment adoptList;
    AdoptListFragment scheduleList;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                adoptList = new AdoptListFragment();
                return adoptList;
            case 1:
                scheduleList = new AdoptListFragment();
                return scheduleList;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public AdoptListFragment getAdoptList() {
        return adoptList;
    }

    public AdoptListFragment getScheduleList() {
        return scheduleList;
    }
}