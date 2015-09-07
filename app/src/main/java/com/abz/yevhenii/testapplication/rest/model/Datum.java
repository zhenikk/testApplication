package com.abz.yevhenii.testapplication.rest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import org.kaerdan.twolevelexpandablerecyclerview.TwoLevelExpandableAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Datum")
public class Datum extends Model implements TwoLevelExpandableAdapter.DataSet<Datum,Item>,Serializable{

    @Expose
    @Column(name = "title")
    private String title;
    @Expose
    @Column(name = "icon")
    private String icon;
    @Expose
    private List<Item> items = new ArrayList<>();

    public Datum() {
        super();
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @param icon
     * The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     *
     * @return
     * The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items
     * The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public Datum getData() {
        return this;
    }

    @Override
    public List<Item> getChildren() {
        return getItems();
    }
}
