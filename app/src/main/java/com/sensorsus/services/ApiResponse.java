package com.sensorsus.services;

import com.sensorsus.model.Content;
import com.sensorsus.model.Pageable;
import com.sensorsus.model.Sort;

import java.util.ArrayList;

public class ApiResponse {

    public ArrayList<Content> content;
    public Pageable pageable;
    public boolean last;
    public int totalPages;
    public int totalElements;
    public int size;
    public int number;
    public Sort sort;
    public boolean first;
    public int numberOfElements;
    public boolean empty;
}
