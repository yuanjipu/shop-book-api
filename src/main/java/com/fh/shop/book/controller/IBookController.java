package com.fh.shop.book.controller;


import com.fh.shop.book.biz.IBookService;
import com.fh.shop.book.param.BookParam;
import com.fh.shop.book.param.BookUpdateParam;
import com.fh.shop.book.param.QueryBookParam;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.OSSUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@CrossOrigin
@RestController
@RequestMapping("/api/book")
public class IBookController {

    @Resource(name = "bookService")
    private IBookService bookService;

    @GetMapping("/findBook")
    public ServerResponse findBook(QueryBookParam queryBookParam){
        return bookService.findBook(queryBookParam);
    }

    @PostMapping("/addBook")
    public ServerResponse addBook(@RequestBody BookParam bookParam){
        return bookService.addBook(bookParam);
    }

    @GetMapping("/{id}")
    public ServerResponse findBookById(@PathVariable Long id){
        return bookService.findBookById(id);
    }

    @PostMapping("/updateBook")
    public ServerResponse updateBook(@RequestBody BookUpdateParam bookUpdateParam){
        return bookService.updateBook(bookUpdateParam);
    }

    @DeleteMapping("/deleteBook")
    public ServerResponse deleteBook(Long id){
        return bookService.deleteBook(id);
    }


    @PostMapping("/deleteBatch")
    public ServerResponse deleteBatch(String ids){
        return bookService.deleteBatch(ids);
    }
    @PostMapping("/deleteMainImage")
    public ServerResponse deleteMainImage(String imagePath){
        OSSUtil.deleteFile(imagePath);
        return ServerResponse.success();
    }
    @RequestMapping(value = "/uploadImageOss",method = RequestMethod.POST)
    public @ResponseBody ServerResponse uploadImageOss(MultipartFile file, HttpServletRequest request){
        try {
            //IO流读取文件
            InputStream inputStream = file.getInputStream();
            //获取文件原名
            String originalFilename = file.getOriginalFilename();

            String uploadFileName = OSSUtil.upload(originalFilename, inputStream);

            return ServerResponse.success(uploadFileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(value = "/uploadImagesOss",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadImages(@RequestParam MultipartFile[] file, HttpServletRequest request){


        try {
            StringBuffer uploadPaths = new StringBuffer();
             for (MultipartFile imageFile : file) {
                //IO流读取文件
                InputStream inputStream = imageFile.getInputStream();
                //获取文件原名
                String originalFilename = imageFile.getOriginalFilename();

                String uploadFileName = OSSUtil.upload(originalFilename, inputStream);
//                拼接路径响应给前台
                uploadPaths.append(",").append(uploadFileName);
            }
            return ServerResponse.success(uploadPaths);

        } catch (IOException e) {
//                遇到处理异常会将异常捕获导致我们自己的异常统一处理的方法捕获不到异常
//                这样我们就要再往上抛一个异常让我们的异常统一处理方法捕获
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
}
