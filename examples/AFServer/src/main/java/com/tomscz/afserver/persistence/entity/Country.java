package com.tomscz.afserver.persistence.entity;

public class Country {

    private Long id;
    private String name;
    private String shortCut;
    private boolean active;
  
    public Country(){
        super();
    }
    
    public Country(String name, String shortCut, boolean active){
        this.name = name;
        this.shortCut = shortCut;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getShortCut() {
        return shortCut;
    }

    public void setShortCut(String shortCut) {
        this.shortCut = shortCut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
