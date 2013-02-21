package com.mareksebera.ab_bug;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import com.mareksebera.ab_bug.R;

public class MainActivity extends FragmentActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	public static class DummySectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";
		public static final int MENU_SEARCH = -1;
		protected MenuItem searchItem;
		protected SearchView mSearchView;

		public DummySectionFragment() {
			setHasOptionsMenu(true);
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			mSearchView = new SearchView(getActivity().getActionBar()
					.getThemedContext());
			searchItem = menu
					.add(Menu.NONE, MENU_SEARCH, Menu.NONE, "Search")
					.setIcon(android.R.drawable.ic_menu_search)
					.setActionView(mSearchView)
					.setShowAsActionFlags(
							MenuItem.SHOW_AS_ACTION_ALWAYS
									| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			super.onCreateOptionsMenu(menu, inflater);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}

		@Override
		public void setUserVisibleHint(boolean isVisibleToUser) {
			super.setUserVisibleHint(isVisibleToUser);
			if (isVisibleToUser) {
				ActionBar ab = getActivity().getActionBar();
				int mode = 0;
				switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
				default:
				case 1:
					mode = ActionBar.NAVIGATION_MODE_LIST;
					ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
							getActivity(),
							android.R.layout.simple_spinner_dropdown_item,
							new String[] { "A", "B", "C" });
					ab.setListNavigationCallbacks(spinnerArrayAdapter,
							new OnNavigationListener() {

								@Override
								public boolean onNavigationItemSelected(
										int itemPosition, long itemId) {
									return false;
								}
							});
					break;
				case 2:
				case 3:
					mode = ActionBar.NAVIGATION_MODE_STANDARD;
					break;
				}
				getActivity().getActionBar().setNavigationMode(mode);
			} else {
				if (getActivity() != null
						&& getActivity().getActionBar() != null)
					getActivity().getActionBar().setNavigationMode(
							ActionBar.NAVIGATION_MODE_STANDARD);
			}
		}
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

}
