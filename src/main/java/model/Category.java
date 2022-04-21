package model;

import java.sql.Date;

public class Category {
    private int categoryId;
    private String name;
    private Date createdDate;

    public Category() {
    }

    public Category(int categoryId, String name, Date createdDate) {
        this.categoryId = categoryId;
        this.name = name;
        this.createdDate = createdDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
