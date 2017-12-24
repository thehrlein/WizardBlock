package com.tobiashehrlein.tobiswizardblock.listener;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 24.12.2017.
 */

public interface ChangePlayerListener {

    void onPlayerNameChanged(RealmList<String> newPlayerNames);
}
