package com.fh.shop.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.book.param.QueryBookParam;
import com.fh.shop.book.po.Book;

import java.util.List;

public interface IBookMapper extends BaseMapper<Book> {
    List<Book> findList(QueryBookParam queryBookParam);

    Long findListCount(QueryBookParam queryBookParam);
}
