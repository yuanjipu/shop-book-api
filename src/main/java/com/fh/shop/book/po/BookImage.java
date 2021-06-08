package com.fh.shop.book.po;

import lombok.Data;

@Data
public class BookImage {

    private Long id;

    private String imagePath;

    private Long bookId;
}
