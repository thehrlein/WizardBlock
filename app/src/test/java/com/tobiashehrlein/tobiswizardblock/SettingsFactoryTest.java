package com.tobiashehrlein.tobiswizardblock;

import com.tobiashehrlein.tobiswizardblock.model.settings.Normal;
import com.tobiashehrlein.tobiswizardblock.model.settings.NormalAnniversary;
import com.tobiashehrlein.tobiswizardblock.model.settings.NormalButFirstRound;
import com.tobiashehrlein.tobiswizardblock.model.settings.NormalButFirstRoundAnniversary;
import com.tobiashehrlein.tobiswizardblock.model.settings.SettingsFactory;
import com.tobiashehrlein.tobiswizardblock.model.settings.TippsEqualStitches;
import com.tobiashehrlein.tobiswizardblock.model.settings.TippsEqualStitchesAnniversary;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;


/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class SettingsFactoryTest {

    private static SettingsFactory settingsFactory;

    @BeforeClass
    public static void initTestClass() {
        settingsFactory = new SettingsFactory();
    }

    @Test
    public void getSettings_true_false_true_returns_tippsEqualStitchesAnniversary() {
        assertThat(settingsFactory.getSettings(true, false, true), instanceOf(TippsEqualStitchesAnniversary.class));
    }

    @Test
    public void getSettings_true_false_false_returns_tippsEqualStitches() {
        assertThat(settingsFactory.getSettings(true, false, false), instanceOf(TippsEqualStitches.class));
    }

    @Test
    public void getSettings_false_true_true_returns_normalButFirstRoundAnniversary() {
        assertThat(settingsFactory.getSettings(false, true, true), instanceOf(NormalButFirstRoundAnniversary.class));
    }

    @Test
    public void getSettings_false_false_true_returns_normalAnniversary() {
        assertThat(settingsFactory.getSettings(false, false, true), instanceOf(NormalAnniversary.class));
    }

    @Test
    public void getSettings_false_true_false_returns_normalButFirstRound() {
        assertThat(settingsFactory.getSettings(false, true, false), instanceOf(NormalButFirstRound.class));
    }

    @Test
    public void getSettings_false_false_false_returns_normalButFirstRound() {
        assertThat(settingsFactory.getSettings(false, false, false), instanceOf(Normal.class));
    }
}
