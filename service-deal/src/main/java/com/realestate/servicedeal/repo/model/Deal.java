package com.realestate.servicedeal.repo.model;

import com.realestate.servicedeal.api.enums.DealStatusEnum;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "deal")
public class Deal {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "estate")
    private long estate;

    @Column(name = "owner")
    private long realtor;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DealStatusEnum status;

    public Deal() {
    }

    public Deal(long estate, long realtor) {
        this.estate = estate;
        this.realtor = realtor;
        this.time = new Timestamp(System.currentTimeMillis());
        this.status = DealStatusEnum.STRIKED;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getEstateId() {
        return estate;
    }

    public long getRealtorId() {
        return realtor;
    }

    public Timestamp getTime() {
        return time;
    }

    public DealStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DealStatusEnum status) {
        this.status = status;
    }

}
