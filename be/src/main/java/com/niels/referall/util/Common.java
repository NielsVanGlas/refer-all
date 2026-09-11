package com.niels.referall.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Random;

public class Common {

    private static final Random random = new Random();

    public static Pageable getPagination(int page, int size, String[] sort) {

        return PageRequest.of(
                page - 1,
                size,
                Sort.by(
                        new Sort.Order(sort[1].equals("asc")
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC,
                                sort[0])
                )
        );
    }

    public static int setPage(int page) {
        return Math.max(page, 1);
    }

}
