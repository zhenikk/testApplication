package com.abz.yevhenii.testapplication.rest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

@Table(name = "Content")
public class Content extends Model implements Serializable{

    @Expose
    @Column(name = "type")
    private String type;
    @Expose
    @Column(name = "content")
    private String content;

    @Column(name = "Item", onUpdate = Column.ForeignKeyAction.CASCADE,onDelete = Column.ForeignKeyAction.CASCADE)
    public Item item;

    public Content() {
        super();
    }

    public Content(String type, String content, Item item) {
        super();
        this.type = type;
        this.content = content;
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }
    public static List<Content> getAllContent(Item item){
        return new Select().from(Content.class).where("Item = ?",item.getId()).orderBy("Name ASC").execute();
    }

}