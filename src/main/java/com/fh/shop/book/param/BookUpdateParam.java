package com.fh.shop.book.param;

import com.fh.shop.book.po.Book;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class BookUpdateParam extends Book {

    private List<String> deleteMainImageList = new ArrayList<>();

    private List<String> deleteSubImageList = new ArrayList<>();

    private int deleteUsbImageStatus;

    private List<String> subImages= new ArrayList<>();
}
