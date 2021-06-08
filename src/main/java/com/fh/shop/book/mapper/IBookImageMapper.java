package com.fh.shop.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.book.po.BookImage;

import java.util.List;

public interface IBookImageMapper extends BaseMapper<BookImage> {


    void addImages(List<BookImage> bookImages);

}
