package com.fh.shop.book.biz;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.book.mapper.IBookImageMapper;
import com.fh.shop.book.mapper.IBookMapper;
import com.fh.shop.book.param.BookParam;
import com.fh.shop.book.param.BookUpdateParam;
import com.fh.shop.book.param.QueryBookParam;
import com.fh.shop.book.po.Book;
import com.fh.shop.book.po.BookImage;
import com.fh.shop.book.vo.BookVo;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.TableResult;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.OSSUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service("bookService")
public class IBookServiceImpl implements IBookService {

    @Resource
    private IBookMapper bookMapper;
    @Resource
    private IBookImageMapper bookImageMapper;
    @Override
    @Transactional(readOnly = true)
    public ServerResponse findBook(QueryBookParam queryBookParam) {
        //获取总条数
        Long totalCount = bookMapper.findListCount(queryBookParam);
        List<Book> bookList = bookMapper.findList(queryBookParam);

        List<BookVo> bookVoList = bookList.stream().map(x -> {
            BookVo bookVo = new BookVo();
            bookVo.setId(x.getId());
            bookVo.setBookName(x.getBookName());
            bookVo.setAuthor(x.getAuthor());
            bookVo.setPrice(x.getPrice()+"");
            bookVo.setPubTime(DateUtil.date2str(x.getPubTime(),DateUtil.Y_M));
            bookVo.setMainImage(x.getMainImage());
            return bookVo;
        }).collect(Collectors.toList());

        TableResult tableResult = new TableResult(totalCount, bookVoList);
        return ServerResponse.success(tableResult);
    }

    @Override
    public ServerResponse addBook(BookParam bookParam) {
        Book book = bookParam.getBook();
        bookMapper.insert(book);
        Long bookId = book.getId();
        List<String> subImages = bookParam.getSubImages();
        List<BookImage> bookImages = subImages.stream().map(x -> {
            BookImage bookImage = new BookImage();
            bookImage.setBookId(bookId);
            bookImage.setImagePath(x);
            return bookImage;
        }).collect(Collectors.toList());
        bookImageMapper.addImages(bookImages);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateBook(BookUpdateParam bookUpdateParam) {
        Long bookId = bookUpdateParam.getId();
        //删除主图
        List<String> deleteMainImageList = bookUpdateParam.getDeleteMainImageList();
        if(deleteMainImageList.size()>0){
            OSSUtil.deleteFiles(deleteMainImageList);
        }
        //删除子图
        List<String> deleteSubImageList = bookUpdateParam.getDeleteSubImageList();
        if(deleteSubImageList.size()>0){
            OSSUtil.deleteFiles(deleteSubImageList);
        }

        //删除数据库子图并新增
        if(bookUpdateParam.getDeleteUsbImageStatus()==1){
            QueryWrapper<BookImage> bookImageQueryWrapper = new QueryWrapper<>();
            bookImageQueryWrapper.eq("bookId",bookId);
            bookImageMapper.delete(bookImageQueryWrapper);
            List<String> subImages = bookUpdateParam.getSubImages();
            if(subImages.size()>0){
                List<BookImage> bookImages = subImages.stream().map(x -> {
                    BookImage bookImage = new BookImage();
                    bookImage.setBookId(bookId);
                    bookImage.setImagePath(x);
                    return bookImage;
                }).collect(Collectors.toList());
                bookImageMapper.addImages(bookImages);
                bookMapper.updateById(bookUpdateParam);
                //更新图书

            }

        }

//       bookMapper.updateById(book);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBook(Long id) {
        bookMapper.deleteById(id);
        QueryWrapper<BookImage> bookImageQueryWrapper = new QueryWrapper<>();
        bookImageQueryWrapper.eq("bookId",id);
        bookImageMapper.delete(bookImageQueryWrapper);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findBookById(Long id) {
        Book book = bookMapper.selectById(id);

        BookVo bookVo = new BookVo();
        bookVo.setId(book.getId());
        bookVo.setBookName(book.getBookName());
        bookVo.setAuthor(book.getAuthor());
        bookVo.setPrice(book.getPrice()+"");
        bookVo.setPubTime(DateUtil.date2str(book.getPubTime(),DateUtil.Y_M));
        bookVo.setMainImage(book.getMainImage());

        QueryWrapper<BookImage> bookImageQueryWrapper = new QueryWrapper<>();
        bookImageQueryWrapper.eq("bookId",id);
        List<BookImage> bookImageList = bookImageMapper.selectList(bookImageQueryWrapper);
        List<String> imagePathList = bookImageList.stream().map(x -> x.getImagePath()).collect(Collectors.toList());
        bookVo.setSubImages(imagePathList);
        return ServerResponse.success(bookVo);
    }

    @Override
    public ServerResponse deleteBatch(String ids) {
        if(StringUtils.isEmpty(ids)){
            ServerResponse.error();
        }
        List<Long> idList = Arrays.stream(ids.split(",")).map(x -> Long.parseLong(x)).collect(Collectors.toList());
        bookMapper.deleteBatchIds(idList);
        return ServerResponse.success();
    }
}
