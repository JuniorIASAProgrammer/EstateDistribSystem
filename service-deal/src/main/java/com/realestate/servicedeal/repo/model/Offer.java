package com.realestate.servicedeal.repo.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Offer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "estate_id")
    private long estate;

    @Column(name = "realtor_id")
    private long realtor;

    @Column(name = "date")
    private Timestamp datetime;

    public Offer() {
    }

    public Offer(long estate, long realtor) {
        this.estate = estate;
        this.realtor = realtor;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getEstate() {
        return estate;
    }

    public void setEstate(long estate) {
        this.estate = estate;
    }

    public long getRealtor() {
        return realtor;
    }

    public void setRealtor(long realtor) {
        this.realtor = realtor;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

}
