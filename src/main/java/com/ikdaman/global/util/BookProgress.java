package com.ikdaman.global.util;

public class BookProgress {

    private BookProgress() { }

    public static int calculateProgress(int nowPage, int totalPage) {
        if (totalPage <= 0) return 0;
        int progress = (int) ((nowPage / (double) totalPage) * 100);
        return Math.min(progress, 100); // 최대 100%
    }
}
