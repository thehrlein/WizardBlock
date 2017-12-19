package com.tobiashehrlein.tobiswizardblock;

import com.tobiashehrlein.tobiswizardblock.model.settings.Normal;

import org.junit.Test;

import io.realm.RealmList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Tobias Hehrlein on 19.12.2017.
 */

public class BaseSettingsTest {

    @Test
    public void getCombinedInput_0_0_0_returns_0() {
        Normal normal = new Normal();
        RealmList<Integer> list = new RealmList<>(0, 0, 0);
        assertThat(normal.getCombinedInput(list), is(0));
    }
}
