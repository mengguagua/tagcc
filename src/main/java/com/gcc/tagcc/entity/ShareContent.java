package com.gcc.tagcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaocc
 * @create 2019-11-27 17:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareContent {

    private int id;
    private String urlName;
    private String icon;
    private String url;
    private String weight;
    private String collection;
    private String uid;

}
