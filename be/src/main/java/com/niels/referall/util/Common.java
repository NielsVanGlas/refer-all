package com.niels.referall.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
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

    public static String calculateSha256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

}
