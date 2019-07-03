package model;

import Helpers.LocalDateDeserializer;
import Helpers.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by oleg on 03.06.2019.
 */

@Entity
@Table(name = "ITEM")
public class Item {

    private Long id;
    private String name;
    private LocalDate dateCreated;
    private LocalDate lastUpdatedDate;
    private String description;


    public Item() {
    }


    public Item(Long id, String name, LocalDate dateCreated, LocalDate lastUpdatedDate, String description) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.lastUpdatedDate = lastUpdatedDate;
        this.description = description;
    }

    public Item(String name, LocalDate dateCreated, LocalDate lastUpdatedDate, String description) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.lastUpdatedDate = lastUpdatedDate;
        this.description = description;
    }
    public Item(String name,String description) {
        this.name = name;
        this.description = description;
    }

    public Item(Long id, String name,String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    @Id
    @SequenceGenerator(name = "ITEM_SEQ", sequenceName = "SEQ_ITEM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "DATE_CREATED")
    public LocalDate getDateCreated() {
        return dateCreated;
    }


    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "LAST_UPDATED_DATE")
    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastUpdatedDate=" + lastUpdatedDate +
                ", description='" + description + '\'' +
                '}';
    }
}
