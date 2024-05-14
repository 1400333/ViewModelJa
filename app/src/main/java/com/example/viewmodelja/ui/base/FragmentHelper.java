package com.example.viewmodelja.ui.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.viewmodelja.R;

public class FragmentHelper {
    private FragmentManager m_fm = null;

    public FragmentHelper(FragmentManager fm) {
        m_fm = fm;
    }

    public BaseFragment getTopFragment() {
        if (m_fm == null) {
            return null;
        }

        int iTopChildIdx = getFragmentCount() - 1;

        if (iTopChildIdx < 0) {
            return null;
        }

        FragmentManager.BackStackEntry bsEntry = m_fm.getBackStackEntryAt(iTopChildIdx);

        return (BaseFragment) m_fm.findFragmentByTag(bsEntry.getName());
    }

    public void replaceFragment(String strFragmentName) {
        try {
            BaseFragment fragment = (BaseFragment) Class.forName(strFragmentName).newInstance();

            replaceFragment(fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(Fragment fragment) {
        replaceFragmentOnStack(fragment);
    }

    public void addFragment(Fragment fragment) {
        if (getFragmentCount() == 0) {
            replaceFragmentOnStack(fragment);
        } else {
            addFragmentOnStack(fragment);
        }
    }

    public int getFragmentCount() {
        if (m_fm == null) {
            return 0;
        }

        return m_fm.getBackStackEntryCount();
    }

    public void popFragment() {
        int iTopChildIdx = getFragmentCount() - 1;

        if (iTopChildIdx < 0) {
            return;
        }

        FragmentManager.BackStackEntry bsEntry = m_fm.getBackStackEntryAt(iTopChildIdx);
        Fragment fragment = m_fm.findFragmentByTag(bsEntry.getName());

        // IMPORTANT! DO NOT REMOVE!
        // customized lifecycle --> hide fragment before pop it
        m_fm.beginTransaction().hide(fragment).commit();
        //----------------------------------------------------------------

        m_fm.popBackStackImmediate(bsEntry.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

        showTopFragment();
    }

    public void popFragmentUpTo(String strFragmentName) {
        FragmentManager.BackStackEntry bsEntry;
        Fragment fragment;

        for (int i = getFragmentCount() - 1; i >= 0; i--) {
            bsEntry = m_fm.getBackStackEntryAt(i);

            if (bsEntry.getName().compareToIgnoreCase(strFragmentName) == 0) {
                break;
            }

            fragment = m_fm.findFragmentByTag(bsEntry.getName());

            // IMPORTANT! DO NOT REMOVE!
            // customized lifecycle --> hide fragment before pop it
            m_fm.beginTransaction().hide(fragment).commit();
            //----------------------------------------------------------------

            m_fm.popBackStackImmediate(bsEntry.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        showTopFragment();
    }

    private void showTopFragment() {
        int iTopChildIdx = getFragmentCount() - 1;

        if (iTopChildIdx < 0) {
            return;
        }

        FragmentManager.BackStackEntry bsEntry = m_fm.getBackStackEntryAt(iTopChildIdx);
        Fragment fragment = m_fm.findFragmentByTag(bsEntry.getName());

        m_fm.beginTransaction().show(fragment).commit();
    }

    private void replaceFragmentOnStack(Fragment fragment) {
        String strName = fragment.getClass().getName();
        Fragment tagfragment = m_fm.findFragmentByTag(strName);

        if (tagfragment == null) {
            m_fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.rlContent, fragment, strName)
                    .addToBackStack(strName)
                    .commit();
        } else {
            m_fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.rlContent, fragment, strName)
                    .commit();
        }
    }

    private void addFragmentOnStack(Fragment fragment) {
        hideAllFragment();

        String strName = fragment.getClass().getName();

        m_fm.beginTransaction()
                .add(R.id.rlContent, fragment, strName)
                .addToBackStack(strName)
                .commit();
    }

    public void hideAllFragment() {
        FragmentManager.BackStackEntry bsEntry;
        Fragment fragment;

        for (int i = getFragmentCount() - 1; i >= 0; i--) {
            bsEntry = m_fm.getBackStackEntryAt(i);
            fragment = m_fm.findFragmentByTag(bsEntry.getName());

            m_fm.beginTransaction().hide(fragment).commit();
        }
    }
}
