package com.touchpro.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
@SuppressWarnings("all")
public class PanelSettingPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_COUNT = 2;

    public PanelSettingPagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public void destroyItem(final View view, final int n, final Object o) {
        Log.d("TEST", "remove");
        ((ViewPager) view).removeView((View) o);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(final int n) {
        final PanelSettingFragment panelSettingFragment = new PanelSettingFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt("page", n);
        panelSettingFragment.setArguments(arguments);
        return panelSettingFragment;
    }

    @Override
    public int getItemPosition(final Object o) {
        return -2;
    }

    @Override
    public CharSequence getPageTitle(final int n) {
        return "Th\u00e1ng ";
    }
}