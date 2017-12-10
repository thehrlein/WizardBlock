package com.tobiashehrlein.tobiswizardblock.old;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tobiashehrlein.tobiswizardblock.R;

import io.realm.Realm;

public class GameInfoSlideActivity extends AppCompatActivity
{
    // Number of pages (wizard steps)
    private static final int NUM_PAGES = 7;

    // the pager widget, which handles animation and allows swiping horizontally to access
    // previous and next wizard steps
    private ViewPager mPager;

    // the pager adapter, which provides the pages to the view pager widget
    private PagerAdapter mPagerAdapter;
    private Button buttonNext;
    private Button buttonBack;
    private ImageView dotImage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        /*if (Build.VERSION.SDK_INT >= 21)
        {
           // getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackGroundGrey));
        } */

        safeTutorialShownToRealm();

        dotImage = (ImageView) findViewById(R.id.dotImage);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setBackgroundDrawable(null);
        buttonNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mPager.getCurrentItem() < 6)
                {
                    mPager.setCurrentItem(mPager.getCurrentItem()+1);
                }
                else
                {
                    startStartActivity();
                }
            }
        });

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setBackgroundDrawable(null);
        buttonBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mPager.getCurrentItem() > 0)
                {
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                }
                else
                {
                    startStartActivity();
                }
            }
        });


        // instantiate a ViewPager and a PagerAdapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
        public void onPageSelected(int position)
            {
             //   Log.d("Test", "onPageSelected position: " + position);
                // when changing pages, reset the action bar actions since they are dependent on which page is currently
                // active. an alternative approach is to have each fragment expose actions itself (rather than the
                // activity exposing actions) but for simplicity, the activity provides the action in this sample
                invalidateOptionsMenu();
                checkButton();
                checkDotImage();
            }
        });

    }

    private void safeTutorialShownToRealm()
    {
        Realm myRealm = Realm.getDefaultInstance();

        myRealm.beginTransaction();

        RealmInt tutorialShown = myRealm.createObject(RealmInt.class);
        tutorialShown.setVal(0);

        myRealm.commitTransaction();
    }

    private void startStartActivity()
    {
        Intent intent = new Intent(GameInfoSlideActivity.this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void checkDotImage()
    {
        switch (mPager.getCurrentItem())
        {
            case 0:
                dotImage.setImageResource(R.drawable.eins);
                break;
            case 1:
                dotImage.setImageResource(R.drawable.zwei);
                break;
            case 2:
                dotImage.setImageResource(R.drawable.drei);
                break;
            case 3:
                dotImage.setImageResource(R.drawable.vier);
                break;
            case 4:
                dotImage.setImageResource(R.drawable.fuenf);
                break;
            case 5:
                dotImage.setImageResource(R.drawable.sechs);
                break;
            case 6:
                dotImage.setImageResource(R.drawable.sieben);
                break;
        }
    }

    private void checkButton()
    {
        if (mPager.getCurrentItem() < 6)
        {
            buttonNext.setText(">");
            buttonNext.setTextSize(25f);

        }
        else
        {
            buttonNext.setText(R.string.letsgo);
            buttonNext.setTextSize(15f);

        }

        if (mPager.getCurrentItem() == 0)
        {
            buttonBack.setText(R.string.gofurther);
            buttonBack.setTextSize(15f);

        }
        else
        {
            buttonBack.setText("<");
            buttonBack.setTextSize(25f);

        }
    }

    @Override
    public void onBackPressed()
    {
        if (mPager.getCurrentItem() == 0)
        {
            // if the user is currently looking at the first step, allow the system to handle
            // Back button. This calls finish() on this activity and pops the back stack
            super.onBackPressed();
        }
        else
        {
            // otherwise, select the previous step
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    // A simple pager adapter that represents 5 GameInfoSlideFragment objects, in sequence.
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return new GameInfoSlideFragment().create(position);
        }

        @Override
        public int getCount()
        {
            return NUM_PAGES;
        }
    }
}
