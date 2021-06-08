package com.fh.shop.book.param;

import com.fh.shop.common.Page;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class QueryBookParam extends Page implements Serializable {

    private String bookName;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

}
