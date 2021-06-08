package com.fh.shop.book.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookVo implements Serializable {

    private Long id;

    private String bookName;

    private String price;

    private String author;

    private String pubTime;

    private String mainImage;

    List<String> subImages = new ArrayList<>();
}
