package com.roman.ws.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 3/24/16.
 */
@Entity
@Data
public class Greeting {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
}
