package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public abstract class SearchEvent {

    public abstract void onSearchStart();

    public abstract void onSearchFinished();

}
