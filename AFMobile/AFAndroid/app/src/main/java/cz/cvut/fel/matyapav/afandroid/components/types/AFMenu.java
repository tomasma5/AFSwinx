package cz.cvut.fel.matyapav.afandroid.components.types;

import android.view.View;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidScreenPreparedListener;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class AFMenu {

    private Map<String, AFScreenButton> menuButtons;

    public AFMenu() {
    }

    public AFMenu(Map<String, AFScreenButton> menuButtons) {
        this.menuButtons = menuButtons;
    }

    public void addMenuButton(AFScreenButton button) {
        addMenuButton(button.getKey(), button);
    }

    public void addMenuButton(String key, AFScreenButton button) {
        if (menuButtons == null) {
            menuButtons = new HashMap<>();
        }
        menuButtons.put(key, button);
    }

    public void addOnClickListenerToButton(String key, View.OnClickListener listener) {
        if (menuButtons != null) {
            AFScreenButton button = menuButtons.get(key);
            if (button != null) {
                button.setOnClickListener(listener);
            }
        }
    }

    public void addOnScreenPreparedListenerToButton(String key, AFAndroidScreenPreparedListener listener) {
        if (menuButtons != null) {
            AFScreenButton button = menuButtons.get(key);
            if (button != null) {
                button.setScreenPreparedListener(listener);
            }
        }
    }

    public void sort() {
        if (menuButtons != null) {
            Map<String, AFScreenButton> sorted = new TreeMap<>(new MenuOrderComparator(menuButtons));
            sorted.putAll(menuButtons);
            menuButtons = sorted;
        }
    }

    public Set<String> getMenuButtonNames() {
        return menuButtons.keySet();
    }

    public Map<String, AFScreenButton> getMenuButtons() {
        return menuButtons;
    }

    class MenuOrderComparator implements Comparator<String> {

        HashMap<String, AFScreenButton> map = new HashMap<>();

        MenuOrderComparator(Map<String, AFScreenButton> map) {
            this.map.putAll(map);
        }

        @Override
        public int compare(String s1, String s2) {
            return map.get(s1).getMenuOrder() - map.get(s2).getMenuOrder();
        }
    }
}
