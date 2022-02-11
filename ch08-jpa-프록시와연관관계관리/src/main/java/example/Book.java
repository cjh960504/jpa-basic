package example;

import javax.persistence.*;

@Entity
@DiscriminatorValue("A")
//@PrimaryKeyJoinColumn(name = "BOOK_ID", referencedColumnName = "ITEM_ID") SINGLE_TABLE 이기 떄문에, 필요없을듯?
public class Book extends Item{
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

