package com.tobiashehrlein.tobiswizardblock;



        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.preference.Preference;
        import android.preference.PreferenceFragment;
        import android.preference.PreferenceManager;
        import android.preference.SwitchPreference;
        import android.support.annotation.Nullable;
        import android.support.v4.app.FragmentActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.ImageView;
        import android.widget.TextView;


public class Settings extends AppCompatActivity
{

    private static Context context;
    public static boolean displayActive;
    private TextView email;
    private ImageView facebookLike;
    public static String FACEBOOK_URL = "https://www.facebook.com/wizard.block";
    public static String FACEBOOK_PAGE_ID = "WizardBlock";


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;


        setContentView(R.layout.settings);

        setTitle(R.string.settings);

        checkDisplayActiveStatus();
        setDisplayAlwaysActive(displayActive);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.relativeForPref, new SettingsFragment())
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = (TextView) findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendEmail();
            }
        });

        facebookLike = (ImageView) findViewById(R.id.facebookLike);
        facebookLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/wizard.block"));
                startActivity(browserIntent);
            }
        });
    }


    private void sendEmail()
    {
        String text = getString(R.string.hello_tobias);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Wizard Block");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"tobi.applications@gmail.com"});
        Intent mailer = Intent.createChooser(intent, null);
        startActivity(mailer);
    }

    // set display always active if checked in GameSettings (called by onCreate)
    private void setDisplayAlwaysActive(boolean value)
    {
        if (value)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else
        {
            getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public static void safeDisplayActitveStatus(boolean display)
    {
        displayActive = display;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("displayActive", displayActive);
        editor.apply();

        Log.d("Settings", "safeDisplayActiveStatus displayActive: " + displayActive);
    }

    private static void checkDisplayActiveStatus()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        displayActive = preferences.getBoolean("displayActive", false);
        Log.d("Settings", "checkDisplayActiveStatus displayActive: " + displayActive);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    public static class AppInfo extends FragmentActivity
    {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.settings);
        }
    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener
    {
        private SwitchPreference pref_switch_display_active;
        private boolean displayActive;


        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);



            pref_switch_display_active = (SwitchPreference) findPreference("pref_switch_display_on");
            pref_switch_display_active.setOnPreferenceClickListener(this);

            Preference pref_geo_tutorial = findPreference("tutorial");
            pref_geo_tutorial.setOnPreferenceClickListener(this);

            displayActive = Settings.displayActive;

            if (displayActive)
            {
                pref_switch_display_active.setChecked(true);
            }
            else
            {
                pref_switch_display_active.setChecked(false);
            }
        }

        @Override
        public boolean onPreferenceClick(Preference preference)
        {
            String key = preference.getKey();
            Log.d("Settings", "clicked: " + key);

            switch (key)
            {
                case "pref_switch_display_on":
                    displayActive = !displayActive;
                    Settings.setDisplayActive(displayActive);
                    pref_switch_display_active.setChecked(displayActive);
                    Log.d("Settings", "onPrefClick displayAcitve: " + displayActive);
                    Settings.safeDisplayActitveStatus(displayActive);
                    break;
                case "tutorial":
                    showTutorial();
                    break;
            }
            return false;
        }

        private void showTutorial()
        {
            Intent intent = new Intent(getActivity(), GameInfoSlideActivity.class);
            startActivity(intent);
        }

    }

    public static boolean isDisplayActive()
    {
        return displayActive;
    }

    public static void setDisplayActive(boolean displayActive)
    {
        Settings.displayActive = displayActive;
    }
}