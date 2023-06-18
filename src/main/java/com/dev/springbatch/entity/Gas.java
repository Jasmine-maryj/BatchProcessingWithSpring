package com.dev.springbatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gas_info")
public class Gas {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "PM1")
    private float pm1;

    @Column(name = "PM2")
    private float pm2;

    @Column(name = "NH3")
    private float nh3;

    @Column(name = "NO2")
    private float no2;

    @Column(name = "CO")
    private float co;

    @Column(name = "SO2")
    private float so2;

    @Column(name = "O3")
    private float o3;

    @Column(name = "PH")
    private float ph;

    @Column(name = "Status")
    private String status;
}
