package com.abz.yevhenii.testapplication.rest.model;

/**
 * Created by yevhenii
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Table(name = "Items")
public class Item extends Model
        implements Serializable {

    @Expose
    @Column(name = "item_id")
    private String id;
    @Expose
    @Column(name = "title")
    private String title;
    @Expose
    @Column(name = "category")
    private String category;
    @Expose
        private List<Content> content = new ArrayList<>();

    @Column(name = "Datum", onUpdate = Column.ForeignKeyAction.CASCADE,onDelete = Column.ForeignKeyAction.CASCADE)
    public Datum datum;

    public Item() {
        super();
    }

    public Item(String id, String title, String category, List<Content> content, Datum datum) {
        super();
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.datum = datum;
    }

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    /**
     *
     * @return
     * The id
     */
    public String getItemId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setItemId(String id) {
        this.id = id;
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
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The content
     */
    public List<Content> getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(List<Content> content) {
        this.content = content;
    }
    public List<Item> items(){return getMany(Item.class,"Datum");}
    public static List<Item> getAllItems(Datum datum){
        return new Select().from(Content.class).where("Datum = ?",datum.getId()).orderBy("Name ASC").execute();
    }
}