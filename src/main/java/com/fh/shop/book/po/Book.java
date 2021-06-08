package com.fh.shop.book.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class Book implements Serializable {

    private Long id;

    private String bookName;

    private BigDecimal price;

    private String author;
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    private Date pubTime;

    private String mainImage;


}
