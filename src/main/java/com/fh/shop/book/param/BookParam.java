package com.fh.shop.book.param;

import com.fh.shop.book.po.Book;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookParam {

    private Book book = new Book();

    private List<String> subImages = new ArrayList<>();
}
