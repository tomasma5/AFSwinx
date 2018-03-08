package com.tomscz.afswinx.component;

import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;

import java.awt.event.ActionListener;
import java.util.*;

public class AFSwinxMenu {

    private static final long serialVersionUID = 1L;

    private Map<String, AFSwinxScreenButton> menuButtons;

    public AFSwinxMenu() {
    }

    public AFSwinxMenu(Map<String, AFSwinxScreenButton> menuButtons) {
        this.menuButtons = menuButtons;
    }

    public void addMenuButton(AFSwinxScreenButton button) {
        addMenuButton(button.getKey(), button);
    }

    public void addMenuButton(String key, AFSwinxScreenButton button) {
        if(menuButtons == null) {
            menuButtons = new HashMap<>();
        }
        menuButtons.put(key, button);
    }

    public void addOnClickListenerToButton(String key, ActionListener listener) {
        if(menuButtons != null){
            AFSwinxScreenButton button = menuButtons.get(key);
            if(button != null){
                button.setOnClickListener(listener);
            }
        }
    }

    public void addOnScreenPreparedListenerToButton(String key, ScreenPreparedListener listener) {
        if(menuButtons != null){
            AFSwinxScreenButton button = menuButtons.get(key);
            if(button != null){
                button.setScreenPreparedListener(listener);
            }
        }
    }

    public void sort(){
        if(menuButtons != null){
            Map<String, AFSwinxScreenButton> sorted = new TreeMap<>(new MenuOrderComparator(menuButtons));
            sorted.putAll(menuButtons);
            menuButtons = sorted;
        }
    }

    public Set<String> getMenuButtonNames() {
        return menuButtons.keySet();
    }

    public Map<String, AFSwinxScreenButton> getMenuButtons() {
        return menuButtons;
    }

    class MenuOrderComparator implements Comparator<String>{

        HashMap<String, AFSwinxScreenButton> map = new HashMap<>();

        MenuOrderComparator(Map<String, AFSwinxScreenButton> map){
            this.map.putAll(map);
        }

        @Override
        public int compare(String s1, String s2) {
            return map.get(s1).getMenuOrder() - map.get(s2).getMenuOrder();
        }
    }
}
