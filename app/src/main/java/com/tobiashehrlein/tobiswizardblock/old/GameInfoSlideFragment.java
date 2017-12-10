package com.tobiashehrlein.tobiswizardblock.old;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tobiashehrlein.tobiswizardblock.R;

/**
 * Created by Tobias on 27.12.2015.
 */
public class GameInfoSlideFragment extends Fragment
{
    public static final String ARG_PAGE = "page";
    private int mPageNumber;
    private ImageView imageView;
    private TextView description;


    public static GameInfoSlideFragment create(int pageNumber)
    {
        GameInfoSlideFragment fragment = new GameInfoSlideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GameInfoSlideFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // inflate the layout containing a title and body text
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.screenSlideImage);
        description = (TextView) rootView.findViewById(R.id.description);


        // set the title view to show the page number
        switch (mPageNumber)
        {
            case 0:
                rootView = (ViewGroup) inflater.inflate(R.layout.screen_slide_first, container, false);
                break;
            case 1:
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide, container, false);
                imageView = (ImageView) rootView.findViewById(R.id.screenSlideImage);
                imageView.setImageResource(R.drawable.startscreen);
                description.setText(R.string.startscreentext);
                break;
            case 2:
                imageView.setImageResource(R.drawable.gamsettings);
                description.setText(R.string.gamesettingstext);
                break;
            case 3:
                imageView.setImageResource(R.drawable.playersettings);
                description.setText(R.string.playersettingstext);
                break;
            case 4:
                imageView.setImageResource(R.drawable.tipps);
                description.setText(R.string.tippstext);
                break;
            case 5:
                imageView.setImageResource(R.drawable.block);
                description.setText(R.string.blocktext);
                break;
            case 6:
                imageView.setImageResource(R.drawable.settings);
                description.setText(R.string.settingstext);
                break;
        }

        return rootView;
    }

    public int getmPageNumber()
    {
        return mPageNumber;
    }

}
