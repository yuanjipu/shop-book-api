package com.fh.shop.book.biz;


import com.fh.shop.book.param.BookParam;
import com.fh.shop.book.param.BookUpdateParam;
import com.fh.shop.book.param.QueryBookParam;
import com.fh.shop.book.po.Book;
import com.fh.shop.common.ServerResponse;

public interface IBookService {

    public ServerResponse findBook(QueryBookParam queryBookParam);

    ServerResponse addBook(BookParam bookParam);

    ServerResponse updateBook(BookUpdateParam bookUpdateParam);

    ServerResponse deleteBook(Long id);

    ServerResponse findBookById(Long id);

    ServerResponse deleteBatch(String ids);
}
